package me.ayolk.ultimateguild.Listeners;

import me.ayolk.ultimateguild.sql.data;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerQuitEventListener implements Listener {
    private final Plugin plugin;

    public PlayerQuitEventListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoinEventListener(PlayerQuitEvent event){
        event.getPlayer().sendRawMessage("test");
        plugin.getLogger().info("test");
        new BukkitRunnable(){

            @Override
            public void run() {
                data.getPlayerDataRemove(event.getPlayer());
                plugin.getLogger().info("GG");
            }
        }.runTaskAsynchronously(plugin);

    }
}
