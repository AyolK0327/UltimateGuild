package me.ayolk.ultimateguild.Task;

import me.ayolk.ultimateguild.sql.data;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;

public class taskForSaveData {
    private Plugin plugin;
    public taskForSaveData(Plugin plugin){
        this.plugin = plugin;
    }
    public void saveTask(){
        new BukkitRunnable(){

            @Override
            public void run() {
                Collection<? extends Player> Player = plugin.getServer().getOnlinePlayers();
                for(Player player : Player){
                    //data中的Player_Data存到数据库中
                    data.saveData();
                }
                data.saveGuildData();
            }
        }.runTaskTimerAsynchronously(plugin , 120,plugin.getConfig().getLong("AutoSave.delay") * 20);
    }
}
