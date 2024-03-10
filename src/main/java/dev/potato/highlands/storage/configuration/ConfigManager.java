package dev.potato.highlands.storage.configuration;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * All configs here are in memory so calling getConfig(); on them will
 * only return the config in memory.
 * Reload the config object to get config from disk.
 * use "/" for separating paths when getting configs
 */
public class ConfigManager {

    private final Map<String, Config> configs = new HashMap<>();
    private static ConfigManager manager;

    private ConfigManager(){}

    public static ConfigManager getManager() {
        return manager == null ? manager = new ConfigManager() : manager;
    }

    /**
     * Called in Plugin Main Class
     * Loads every file ending with .yml in the plugin's data folder
     * into memory.
     *
     * @param plugin Plugin Instance
     */
    public void register(JavaPlugin plugin) {
        File dataFolder = plugin.getDataFolder();
        if (dataFolder.exists() && dataFolder.isDirectory()) {
            loadConfigsFromFolder(dataFolder, plugin, new HashSet<>());
        }
    }

    /**
     * Called once in Config class for every config created
     * and caches it to configs Map
     *
     * @param path path to config file
     * @param name file name
     * @param config config object
     */
    public void register(String path, String name, Config config) {
        if(name.endsWith(".yml")) name = name.replace(".yml", "");
        configs.put(path +"/"+ name, config);
    }

    private void loadConfigsFromFolder(File folder, JavaPlugin plugin, Set<String> loadedFiles) {
        File[] files = folder.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                loadConfigsFromFolder(file, plugin, loadedFiles);
            } else if (file.getName().endsWith(".yml") && !loadedFiles.contains(file.getName())) {
                File parentFolder = new File(folder.getParentFile().getParent());
                String relativePath = folder.getPath().replace(parentFolder.getPath(), "").substring(1);

                createOrGetConfig(relativePath, file.getName(), plugin);
                loadedFiles.add(file.getName());
            }
        }
    }

    public Config createOrGetConfig(String path, String name, JavaPlugin plugin){
        if(configs.containsKey(path +"/"+ name)) return configs.get(path +"/"+ name);
        Config config = new Config(path, name, plugin);

        if(name.endsWith(".yml")) name = name.replace(".yml", "");
        configs.put(path +"/"+ name, config);
        return config;
    }

    @Nullable
    public Config getConfig(String path, String name) {
        name = path +"/"+ name;
        if(configs.containsKey(name)) return configs.get(name);
        if(name.endsWith(".yml")) name = name.replace(".yml", "");
        if(configs.containsKey(name)) return configs.get(name);
        return null;
    }

    @Nullable
    public Config getConfig(String path) {
        if(configs.containsKey(path)) return configs.get(path);
        if(path.endsWith(".yml")) path = path.replace(".yml", "");
        if(configs.containsKey(path)) return configs.get(path);
        return null;
    }

    public void reload(String path, String name) {
        Config config = getConfig(path, name);
        if(config != null) {
            config.reload();
        }
    }

    public void getNames() {
        configs.keySet().forEach((name)-> Bukkit.getConsoleSender().sendMessage(name));
    }

    public void saveAll() {
        configs.values().forEach(Config::save);
    }

    public void loadAll() {
        configs.values().forEach(Config::reload);
    }
}
