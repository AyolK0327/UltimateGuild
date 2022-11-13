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

public class Gui{
    private final Plugin plugin;
    public static List<String[]> MenuList = new ArrayList<>();

    public Gui(Plugin plugin) {
        this.plugin = plugin; // 给句柄添加引用
    }
    public static YamlConfiguration config;
    public void LoadGuiConfig(Plugin plugin) throws IOException {
        String Path = "\\Menu.yml";
        File file = new File(plugin.getDataFolder() + Path);
        config = YamlConfiguration.loadConfiguration(file);
        if(!file.exists()){
            plugin.saveResource("Menu.yml",false);
        }
        //plugin.saveResource(Path,false);
        Set<String> Keys = config.getConfigurationSection("icon").getKeys(false);
        for (String key : Keys){
            String name = config.getString("icon."+key+".name").replace("&","§");
            String amount = String.valueOf(config.getInt("icon."+ key +".amount"));
            String slot = String.valueOf(config.getInt("icon."+ key +".slot"));
            String type = String.valueOf(config.get("icon."+ key +".type"));
            String[] all = {name,amount,slot,key,type};
            MenuList.add(all);
        }
    }
    public YamlConfiguration getConfigMenu(){
        return config;
    }

    public void showUpMenu(Inventory inv , Player player){
        for(String[] a: MenuList){
        ItemStack item = new ItemStack(Material.valueOf(a[4]),Integer.parseInt(a[1]));
        player.sendRawMessage(a[4]);
        ItemMeta item2 = item.getItemMeta();
        item2.setDisplayName(a[0]);
        List<String> getLore = config.getStringList("icon."+a[3]+".lore");

        List<String> rep = new ArrayList<>() ;
        for(String l : getLore){
            rep.add(l.replace("&","§"));
        }
        String[] lore = rep.toArray(new String[0]);
        player.sendRawMessage(String.valueOf(getLore.size()));
        item2.setLore(Arrays.asList(lore));
        item.setItemMeta(item2);
        inv.setItem(Integer.parseInt(a[2]),item);
        }
    }
    public void test(Player player){
        Inventory inv = Bukkit.createInventory(player, config.getInt("slot") * 9, config.getString("name"));
        showUpMenu(inv,player);
        player.openInventory(inv);
    }
}
