package dev.potato.highlands.core.spell.trigger;

import org.bukkit.event.block.Action;

public enum TriggerAction {
    LLL(new Action[]{Action.LEFT_CLICK_AIR, Action.LEFT_CLICK_AIR, Action.LEFT_CLICK_AIR}, new char[]{'L', 'L', 'L'}),
    LLR(new Action[]{Action.LEFT_CLICK_AIR, Action.LEFT_CLICK_AIR, Action.RIGHT_CLICK_AIR}, new char[]{'L', 'L', 'R'}),
    LRL(new Action[]{Action.LEFT_CLICK_AIR, Action.RIGHT_CLICK_AIR, Action.LEFT_CLICK_AIR}, new char[]{'L', 'R', 'L'}),
    LRR(new Action[]{Action.LEFT_CLICK_AIR, Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_AIR}, new char[]{'L', 'R', 'R'}),
    RRR(new Action[]{Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_AIR}, new char[]{'R', 'R', 'R'}),
    RRL(new Action[]{Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_AIR, Action.LEFT_CLICK_AIR}, new char[]{'R', 'R', 'L'}),
    RLR(new Action[]{Action.RIGHT_CLICK_AIR, Action.LEFT_CLICK_AIR, Action.RIGHT_CLICK_AIR}, new char[]{'R', 'L', 'R'}),
    RLL(new Action[]{Action.RIGHT_CLICK_AIR, Action.LEFT_CLICK_AIR, Action.LEFT_CLICK_AIR}, new char[]{'R', 'L', 'L'});

    private final Action[] actions;
    private final char[] characters;

    TriggerAction(Action[] actions, char[] characters) {
        this.actions = actions;
        this.characters = characters;
    }

    public Action[] getActions() {
        return actions;
    }

    public char[] getCharacters() {
        return characters;
    }
}

