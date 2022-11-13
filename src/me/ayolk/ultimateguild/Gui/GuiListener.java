package me.ayolk.ultimateguild.Gui;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;


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
        InventoryView inv = player.getOpenInventory();
        ItemStack clickedItem = e.getCurrentItem();
        if (inv.getTitle().equals(configuration.getString("name").replace("&","§"))) {
            // 通过标题区分 GUI
            e.setCancelled(true);
            if(e.getRawSlot() == -999){
                return;
            }
            if (e.getRawSlot()  < 0 || e.getRawSlot() < e.getInventory().getSize()) {
                if (clickedItem == null){
                    return;
                }
                // 这个方法来源于 Bukkit Development Note
                // 如果在合理的范围内，getRawSlot 会返回一个合适的编号（0 ~ 物品栏大小-1）
                if(e.getRawSlot() == 0) {
                    //执行指令
                    Bukkit.dispatchCommand(player,"ugadmin test");

                }else{
                    return;
                }
                player.sendRawMessage(String.valueOf(e.getRawSlot()));
                return;
                // 结束处理，使用 return 避免了多余的 else
            }
            // 获取被点的物品

        }
        // 后续处理
    }
}
