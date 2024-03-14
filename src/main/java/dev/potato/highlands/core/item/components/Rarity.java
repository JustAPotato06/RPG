package dev.potato.highlands.core.item.components;

import dev.potato.highlands.util.Color;
import dev.potato.highlands.util.TextUtil;
import net.kyori.adventure.text.Component;

public enum Rarity {
    COMMON("Common Item", Color.GRAY),
    UNCOMMON("Uncommon Item", Color.DARK_GREEN),
    RARE("Rare Item", Color.LIGHT_PURPLE),
    EPIC("Epic Item", Color.YELLOW),
    UNIQUE("Unique Item", Color.GOLD),
    LEGENDARY("Legendary Item", Color.AQUA),
    MYTHIC("Mythic Item", Color.DARK_PURPLE),
    SET("Set Item", Color.GREEN);

    private final Component name;
    private final Color color;

    Rarity(String name, Color color) {
        this.name = TextUtil.toComponent(name).color(color.getTextColor());
        this.color = color;
    }

    public Component getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

}
