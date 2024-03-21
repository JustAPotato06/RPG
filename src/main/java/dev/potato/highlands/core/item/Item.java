package dev.potato.highlands.core.item;

import com.google.common.collect.Multimap;
import dev.potato.highlands.core.item.components.Material;
import dev.potato.highlands.core.item.components.Rarity;
import dev.potato.highlands.core.rpgclass.RpgClass;
import dev.potato.highlands.util.Color;
import dev.potato.highlands.util.TextUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// FIXME: Find a cleaner alternative to add attributes without using complex data structures
//  in constructor #see(HashMap<Attribute, AttributeModifier> attributes)
public class Item {

    private final ItemStack item;
    private Class<? extends RpgClass> owningClass;
    private List<String> description;
    private Rarity rarity;
    private HashMap<Attribute, AttributeModifier> attributes;

    public Item(@NotNull Component name,
                @NotNull Material material,
                @Nullable Class<? extends RpgClass> owningClass,
                @Nullable List<String> description,
                @Nullable Rarity rarity,
                @Nullable HashMap<Attribute, AttributeModifier> attributes) {
        ItemStack item = new ItemStack(material.getBukkitMaterial());
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();

        StringBuilder pdcData = new StringBuilder();

        if(owningClass != null) {
            pdcData.append("Class:").append(owningClass.getName()).append(";");
            this.owningClass = owningClass;
        }

        if(rarity != null) {
            pdcData.append("Rarity:").append(rarity.name()).append(";");
            meta.displayName(name.color(rarity.getColor().getTextColor()).decoration(TextDecoration.ITALIC, false));
            this.rarity = rarity;
        }else {
            meta.displayName(name.decoration(TextDecoration.ITALIC, false));
        }

        if (description != null) {
            pdcData.append("Description:");
            for (String desc : description) {
                pdcData.append(desc).append("`");
            }
            pdcData.append(";");
            this.description = description;
        }

        if(attributes != null) {
            for (Map.Entry<Attribute, AttributeModifier> entry : attributes.entrySet()) {
                Attribute attribute = entry.getKey();
                AttributeModifier modifier = entry.getValue();

                meta.addAttributeModifier(attribute, modifier);
            }
            this.attributes = attributes;
        }

        data.set(material.getKey(), PersistentDataType.STRING, pdcData.toString());

        meta.setUnbreakable(true);
        //meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.lore(processLore(owningClass, description, rarity, attributes));
        meta.setCustomModelData(material.getCustomModelData());

        item.setItemMeta(meta);
        this.item = item;
    }

    @NotNull
    public ItemStack getItemStack() {
        return item;
    }

    @Nullable
    public Class<? extends RpgClass> getOwningClass() {
        return owningClass;
    }

    public List<String> getDescription() {
        return description;
    }

    @Nullable
    public Rarity getRarity() {
        return rarity;
    }

    @Nullable
    public HashMap<Attribute, AttributeModifier> getAttribute() {
        return attributes;
    }

    // FIXME: Better lore organisation (User friendly)
    protected static List<Component> processLore(@Nullable Class<? extends RpgClass> owningClass,
                                               @Nullable List<String> description,
                                               @Nullable Rarity rarity,
                                               @Nullable HashMap<Attribute, AttributeModifier> attributes) {
        List<Component> lore = new ArrayList<>();

        if (attributes != null && !attributes.isEmpty()) {
            lore.add(Component.empty());
            for (Map.Entry<Attribute, AttributeModifier> entry : attributes.entrySet()) {
                if(entry == null) continue;
                Attribute attribute = entry.getKey();
                AttributeModifier value = entry.getValue();

                lore.add(TextUtil.toComponent(toReadableString(attribute) +" "+ value.getAmount()).color(rarity != null ? rarity.getColor().getTextColor() : Color.RED.getTextColor()));
            }
        }

        if (owningClass != null) {
            lore.add(Component.empty());
            lore.add(TextUtil.toComponent( "Class Req: " + owningClass.getSimpleName()).color(Color.GRAY.getTextColor()));
        }

        if (description != null && !description.isEmpty()) {
            lore.add(Component.empty());
            for (String desc : description) {
                lore.add(TextUtil.toComponent(desc).color(Color.DARK_GRAY.getTextColor()));
            }
        }

        if (rarity != null) {
            lore.add(Component.empty());
            lore.add(rarity.getName());
        }

        return lore;
    }

    public static boolean isRpgItem(ItemStack item) {
        if(item.getItemMeta() == null) return false;
        int data = item.getItemMeta().getCustomModelData();
        return Integer.toString(data).startsWith("777") && !item.getItemMeta().getPersistentDataContainer().isEmpty();
    }

