package dev.potato.highlands.core.admin;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class AdminState {
    private static final HashMap<UUID, State> stateMap = new HashMap<>();

    public static boolean isInGodMode(Player player) {
        if(!stateMap.containsKey(player.getUniqueId())) return false;
        return stateMap.get(player.getUniqueId()) == State.GOD_MODE;
    }

    public static boolean isInBuildMode(Player player) {
        if(!stateMap.containsKey(player.getUniqueId())) return false;
        return stateMap.get(player.getUniqueId()) == State.BUILD_MODE;
    }

    public static void setState(Player player, State state) {
        if(state == null) {
            stateMap.remove(player.getUniqueId());
            return;
        }
        stateMap.put(player.getUniqueId(), state);
    }

    public static State getState(Player player) {
        if(!stateMap.containsKey(player.getUniqueId())) return State.DEFAULT;
        return stateMap.get(player.getUniqueId());
    }

    public enum State {
        DEFAULT("Default"),
        GOD_MODE("God Mode"),
        BUILD_MODE("Build Mode");

        public final String humanName;

        State(String humanName) {
            this.humanName = humanName;
        }
    }


}
