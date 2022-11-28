package me.ayolk.ultimateguild;

import me.ayolk.ultimateguild.sql.data;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

import static me.ayolk.ultimateguild.AdminCommand.Players;
import static me.ayolk.ultimateguild.sql.data.Guild_data;
import static me.ayolk.ultimateguild.sql.data.Player_data;

public class UserCommand implements CommandExecutor {
    private final Plugin plugin;
    private List<String[]> listInvite = new ArrayList<>();

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
        if(args[0].equalsIgnoreCase("leave")){
            //离开工会
            for(String[] a : Player_data){

                if(a[0].equals(sender.getName())){
                    if( a[1].equals("无工会")){
                        sender.sendMessage("未加入工会,无法退出工会.");
                        return true;
                    }
                    if(a[3].equals("会长")){
                        sender.sendMessage("会长无法退出工会.");
                        return true;
                    }

                }
            }

            //

            //

        }
        //隐性指令，无法被正常使用
        if(args[0].equalsIgnoreCase("kick")){
            return true;
        }

        if(args[0].equalsIgnoreCase("create")){
            for(Player b : Players){
                if(b.equals(((Player) sender).getPlayer())){
                    sender.sendMessage("正在创建工会请稍后在试.");
                    return true;
                }
            }
            for(String[] a : Player_data){
                if(a[0].equals(sender.getName()) && !a[1].equals("无工会")){
                    sender.sendMessage("已经加入或创建一个工会,请勿重复创建.");
                    return true;
                }
            }
            /*
            if(!econ.has(((Player) sender).getPlayer(),plugin.getConfig().getInt("Guild.CreateCost"))){
                sender.sendMessage("金钱不足.");
                return true;
            }
             */
            Players.add((Player) sender);
            sender.sendMessage("聊天框中输入工会名字以创建工会,输入\"取消\"或者\"cancel\"来取消创建工会!");
        }
        if(args[0].equalsIgnoreCase("accept")){
            //接受
            if(listInvite != null){
                boolean hasInvite = false ;
                String InvitePlayer = new String();
                String InviteGuild = new String();
                for (String[] a : listInvite){
                    if(a[1].equals(sender.getName())){
                        hasInvite = true;
                        InvitePlayer = a[1];
                        InviteGuild = a[0];
                    }
                }
                if(hasInvite){
                    data.PlayerJoinGuild(InvitePlayer,InviteGuild);
                    data.saveGuildData();
                }else{
                    sender.sendMessage("还没有工会邀请你嗷");
                }
            }
            return true;
        }
        if(args[0].equalsIgnoreCase("deny")){
            boolean hasInvite = false ;
            for(String[] a : listInvite){
                if(a[1].equals(sender.getName())){
                    hasInvite = true;
                }
            }
            //拒绝
            if(hasInvite){
                sender.sendMessage("成功拒绝工会的邀请.");
                listInvite.removeIf(a -> a[1].equals(sender.getName()));
            }else{
                sender.sendMessage("暂时还没有工会邀请你...");
            }
            return true;
        }

        if(args[0].equalsIgnoreCase("invite")){
            if(args[1] == null){
                sender.sendMessage("需要填写邀请玩家的ID.");
                return true;
            }
            if(args[1].equals(sender.getName())){
                sender.sendMessage("请不要邀请自己!");
                return true;
            }
            if(listInvite != null){
                 for(String[] a : listInvite){
                     if(a[1].equals(args[1])){
                         sender.sendMessage("该玩家有其他工会的邀请,请稍后再试.");
                         return true;
                     }
                 }
            }
            //邀请
            //[工会名字],[被邀请人],[邀请人]
            if(getGuild(((Player) sender).getPlayer()) == null){
                sender.sendMessage("错误,联系管理员!");
                return true;
            }
            if(getGuild(((Player) sender).getPlayer()).equals("无工会")){
                sender.sendMessage("错误,请先加入一个工会在邀请其他人!");
                return true;
            }
            String[] inv = {getGuild(((Player) sender).getPlayer()),args[1],sender.getName()};
            sender.sendMessage("邀请已经发送!");
            listInvite.add(inv);
            return true;
        }
        sender.sendMessage("啥事没有.");
        return false;
    }
    public Boolean getPlayerGuild(Player player , String Guild){
        for (String[] a : Player_data){
            if(a[1].equals(Guild)){
                return true;
            }
        }
        return false;
    }
    public String getPlayerGuildName(Player player){
        for (String[] a : Player_data){
            if(a[0].equals(player.getName())){
                return a[1];
            }
        }
        return "无工会";

    }
    public void kickPlayer(Player player,String Guild){
        for (String[] a : Player_data){
            if(a[0].equals(player.getName())){
                a[1] = "无公会";
            }
        }
        for (String[] a: Guild_data){
            if(a[0].equals(Guild)){
                int setInt = Integer.parseInt(a[7]) - 1;
                a[7] = String.valueOf(setInt);
            }
        }
    }
    public String getGuild(Player player){
        for (String[] a : Player_data){
            if(a[0].equals(player.getName())){
                return a[1];
            }
        }
        return null;
    }
}
