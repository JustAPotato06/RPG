package dev.potato.highlands.listeners;

import dev.potato.highlands.core.admin.AdminState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class WorldModificationListener implements Listener {

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        AdminState.State state = AdminState.getState(player);

        switch (state) {
            case GOD_MODE, BUILD_MODE -> event.setCancelled(false);
            default -> event.setCancelled(true);
        }
    }

    @EventHandler
    public void blockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        AdminState.State state = AdminState.getState(player);

        switch (state) {
            case GOD_MODE, BUILD_MODE -> event.setCancelled(false);
            default -> event.setCancelled(true);
        }
    }

    @EventHandler
    public void entityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if(!(entity instanceof Player player)) return;
        AdminState.State state = AdminState.getState(player);

        switch (state) {
            case GOD_MODE -> event.setCancelled(true);
            default -> event.setCancelled(false);
        }
    }
}
