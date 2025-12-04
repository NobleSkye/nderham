package dev.nobleskye.scorepack.listeners;

import dev.nobleskye.scorepack.ScorePackPlugin;
import dev.nobleskye.scorepack.config.ConfigManager;
import dev.nobleskye.scorepack.config.ResourcePackConfig;
import dev.nobleskye.scorepack.config.ScoreboardConfig;
import dev.nobleskye.scorepack.handlers.ResourcePackHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

/**
 * Listens for scoreboard changes and applies resource packs.
 */
public class ScoreboardListener implements Listener {
    private final ScorePackPlugin plugin;
    private final ConfigManager configManager;
    private final ResourcePackHandler packHandler;
    
    /**
     * Creates a new scoreboard listener.
     * @param plugin The plugin instance
     * @param configManager The configuration manager
     */
    public ScoreboardListener(ScorePackPlugin plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
        this.packHandler = new ResourcePackHandler(plugin);
        
        // Start periodic check for score changes
        startPeriodicCheck();
    }
    
    /**
     * Handles player join events to check their initial scores.
     * @param event The player join event
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        
        // Delay check slightly to ensure player is fully loaded
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            checkPlayerScores(player);
        }, 20L); // 1 second delay
    }
    
    /**
     * Starts a periodic task to check for score changes.
     * This is necessary because Bukkit doesn't have a direct scoreboard change event.
     * Note: The handler tracks applied packs to prevent redundant applications,
     * minimizing the performance impact of periodic checking.
     */
    private void startPeriodicCheck() {
        // Check every 5 seconds (100 ticks)
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                checkPlayerScores(player);
            }
        }, 100L, 100L);
    }
    
    /**
     * Checks all configured scoreboard objectives for a player.
     * @param player The player to check
     */
    private void checkPlayerScores(Player player) {
        if (player == null || !player.isOnline()) {
            return;
        }
        
        for (String objectiveName : configManager.getLoadedObjectives()) {
            checkPlayerScore(player, objectiveName);
        }
    }
    
    /**
     * Checks a specific scoreboard objective for a player.
     * @param player The player to check
     * @param objectiveName The name of the scoreboard objective
     */
    private void checkPlayerScore(Player player, String objectiveName) {
        try {
            ScoreboardConfig config = configManager.getConfig(objectiveName);
            if (config == null) {
                return;
            }
            
            // Get the player's scoreboard
            org.bukkit.scoreboard.Scoreboard scoreboard = player.getScoreboard();
            if (scoreboard == null) {
                return;
            }
            
            // Get the objective
            Objective objective = scoreboard.getObjective(objectiveName);
            if (objective == null) {
                // Try main scoreboard
                org.bukkit.scoreboard.ScoreboardManager manager = Bukkit.getScoreboardManager();
                if (manager == null) {
                    return;
                }
                scoreboard = manager.getMainScoreboard();
                if (scoreboard == null) {
                    return;
                }
                objective = scoreboard.getObjective(objectiveName);
                
                if (objective == null) {
                    return;
                }
            }
            
            // Get the player's score
            // Note: Bukkit Scoreboard API uses player names as entry identifiers
            Score score = objective.getScore(player.getName());
            if (!score.isScoreSet()) {
                return;
            }
            
            int scoreValue = score.getScore();
            
            // Check if there's a resource pack for this score value
            ResourcePackConfig packConfig = config.getResourcePack(scoreValue);
            if (packConfig != null) {
                packHandler.applyResourcePack(player, packConfig, objectiveName, scoreValue);
            }
            
        } catch (Exception e) {
            plugin.getLogger().warning("Error checking score for player " + player.getName() + " on objective " + objectiveName + ": " + e.getMessage());
        }
    }
}
