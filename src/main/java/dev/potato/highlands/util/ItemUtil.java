package dev.potato.highlands.util;

import dev.potato.highlands.core.item.Item;
import dev.potato.highlands.core.item.components.Lore;
import dev.potato.highlands.core.item.components.Material;
import dev.potato.highlands.core.item.components.Rarity;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemUtil {

    public static Item fromItemStack(ItemStack itemStack) {
        if(itemStack.getItemMeta() == null) return null;
        Component name = itemStack.getItemMeta().displayName();

        Material material = getMaterial(itemStack);
        Lore lore = getLore(itemStack);
        Rarity rarity = getRarity(itemStack);

        if(material == null) {
            org.bukkit.Material bukkitMaterial = getBukkitMaterial(itemStack);
            if(name == null) return null;
            if(bukkitMaterial == null) return null;
            if(lore == null) return null;
            if(rarity == null) return null;
            return new Item(name, getDescription(itemStack), rarity, bukkitMaterial);
        }else {
            if(name == null) return null;
            if(lore == null) return null;
            if(rarity == null) return null;
            return new Item(name, getDescription(itemStack), rarity, material);
        }
    }

    @Nullable
    public static Material getMaterial(ItemStack item) {
        if(item.getItemMeta() == null) return null;
        if(item.getItemMeta().getPersistentDataContainer().isEmpty()) return null;
        if(item.getItemMeta().getPersistentDataContainer().has(Keys.CUSTOM_ITEM_KEY)) return null; // key is for a custom item but not for a custom material

        ArrayList<Material> materials = new ArrayList<>(Arrays.asList(Material.values()));
        for (Material material : materials) {
            NamespacedKey key = material.getKey();
            int data = material.getCustomModelData();
            if(item.getItemMeta().getPersistentDataContainer().has(key) && item.getItemMeta().getCustomModelData() == data) return material;
        }

        return null;
    }

    @Nullable
    public static org.bukkit.Material getBukkitMaterial(ItemStack item) {
        if(item.getItemMeta() == null) return null;
        if(item.getItemMeta().getPersistentDataContainer().isEmpty()) return null;
        if(!item.getItemMeta().getPersistentDataContainer().has(Keys.CUSTOM_ITEM_KEY)) return null;
        return item.getType();
    }

    @Nullable
    public static Rarity getRarity(ItemStack item) {
        Lore lore = getLore(item);
        if(lore == null) return null;
        ArrayList<Rarity> rarities = new ArrayList<>(Arrays.asList(Rarity.values()));

        for(Rarity rarity : rarities) {
            for (Component component : lore.getLore()) {
                if(rarity.getName().equals(component)) return rarity;
            }
        }
        return null;
    }

    @Nullable
    public static Lore getLore(ItemStack item) {
        if(item.getItemMeta() == null) return null;
        if(item.getItemMeta().lore() == null) return null;

        List<Component> loreList = item.getItemMeta().lore();
        if (loreList == null) return null;
        ArrayList<Component> arrayListLore = new ArrayList<>(loreList);

        return new Lore(arrayListLore);
    }

    @Nullable
    public static List<Component> getDescription(ItemStack item) {
        int found = 0;
        Lore lore = getLore(item);
        if(lore == null) return null;
        ArrayList<Component> description = new ArrayList<>();

        Rarity rarity = getRarity(item);
        if (rarity == null) return null;
        for (Component component : lore.getLore()) {
            if(found == 1) {
                description.add(component);
            }
            if(rarity.getName().equals(component) && found != 1) found = 1;

            }

        return description;
    }
}
