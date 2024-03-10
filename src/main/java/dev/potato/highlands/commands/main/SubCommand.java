package dev.potato.highlands.commands.main;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public abstract class SubCommand {
    protected final JavaPlugin plugin;
    private String name;
    private String description;
    private String syntax;
    private String permission;
    private List<String> aliases;

    public SubCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSyntax() {
        return syntax;
    }

    public void setSyntax(String syntax) {
        this.syntax = syntax;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    public abstract void onCommand(Player player, String[] args);

    public abstract List<String> onTabComplete(Player player, String[] args);
}
