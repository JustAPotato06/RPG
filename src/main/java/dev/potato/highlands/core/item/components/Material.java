package dev.potato.highlands.core.item.components;

import dev.potato.highlands.RPG;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public enum Material {

    CLOUD_DUST(777001, org.bukkit.Material.PAPER, new NamespacedKey(RPG.plugin, "highlands_cloud_dust_key")),
    GENERIC_WOODEN_SWORD(777002, org.bukkit.Material.WOODEN_SWORD, new NamespacedKey(RPG.plugin, "highlands_generic_wooden_sword_key"));

    private final int customModelData;
    private final org.bukkit.Material bukkitMaterial;
    private final NamespacedKey key;
    private final JavaPlugin plugin = RPG.plugin;

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