package me.ayolk.ultimateguild.sql;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static me.ayolk.ultimateguild.sql.sql.connection;

public class data{

    private static Plugin plugin;
    public static List<String[]> Player_data = new ArrayList<>();
    public static List<String[]> Guild_data = new ArrayList<>();

    public data(Plugin plugin) {
        data.plugin = plugin;
    }
    public static void saveGuildData(){
        for (String[] a : Guild_data){
            try {
                String save = "UPDATE ultimateguild_data SET " +
                        "GuildLevel=" +  "\"" + Integer.parseInt(a[1]) + "\","  +
                        "GuildExp=" +  "\"" + Integer.parseInt(a[2]) + "\","  +
                        "GuildCoin=" +  "\"" + Integer.parseInt(a[3]) + "\","  +
                        "GuildInfo =" +  "\"" + a[5] + "\","  +
                        "GuildSign =" +  "\"" + a[4] + "\","  +
                        "GuildMax =" +  "\"" + plugin.getConfig().getString("Guild.Level." + a[1] + ".max") + "\","  +
                        "GuildHas=" +  "\"" + Integer.parseInt(a[7]) + "\","  +
                        "GuildDate=" +  "\"" + a[8] + "\","  +
                        "TransferLimit=" +  "\" " + a[9] + "\","  +
                        "Master=" +  "\"" + a[10] + "\","  +
                        "GuildUid=" +  "\"" + a[11] + "\" "  +
                        "WHERE GuildName =" + "\"" +a[0] +"\"";
                new sql(plugin).run();
                connection.prepareStatement(save).executeUpdate();
                connection.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public static void getGuildData(){
        Guild_data.clear();
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
                        String GuildName = rs.getString("GuildName");
                        String GuildLevel = rs.getString("GuildLevel");
                        String GuildExp = rs.getString("GuildExp");
                        String GuildCoin = rs.getString("GuildCoin");
                        String GuildSign = rs.getString("GuildSign");
                        String GuildInfo = rs.getString("GuildInfo");
                        String GuildMax = rs.getString("GuildMax");
                        String GuildHas = rs.getString("GuildHas");
                        String GuildDate = rs.getString("GuildDate");
                        String TransferLimit = rs.getString("TransferLimit");
                        String Master = rs.getString("Master");
                        String GuildUid = rs.getString("GuildUid");
                        String[] getAll = {GuildName,GuildLevel,GuildExp,GuildCoin,GuildSign,GuildInfo,GuildMax,GuildHas,GuildDate,TransferLimit,Master,GuildUid};
                        Guild_data.add(getAll);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.runTaskAsynchronously(plugin);
    }
    public static void saveData() {
        for(String[] a : Player_data){
            try {
                String save = "UPDATE guildplayer_data SET " +
                        "GuildName= " + "\"" +a[1] +"\"," +
                        "GuildPosition= \""+a[3] + "\"," +
                        "GuildCoin= " +Integer.parseInt(a[2]) +
                        " WHERE PlayerName=\""+a[0]+"\"";
                new sql(plugin).run();
                connection.prepareStatement(save).executeUpdate();
                connection.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public static boolean hasGuild(Player player){
        for (String[] a : Player_data){
            if(a[0].equals(player.getName()) && a[1].equals("?????????")){
                return false;
            }
        }
        return true;
    }
    public static String refFormatNowDate(){
        Date nowTime = new Date(System.currentTimeMillis());
        SimpleDateFormat sdFormatter = new SimpleDateFormat("yyy???MM???dd???hh???mm???");
        String retStrFormatNowDate = sdFormatter.format(nowTime);
        return retStrFormatNowDate;
    }
    public static String getLastUid(){
        return String.valueOf(new Date().getTime());
    }

    public static void createGuild(Player player, String GuildName){
        new sql(plugin).run();
        try{
            /*
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
                "TransferLimit VARCHAR(255)," +
                "Master VARCHAR(255)"+
                ");";
             */
            //??????????????????
            String insertsGuild =
                    "INSERT INTO ultimateguild_data "  +
                            "(GuildName," +
                            "GuildLevel," +
                            "GuildExp," +
                            "GuildCoin," +
                            "GuildInfo, " +
                            "GuildSign, " +
                            "GuildMax, " +
                            "GuildHas," +
                            "GuildDate," +
                            "TransferLimit," +
                            "Master," +
                            "GuildUid"+
                    ")VALUES(" +
                            "\""+ GuildName +"\"," +
                            "1," +
                            "0," +
                            "0," +
                            "\"????????????,??????????????????...\"," +
                            "\"APPLE\"," +
                            "\""+plugin.getConfig().getString("Guild.Level.1.max") + "\"," +
                            "1," +
                            "\"" + refFormatNowDate() + "\"," +
                            "0," +
                            "\""+player.getName()+"\","+
                            getLastUid() +
                            ")";
            //??????????????????
            String setPlayer = "UPDATE guildplayer_data SET " +
                    "GuildName=" + "\"" +GuildName +"\", " +
                    "GuildPosition=\"??????\" " +
                    "WHERE PlayerName=\""+player.getName()+"\"";
            /*
            ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
            ?????????????????????????????????????????????
            ?????? -> ?????????????????????????????? -> ??????????????? -> ????????????????????????
             */
            if(!hasGuild(player)){
                try {
                    connection.prepareStatement(setPlayer).executeUpdate();
                    player.sendRawMessage("???????????? " +GuildName+" ??????.");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else {
                player.sendRawMessage("?????????????????????,???????????????????????????!");
                return;
            }
            for (String[] a : Player_data){
                if(a[0].equals(player.getName())){
                    //??????????????????
                    a[1] = GuildName;
                    a[3] = "??????";
                    data.getPlayerData(player);
                }
            }
            data.getGuildData();
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
                //?????????????????????
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
                            String inserts = "INSERT INTO guildplayer_data (PlayerName,GuildName,GuildCoin,GuildPosition,Invitation) " +
                                    "VALUES(\""+ player.getName() +"\",\"?????????\",0,\"???\",\"???\")";
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
                "GuildUid VARCHAR(255) NOT NULL PRIMARY KEY NOT NULL," +
                "GuildName VARCHAR(255),\n" +
                "GuildLevel INTEGER NOT NULL,\n" +
                "GuildExp INTEGER NOT NULL, \n" +
                "GuildCoin INTEGER NOT NULL,\n" +
                "GuildSign VARCHAR(255),\n" +
                "GuildInfo VARCHAR(255),\n" +
                "GuildMax INTEGER NOT NULL," +
                "GuildHas INTEGER NOT NULL," +
                "GuildDate VARCHAR(255)," +
                "TransferLimit VARCHAR(255)," +
                "Master VARCHAR(255)"+
                ");";
        String createTableGuildPlayer_data = "CREATE TABLE IF NOT EXISTS GuildPlayer_data (" +
                "PlayerName VARCHAR(255) PRIMARY KEY NOT NULL," +
                "GuildName VARCHAR(255) NOT NULL," +
                "GuildCoin INTEGER NOT NULL," +
                "GuildPosition VARCHAR(255) NOT NULL," +
                "Invitation VARCHAR(255) NOT NULL" +
                ");";
        String createCrystal_data = "CREATE TABLE IF NOT EXISTS Crystal_data (" +
                "CrystalUid INTEGER NOT NULL PRIMARY KEY NOT NULL,"+
                "WorldName VARCHAR(255)," +
                "GuildName VARCHAR(255) NOT NULL," +
                "LocationX VARCHAR(255) NOT NULL," +
                "LocationY VARCHAR(255) NOT NULL," +
                "LocationZ VARCHAR(255) NOT NULL," +
                "CrystalSize VARCHAR(255) NOT NULL," +
                "CrystalMain VARCHAR(255) NOT NULL" +
                ");";
        try {
            Statement stmt = connection.createStatement();

            stmt.executeUpdate(createTableGuild_data);
            stmt.executeUpdate(createTableGuildPlayer_data);
            stmt.executeUpdate(createCrystal_data);
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static String getGuildName(String player) {
        //???????????????
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
    //?????????
    public void test(Plugin plugin){
        new BukkitRunnable(){
            @Override
            public void run() {
                try {
                    plugin.getLogger().info("??????????????????...");
                    connection.createStatement().execute("INSERT INTO player_data (uuid,name,isGirl,money) VALUES (\"like\",\"test\",1,231)");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }.runTaskAsynchronously(plugin);
    }
    public static void PlayerJoinGuild(String player,String guild){
        //
        for(String[] a : Player_data){
            if(a[0].equals(player)){
                a[1] = guild;
                a[3] = "??????";
            }
        }
        for (String[] b :Guild_data){
            if(b[0].equals(guild)){
                int setInt = Integer.parseInt(b[7]) + 1;
                b[7] = String.valueOf(setInt);
                return;
            }
        }
    }
}
