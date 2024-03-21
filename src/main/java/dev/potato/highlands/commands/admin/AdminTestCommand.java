package dev.potato.highlands.commands.admin;

import dev.potato.highlands.commands.main.SubCommand;
import dev.potato.highlands.core.item.Item;
import dev.potato.highlands.core.item.components.Material;
import dev.potato.highlands.core.item.components.Rarity;
import dev.potato.highlands.core.rpgclass.RpgClass;
import dev.potato.highlands.util.TextUtil;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class AdminTestCommand extends SubCommand {

    public AdminTestCommand(JavaPlugin plugin) {
        super(plugin);
        setName("test");
    }

    @Override
    public void onCommand(Player player, String[] args) {
        player.getInventory().addItem(
                new Item(
                        TextUtil.toComponent("Weak Sword"),
                        Material.GENERIC_WOODEN_SWORD,
                        RpgClass.class, // for testing purposes only, RpgClass should not be a concrete implementation
                        List.of("Beware of Splinters",
                                "Does little to no dmg."),
                        Rarity.COMMON,
                        null // modifying this messes up vanilla attributes on the item, take note of that
                ).getItemStack()
        );
    }

    @Override
    public List<String> onTabComplete(Player player, String[] args) {
        return null;
    }
}
