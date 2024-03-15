package dev.potato.highlands.core.item.components;

import dev.potato.highlands.RPG;
import org.bukkit.NamespacedKey;

public enum Material {

    CLOUD_DUST(1, org.bukkit.Material.PAPER, new NamespacedKey(RPG.plugin, "highlands_cloud_dust_key"));

    private final int customModelData;
    private final org.bukkit.Material bukkitMaterial;
    private final NamespacedKey key;

    Material(int customModelData, org.bukkit.Material bukkitMaterial, NamespacedKey key) {
        this.customModelData = customModelData;
        this.bukkitMaterial = bukkitMaterial;
        this.key = key;
    }

    public org.bukkit.Material getBukkitMaterial() {
        return bukkitMaterial;
    }

    public int getCustomModelData() {
        return customModelData;
    }

    public NamespacedKey getKey() {
        return key;
    }
}
