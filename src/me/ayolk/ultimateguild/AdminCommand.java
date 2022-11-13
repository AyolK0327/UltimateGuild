package me.ayolk.ultimateguild;

import me.ayolk.ultimateguild.Gui.Gui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class AdminCommand implements CommandExecutor {
    public String getMax(Object[] b){
        StringBuilder test = new StringBuilder();
        int maxGet = (Arrays.toString(b).toLowerCase().indexOf("max")+4);
        for(int i=0 ; i < Arrays.toString(b).length() - (Arrays.toString(b).indexOf("max")+4) ; i++){
            if(Arrays.toString(b).substring(maxGet+i,maxGet+i+1).equals("}") || Arrays.toString(b).substring(maxGet+i,maxGet+i+1).equals("]")){
                break;
            }else{
                test.append(Arrays.toString(b), maxGet + i, maxGet + i + 1);
            }

        }
        return test.toString();
    }
    public String getExp(Object[] b){
        StringBuilder test = new StringBuilder();
        int getComma = (Arrays.toString(b).indexOf("max")-8);
        int getExp = (Arrays.toString(b).toLowerCase().indexOf("exp")+4);
        for(int i=0 ; i < getComma ; i++){
            if(Arrays.toString(b).substring(getExp+i,getExp+i+1).equals("}") || Arrays.toString(b).substring(getExp+i,getExp+i+1).equals("]")){
                break;
            }else{
                test.append(Arrays.toString(b),getExp+i,getExp+i+1);
            }

        }
        return test.toString();
    }

    private final Plugin plugin;

    public AdminCommand(Plugin plugin) {
        this.plugin = plugin; // 给句柄添加引用
    }
    @Override
    @ParametersAreNonnullByDefault
    public boolean  onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if(args[0].toLowerCase().equals("reload")){
            sender.sendMessage("重置成功.");
            this.plugin.reloadConfig();
            this.plugin.saveConfig();
            return true;
        }
        if(args[0].toLowerCase().equals("test")){
            sender.sendMessage(new Gui(plugin).getConfigMenu().getConfigurationSection("item").getKeys(false).toString());

            return true;
        }
        if(args[0].toLowerCase().equals("open")){
            new Gui(plugin).test((Player) sender);
        }
        if(args[0].toLowerCase().equals("get")){
            List<Map<?, ?>> a = plugin.getConfig().getMapList("Guild.Level");
            if(Integer.parseInt(args[1]) > a.size() ){
                sender.sendMessage("等级过高..");
                return true;
            }
            //sender.sendMessage(Arrays.toString(a.get(1).values().toArray()));
            Object[] b = a.get(Integer.parseInt(args[1])+1).values().toArray();
            sender.sendMessage("等级:"+args[1] + "经验:"+getExp(b) + "人数:" + getMax(b));

        }
        return true;
    }



}
