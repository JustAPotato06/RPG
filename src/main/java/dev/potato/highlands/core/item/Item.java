package dev.potato.highlands.core.item;

import dev.potato.highlands.core.item.components.Lore;
import dev.potato.highlands.core.item.components.Material;
import dev.potato.highlands.core.item.components.Rarity;
import dev.potato.highlands.util.Color;
import dev.potato.highlands.util.Keys;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class Item {

    private final ItemStack itemStack;

    // Constructor for custom Materials
    public Item(Component name, List<Component> description, Rarity rarity, Material material) {
        ItemStack item = new ItemStack(material.getBukkitMaterial());
        ItemMeta meta = item.getItemMeta();

        meta.displayName(name.color(rarity.getColor().getTextColor()).decoration(TextDecoration.ITALIC, false)); // Name Set
        meta.lore(addLore(rarity, description).getLore()); //Lore Set
        meta.setUnbreakable(true); // Set Unbreakable
        meta.setCustomModelData(material.getCustomModelData()); // Set CustomModelData
        meta.getPersistentDataContainer().set(material.getKey(), PersistentDataType.STRING, ""); // Set PDC
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES); // Hide flag attributes
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE); // Hide flag unbreakable

        item.setItemMeta(meta);
        this.itemStack = item;
    }

    // Constructor for bukkit Materials
    public Item(Component name, List<Component> description, Rarity rarity, org.bukkit.Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(name.color(rarity.getColor().getTextColor()).decoration(TextDecoration.ITALIC, false)); // Name Set
        meta.lore(addLore(rarity, description).getLore()); //Lore Set
        meta.setUnbreakable(true); // Set Unbreakable
        meta.getPersistentDataContainer().set(Keys.CUSTOM_ITEM_KEY, PersistentDataType.STRING, ""); // Set PDC
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);// Hide flag attributes
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE); // Hide flag unbreakable

        item.setItemMeta(meta);
        this.itemStack = item;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    protected static Lore addLore(Rarity rarity, List<Component> description) {
        Lore lore = new Lore();
        lore.addLine(Component.empty());
        lore.addLine(rarity.getName());
        for(Component component : description) {
            lore.addLine(component.color(Color.DARK_GRAY.getTextColor()));
        }
        return lore;
    }

}
