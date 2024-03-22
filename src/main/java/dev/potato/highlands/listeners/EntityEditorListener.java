package dev.potato.highlands.listeners;

import dev.potato.highlands.core.admin.AdminState;
import dev.potato.highlands.util.Keys;
import dev.potato.highlands.util.entityeditor.EntityEditorMenu;
import dev.potato.highlands.util.entityeditor.LivingEntityEditorMenu;
import dev.potato.highlands.util.entityeditor.display.DisplayEntityMenu;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.Collection;

public class EntityEditorListener implements Listener {

    @EventHandler
    public void onClickDisplay(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if(!player.isSneaking()) return;
        if(!AdminState.isInEntityEditorMode(player)) return;
        if (event.getHand() != EquipmentSlot.HAND) return;
        if(player.getInventory().getItemInMainHand().getType().isAir()) return;

        if(event.getRightClicked() instanceof Player) return;

        if(event.getRightClicked() instanceof LivingEntity entity) {
            new LivingEntityEditorMenu(entity, new EntityEditorMenu(entity)).open(player);
        }

    }

    @EventHandler
    public void onPlaceDisplay(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(!player.isSneaking()) return;
        if(!AdminState.isInEntityEditorMode(player)) return;
        if(event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (event.getHand() != EquipmentSlot.HAND) return;
        Location location = event.getInteractionPoint();
        if(location == null) return;
        if(player.getInventory().getItemInMainHand().getType().isAir()) return;

        // Detect Displays since they cant be detected by clicking on them (No hitboxes)
        // TODO: Create individual classes to handle each display type
        if(player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(Keys.ENTITY_EDITOR)) {
            Collection<Entity> entities = location.getNearbyEntities(1,1,1);
            for(Entity entity: entities) {
                if(entity instanceof BlockDisplay blockDisplay) {
                    new DisplayEntityMenu(blockDisplay, new EntityEditorMenu(blockDisplay)).open(player);
                    return;
                }
                if(entity instanceof ItemDisplay itemDisplay) {
                    new DisplayEntityMenu(itemDisplay, new EntityEditorMenu(itemDisplay)).open(player);
                    return;
                }
                if(entity instanceof TextDisplay textDisplay) {
                    new DisplayEntityMenu(textDisplay, new EntityEditorMenu(textDisplay)).open(player);
                    return;
                }
            }
            return;
        }
        // Detect end

        if(player.getInventory().getItemInMainHand().getType().isBlock()) {
            BlockDisplay blockDisplay = (BlockDisplay) player.getWorld().spawnEntity(location, EntityType.BLOCK_DISPLAY);
            blockDisplay.setBlock(player.getInventory().getItemInMainHand().getType().createBlockData());
        }

        if(player.getInventory().getItemInMainHand().getType().isItem()) {
            ItemDisplay itemDisplay = (ItemDisplay) player.getWorld().spawnEntity(location, EntityType.ITEM_DISPLAY);
            itemDisplay.setItemStack(player.getInventory().getItemInMainHand());
        }
    }
}
