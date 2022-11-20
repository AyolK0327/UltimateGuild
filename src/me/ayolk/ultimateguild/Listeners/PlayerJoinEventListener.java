package me.ayolk.ultimateguild.Listeners;

import me.ayolk.ultimateguild.sql.data;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerJoinEventListener implements Listener {
    private final Plugin plugin;

    public PlayerJoinEventListener(Plugin plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event){
        new BukkitRunnable(){
            @Override
            public void run() {
                data.getPlayerData(event.getPlayer());
            }
        }.runTaskAsynchronously(plugin);

    }

}
