package me.ayolk.ultimateguild;

import me.ayolk.ultimateguild.Gui.Gui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import static me.ayolk.ultimateguild.sql.data.*;

public class AdminCommand implements CommandExecutor {
    public static List<Player> Players = new ArrayList<>();
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
        this.plugin = plugin;
    }
    @Override
    @ParametersAreNonnullByDefault
    public boolean  onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args[0].equalsIgnoreCase("reload")){
            sender.sendMessage("????????????.");
            try {
                new Gui(plugin).LoadGuiConfig(plugin);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.plugin.reloadConfig();
            this.plugin.saveConfig();
            return true;
        }
        if(!(sender instanceof Player)){
            sender.sendMessage("???????????????!");
            return true;
        }
        if(args[0].equalsIgnoreCase("give")){
            for(String[] a : Player_data){
                if(a[0].equals(args[1])){
                    int setMoney = Integer.parseInt(a[2]) + Integer.parseInt(args[2]);
                    a[2] = String.valueOf(setMoney);
                    sender.sendMessage("??????" + args[1] +"????????? +"+ args[2]);
                    return true;
                }
            }
            sender.sendMessage("???????????????!");
        }

        if(args[0].equalsIgnoreCase("test")){
            //??????prob1t??????
            for(String[]a : Guild_data){
                sender.sendMessage(a[0] + "??????" +a[7]);
                int setInt = Integer.parseInt(a[7]) + 1;
                a[7] = String.valueOf(setInt);
                sender.sendMessage(a[0]+"?????????" + a[7]);
            }
        }
        if(args[0].equals("test2")){
            sender.sendMessage(Arrays.toString(Guild_data.get(0)));
        }
        if(args[0].equalsIgnoreCase("open")){
            new Gui(plugin).test((Player) sender, Integer.parseInt(args[1]));
        }
        if(args[0].equalsIgnoreCase("create")){
            Players.add((Player) sender);
            sender.sendMessage("?????????????????????????????????????????????,??????\"??????\"??????\"cancel\"?????????????????????!");
        }
        if(args[0].equalsIgnoreCase("get")){
            Set<String> LevelKeys = plugin.getConfig().getConfigurationSection("Guild.Level").getKeys(false);
            if(Integer.parseInt(args[1]) > LevelKeys.size() ){
                sender.sendMessage("????????????..");
                return true;
            }
            //sender.sendMessage(Arrays.toString(a.get(1).values().toArray()));
        }
        return true;
    }
    public String refFormatNowDate(){
        Date nowTime = new Date(System.currentTimeMillis());
        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyy???MM???dd???hh???mm???");
        String retStrFormatNowDate = sdFormatter.format(nowTime);
        return retStrFormatNowDate;
    }


}
