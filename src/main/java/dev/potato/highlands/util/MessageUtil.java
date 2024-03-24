package dev.potato.highlands.util;

import net.kyori.adventure.text.Component;

import java.util.ArrayList;

public class MessageUtil {

    public static ArrayList<Component> entityManipulationTutorial() {
        ArrayList<Component> list = new ArrayList<>();
        list.add(TextUtil.translateLegacy("&8-+=============ENTITY==============+-"));
        list.add(TextUtil.translateLegacy("&7Shift right click an entity to open its"));
        list.add(TextUtil.translateLegacy("&7editor gui, Living entities inherits a"));
        list.add(TextUtil.translateLegacy("&7gui from entities(Use Entity Editor Wand)"));
        list.add(TextUtil.translateLegacy("&8-+============DISPLAY==============+-"));
        list.add(TextUtil.translateLegacy("&7Shift right click a block with an item"));
        list.add(TextUtil.translateLegacy("&7to place it as a display entity, turn on"));
        list.add(TextUtil.translateLegacy("&7hitboxes to see display's pivot points"));
        list.add(TextUtil.translateLegacy("&7Displays inherits a gui from entities."));
        list.add(TextUtil.translateLegacy("&8-+==========GUI-CONTROLS===========+-"));
        list.add(TextUtil.translateLegacy("&ashift click, right click, left click"));
        list.add(TextUtil.translateLegacy("&7"));
        return list;
    }
}
