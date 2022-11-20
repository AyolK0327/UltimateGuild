package me.ayolk.ultimateguild.Listeners;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class testEvent implements Listener {
    private Plugin plugin;

    public testEvent(Plugin plugin){
        this.plugin = plugin;
    }
    @EventHandler
    public void blockevet(BlockBreakEvent event){
        List<MetadataValue> test = event.getBlock().getMetadata("1");
        event.getPlayer().sendRawMessage(test.toString());
    }
    @EventHandler
    public void endCrystalLeftEvent(EntityDamageEvent event){
        if(event.getEntity().getType().equals(EntityType.ENDER_CRYSTAL)){
            event.setCancelled(true);
            plugin.getServer().getPlayer("Prob1tX_x").sendRawMessage("aaa");
        }
    }
    @EventHandler
    public void endCrystalRightEvent(PlayerInteractEntityEvent event){
        if(event.getHand().equals(EquipmentSlot.OFF_HAND)){
            return;
        }
        if(event.getRightClicked().getType().equals(EntityType.ENDER_CRYSTAL)){
            Location loc = event.getRightClicked().getLocation();
            event.getPlayer().sendRawMessage(loc.getX()+"|"+loc.getY()+"|"+loc.getZ());
            event.getPlayer().sendRawMessage("bbb");
        }
    }
}
