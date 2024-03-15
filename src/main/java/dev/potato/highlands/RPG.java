package dev.potato.highlands;

import dev.potato.highlands.commands.admin.AdminCommand;
import dev.potato.highlands.listeners.DamageListener;
import dev.potato.highlands.listeners.WorldModificationListener;
import dev.potato.highlands.storage.configuration.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class RPG extends JavaPlugin {
    public static JavaPlugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        ConfigManager.getManager().loadAll();

        Objects.requireNonNull(this.getCommand("admin")).setExecutor(new AdminCommand(this));

        this.getServer().getPluginManager().registerEvents(new DamageListener(), this);
        this.getServer().getPluginManager().registerEvents(new WorldModificationListener(), this);

    }

}
