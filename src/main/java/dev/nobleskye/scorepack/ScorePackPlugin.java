package dev.nobleskye.scorepack;

import dev.nobleskye.scorepack.commands.ReloadCommand;
import dev.nobleskye.scorepack.config.ConfigManager;
import dev.nobleskye.scorepack.listeners.ScoreboardListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

/**
 * Main plugin class for ScorePack.
 * Manages resource pack assignments based on scoreboard values.
 */
public class ScorePackPlugin extends JavaPlugin {
    
    private ConfigManager configManager;
    private ScoreboardListener scoreboardListener;
    
    @Override
    public void onEnable() {
        getLogger().info("ScorePack is starting...");
        
        // Initialize configuration manager
        configManager = new ConfigManager(this);
        
        // Load configurations
        try {
            configManager.loadAllConfigurations();
            getLogger().info("Loaded " + configManager.getLoadedObjectives().size() + " scoreboard configuration(s)");
        } catch (Exception e) {
            getLogger().log(Level.SEVERE, "Failed to load configurations", e);
        }
        
        // Register event listener
        scoreboardListener = new ScoreboardListener(this, configManager);
        getServer().getPluginManager().registerEvents(scoreboardListener, this);
        
        // Register commands
        ReloadCommand reloadCommand = new ReloadCommand(this, configManager);
        getCommand("scorepack").setExecutor(reloadCommand);
        getCommand("scorepack").setTabCompleter(reloadCommand);
        
        getLogger().info("ScorePack has been enabled!");
    }
    
    @Override
    public void onDisable() {
        getLogger().info("ScorePack has been disabled!");
    }
    
    /**
     * Gets the configuration manager instance.
     * @return The configuration manager
     */
    public ConfigManager getConfigManager() {
        return configManager;
    }
}
