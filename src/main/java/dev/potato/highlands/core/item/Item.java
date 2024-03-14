package dev.potato.highlands.core.item;

import dev.potato.highlands.core.item.components.Lore;
import dev.potato.highlands.core.item.components.Material;
import dev.potato.highlands.core.item.components.Rarity;
import dev.potato.highlands.util.Color;
import dev.potato.highlands.util.TextUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Item {

    private final ItemStack itemStack;

    public Item(String name, List<String> description, Rarity rarity, Material material) {
        ItemStack item = new ItemStack(material.getBukkitMaterial());
        ItemMeta meta = item.getItemMeta();

        meta.displayName(TextUtil.toComponent(name).color(rarity.getColor().getTextColor()));
        meta.lore(addLore(rarity, description).getLore());
        meta.setUnbreakable(true);
        meta.setCustomModelData(material.getCustomModelData());
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        item.setItemMeta(meta);
        this.itemStack = item;
    }

    public Item(String name, List<String> description, Rarity rarity, org.bukkit.Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(TextUtil.toComponent(name).color(rarity.getColor().getTextColor()));
        meta.lore(addLore(rarity, description).getLore());
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        item.setItemMeta(meta);
        this.itemStack = item;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public static Lore addLore(Rarity rarity, List<String> description) {
        Lore lore = new Lore();
        lore.addLine(Component.empty());
        lore.addLine(rarity.getName());
        for(String str : description) {
            lore.addLine(TextUtil.toComponent(str).color(Color.DARK_GRAY.getTextColor()));
        }
        return lore;
    }

}
