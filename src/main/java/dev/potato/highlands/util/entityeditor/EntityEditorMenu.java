package dev.potato.highlands.util.entityeditor;

import dev.potato.highlands.util.TextUtil;
import dev.potato.highlands.util.menu.ItemUtil;
import dev.potato.highlands.util.menu.Menu;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Pose;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Fold some methods while reading this for your own safety.
public class EntityEditorMenu extends Menu {

    private final Entity entity;
    private final Pose[] poses = Pose.values();

    public EntityEditorMenu(Entity entity) {
        super(27, TextUtil.translateLegacy("&7Entity Editor"));
        this.entity = entity;

        setItem(0, glowButton(), ((p, event) -> {
            entity.setGlowing(!entity.isGlowing());
            setItem(0, glowButton());
        }));
        setItem(1, silentButton(), ((p, event) -> {
            entity.setSilent(!entity.isSilent());
            setItem(1, silentButton());
        }));
        setItem(2, invulnerableButton(), ((p, event) -> {
            entity.setInvulnerable(!entity.isInvulnerable());
            setItem(2, invulnerableButton());
        }));
        setItem(3, gravityButton(), ((p, event) -> {
            entity.setGravity(!entity.hasGravity());
            setItem(3, gravityButton());
        }));
        setItem(4, poseButton(), ((p, event) -> {
            if(event.isLeftClick()) entity.setPose(Objects.requireNonNull(nextPose()));
            if (event.isRightClick()) entity.setPose(Objects.requireNonNull(prevPose()));
            setItem(4, poseButton());
        }));
        setItem(5, persistentButton(), ((p, event) -> {
            entity.setPersistent(!entity.isPersistent());
            setItem(5, persistentButton());
        }));
        setItem(26, ItemUtil.create(Material.BARRIER, TextUtil.translateLegacy("&4DELETE")), ((p, event) -> {
            entity.remove();
            close(p);
        }));
    }

    public ItemStack glowButton() {
        return entity.isGlowing() ? ItemUtil.create(Material.LIGHT, TextUtil.translateLegacy("&6Glowing"), List.of(TextUtil.translateLegacy("&atrue")))
                : ItemUtil.create(Material.LIGHT, TextUtil.translateLegacy("Glowing"), List.of(TextUtil.translateLegacy("&cfalse")));
    }
    public ItemStack silentButton() {
        return entity.isSilent() ? ItemUtil.create(Material.BELL, TextUtil.translateLegacy("&6Silent"), List.of(TextUtil.translateLegacy("&atrue")))
                : ItemUtil.create(Material.BELL, TextUtil.translateLegacy("&6Silent"), List.of(TextUtil.translateLegacy("&cfalse")));
    }
    public ItemStack invulnerableButton() {
        return entity.isInvulnerable() ? ItemUtil.create(Material.ANVIL, TextUtil.translateLegacy("&6Invulnerable"), List.of(TextUtil.translateLegacy("&atrue")))
                : ItemUtil.create(Material.CHIPPED_ANVIL, TextUtil.translateLegacy("&6Invulnerable"), List.of(TextUtil.translateLegacy("&cfalse")));
    }
    public ItemStack gravityButton() {
        return entity.hasGravity() ? ItemUtil.create(Material.SAND, TextUtil.translateLegacy("&6Gravity"), List.of(TextUtil.translateLegacy("&atrue")))
                : ItemUtil.create(Material.STONE, TextUtil.translateLegacy("&6Gravity"), List.of(TextUtil.translateLegacy("&cfalse")));
    }
    public ItemStack persistentButton() {
        return entity.isPersistent() ? ItemUtil.create(Material.WATER_BUCKET, TextUtil.translateLegacy("&6Persistent"), List.of(TextUtil.translateLegacy("&atrue")))
                : ItemUtil.create(Material.BUCKET, TextUtil.translateLegacy("&6Persistent"), List.of(TextUtil.translateLegacy("&cfalse")));
    }

    public ItemStack poseButton() {
        ItemStack item = ItemUtil.create(Material.ARMOR_STAND, TextUtil.translateLegacy("&6Pose"));
        ItemMeta meta = item.getItemMeta();
        List<Component> lore = new ArrayList<>();
        for(Pose pose : poses) {
            if(entity.getPose() == pose) {
                lore.add(TextUtil.translateLegacy("&7>> " + pose.name().toLowerCase()));
            }else {
                lore.add(TextUtil.translateLegacy("&8> " + pose.name().toLowerCase()));
            }
        }
        meta.lore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private Pose prevPose() {
        for (int i = 0; i < poses.length; i++) {
            if (entity.getPose() == poses[i]) {
                int prevIndex = (i + poses.length - 1) % poses.length;
                return poses[prevIndex];
            }
        }
        return null;
    }
    private Pose nextPose() {
        for (int i = 0; i < poses.length; i++) {
            if (entity.getPose() == poses[i]) {
                int nextIndex = (i + 1) % poses.length;
                return poses[nextIndex];
            }
        }
        return null;
    }
}
