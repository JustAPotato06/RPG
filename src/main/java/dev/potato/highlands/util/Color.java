package dev.potato.highlands.util;

import net.kyori.adventure.text.format.TextColor;
import org.bukkit.ChatColor;

@SuppressWarnings("deprecation")
public enum Color {
    WHITE(ChatColor.WHITE, TextColor.color(255, 255, 255), "&f"),
    BLACK(ChatColor.BLACK, TextColor.color(0, 0, 0), "&0"),
    RED(ChatColor.RED, TextColor.color(255, 0, 0), "&c"),
    GREEN(ChatColor.GREEN, TextColor.color(0, 255, 0), "&a"),
    BLUE(ChatColor.BLUE, TextColor.color(0, 0, 255), "&9"),
    AQUA(ChatColor.AQUA, TextColor.color(100, 255, 255), "&b"),
    YELLOW(ChatColor.YELLOW, TextColor.color(255, 255, 0), "&e"),
    DARK_PURPLE(ChatColor.DARK_PURPLE, TextColor.color(128, 0, 128), "&5"),
    GOLD(ChatColor.GOLD, TextColor.color(255, 165, 0), "&6"),
    GRAY(ChatColor.GRAY, TextColor.color(111, 111, 111), "&7"),
    LIGHT_PURPLE(ChatColor.LIGHT_PURPLE, TextColor.color(166, 55, 166), "&d"),
    DARK_RED(ChatColor.DARK_RED, TextColor.color(139, 0, 0), "&4"),
    DARK_GREEN(ChatColor.DARK_GREEN, TextColor.color(0, 100, 0), "&2"),
    DARK_BLUE(ChatColor.DARK_BLUE, TextColor.color(0, 0, 139), "&1"),
    DARK_GRAY(ChatColor.DARK_GRAY, TextColor.color(50, 50, 50), "&8");

    Color(ChatColor legacyColor, TextColor color, String colorCode) {
        this.legacyColor = legacyColor;
        this.color = color;
        this.colorCode = colorCode;
    }

    private final ChatColor legacyColor;
    private final TextColor color;
    private final String colorCode;

    public ChatColor getLegacyColor() {
        return legacyColor;
    }

    public TextColor getTextColor() {
        return color;
    }

    public String getColorCode() {
        return colorCode;
    }
}
