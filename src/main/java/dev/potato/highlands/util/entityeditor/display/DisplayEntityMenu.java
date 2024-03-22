package dev.potato.highlands.util.entityeditor.display;

import dev.potato.highlands.util.TextUtil;
import dev.potato.highlands.util.entityeditor.EntityEditorMenu;
import dev.potato.highlands.util.menu.ItemUtil;
import dev.potato.highlands.util.menu.Menu;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Display;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DisplayEntityMenu extends Menu {

    private final Display display;
    private final Display.Billboard[] billboards = Display.Billboard.values();
    private boolean editingBlockLight = true;

    public DisplayEntityMenu(Display display, EntityEditorMenu entityMenu) {
        super(27, TextUtil.translateLegacy("&7Display Editor Menu: "+ display.getType().name()));
        this.display = display;

        setItem(0, billBoardButton(), ((p, event) -> {
            if(event.isLeftClick()) display.setBillboard(Objects.requireNonNull(nextBoard()));
            if(event.isRightClick()) display.setBillboard(Objects.requireNonNull(prevBoard()));
            setItem(0, billBoardButton());
        }));

        setItem(1, brightnessButton(), ((p, event) -> {
            int blocklightInt = display.getBrightness() != null ? display.getBrightness().getBlockLight() : 0;
            int skylightInt = display.getBrightness() != null ? display.getBrightness().getSkyLight() : 0;

            if(event.isShiftClick()) {
                editingBlockLight = !editingBlockLight;
                setItem(1, brightnessButton());
                return;
            }
            if (event.isLeftClick()) {
                adjustBrightness(true, editingBlockLight, blocklightInt, skylightInt);
            }

            if (event.isRightClick()) {
                adjustBrightness(false, editingBlockLight, blocklightInt, skylightInt);
            }

            setItem(1, brightnessButton());
        }));

        // Inherits menu from Entity via Composition
        setItem(18, ItemUtil.create(Material.GREEN_CONCRETE, TextUtil.translateLegacy("&7Entity")), ((p, event) -> {
            entityMenu.open(p);
            entityMenu.setItem(18, ItemUtil.create(Material.GREEN_CONCRETE, TextUtil.translateLegacy("&8Display")), ((p1, event1) -> this.open(p1)));
        }));

        // DELETE ENTITY
        setItem(26, ItemUtil.create(Material.BARRIER, TextUtil.translateLegacy("&4DELETE")), ((p, event) -> {
            display.remove();
            close(p);
        }));
    }

    public ItemStack brightnessButton() {
        String blocklightInt = display.getBrightness() != null ? Integer.toString(display.getBrightness().getBlockLight()) : Integer.toString(0);
        String skylightInt = display.getBrightness() != null ? Integer.toString(display.getBrightness().getSkyLight()) : Integer.toString(0);

        return editingBlockLight ? ItemUtil.create(Material.LANTERN, TextUtil.translateLegacy("&6Brightness"), List.of(TextUtil.translateLegacy("&7>> Block light: " + blocklightInt), TextUtil.translateLegacy("&8> Sky Light: " + skylightInt))) :
                ItemUtil.create(Material.LANTERN, TextUtil.translateLegacy("&6Brightness"), List.of(TextUtil.translateLegacy("&8> Block light: " + blocklightInt), TextUtil.translateLegacy("&7>> Sky Light: " + skylightInt)));
    }

    public ItemStack billBoardButton() {
        ItemStack item = ItemUtil.create(Material.OAK_HANGING_SIGN, TextUtil.translateLegacy("&6BillBoard"));
        ItemMeta meta = item.getItemMeta();
        List<Component> lore = new ArrayList<>();
        for(Display.Billboard billboard : billboards) {
            if(display.getBillboard() == billboard) {
                lore.add(TextUtil.translateLegacy("&7>> " + billboard.name().toLowerCase()));
            }else {
                lore.add(TextUtil.translateLegacy("&8> " + billboard.name().toLowerCase()));
            }
        }
        meta.lore(lore);
        item.setItemMeta(meta);
        return item;
    }

    private Display.Billboard prevBoard() {
        for (int i = 0; i < billboards.length; i++) {
            if (display.getBillboard() == billboards[i]) {
                int prevIndex = (i + billboards.length - 1) % billboards.length;
                return billboards[prevIndex];
            }
        }
        return null;
    }

    private Display.Billboard nextBoard() {
        for (int i = 0; i < billboards.length; i++) {
            if (display.getBillboard() == billboards[i]) {
                int nextIndex = (i + 1) % billboards.length;
                return billboards[nextIndex];
            }
        }
        return null;
    }

    private void adjustBrightness(boolean increase, boolean editingBlockLight, int blocklightInt, int skylightInt) {
        int newBlocklight = blocklightInt;
        int newSkylight = skylightInt;

        if (editingBlockLight) {
            newBlocklight += increase ? 1 : -1;
            newBlocklight = Math.min(Math.max(newBlocklight, 0), 15);
        } else {
            newSkylight += increase ? 1 : -1;
            newSkylight = Math.min(Math.max(newSkylight, 0), 15);
        }
        display.setBrightness(new Display.Brightness(newBlocklight, newSkylight));
    }
}
