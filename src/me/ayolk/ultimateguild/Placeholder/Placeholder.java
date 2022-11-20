package me.ayolk.ultimateguild.Placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import static me.ayolk.ultimateguild.sql.data.Player_data;

public class Placeholder extends PlaceholderExpansion {
    private Plugin plugin;
    public Placeholder(Plugin plugin){
        this.plugin = plugin;

    }
    @Override
    public @NotNull String getIdentifier() {
        return "UltimateGuild";
    }

    @Override
    public @NotNull String getAuthor() {
        return "AyolK_";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }
    @Override
    public String onRequest(OfflinePlayer player, String params) {

        if(params.equalsIgnoreCase("guildcoin")){
            return getGuildCoin(player.getName());
        }
        if(params.equalsIgnoreCase("guildname")){
            return getGuildName(player.getName());
        }
        return null;

    }
    public String getGuildCoin(String player){
        for (String[] a: Player_data){
            if(a[0].equals(player)){
                return a[2];
            }
        }
        return null;
    }
    public String getGuildName(String player){
        for (String[] a: Player_data){
            if(a[0].equals(player)){
                return a[1];
            }
        }
        return null;
    }

}
