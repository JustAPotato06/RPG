package dev.potato.highlands.core.item.components;

public enum Material {
    CLOUD_DUST(1, org.bukkit.Material.PAPER);

    private final int customModelData;
    private final org.bukkit.Material bukkitMaterial;

    Material(int customModelData, org.bukkit.Material bukkitMaterial) {
        this.customModelData = customModelData;
        this.bukkitMaterial = bukkitMaterial;
    }

    public org.bukkit.Material getBukkitMaterial() {
        return bukkitMaterial;
    }

    public int getCustomModelData() {
        return customModelData;
    }
}
