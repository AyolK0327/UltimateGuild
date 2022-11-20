package me.ayolk.ultimateguild;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.annotation.ParametersAreNonnullByDefault;

public class UserCommand implements CommandExecutor {
    private final Plugin plugin;

    public UserCommand(Plugin plugin) {
        this.plugin = plugin; // 给句柄添加引用
    }
    @Override
    @ParametersAreNonnullByDefault
    public boolean  onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("必须是玩家!");
            return true;
        }
        sender.sendMessage("啥事没有.");
        return false;
    }
}
