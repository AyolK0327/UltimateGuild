package me.ayolk.ultimateguild.Gui;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.List;

import static me.ayolk.ultimateguild.Gui.Gui.MenuList;
import static me.ayolk.ultimateguild.Gui.Gui.config;
import static me.ayolk.ultimateguild.sql.data.Guild_data;


public class GuiListener implements Listener {
    private final Plugin plugin;

    public void OpPerformCommand(Player player,String command){
        //op执行者
        if(!(player.isOp())){
            try {
                player.setOp(true);
                Bukkit.dispatchCommand(player,command);
            }finally {
                player.setOp(false);
            }
        }else {
            Bukkit.dispatchCommand(player,command);
        }
    }
    public GuiListener(Plugin plugin) {this.plugin = plugin;}
    public YamlConfiguration configuration =Gui.config;
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        // 只有玩家可以触发 InventoryClickEvent，可以强制转换
        ItemStack clickedItem = e.getCurrentItem();
        if(!(e.getInventory().equals(Gui.inv1))){
            return;
        }
        e.setCancelled(true);
        if(e.getRawSlot() == -999){
            return;
        }
        if (e.getRawSlot()  < 0 || e.getRawSlot() < e.getInventory().getSize()) {
            if (clickedItem == null){
                return;
            }
            Actions(e.getRawSlot(),player);

            String testtttt = "1";
            int tss = Integer.parseInt(testtttt);
            for(String[] a:Guild_data){
                if(a[0].equals(e.getCurrentItem().getItemMeta().getDisplayName())){
                    player.sendRawMessage("获取到工会");
                }

            }
            player.sendRawMessage(e.getCurrentItem().getItemMeta().getDisplayName().replace("§","&"));
            player.sendRawMessage(String.valueOf(e.getRawSlot()));
        }
    }
    public void Actions(int slot,Player player){
        for (String[] Menu : MenuList){
            List<Integer> getSlot = config.getIntegerList("icon."+Menu[3]+".slot");
            for(int i : getSlot){
                if(i == slot){
                    List<String> ActionCommand = config.getStringList("icon."+Menu[3]+".action");
                    for(String a : ActionCommand){
                        if(a.equalsIgnoreCase("[test]")){
                            player.sendRawMessage("获取到[test]");
                        }else{
                            String b = PlaceholderAPI.setPlaceholders(player,a);
                            OpPerformCommand(player,b);
                        }
                    }
                }
            }
        }
    }
}
