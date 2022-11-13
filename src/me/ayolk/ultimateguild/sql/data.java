package me.ayolk.ultimateguild.sql;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import java.sql.SQLException;

import static me.ayolk.ultimateguild.sql.sql.connection;

public class data {
    //测试用
    public void test(Plugin plugin){
        new BukkitRunnable(){
            @Override
            public void run() {
                try {
                    plugin.getLogger().info("尝试写入数据...");
                    connection.createStatement().execute("INSERT INTO player_data (uuid,name,isGirl,money) VALUES (\"like\",\"test\",1,231)");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }.runTaskAsynchronously(plugin);
    }
}
