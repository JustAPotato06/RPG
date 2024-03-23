package dev.potato.highlands.util.entityeditor;

import dev.potato.highlands.util.TextUtil;
import dev.potato.highlands.util.menu.ItemUtil;
import dev.potato.highlands.util.menu.Menu;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pose;
import org.bukkit.inventory.ItemStack;

import java.util.List;

// Fold some methods while reading this for your own safety.
public class LivingEntityEditorMenu extends Menu {

    private final LivingEntity entity;
    private final Pose[] poses = Pose.values();

    public LivingEntityEditorMenu(LivingEntity entity, EntityEditorMenu entityMenu) {
        super(27, TextUtil.translateLegacy("&7Living Entity Editor: " + entity.getType().name()));
        this.entity = entity;

        setItem(0, aiButton(), ((p, event) -> {
            entity.setAI(!entity.hasAI());
            setItem(0, aiButton());
        }));
        setItem(1, collidableButton(), ((p, event) -> {
            entity.setCollidable(!entity.isCollidable());
            setItem(1, collidableButton());
        }));
        setItem(2, despawnButton(), ((p, event) -> {
            entity.setRemoveWhenFarAway(!entity.getRemoveWhenFarAway());
            setItem(2, despawnButton());
        }));
        setItem(18, ItemUtil.create(Material.GREEN_CONCRETE, TextUtil.translateLegacy("&7Entity")), ((p, event) -> {
            entityMenu.open(p);
            entityMenu.setItem(18, ItemUtil.create(Material.GREEN_CONCRETE, TextUtil.translateLegacy("&8Living Entity")), ((p1, event1) -> this.open(p1)));
        }));
        setItem(26, ItemUtil.create(Material.BARRIER, TextUtil.translateLegacy("&4DELETE")), ((p, event) -> {
            entity.remove();
            close(p);
        }));
    }

    public ItemStack aiButton() {
        return entity.hasAI() ? ItemUtil.create(Material.PINK_WOOL, TextUtil.translateLegacy("&6AI"), List.of(TextUtil.translateLegacy("&atrue")))
                : ItemUtil.create(Material.PINK_WOOL, TextUtil.translateLegacy("&6AI"), List.of(TextUtil.translateLegacy("&cfalse")));
    }
    public ItemStack collidableButton() {
        return entity.isCollidable() ? ItemUtil.create(Material.TINTED_GLASS, TextUtil.translateLegacy("&6Collidable"), List.of(TextUtil.translateLegacy("&atrue")))
                : ItemUtil.create(Material.GLASS, TextUtil.translateLegacy("&6Collidable"), List.of(TextUtil.translateLegacy("&cfalse")));
    }
    public ItemStack despawnButton() {
        return entity.getRemoveWhenFarAway() ? ItemUtil.create(Material.RED_STAINED_GLASS, TextUtil.translateLegacy("&6Despawn when Far"), List.of(TextUtil.translateLegacy("&atrue")))
                : ItemUtil.create(Material.WHITE_STAINED_GLASS, TextUtil.translateLegacy("&6Despawn when Far"), List.of(TextUtil.translateLegacy("&cfalse")));
    }
}
