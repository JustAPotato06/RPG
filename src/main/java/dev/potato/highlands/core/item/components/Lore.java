package dev.potato.highlands.core.item.components;

import net.kyori.adventure.text.Component;

import java.util.ArrayList;

public class Lore {

    private final ArrayList<Component> lore;

    public Lore() {
        this.lore = new ArrayList<>();
    }

    public void addLine(Component text) {
        lore.add(text);
    }
    public void setLine(int line, Component text) {
        lore.set(line, text);
    }

    public void changeLine(int line, Component text) {
        removeLine(line);
        setLine(line, text);
    }

    public void removeLine(int line) {
        lore.remove(line);
    }

    public ArrayList<Component> getLore() {
        return lore;
    }
}
