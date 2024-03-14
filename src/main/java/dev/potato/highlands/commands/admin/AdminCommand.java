package dev.potato.highlands.commands.admin;

import dev.potato.highlands.commands.main.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AdminCommand extends CommandManager {
    public AdminCommand(JavaPlugin plugin) {
        super(plugin);
        register(new AdminModeCommand(plugin));
    }
}
