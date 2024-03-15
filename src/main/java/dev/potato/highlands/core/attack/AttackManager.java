package dev.potato.highlands.core.attack;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class AttackManager {

    private static AttackManager manager;
    private AttackManager(){}
    public static AttackManager getManager() {return manager == null ? manager = new AttackManager() : manager;}


    public void handlePlayerAttackMob(Player player, LivingEntity entity) {

    }

    public void handleMobAttackPlayer(LivingEntity entity, Player player ) {

    }

}
