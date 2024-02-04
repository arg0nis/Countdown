package com.arg0nis.countdown;

import com.arg0nis.countdown.expansions.CDPH;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.arg0nis.countdown.Countdown.a;


public final class CountdownPlugin extends JavaPlugin {
    public static CountdownPlugin getInstance() {
        return getPlugin(CountdownPlugin.class);
    }
    @Override
    public void onEnable() {
        // Plugin startup logic
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new CDPH(this).register();
            Bukkit.getLogger().info("Hook Registered");
        } else
            Bukkit.getLogger().info("Hook registration failed!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(label.equalsIgnoreCase("countdown")) {
            if(args.length != 1) {
                sender.sendMessage(a("&cIncorrect usage!"));
                return false;
            }

            String arg = args[0];

            if(List.of("pause", "resume", "stop").contains(arg)) {
                Countdown current;
                switch (arg) {
                    case "pause":
                        current = Countdown.current;
                        if(current == null) {
                            sender.sendMessage(a("&cThere is no countdown ongoing!"));
                            return false;
                        }
                        if(current.isPaused()) {
                            sender.sendMessage(a("&cCountdown already paused at " + Countdown.getStaticCurrent()));
                            return false;
                        } else {
                            current.pause();
                            sender.sendMessage(a("&cPaused successfully at " + Countdown.getStaticCurrent()));
                            return true;
                        }

                    case "resume" :
                        current = Countdown.current;
                        if(current==null) {
                            sender.sendMessage(a("&cThere is no countdown ongoing!"));
                            return false;
                        }
                        if(!current.isPaused()) {
                            sender.sendMessage(a("&cCountdown is not paused and currently at " + Countdown.getStaticCurrent()));
                            return false;
                        } else {
                            current.resume();
                            sender.sendMessage(a("&aResumed successfully at " + Countdown.getStaticCurrent()));
                            return true;
                        }

                    case "stop":
                        current = Countdown.current;
                        if(current==null) {
                            sender.sendMessage(a("&cThere is no countdown ongoing!"));
                            return false;
                        }
                        current.stop();
                        sender.sendMessage(a("&aCountdown stopped successfully"));
                        break;
                }
                return true;
            }
            long duration = 0;
            try {
                duration = Long.parseLong(args[0]);
            } catch (Exception e) {
                sender.sendMessage(a("&cInteger needed (unit in seconds)"));
                return false;
            }

            if(duration==0)
                return false;

            Countdown cd = new Countdown(duration);
            cd.start();

        }
        return true;
    }

}
