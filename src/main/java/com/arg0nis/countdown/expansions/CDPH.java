package com.arg0nis.countdown.expansions;

import com.arg0nis.countdown.Countdown;
import com.arg0nis.countdown.CountdownPlugin;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class CDPH extends PlaceholderExpansion {
    private CountdownPlugin plugin = CountdownPlugin.getInstance();
    public CDPH(CountdownPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "countdown";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Arg0nis";
    }

    @Override
    public @NotNull String getVersion() {
        return "0.1";
    }

    @Override
    public @NotNull String getRequiredPlugin() {
        return plugin.getName();
    }

    @Override
    public boolean canRegister() {
        return (plugin = (CountdownPlugin) Bukkit.getPluginManager().getPlugin(getRequiredPlugin())) != null;
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        if(params.equalsIgnoreCase("a")) {
            try {
                return Countdown.getStaticCurrent();
            } catch(Exception e) {
                return "";
            }
        }
        return null;
    }






}
