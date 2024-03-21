package dev.potato.highlands.core.spell.trigger.util;

import dev.potato.highlands.core.spell.trigger.TriggerAction;
import dev.potato.highlands.util.TextUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class ClickData {

    private static final double CLICK_DELAY = 2;
    private static final HashMap<UUID, Long> lastClick = new HashMap<>();
    private static final HashMap<UUID, Action[]> clickCombo = new HashMap<>();

    @Nullable
    public static TriggerAction updateClick(Player player, Action action) {
        if((double) getLastClick(player) / 1000 > CLICK_DELAY ) {
            lastClick.remove(player.getUniqueId());
            clickCombo.remove(player.getUniqueId());
        }

        lastClick.put(player.getUniqueId(), System.currentTimeMillis());

        if (!clickCombo.containsKey(player.getUniqueId())) {
            clickCombo.put(player.getUniqueId(), new Action[3]);
        }

        Action[] actions = clickCombo.get(player.getUniqueId());

        StringBuilder builder = new StringBuilder();
        boolean comboFull = false;
        boolean justSet = false;

        if(actions[0] == null) {
            actions[0] = action;
            justSet = true;
            clickCombo.put(player.getUniqueId(), actions);

            // Send ActionBar
            builder.append("<u>")
                    .append(getChar(actions[0]))
                    .append("</u>")
                    .append(" ")
                    .append("<u>?</u>")
                    .append(" ")
                    .append("<u>?</u>");
            player.sendActionBar(TextUtil.translate(builder.toString()));
        }
        if(actions[1] == null && !justSet) {
            actions[1] = action;
            justSet = true;
            clickCombo.put(player.getUniqueId(), actions);

            // Send ActionBar
            builder.append("<u>")
                    .append(getChar(actions[0]))
                    .append("</u>")
                    .append(" ")
                    .append("<u>")
                    .append(getChar(actions[1]))
                    .append("</u>")
                    .append(" ")
                    .append("<u>?</u>");
            player.sendActionBar(TextUtil.translate(builder.toString()));
        }
        if(actions[2] == null && !justSet) {
            actions[2] = action;
            comboFull = true;
            clickCombo.put(player.getUniqueId(), actions);

            // Send ActionBar
            builder.append("<u>")
                    .append(getChar(actions[0]))
                    .append("</u>")
                    .append(" ")
                    .append("<u>")
                    .append(getChar(actions[1]))
                    .append("</u>")
                    .append(" ")
                    .append("<u>")
                    .append(getChar(actions[2]))
                    .append("</u>");
            player.sendActionBar(TextUtil.translate(builder.toString()));
        }

        if (comboFull) {
            for (TriggerAction triggerAction : TriggerAction.values()) {
                if (Arrays.equals(triggerAction.getActions(), actions)) {
                    lastClick.remove(player.getUniqueId());
                    clickCombo.remove(player.getUniqueId());
                    return triggerAction;
                }
            }
        }

        return null;
    }

    private static long getLastClick(Player player) {
        return System.currentTimeMillis() - lastClick.getOrDefault(player.getUniqueId(), 0L);
    }

    private static char getChar(Action action) {
        if(action == Action.LEFT_CLICK_AIR) return 'L';
        else return 'R';
    }
}
