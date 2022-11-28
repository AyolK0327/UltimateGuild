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

public class GuildGui {
    //点进来的或者直接打开进来
    public static List<String[]> GuildGuiList = new ArrayList<>();
    private final Plugin plugin;
    public static YamlConfiguration configGuildGui;

    public GuildGui(Plugin plugin) {
        this.plugin = plugin; // 给句柄添加引用
    }
    public void LoadGuiConfig(Plugin plugin) throws IOException {
        File First = new File(plugin.getDataFolder()+ "\\Menu");
        String[] N = First.list();

        String Path = "\\Menu\\GuildGui.yml";
        File file = new File(plugin.getDataFolder() + Path);
        plugin.getLogger().info(Arrays.toString(First.list()));
        configGuildGui = YamlConfiguration.loadConfiguration(file);
        if(!file.exists()){
            plugin.saveResource("Menu\\GuildGui.yml",false);
        }
        Set<String> Keys = configGuildGui.getConfigurationSection("icon").getKeys(false);
        for(String key : Keys){
            String slot = "xixi";
            String type = String.valueOf(configGuildGui.get("icon."+ key +".type"));
            String name = configGuildGui.getString("icon."+key+".name").replace("&","§");
            String amount = String.valueOf(configGuildGui.getInt("icon."+ key +".amount"));
            String getByte = configGuildGui.getString("icon."+ key +".Byte");
            String[] all = {name,amount,slot,key,type,getByte};
            GuildGuiList.add(all);
        }
    }
    public static void getGuildGui(Player player , String GuildUid){
        Inventory inv2 = Bukkit.createInventory(player, configGuildGui.getInt("slot") * 9, getGuildName(GuildUid) + "工会");
        showUpMenu(inv2,GuildUid);
        player.openInventory(inv2);
    }

    public static String getGuildName(String GuildUid){
        for(String[] a : Guild_data){
            if(a[11].equalsIgnoreCase(GuildUid)){
                return a[0];
            }
        }
        return null;
    }

    private static void showUpMenu(Inventory inv, String GuildUid) {
        for(String[] a : GuildGuiList){
            ItemStack itemStack = new ItemStack(Material.valueOf(configGuildGui.getString("icon."+a[3]+".type")),Integer.parseInt(a[1]));
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(configGuildGui.getString("icon."+a[3]+".name"));
            List<String> lore = configGuildGui.getStringList("icon."+a[3]+".lore");
            List<String> lore2 = new ArrayList<>();
            for(String b : lore){
                lore2.add(b.replace("&","§"));
            }
            itemMeta.setLore(lore2);
            itemStack.setItemMeta(itemMeta);
            List<Integer> slot = configGuildGui.getIntegerList("icon."+a[3]+".slot");
            for(int c : slot){
                inv.setItem(c,itemStack);
            }
        }
    }
}
