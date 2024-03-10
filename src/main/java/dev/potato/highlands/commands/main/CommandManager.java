package dev.potato.highlands.commands.main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Extend CommandManager to create a new Command and register
 * it in main class, register subcommands by adding them to
 * the constructor in your class
 * that extends CommandManager.
 */
public abstract class CommandManager implements TabExecutor {
    private final Map<String, SubCommand> commands;

    public CommandManager(JavaPlugin plugin) {
        this.commands = new HashMap<>();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) return true;

        if (args.length == 0) {

            return true;
        }
        SubCommand command = getCommand(args[0]);
        if (command == null) return true;
        if (command.getPermission() != null && !player.hasPermission(command.getPermission())) return true;
        command.onCommand(player, args);
        return true;
    }

    protected void register(SubCommand subCommand) {
        commands.put(subCommand.getName(), subCommand);
    }

    private SubCommand getCommand(String name) {
        SubCommand subCommand = commands.getOrDefault(name, null);
        if (subCommand == null) {
            subCommand = commands
                    .values()
                    .stream()
                    .filter(cmd -> cmd.getAliases() != null && cmd.getAliases().contains(name))
                    .findFirst()
                    .orElse(null);
        }
        return subCommand;
    }


    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) return null;
        if (args.length == 1) {
            return commands.keySet().stream()
                    .filter(command -> command != null && command.startsWith(args[0]))
                    .collect(Collectors.toList());
        }
        SubCommand command = getCommand(args[0]);
        if (command == null) return null;
        if (command.getPermission() != null && !player.hasPermission(command.getPermission())) return null;
        return command.onTabComplete(player, args);
    }
}
