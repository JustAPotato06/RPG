package dev.potato.highlands.util.entityeditor.display;

import dev.potato.highlands.util.TextUtil;
import dev.potato.highlands.util.entityeditor.EntityEditorMenu;
import dev.potato.highlands.util.menu.ItemUtil;
import dev.potato.highlands.util.menu.Menu;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Display;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Transformation;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// this code is only to be read by whoever wrote it
public class DisplayEntityMenu extends Menu {

    private final Display display;
    private final Display.Billboard[] billboards = Display.Billboard.values();
    private boolean editingBlockLight = true;

    private final boolean[] translationOptions = new boolean[]{true, false, false};
    private final boolean[] scaleOptions = new boolean[]{true, false, false};
    private final boolean[] leftRotOptions = new boolean[]{true, false, false, false};
    private final boolean[] rightRotOptions = new boolean[]{true, false, false, false};

    private final boolean[] settings = new boolean[]{true, false, false};
    private boolean precision = false;
    private boolean modifyAllAngles = false;
    private float modifier = 0.1f;

    //TODO: ability to change block/item type and maybe undo /redo system
    public DisplayEntityMenu(Display display, EntityEditorMenu entityMenu) {
        super(27, TextUtil.translateLegacy("&7Display Editor Menu: "+ getName(display)));
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
            if (event.isLeftClick()) adjustBrightness(true, editingBlockLight, blocklightInt, skylightInt);
            if (event.isRightClick()) adjustBrightness(false, editingBlockLight, blocklightInt, skylightInt);
            setItem(1, brightnessButton());
        }));
        setItem(2, translationButton(), ((p, event) -> {
            if (event.isShiftClick()) {
                handleShiftClick(translationOptions, event);
                setItem(2, translationButton());
                return;
            }
            Vector3f translation = getTranslation(event);
            display.setTransformation(new Transformation(
                    translation,
                    display.getTransformation().getLeftRotation(),
                    display.getTransformation().getScale(),
                    display.getTransformation().getRightRotation()));
            setItem(2, translationButton());
        }));
        setItem(3, scaleButton(), ((p, event) -> {
            if (event.isShiftClick()) {
                handleShiftClick(scaleOptions, event);
                setItem(3, scaleButton());
                return;
            }
            Vector3f scale = getScale(event);
            display.setTransformation(new Transformation(
                    display.getTransformation().getTranslation(),
                    display.getTransformation().getLeftRotation(),
                    scale,
                    display.getTransformation().getRightRotation()));
            setItem(3, scaleButton());
        }));
        setItem(4, leftRotButton(), ((p, event) -> {
            if (event.isShiftClick()) {
                handleShiftClick(leftRotOptions, event);
                setItem(4, leftRotButton());
                return;
            }
            Quaternionf leftRot = getLeftRot(event);
            display.setTransformation(new Transformation(
                    display.getTransformation().getTranslation(),
                    leftRot,
                    display.getTransformation().getScale(),
                    display.getTransformation().getRightRotation()));
            setItem(4, leftRotButton());
        }));
        setItem(5, rightRotButton(), ((p, event) -> {
            if (event.isShiftClick()) {
                handleShiftClick(rightRotOptions, event);
                setItem(5, rightRotButton());
                return;
            }
            Quaternionf rightRot = getRightRot(event);
            display.setTransformation(new Transformation(
                    display.getTransformation().getTranslation(),
                    display.getTransformation().getLeftRotation(),
                    display.getTransformation().getScale(),
                    rightRot
            ));
            setItem(5, rightRotButton());
        }));
        setItem(18, ItemUtil.create(Material.GREEN_CONCRETE, TextUtil.translateLegacy("&2Entity")), ((p, event) -> {
            entityMenu.open(p);
            entityMenu.setItem(18, ItemUtil.create(Material.GREEN_CONCRETE, TextUtil.translateLegacy("&8Display")), ((p1, event1) -> this.open(p1)));
        }));
        setItem(19, settingsButton(), ((p, event) -> {
            if (event.isShiftClick()) {
                handleShiftClick(settings, event);
                setItem(19, settingsButton());
                return;
            }
            handleSettingsButton(event);
        }));
        setItem(20, ItemUtil.create(Material.SPONGE, TextUtil.translateLegacy("&2Reset")), ((p, event) -> display.setTransformation(getDefaultTransformation())));
        setItem(26, ItemUtil.create(Material.BARRIER, TextUtil.translateLegacy("&4DELETE")), ((p, event) -> {
            display.remove();
            close(p);
        }));
    }

    public ItemStack settingsButton() {
        ItemStack item = ItemUtil.create(Material.STRUCTURE_VOID, TextUtil.translateLegacy("&2Settings"));
        ItemMeta meta = item.getItemMeta();
        ArrayList<Component> lore = new ArrayList<>();

        String[] prefixes = new String[]{"&8> ", "&8> ", "&8> "};

        for (int i = 0; i < settings.length; i++) {
            if (settings[i]) {
                prefixes[i] = "&7>> ";
                break;
            }
        }
        String precisionVal = precision ? "%.2f" : "%.1f";
        lore.add(TextUtil.translateLegacy(prefixes[0] + "precision: " + precision));
        lore.add(TextUtil.translateLegacy(prefixes[1] + "global scaling: " + modifyAllAngles));
        lore.add(TextUtil.translateLegacy(prefixes[2] + "modifier: " + String.format(precisionVal, modifier)));

        meta.lore(lore);
        item.setItemMeta(meta);
        return item;

    }
    public ItemStack brightnessButton() {
        String blocklightInt = display.getBrightness() != null ? Integer.toString(display.getBrightness().getBlockLight()) : Integer.toString(0);
        String skylightInt = display.getBrightness() != null ? Integer.toString(display.getBrightness().getSkyLight()) : Integer.toString(0);

        return editingBlockLight ? ItemUtil.create(Material.LANTERN, TextUtil.translateLegacy("&6Brightness"), List.of(TextUtil.translateLegacy("&7>> Block light: " + blocklightInt), TextUtil.translateLegacy("&8> Sky Light: " + skylightInt))) :
                ItemUtil.create(Material.LANTERN, TextUtil.translateLegacy("&6Brightness"), List.of(TextUtil.translateLegacy("&8> Block light: " + blocklightInt), TextUtil.translateLegacy("&7>> Sky Light: " + skylightInt)));
    }
    public ItemStack translationButton() {
        ItemStack item = ItemUtil.create(Material.BAMBOO_BLOCK, TextUtil.translateLegacy("&6Translation"));
        ItemMeta meta = item.getItemMeta();
        ArrayList<Component> lore = new ArrayList<>();

        Vector3f translation = display.getTransformation().getTranslation();
        String[] prefixes = new String[]{"&8> ", "&8> ", "&8> "};

        for (int i = 0; i < translationOptions.length; i++) {
            if (translationOptions[i]) {
                prefixes[i] = "&7>> ";
                break;
            }
        }
        String precisionVal = precision ? "%.2f" : "%.1f";
        lore.add(TextUtil.translateLegacy(prefixes[0] + "x: " + String.format(precisionVal, translation.x())));
        lore.add(TextUtil.translateLegacy(prefixes[1] + "y: " + String.format(precisionVal, translation.y())));
        lore.add(TextUtil.translateLegacy(prefixes[2] + "z: " + String.format(precisionVal, translation.z())));

        meta.lore(lore);
        item.setItemMeta(meta);
        return item;
    }
    public ItemStack leftRotButton() {
        ItemStack item = ItemUtil.create(Material.BONE_BLOCK, TextUtil.translateLegacy("&6Left Rot"));
        ItemMeta meta = item.getItemMeta();
        ArrayList<Component> lore = new ArrayList<>();

        Quaternionf leftRotation = display.getTransformation().getLeftRotation();
        String[] prefixes = new String[]{"&8> ", "&8> ", "&8> ", "&8> "};

        for (int i = 0; i < leftRotOptions.length; i++) {
            if (leftRotOptions[i]) {
                prefixes[i] = "&7>> ";
                break;
            }
        }
        String precisionVal = precision ? "%.2f" : "%.1f";
        lore.add(TextUtil.translateLegacy(prefixes[0] + "x: " + String.format(precisionVal, leftRotation.x())));
        lore.add(TextUtil.translateLegacy(prefixes[1] + "y: " + String.format(precisionVal, leftRotation.y())));
        lore.add(TextUtil.translateLegacy(prefixes[2] + "z: " + String.format(precisionVal, leftRotation.z())));
        lore.add(TextUtil.translateLegacy(prefixes[3] + "w: " + String.format(precisionVal, leftRotation.w())));

        meta.lore(lore);
        item.setItemMeta(meta);
        return item;
    }
    public ItemStack scaleButton() {
        ItemStack item = ItemUtil.create(Material.BAMBOO_MOSAIC, TextUtil.translateLegacy("&6Scale"));
        ItemMeta meta = item.getItemMeta();
        ArrayList<Component> lore = new ArrayList<>();

        Vector3f scale = display.getTransformation().getScale();
        String[] prefixes = new String[]{"&8> ", "&8> ", "&8> "};

        for (int i = 0; i < scaleOptions.length; i++) {
            if (scaleOptions[i]) {
                prefixes[i] = "&7>> ";
                break;
            }
        }
        String precisionVal = precision ? "%.2f" : "%.1f";
        lore.add(TextUtil.translateLegacy(prefixes[0] + "x: " + String.format(precisionVal, scale.x())));
        lore.add(TextUtil.translateLegacy(prefixes[1] + "y: " + String.format(precisionVal, scale.y())));
        lore.add(TextUtil.translateLegacy(prefixes[2] + "z: " + String.format(precisionVal, scale.z())));

        meta.lore(lore);
        item.setItemMeta(meta);
        return item;
    }
    public ItemStack rightRotButton() {
        ItemStack item = ItemUtil.create(Material.WHITE_TERRACOTTA, TextUtil.translateLegacy("&6Right Rot"));
        ItemMeta meta = item.getItemMeta();
        ArrayList<Component> lore = new ArrayList<>();

        Quaternionf rightRotation = display.getTransformation().getRightRotation();
        String[] prefixes = new String[]{"&8> ", "&8> ", "&8> ", "&8> "};

        for (int i = 0; i < rightRotOptions.length; i++) {
            if (rightRotOptions[i]) {
                prefixes[i] = "&7>> ";
                break;
            }
        }
        String precisionVal = precision ? "%.2f" : "%.1f";
        lore.add(TextUtil.translateLegacy(prefixes[0] + "x: " + String.format(precisionVal, rightRotation.x())));
        lore.add(TextUtil.translateLegacy(prefixes[1] + "y: " + String.format(precisionVal, rightRotation.y())));
        lore.add(TextUtil.translateLegacy(prefixes[2] + "z: " + String.format(precisionVal, rightRotation.z())));
        lore.add(TextUtil.translateLegacy(prefixes[3] + "w: " + String.format(precisionVal, rightRotation.w())));

        meta.lore(lore);
        item.setItemMeta(meta);
        return item;
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
    private Vector3f getTranslation(InventoryClickEvent event) {
        Vector3f translation = display.getTransformation().getTranslation();

        if (event.isLeftClick() || event.isRightClick()) {
            final boolean isLeftClick = event.isLeftClick();

            final float calcModifier = isLeftClick ? modifier : -modifier;

            for (int i = 0; i < translationOptions.length; i++) {
                if (!translationOptions[i]) continue;
                switch (i) {
                    case 0 -> translation = translation.add(calcModifier, 0.0f, 0.0f);
                    case 1 -> translation = translation.add(0.0f, calcModifier, 0.0f);
                    case 2 -> translation = translation.add(0.0f, 0.0f, calcModifier);
                    default -> throw new IllegalArgumentException("Invalid translation option index");
                }
                break;
            }
        }
        return translation;
    }
    private Vector3f getScale(InventoryClickEvent event) {
        Vector3f scale = display.getTransformation().getScale();

        if (event.isLeftClick() || event.isRightClick()) {
            final boolean isLeftClick = event.isLeftClick();

            final float modifyAll = isLeftClick ? (modifyAllAngles ? modifier : 0f) : (modifyAllAngles ? -modifier : 0f);
            final float calcModifier = isLeftClick ? modifier : -modifier;

            for (int i = 0; i < scaleOptions.length; i++) {
                if (!scaleOptions[i]) continue;
                switch (i) {
                    case 0 -> scale = scale.add(calcModifier, modifyAll, modifyAll);
                    case 1 -> scale = scale.add(modifyAll, calcModifier, modifyAll);
                    case 2 -> scale = scale.add(modifyAll, modifyAll, calcModifier);
                    default -> throw new IllegalArgumentException("Invalid translation option index");
                }
                break;
            }
        }
        return scale;
    }
    private Quaternionf getLeftRot(InventoryClickEvent event) {
        Quaternionf leftRot = display.getTransformation().getLeftRotation();

        if (event.isLeftClick() || event.isRightClick()) {
            final boolean isLeftClick = event.isLeftClick();

            final float calcModifier = isLeftClick ? modifier : -modifier;

            for (int i = 0; i < leftRotOptions.length; i++) {
                if (!leftRotOptions[i]) continue;
                switch (i) {
                    case 0 -> leftRot = leftRot.add(calcModifier, 0.0f, 0.0f, 0.0f);
                    case 1 -> leftRot = leftRot.add(0.0f, calcModifier, 0.0f, 0.0f);
                    case 2 -> leftRot = leftRot.add(0.0f, 0.0f, calcModifier, 0.0f);
                    case 3 -> leftRot = leftRot.add(0.0f, 0.0f, 0.0f, calcModifier);
                    default -> throw new IllegalArgumentException("Invalid translation option index");
                }
                break;
            }
        }
        return leftRot;
    }
    private Quaternionf getRightRot(InventoryClickEvent event) {
        Quaternionf rightRot = display.getTransformation().getRightRotation();

        if (event.isLeftClick() || event.isRightClick()) {
            final boolean isLeftClick = event.isLeftClick();

            final float calcModifier = isLeftClick ? modifier : -modifier;

            for (int i = 0; i < rightRotOptions.length; i++) {
                if (!rightRotOptions[i]) continue;
                switch (i) {
                    case 0 -> rightRot = rightRot.add(calcModifier, 0.0f, 0.0f, 0.0f);
                    case 1 -> rightRot = rightRot.add(0.0f, calcModifier, 0.0f, 0.0f);
                    case 2 -> rightRot = rightRot.add(0.0f, 0.0f, calcModifier, 0.0f);
                    case 3 -> rightRot = rightRot.add(0.0f, 0.0f, 0.0f, calcModifier);
                    default -> throw new IllegalArgumentException("Invalid translation option index");
                }
                break;
            }
        }
        return rightRot;
    }
    private void handleSettingsButton(InventoryClickEvent event) {
        final float modifierIncrement = precision ? 0.01f : 0.1f;
        final float modifierMax = 5.0f;
        final float modifierMin = -0.5f;

        if (event.isLeftClick() || event.isRightClick()) {
            final boolean isLeftClick = event.isLeftClick();

            for (int i = 0; i < settings.length; i++) {
                if (!settings[i]) continue;
                switch (i) {
                    case 0 -> precision = !precision;
                    case 1 -> modifyAllAngles = !modifyAllAngles;
                    case 2 -> modifier = isLeftClick ? Math.min(modifier + modifierIncrement, modifierMax) : Math.max(modifier - modifierIncrement, modifierMin);
                    default -> throw new IllegalArgumentException("Invalid translation option index");
                }
                updateAllLore();
                break;
            }
        }
    }
    private void handleShiftClick(boolean[] array, InventoryClickEvent event) {
        if (event.isShiftClick()) {
            int currentIndex = -1;
            for (int i = 0; i < array.length; i++) {
                if (array[i]) {
                    currentIndex = i;
                    break;
                }
            }
            if (currentIndex != -1) {
                if (event.isLeftClick()) {
                    array[currentIndex] = false;
                    currentIndex = (currentIndex + 1) % array.length;
                    array[currentIndex] = true;
                } else if (event.isRightClick()) {
                    array[currentIndex] = false;
                    currentIndex = (currentIndex - 1 + array.length) % array.length;
                    array[currentIndex] = true;
                }
            }
        }
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
    private Transformation getDefaultTransformation() {
        return new Transformation(new Vector3f(0.0f, 0f, 0f), new Quaternionf(0f,0f,0f,1f), new Vector3f(1f,1f,1f), new Quaternionf(0f,0f,0f,1f));
        //{translation:[-0.5f,0f,-0.5f],left_rotation:[0f,0f,0f,1f],scale:[1f,1f,1f],right_rotation:[0f,0f,0f,1f]}
    }
    private void updateAllLore() {
        setItem(0, billBoardButton());
        setItem(1, brightnessButton());
        setItem(2, translationButton());
        setItem(3, scaleButton());
        setItem(4, leftRotButton());
        setItem(5, rightRotButton());

        setItem(19, settingsButton());
    }
    private static String getName(Display display) {
        if(display instanceof BlockDisplay b) return b.getBlock().getMaterial().name().toLowerCase().replace("_", " ");
        if(display instanceof ItemDisplay i) return i.getItemStack() != null ? i.getItemStack().getType().name().toLowerCase().replace("_", " ")
                : display.getType().name().toLowerCase().replace("_", " ");
        return display.getType().name().toLowerCase().replace("_", " ");
    }
}
