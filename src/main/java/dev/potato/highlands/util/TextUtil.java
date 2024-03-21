package dev.potato.highlands.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class TextUtil {

    public static Component translateLegacy(String text) {
        return LegacyComponentSerializer.legacy('&').deserialize(text).decoration(TextDecoration.ITALIC, false);
    }

    public static Component translate(String text) {
        return MiniMessage.miniMessage().deserialize(text).decoration(TextDecoration.ITALIC, false);
    }

    public static Component toComponent(String text) {
        return Component.text(text).decoration(TextDecoration.ITALIC, false);
    }

    public String stripMiniTags(String text) {
        return MiniMessage.miniMessage().stripTags(text);
    }

}
