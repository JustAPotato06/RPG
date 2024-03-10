package dev.potato.highlands.storage.configuration;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class Config {

    private final String path;
    private final String name;
    private FileConfiguration config;
    private File configFile;
    private final File dataFolder;

    /**
     *
     * @param path initial path is plugin folder, if path is null specified file will be created in plugins folder
     * @param name file name to be created in path
     * @param plugin Plugin instance
     */
    public Config(@Nullable String path, String name, JavaPlugin plugin) {
        this.path = path;
        if(!name.endsWith(".yml")) name += (".yml");
        this.name = name;
        this.dataFolder = plugin.getDataFolder();
        try {
            init();
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage("<dark_red>Creation of configuration file " + name + " failed!!");
            throw new RuntimeException(e);
        }
        ConfigManager.getManager().register(path, name, this);
    }

    public void init() throws IOException {
        if(!dataFolder.exists()) dataFolder.mkdir();
        if(path == null) {
            File config = new File(dataFolder, name);
            if(!config.exists()) config.createNewFile();
            this.configFile = config;
            this.config = YamlConfiguration.loadConfiguration(config);
        }else {
            File directory = new File(dataFolder, path);
            if(!directory.exists()) directory.mkdirs();
            File config = new File(directory, name);

            if(!config.exists()) config.createNewFile();
            this.configFile = config;
            this.config = YamlConfiguration.loadConfiguration(config);
        }
    }

    public void save() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            Bukkit.getConsoleSender().sendMessage("<dark_red>Failed to save config: " + name);
            throw new RuntimeException(e);
        }
    }

    public void reload() {
        try {
            config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            Bukkit.getConsoleSender().sendMessage("<dark_red>Failed to reload config: " + name);
            throw new RuntimeException(e);
        }
    }

    public File getConfigFile() {
        return configFile;
    }

    public FileConfiguration getConfig() {
        return config;
    }
}