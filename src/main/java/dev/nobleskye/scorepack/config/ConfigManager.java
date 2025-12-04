package dev.nobleskye.scorepack.config;

import dev.nobleskye.scorepack.ScorePackPlugin;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.*;
import java.util.logging.Level;

/**
 * Manages loading and accessing configuration files for scoreboard objectives.
 */
public class ConfigManager {
    private static final String MAIN_CONFIG_FILE = "config.yml";
    
    private final ScorePackPlugin plugin;
    private final File configDir;
    private final Map<String, ScoreboardConfig> scoreboardConfigs;
    
    /**
     * Creates a new configuration manager.
     * @param plugin The plugin instance
     */
    public ConfigManager(ScorePackPlugin plugin) {
        this.plugin = plugin;
        this.configDir = plugin.getDataFolder();
        this.scoreboardConfigs = new HashMap<>();
    }
    
    /**
     * Loads all configuration files from the plugin directory.
     * Configuration files are named after the scoreboard objective (e.g., level.yml).
     */
    public void loadAllConfigurations() {
        // Create plugin directory if it doesn't exist
        if (!configDir.exists()) {
            configDir.mkdirs();
        }
        
        // Create example configuration files if they don't exist
        createDefaultConfigs();
        
        // Clear existing configurations
        scoreboardConfigs.clear();
        
        // Load all YAML files from the directory (excluding main config)
        File[] files = configDir.listFiles((dir, name) -> name.endsWith(".yml") && !name.equals(MAIN_CONFIG_FILE));
        
        if (files == null || files.length == 0) {
            plugin.getLogger().warning("No scoreboard configuration files found in " + configDir.getPath());
            return;
        }
        
        for (File file : files) {
            try {
                loadConfiguration(file);
            } catch (Exception e) {
                plugin.getLogger().log(Level.SEVERE, "Failed to load configuration file: " + file.getName(), e);
            }
        }
    }
    
    /**
     * Loads a single configuration file.
     * @param file The configuration file
     */
    private void loadConfiguration(File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        
        // Get the scoreboard objective name
        String objectiveName = config.getString("scoreboard_objective");
        
        if (objectiveName == null || objectiveName.isEmpty()) {
            plugin.getLogger().warning("Configuration file " + file.getName() + " is missing 'scoreboard_objective' field");
            return;
        }
        
        // Create scoreboard configuration
        ScoreboardConfig scoreboardConfig = new ScoreboardConfig(objectiveName);
        
        // Load resource packs
        List<Map<?, ?>> resourcePacksList = config.getMapList("resource_packs");
        
        if (resourcePacksList.isEmpty()) {
            plugin.getLogger().warning("Configuration file " + file.getName() + " has no resource packs configured");
            return;
        }
        
        for (Map<?, ?> packMap : resourcePacksList) {
            try {
                Object valueObj = packMap.get("value");
                if (valueObj == null) {
                    plugin.getLogger().warning("Resource pack in " + file.getName() + " is missing 'value' field");
                    continue;
                }
                
                int value = ((Number) valueObj).intValue();
                String url = (String) packMap.get("url");
                String sha256 = (String) packMap.get("sha256"); // Can be null
                
                if (url == null || url.isEmpty()) {
                    plugin.getLogger().warning("Resource pack with value " + value + " in " + file.getName() + " has no URL");
                    continue;
                }
                
                ResourcePackConfig packConfig = new ResourcePackConfig(value, url, sha256);
                scoreboardConfig.addResourcePack(packConfig);
                
            } catch (Exception e) {
                plugin.getLogger().log(Level.WARNING, "Failed to parse resource pack in " + file.getName(), e);
            }
        }
        
        scoreboardConfigs.put(objectiveName, scoreboardConfig);
        plugin.getLogger().info("Loaded " + scoreboardConfig.size() + " resource pack(s) for objective '" + objectiveName + "'");
    }
    
    /**
     * Creates default example configuration files.
     */
    private void createDefaultConfigs() {
        createDefaultConfig("level.yml");
        createDefaultConfig("rank.yml");
        createDefaultConfig("config.yml");
    }
    
    /**
     * Creates a default configuration file if it doesn't exist.
     * @param fileName The name of the configuration file
     */
    private void createDefaultConfig(String fileName) {
        File file = new File(configDir, fileName);
        if (!file.exists()) {
            try (InputStream in = plugin.getResource(fileName)) {
                if (in != null) {
                    Files.copy(in, file.toPath());
                    plugin.getLogger().info("Created default configuration file: " + fileName);
                }
            } catch (IOException e) {
                plugin.getLogger().log(Level.WARNING, "Could not create default config file: " + fileName, e);
            }
        }
    }
    
    /**
     * Gets the configuration for a specific scoreboard objective.
     * @param objectiveName The name of the scoreboard objective
     * @return The configuration, or null if not found
     */
    public ScoreboardConfig getConfig(String objectiveName) {
        return scoreboardConfigs.get(objectiveName);
    }
    
    /**
     * Gets all loaded scoreboard objective names.
     * @return Set of objective names
     */
    public Set<String> getLoadedObjectives() {
        return scoreboardConfigs.keySet();
    }
    
    /**
     * Checks if a scoreboard objective has a configuration loaded.
     * @param objectiveName The name of the scoreboard objective
     * @return true if configuration exists
     */
    public boolean hasConfig(String objectiveName) {
        return scoreboardConfigs.containsKey(objectiveName);
    }
}
