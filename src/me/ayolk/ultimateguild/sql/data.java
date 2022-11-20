package me.ayolk.ultimateguild.sql;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static me.ayolk.ultimateguild.sql.sql.connection;

public class data{

    private static Plugin plugin;
    public static List<String[]> Player_data = new ArrayList<>();
    public static List<String[]> Guild_data = new ArrayList<>();

    public data(Plugin plugin) {
        data.plugin = plugin;
    }
    public static void getGuildData(){
        new BukkitRunnable(){
            @Override
            public void run() {
                new sql(plugin).run();
                try {
                    String getGuild = "SELECT * " +
                            " FROM ultimateguild_data";
                    connection.createStatement().executeQuery(getGuild);
                    Statement stmt = connection.prepareStatement(getGuild);
                    ResultSet rs = stmt.executeQuery(getGuild);
                    while (rs.next()){
                        String GuildName =  rs.getString("GuildName");
                        String GuildLevel = rs.getString("GuildLevel");
                        String GuildExp = rs.getString("GuildExp");
                        String GuildCoin = rs.getString("GuildCoin");
                        String GuildSign = rs.getString("GuildSign");
                        String GuildInfo = rs.getString("GuildInfo");
                        String[] getAll = {GuildName,GuildLevel,GuildExp,GuildCoin,GuildSign,GuildInfo};
                        Guild_data.add(getAll);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(plugin);
    }
    public static void saveData() {
        new sql(plugin).run();
        for(String[] a : Player_data){
            try {
                String save = "UPDATE guildplayer_data SET " +
                        "GuildName= " + "\"" +a[1] +"\"," +
                        "GuildPosition= \""+a[3] + "\"," +
                        "GuildCoin= " +Integer.parseInt(a[2]) +
                        " WHERE PlayerName=\""+a[0]+"\"";
                connection.prepareStatement(save).executeUpdate();
                connection.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public static boolean hasGuild(Player player){
        for (String[] a : Player_data){
            if(a[0].equals(player.getName()) && a[1].equals("无工会")){
                return false;
            }
        }
        return true;
    }
    public static void createGuild(Player player, String GuildName){
        new sql(plugin).run();
        try{
            //创建工会语句
            String insertsGuild =
                    "INSERT INTO ultimateguild_data "  +
                            "(GuildName," +
                            "GuildLevel," +
                            "GuildExp," +
                            "GuildCoin," +
                            "GuildInfo, " +
                            "GuildSign) " +
                    "VALUES(" +
                            "\""+ GuildName +"\"," +
                            "1," +
                            "0," +
                            "0," +
                            "\"会长很懒,什么都没有写...\"," +
                            "\"APPLE\"" +
                            ")";
            //标记会长语句
            String setPlayer = "UPDATE guildplayer_data SET " +
                    "GuildName=" + "\"" +GuildName +"\", " +
                    "GuildPosition=\"会长\" " +
                    "WHERE PlayerName=\""+player.getName()+"\"";
            /*
            标记！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
            这里需要修改玩家表中工会的名称
            思路 -> 判断玩家是否为无工会 -> 在添加职务 -> 创建者默认为会长
             */
            if(!hasGuild(player)){
                try {
                    connection.prepareStatement(setPlayer).executeUpdate();
                    player.sendRawMessage("成功创建 " +GuildName+" 工会.");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else {
                player.sendRawMessage("已经有一个工会,请先退出再尝试创建!");
                return;
            }
            for (String[] a : Player_data){
                if(a[0].equals(player.getName())){
                    //刷新玩家数据
                    data.getPlayerDataRemove(player);
                    data.getPlayerData(player);
                }
            }
            connection.createStatement().execute(insertsGuild);
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void getPlayerDataRemove(Player player){
        for(int i=0 ; i <= Player_data.size() ; i++){
            if(Player_data.get(i)[0].equals(player.getName())){
                Player_data.remove(i);
            }
        }
    }
    public static void getPlayerData(Player player){
        new BukkitRunnable(){
            @Override
            public void run() {
                //启动数据库链接
                new sql(plugin).run();
                try {
                    String getPlayerData = "SELECT * " +
                            " FROM GuildPlayer_data" +
                            " WHERE PlayerName = \"" + player.getName() + "\";";
                    Statement stmt = connection.createStatement();
                    ResultSet resultSet = stmt.executeQuery(getPlayerData);
                    if (resultSet.next()) {
                        String PlayerName = resultSet.getString("PlayerName");
                        String GuildName = resultSet.getString("GuildName");
                        String GuildCoin = String.valueOf(resultSet.getInt("GuildCoin"));
                        String GuildPosition = resultSet.getString("GuildPosition");
                        String[] getData = {PlayerName,GuildName,GuildCoin,GuildPosition};
                        Player_data.add(getData);
                        player.sendRawMessage("hi");
                    }else{
                        try{
                            String inserts = "INSERT INTO guildplayer_data (PlayerName,GuildName,GuildCoin,GuildPosition) " +
                                    "VALUES(\""+ player.getName() +"\",\"无工会\",0,\"无\" )";
                            connection.createStatement().execute(inserts);
                            getPlayerData(player);
                        }catch (Exception ea){
                            ea.printStackTrace();
                        }
                    }
                    stmt.close();
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(plugin);
    }
    public void CreateSQL(){
        new sql(plugin).run();
        String createTableGuild_data  = "CREATE TABLE IF NOT EXISTS UltimateGuild_data (\n" +
                "GuildUid INTEGER NOT NULL PRIMARY KEY NOT NULL," +
                "GuildName VARCHAR(255),\n" +
                "GuildLevel INTEGER NOT NULL,\n" +
                "GuildExp INTEGER NOT NULL, \n" +
                "GuildCoin INTEGER NOT NULL,\n" +
                "GuildSign VARCHAR(255),\n" +
                "GuildInfo VARCHAR(255),\n" +
                "GuildMax INTEGER NOT NULL," +
                "GuildHas INTEGER NOT NULL," +
                "GuildDate VARCHAR(255)," +
                "TransferLimt VARCHAR(255)," +
                "Master VARCHAR(255)"+
                ");";
        String createTableGuildPlayer_data = "CREATE TABLE IF NOT EXISTS GuildPlayer_data (\n" +
                "PlayerName VARCHAR(255) PRIMARY KEY NOT NULL,\n" +
                "GuildName VARCHAR(255) NOT NULL,\n" +
                "GuildCoin INTEGER NOT NULL,\n" +
                "GuildPosition VARCHAR(255) NOT NULL\n" +
                ");";
        String createCrystal_data = "CREATE TABLE IF NOT EXISTS Crystal_data (" +
                "CrystalUid VARCHAR(255) PRIMARY KEY NOT NULL,"+
                "WorldName VARCHAR(255)," +
                "GuildName VARCHAR(255) NOT NULL," +
                "LocationX VARCHAR(255) NOT NULL," +
                "LocationY VARCHAR(255) NOT NULL," +
                "LocationZ VARCHAR(255) NOT NULL," +
                "CrystalSize VARCHAR(255) NOT NULL," +
                "CrystalMain VARCHAR(255) NOT NULL," +
                ");";
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(createCrystal_data);
            stmt.executeUpdate(createTableGuild_data);
            stmt.executeUpdate(createTableGuildPlayer_data);
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static String getGuildName(String player) {
        //启动数据库
        new sql(plugin).run();
        String getGuildName = plugin.getConfig().getString("Guild.GuildIsNull");
        try {
            String getPlayerCoin = "SELECT GuildName " +
                    " FROM GuildPlayer_data" +
                    " WHERE PlayerName = \"" + player + "\";";
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(getPlayerCoin);
            if (resultSet.next()) {
                getGuildName = resultSet.getString("GuildName");
            }
            stmt.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getGuildName;
    }
    public static int getPlayerCoin(String player) {
        new sql(plugin).run();
        int getCoin = 0;
        try {
            String getPlayerCoin = "SELECT GuildCoin " +
                    " FROM GuildPlayer_data" +
                    " WHERE PlayerName = \"" + player + "\";";
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(getPlayerCoin);
            if (resultSet.next()) {
                getCoin = resultSet.getInt("GuildCoin");
            }
            stmt.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getCoin;
    }
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
