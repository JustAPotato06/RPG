package dev.potato.highlands.util;

import java.util.HashMap;
import java.util.UUID;

public class Cooldown {
    private final HashMap<UUID, Long> cooldowns;

    public Cooldown() {
        this.cooldowns = new HashMap<>();
    }

    public void setCooldown(UUID playerId, long cooldownSeconds) {
        cooldowns.put(playerId, System.currentTimeMillis() + cooldownSeconds * 1000);
    }

    public boolean isOnCooldown(UUID playerId) {
        return cooldowns.containsKey(playerId) && System.currentTimeMillis() <= cooldowns.get(playerId);
    }

    public long getRemainingCooldownSecs(UUID playerId) {
        return getRemainingCooldownMillis(playerId) / 1000;
    }

    public long getRemainingCooldownMillis(UUID playerId) {
        if (cooldowns.containsKey(playerId)) {
            long elapsedTime = cooldowns.get(playerId) - System.currentTimeMillis();
            return Math.max(0, elapsedTime);
        }
        return 0;
    }
}