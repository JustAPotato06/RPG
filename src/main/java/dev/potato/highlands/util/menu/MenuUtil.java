package dev.potato.highlands.util.menu;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MenuUtil {
    public static void addBorders(Inventory inventory, Material mat) {
        ItemStack item = ItemUtil.createNameless(mat);
        int size = inventory.getSize();
        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, item);
        }
        for (int i = size - 9; i < size; i++) {
            inventory.setItem(i, item);
        }
        for (int i = 1; i < size / 9 - 1; i++) {
            int leftIndex = i * 9;
            int rightIndex = (i + 1) * 9 - 1;
            inventory.setItem(leftIndex, item);
            inventory.setItem(rightIndex, item);
        }
    }

    public static void addBorders(Inventory inventory, ItemStack item) {
        int size = inventory.getSize();
        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, item);
        }
        for (int i = size - 9; i < size; i++) {
            inventory.setItem(i, item);
        }
        for (int i = 1; i < size / 9 - 1; i++) {
            int leftIndex = i * 9;
            int rightIndex = (i + 1) * 9 - 1;
            inventory.setItem(leftIndex, item);
            inventory.setItem(rightIndex, item);
        }
    }

    public static void fill(Inventory inventory, Material material) {
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, ItemUtil.createNameless(material));
            }
        }
    }

    public static void fill(Inventory inventory, ItemStack item) {
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, item);
            }
        }
    }

    public static void addTopLayer(Inventory inventory, Material mat) {
        ItemStack item = ItemUtil.createNameless(mat);
        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, item);
        }
    }

    public static void addTopLayer(Inventory inventory, ItemStack item) {
        for (int i = 0; i < 9; i++) {
            inventory.setItem(i, item);
        }
    }

    public static void addBottomLayer(Inventory inventory, Material mat) {
        ItemStack item = ItemUtil.createNameless(mat);
        int size = inventory.getSize();
        for (int i = size - 9; i < size; i++) {
            inventory.setItem(i, item);
        }
    }

    public static void addBottomLayer(Inventory inventory, ItemStack item) {
        int size = inventory.getSize();
        for (int i = size - 9; i < size; i++) {
            inventory.setItem(i, item);
        }
    }

    public static void addLeftLayer(Inventory inventory, Material mat) {
        ItemStack item = ItemUtil.createNameless(mat);
        int size = inventory.getSize();
        for (int i = 1; i < size / 9 - 1; i++) {
            int leftIndex = i * 9;
            inventory.setItem(leftIndex, item);
        }
    }

    public static void addLeftLayer(Inventory inventory, ItemStack item) {
        int size = inventory.getSize();
        for (int i = 1; i < size / 9 - 1; i++) {
            int leftIndex = i * 9;
            inventory.setItem(leftIndex, item);
        }
    }

    public static void addRightLayer(Inventory inventory, Material mat) {
        ItemStack item = ItemUtil.createNameless(mat);
        int size = inventory.getSize();
        for (int i = 1; i < size / 9 - 1; i++) {
            int rightIndex = (i + 1) * 9 - 1;
            inventory.setItem(rightIndex, item);
        }
    }

    public static void addRightLayer(Inventory inventory, ItemStack item) {
        int size = inventory.getSize();
        for (int i = 1; i < size / 9 - 1; i++) {
            int rightIndex = (i + 1) * 9 - 1;
            inventory.setItem(rightIndex, item);
        }
    }
}