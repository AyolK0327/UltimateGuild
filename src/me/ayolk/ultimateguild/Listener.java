package me.ayolk.ultimateguild;

import me.ayolk.ultimateguild.sql.data;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import static me.ayolk.ultimateguild.AdminCommand.Players;
import static me.ayolk.ultimateguild.sql.data.Guild_data;

public class Listener implements org.bukkit.event.Listener {
    private Plugin plugin;

    public Listener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void AsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
        event.getPlayer().sendRawMessage(String.valueOf(Players.size()));
        for (Player p : Players) {
            if(p ==null){
                break;
            }
            if (!p.equals(event.getPlayer())) {
                break;
            }
            event.setCancelled(true);
            if (event.getMessage().equalsIgnoreCase("取消") || event.getMessage().equalsIgnoreCase("cancel")) {
                event.getPlayer().sendRawMessage("取消创建工会.");
                return;
            }
            for (String[] a : Guild_data) {
                String setA = a[0].replace("&1", "")
                        .replace("&2", "")
                        .replace("&3", "")
                        .replace("&4", "")
                        .replace("&5", "")
                        .replace("&6", "")
                        .replace("&7", "")
                        .replace("&8", "")
                        .replace("&9", "")
                        .replace("&0", "")
                        .replace("&e", "")
                        .replace("&d", "")
                        .replace("&a", "")
                        .replace("&c", "")
                        .replace("&b", "")
                        .replace("&n", "")
                        .replace("&m", "")
                        .replace("&k", "")
                        .replace("&l", "")
                        .replace("&o", "")
                        .replace("&f", "")
                        .replace("&r", "");

                String setMessage = event.getMessage().replace("&1", "")
                        .replace("&2", "")
                        .replace("&3", "")
                        .replace("&4", "")
                        .replace("&5", "")
                        .replace("&6", "")
                        .replace("&7", "")
                        .replace("&8", "")
                        .replace("&9", "")
                        .replace("&0", "")
                        .replace("&e", "")
                        .replace("&d", "")
                        .replace("&a", "")
                        .replace("&c", "")
                        .replace("&b", "")
                        .replace("&n", "")
                        .replace("&m", "")
                        .replace("&k", "")
                        .replace("&l", "")
                        .replace("&o", "")
                        .replace("&f", "")
                        .replace("&r", "");
                if (setA.equals(setMessage)) {
                    event.getPlayer().sendRawMessage("存在同名工会,已取消创建工会.");
                    return;
                }
                String Message = event.getMessage().replace("&", "§");
                Players.remove(p);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        data.createGuild(event.getPlayer(), Message);
                    }
                }.runTaskAsynchronously(plugin);
                event.getPlayer().sendRawMessage(Message);
                return;
            }
            String Message = event.getMessage().replace("&", "§");
            Players.remove(p);
            new BukkitRunnable() {
                @Override
                public void run() {
                    data.createGuild(event.getPlayer(), Message);
                }
            }.runTaskAsynchronously(plugin);
            event.getPlayer().sendRawMessage(Message);
            return;
        }
    }
}
