package dev.potato.highlands.util;

import net.kyori.adventure.text.Component;

import java.util.ArrayList;

public class MessageUtil {

    public static ArrayList<Component> entityManipulationTutorial() {
        ArrayList<Component> list = new ArrayList<>();
        list.add(TextUtil.translateLegacy("&8-+=================================+-"));
        list.add(TextUtil.translateLegacy("&7Shift right click a block with an item or block"));
        list.add(TextUtil.translateLegacy("&7in your hand to place it as a display entity."));
        list.add(TextUtil.translateLegacy("&8-+=================================+-"));
        list.add(TextUtil.translateLegacy("&7Shift right click an entity with display editor"));
        list.add(TextUtil.translateLegacy("&7to open its editor gui."));
        list.add(TextUtil.translateLegacy("&8-+=================================+-"));
        return list;
    }
}
