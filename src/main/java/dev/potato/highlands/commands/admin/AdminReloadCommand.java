package dev.potato.highlands.commands.admin;

import dev.potato.highlands.commands.main.SubCommand;
import dev.potato.highlands.storage.configuration.ConfigManager;
import dev.potato.highlands.util.ChatUtil;
import dev.potato.highlands.util.MessageType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class AdminReloadCommand extends SubCommand {

    public AdminReloadCommand(JavaPlugin plugin) {
        super(plugin);
        setName("reload");
    }

    @Override
    public void onCommand(Player player, String[] args) {
        ConfigManager.getManager().loadAll();
        ChatUtil.sendLegacyMessage(player, MessageType.SUCCESSFUL, "Configuration Reloaded!");
    }

    @Override
    public List<String> onTabComplete(Player player, String[] args) {
        return null;
    }
}