    public static Item fromItemStack(ItemStack item) {
        if (item.getItemMeta() == null) return null;
        Component displayName = item.getItemMeta().displayName();
        Material material = materialFromItemStack(item);
        if(displayName == null || material == null) return null;

        return new Item(
                displayName,
                material,
                owningClassFromItemStack(item),
                descriptionFromItemStack(item),
                rarityFromItemStack(item),
                attributesFromItemStack(item));
    }

    @Nullable
    public static Class<? extends RpgClass> owningClassFromItemStack(ItemStack item) {
        if (item.getItemMeta() == null) return null;
        Material material = materialFromItemStack(item);
        if (material == null) return null;

        PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
        String pdcData = data.getOrDefault(material.getKey(), PersistentDataType.STRING, "");
        String[] parts = pdcData.split(";");
        for (String part : parts) {
            String[] keyValue = part.split(":");
            if (keyValue.length == 2 && keyValue[0].equals("Class")) {
                try {
                    return (Class<? extends RpgClass>) Class.forName(keyValue[1]);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace(System.out);
                }
            }
        }
        return null;
    }

    public static Rarity rarityFromItemStack(ItemStack item) {
        if (item.getItemMeta() == null) return null;
        Material material = materialFromItemStack(item);
        if (material == null) return null;
        String pdcVal = item.getItemMeta().getPersistentDataContainer().get(material.getKey(), PersistentDataType.STRING);
        if(pdcVal == null) return null;

        String[] parts = pdcVal.split(";");
        for (String part : parts) {
            String[] keyValue = part.split(":");
            if (keyValue.length == 2 && keyValue[0].equals("Rarity")) {
                return Rarity.valueOf(keyValue[1]);
            }
        }
        return null;
    }

    @Nullable
    public static List<String> descriptionFromItemStack(ItemStack item) {
        if (item.getItemMeta() == null) return null;
        Material material = materialFromItemStack(item);
        if (material == null) return null;
        PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
        String pdcData = data.getOrDefault(material.getKey(), PersistentDataType.STRING, "");
        List<String> description = new ArrayList<>();
        String[] parts = pdcData.split(";");
        for (String part : parts) {
            String[] keyValue = part.split(":");
            if (keyValue.length == 2 && keyValue[0].equals("Description")) {
                String[] descValues = keyValue[1].split("`");
                Collections.addAll(description, descValues);
                return description;
            }
        }
        return null;
    }

    @Nullable
    public static HashMap<Attribute, AttributeModifier> attributesFromItemStack(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return null;

        HashMap<Attribute, AttributeModifier> attributes = new HashMap<>();
        ItemMeta meta = item.getItemMeta();
        Multimap<Attribute, AttributeModifier> modifiers = meta.getAttributeModifiers();

        if (modifiers == null) return null;
        for (Map.Entry<Attribute, AttributeModifier> entry : modifiers.entries()) {
            Attribute attribute = entry.getKey();
            AttributeModifier modifier = entry.getValue();
            attributes.put(attribute, modifier);
        }

        return attributes;
    }

    public static Material materialFromItemStack(ItemStack item) {
        if(item.getItemMeta() == null) return null;
        if(!item.getItemMeta().hasCustomModelData()) return null;

        for(Material material : Material.values()) {
            if(material.getCustomModelData() != item.getItemMeta().getCustomModelData()) continue;
            if(material.getBukkitMaterial() != item.getType()) continue;
            if(!item.getItemMeta().getPersistentDataContainer().has(material.getKey())) continue;
            return material;
        }
        return null;
    }

    public static String toReadableString(Attribute attribute) {
        return switch (attribute) {
            case GENERIC_MAX_HEALTH -> "Max Health";
            case GENERIC_FOLLOW_RANGE -> "Follow Range";
            case GENERIC_KNOCKBACK_RESISTANCE -> "Knockback Resistance";
            case GENERIC_MOVEMENT_SPEED -> "Movement Speed";
            case GENERIC_FLYING_SPEED -> "Flying Speed";
            case GENERIC_ATTACK_DAMAGE -> "Attack Damage";
            case GENERIC_ATTACK_KNOCKBACK -> "Attack Knockback";
            case GENERIC_ATTACK_SPEED -> "Attack Speed";
            case GENERIC_ARMOR -> "Armor";
            case GENERIC_ARMOR_TOUGHNESS -> "Armor Toughness";
            case GENERIC_LUCK -> "Luck";
            case GENERIC_MAX_ABSORPTION -> "Maximum Absorption";
            case HORSE_JUMP_STRENGTH -> "Horse Jump Strength";
            case ZOMBIE_SPAWN_REINFORCEMENTS -> "Zombie Spawn Reinforcements";
        };
    }
}
