package me.ayolk.ultimateguild;

import me.ayolk.ultimateguild.Gui.Gui;
import me.ayolk.ultimateguild.Gui.GuiListener;
import me.ayolk.ultimateguild.Gui.GuildGui;
import me.ayolk.ultimateguild.Listeners.PlayerJoinEventListener;
import me.ayolk.ultimateguild.Listeners.PlayerQuitEventListener;
import me.ayolk.ultimateguild.Listeners.testEvent;
import me.ayolk.ultimateguild.Placeholder.Placeholder;
import me.ayolk.ultimateguild.Task.taskForSaveData;
import me.ayolk.ultimateguild.sql.data;
import me.ayolk.ultimateguild.sql.sql;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class UltimateGuild extends JavaPlugin {

    @Override
    public void onLoad() {
        saveDefaultConfig();
        reloadConfig();
        new sql(this).run();
        try {
            new GuildGui(this).LoadGuiConfig(this);
            new Gui(this).LoadGuiConfig(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        new data(this).CreateSQL();

    }
    @Override
    public void onEnable() {
        data.getGuildData();
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new Placeholder(this).register();
        } else {
            this.getLogger().warning("未检测到PlaceholderAPI,将不支持变量的使用!");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        if(getConfig().getBoolean("AutoSave.enable")){
            new taskForSaveData(this).saveTask();
        }
        this.getCommand("UltimateGuild").setExecutor(new UserCommand(this));
        this.getCommand("UltimateGuildAdmin").setExecutor(new AdminCommand(this));
        this.getServer().getPluginManager().registerEvents(new GuiListener(this), this);
        this.getServer().getPluginManager().registerEvents(new Listener(this), this);
        this.getServer().getPluginManager().registerEvents(new testEvent(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerJoinEventListener(this), this);
        this.getServer().getPluginManager().registerEvents(new PlayerQuitEventListener(this), this);
        getLogger().info(getDataFolder().toString());
    }
    @Override
    public void onDisable() {
        data.saveData();
        data.saveGuildData();
    }
}
