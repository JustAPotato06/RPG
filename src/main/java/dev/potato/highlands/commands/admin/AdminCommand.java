package dev.potato.highlands.commands.admin;

import dev.potato.highlands.commands.main.CommandManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AdminCommand extends CommandManager {
    public AdminCommand(JavaPlugin plugin) {
        super(plugin);
        setPermission("highlands.commands.admin");
        register(new AdminModeCommand(plugin));
        register(new AdminReloadCommand(plugin));
        register(new AdminTestCommand(plugin));
    }
}
