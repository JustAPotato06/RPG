package dev.potato.highlands.events;

import dev.potato.highlands.core.item.Item;
import dev.potato.highlands.core.spell.trigger.TriggerAction;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ClickTriggerEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;
    private final Player player;
    private  final TriggerAction action;
    private final Item item;

    public ClickTriggerEvent(Player player, TriggerAction action, Item item) {
        this.player = player;
        this.action = action;
        this.item = item;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }
    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    /**
     * Get the player involved with this event
     * @return the player that triggered this event
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Get the enum representing the trigger sequence
     * in the event
     * @return the trigger sequence
     */
    public TriggerAction getTriggerAction() {
        return action;
    }

    /**
     * Get the item involved with this event
     * @return the Item used to trigger this event
     */
    public Item getItem() {
        return item;
    }
}
