package dev.potato.highlands.listeners;

import dev.potato.highlands.core.item.Item;
import dev.potato.highlands.core.spell.trigger.TriggerAction;
import dev.potato.highlands.core.spell.trigger.util.ClickData;
import dev.potato.highlands.events.ClickTriggerEvent;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ClickTriggerListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(player.getInventory().getItemInMainHand().getType() == Material.AIR) return;
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if(Item.owningClassFromItemStack(itemStack) == null) return; // event is only called if item belongs to an RpgClass
        Item item = Item.fromItemStack(itemStack);
        if(item == null) return;

        Action action = event.getAction();
        switch (action) {
            case LEFT_CLICK_AIR, RIGHT_CLICK_AIR -> { // Do not add any other action type without properly handling in ClickData
                TriggerAction triggerAction = ClickData.updateClick(player, action);
                if(triggerAction == null) return;
                if(Item.owningClassFromItemStack(itemStack) == null) return; // event is only called if item belongs to an RpgClass
                new ClickTriggerEvent(player, triggerAction, item).callEvent();
            }
        }
    }


    @EventHandler // DELETE ME: For testing purposes, delete when not needed
    public void onTrigger(ClickTriggerEvent event) {
        TriggerAction triggerAction = event.getTriggerAction();
        if(triggerAction == TriggerAction.RRR) {
            event.getPlayer().setVelocity(event.getPlayer().getLocation().getDirection().multiply(2));
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1, 1);
        }
    }

}
