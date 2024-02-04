package com.arg0nis.countdown;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Countdown {
    public static Countdown current = null;

    BukkitRunnable r;
    private long count = 0;
    private long duration;
    private boolean paused = false;

    public Countdown(long duration) {
        this.duration = duration;
        current = this;
        r = new BukkitRunnable() {
            @Override
            public void run() {
                if(count <= duration) {
                    displayCountdown();
                    count++;
                } else {
                    this.cancel();
                    onCountdownOver();
                    current = null;
                }
            }
        };
    }

    public void onCountdownOver() {
       for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
          onlinePlayer.sendMessage(ChatColor.GOLD+"Countdown is over!");
       }
    }

    public void start() {
        if(r != null)
            r.runTaskTimer(CountdownPlugin.getInstance(), 0, 20L);
    }

    public void stop() {
        r.cancel();
        duration = 0;
        count = 0;
        r = null;
        paused = false;
        current = null;
    }

    public void pause() {
        paused = true;

        new BukkitRunnable() {
            @Override
            public void run() {
                if(!isPaused())
                    cancel();
                displayCountdown();
            }
        }.runTaskTimer(CountdownPlugin.getInstance(), 0, 20L);

        count--;
        r.cancel();
    }

    public void resume() {
        paused = false;
        r = new BukkitRunnable() {
            @Override
            public void run() {
                if(count <= duration) {
                    displayCountdown();
                    count++;
                } else {
                    this.cancel();
                    onCountdownOver();
                    current = null;
                }
            }
        };
        start();
    }

    public String getCurrent() {
        long i = duration - count;

        long hours = Math.floorDiv(i, 3600);
        i = i - hours*3600;

        long mins = Math.floorDiv(i, 60);
        i = i - mins*60;

        long secs = i;

        if(!paused)
            return a("&c" + hours + "&6:&c" + mins + "&6:&c" + secs);
        return a("&c" + hours + "&6:&c" + mins + "&6:&c" + secs);
    }

    public static String getStaticCurrent() {
        if(current == null)
            return "";
        long duration = current.duration, count = current.count;
        boolean paused = current.paused;
        long i = duration - count;

        long hours = Math.floorDiv(i, 3600);
        i = i - hours*3600;

        long mins = Math.floorDiv(i, 60);
        i = i - mins*60;

        long secs = i;

        if(!paused)
            return a("&c" + hours + "&6:&c" + mins + "&6:&c" + secs);
        return a("&c" + hours + "&6:&c" + mins + "&6:&c" + secs);
    }

    public boolean isPaused() {
        return paused;
    }

    public static String a(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public void displayCountdown() {
        for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
        String text = "%countdown_a%";
        text = PlaceholderAPI.setPlaceholders(onlinePlayer, text);
        onlinePlayer.sendTitle(a(text),"");
        }
    }
}
