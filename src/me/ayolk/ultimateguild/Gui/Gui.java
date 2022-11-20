package me.ayolk.ultimateguild.Gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static me.ayolk.ultimateguild.sql.data.Guild_data;

public class Gui{
    private final Plugin plugin;
    public static Inventory inv1;
    public static List<String[]> MenuList = new ArrayList<>();

    public Gui(Plugin plugin) {
        this.plugin = plugin; // 给句柄添加引用
    }
    public static YamlConfiguration config;
    public void LoadGuiConfig(Plugin plugin) throws IOException {
        File First = new File(plugin.getDataFolder()+ "\\Menu");
        String[] N = First.list();

        String Path = "\\Menu\\Menu.yml";
        File file = new File(plugin.getDataFolder() + Path);
        plugin.getLogger().info(Arrays.toString(First.list()));
        config = YamlConfiguration.loadConfiguration(file);
        if(!file.exists()){
            plugin.saveResource("Menu\\Menu.yml",false);
        }
        Set<String> Keys = config.getConfigurationSection("icon").getKeys(false);
        for(String key : Keys){
            String slot = "xixi";
            String type = String.valueOf(config.get("icon."+ key +".type"));
            String name = config.getString("icon."+key+".name").replace("&","§");
            String amount = String.valueOf(config.getInt("icon."+ key +".amount"));
            String getByte = config.getString("icon."+ key +".Byte");
            String[] all = {name,amount,slot,key,type,getByte};
            MenuList.add(all);
        }

    }
    public void saveIn(String key,String FileName){

    }
    public YamlConfiguration getConfigMenu(){
        return config;
    }
    public void showUpMenu(Inventory inv, int page){
        int slot = 0;
        //Page 1 = 0-9  , Page 2 = 10-19  ,Page 3 = 20-29
        int start = (page-1) * 10;
        for(String[] a: MenuList){

            if(a[4].equalsIgnoreCase("Guild")){
                List<Integer> getSlot1 = config.getIntegerList("icon."+a[3]+".slot");
                for(int i = start; i <= (start + 9) ; i++){
                    if(Guild_data.size()-1 <= i-1){
                        break;
                    }
                    if(Guild_data.get(i) != null){
                        String[] Guild = Guild_data.get(i);
                        ItemStack GuildItem = new ItemStack(Material.valueOf(Guild[4]),1);
                        ItemMeta itemMeta = GuildItem.getItemMeta();
                        itemMeta.setDisplayName(Guild[0]);

                        List<String> getLore1 = config.getStringList("icon."+a[3]+".lore");
                        List<String> repLore = new ArrayList<>();

                        for (String lore1 : getLore1){
                            String SetLore = lore1.replace("%Guild%_has_Player%",Guild[2]).replace("%Guild_Max_Player%",Guild[2]).replace("%Guild_Level%",Guild[1]).replace("%Guild_info%",Guild[5]);
                            repLore.add(SetLore);
                        }
                        itemMeta.setLore(repLore);
                        GuildItem.setItemMeta(itemMeta);

                        inv.setItem(getSlot1.get(slot),GuildItem);
                        slot++;
                        //String[] getAll = {GuildName,GuildLevel,GuildExp,GuildCoin,GuildSign,GuildInfo};
                    }
                }
                continue;
            }
            //Material.valueOf(a[4].toUpperCase())
            ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE,Integer.parseInt(a[1]), (short) 0, (byte)15);
            item.setTypeId(160);

            ItemMeta item2 = item.getItemMeta();
            List<Integer> getSlot = config.getIntegerList("icon."+a[3]+".slot");
            List<String> getLore = config.getStringList("icon."+a[3]+".lore");

            List<String> rep = new ArrayList<>() ;
            for(String l : getLore){
                rep.add(l.replace("&","§"));
            }
            String[] lore = rep.toArray(new String[0]);
            item2.setLore(Arrays.asList(lore));
            item.setItemMeta(item2);
            for (int i : getSlot){
                inv.setItem(i,item);
            }
        }
    }
    public void test(Player player ,int Page){
        Inventory inv = Bukkit.createInventory(player, config.getInt("slot") * 9, config.getString("name").replace("&","§"));
        showUpMenu(inv,Page);
        inv1 = inv;
        player.openInventory(inv);
    }
}
