package me.ayolk.ultimateguild;

import me.ayolk.ultimateguild.sql.data;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import static me.ayolk.ultimateguild.AdminCommand.Players;

public class Listener implements org.bukkit.event.Listener {
    private Plugin plugin;

    public Listener(Plugin plugin){
        this.plugin = plugin;
    }
    @EventHandler
    public void AsyncPlayerChatEvent(AsyncPlayerChatEvent event){


        for (Player p : Players){
            if(!p.equals(event.getPlayer())){
                break;
            }
            event.setCancelled(true);
            if(event.getMessage().equalsIgnoreCase("取消") || event.getMessage().equalsIgnoreCase("cancel")){
                event.getPlayer().sendRawMessage("取消创建工会.");
                return;
            }
            String Message =  event.getMessage().replace("&","§");
            Players.remove(p);
            new BukkitRunnable(){

                @Override
                public void run() {
                    data.createGuild(event.getPlayer() , Message);
                }
            }.runTaskAsynchronously(plugin);
            event.getPlayer().sendRawMessage(Message);
        }
    }
}
