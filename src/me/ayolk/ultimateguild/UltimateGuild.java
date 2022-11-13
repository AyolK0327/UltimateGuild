package me.ayolk.ultimateguild;

import me.ayolk.ultimateguild.Gui.Gui;
import me.ayolk.ultimateguild.Gui.GuiListener;
import me.ayolk.ultimateguild.sql.sql;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;


public class UltimateGuild extends JavaPlugin {


    @Override
    public void onLoad() {
        saveDefaultConfig();
        reloadConfig();
        new sql(this).run();
        try {
            new Gui(this).LoadGuiConfig(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onEnable() {
        this.getCommand("UltimateGuild").setExecutor(new UserCommand(this));
        this.getCommand("UltimateGuildAdmin").setExecutor(new AdminCommand(this));
        this.getServer().getPluginManager().registerEvents(new GuiListener(this), this);

        getLogger().info(getDataFolder().toString());
    }
}
