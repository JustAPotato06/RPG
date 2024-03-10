package dev.potato.highlands;

import dev.potato.highlands.storage.configuration.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class RPG extends JavaPlugin {

    @Override
    public void onEnable() {
        ConfigManager.getManager().register(this);

    }
}
