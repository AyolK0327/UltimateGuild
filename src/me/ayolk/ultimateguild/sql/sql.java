package me.ayolk.ultimateguild.sql;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class sql extends BukkitRunnable {
    public static Connection connection;
    private Plugin plugin;
    public sql(Plugin plugin1){
        plugin = plugin1;
    }
    @Override
    public void run() {
        String JDBC_DRIVER = plugin.getConfig().getString("MySQL.drive"); // 驱动名称
        String PORT = plugin.getConfig().getString("MySQL.port"); // 由服主指定
        String DB_NAME = plugin.getConfig().getString("MySQL.database"); // 由服主指定
        String DB_HOST = plugin.getConfig().getString("MySQL.host");
        String DB_URL = "jdbc:mysql://" + DB_HOST + ":" + PORT + "/" + DB_NAME + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String USERNAME = plugin.getConfig().getString("MySQL.username"); // 由服主指定
        String PASSWORD = plugin.getConfig().getString("MySQL.password"); // 由服主指定
        //链接数据库
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
