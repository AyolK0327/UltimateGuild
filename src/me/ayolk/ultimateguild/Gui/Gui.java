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
import java.util.Arrays;

public class Gui{
    private final Plugin plugin;

    public Gui(Plugin plugin) {
        this.plugin = plugin; // 给句柄添加引用
    }
    public static YamlConfiguration config;
    public void LoadGuiConfig(Plugin plugin) throws IOException {
        plugin.saveResource("Menu.yml",false);
        config = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder() + "\\Menu.yml"));
    }
    public YamlConfiguration getConfigMenu(){
        return config;
    }

    public void test(Player player){
        Inventory inv = Bukkit.createInventory(player, 3 * 9, "标题");
        ItemStack exitItem = new ItemStack(Material.BARRIER);
        //物品名字
        ItemMeta test = exitItem.getItemMeta();
        test.setDisplayName("test");
        String[] Lore = {"test","hi"};
        test.setLore(Arrays.asList(Lore));
        exitItem.setItemMeta(test);
        inv.setItem(0, exitItem);
        player.openInventory(inv);
    }
}
