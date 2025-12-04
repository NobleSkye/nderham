package dev.nobleskye.scorepack.handlers;

import dev.nobleskye.scorepack.ScorePackPlugin;
import dev.nobleskye.scorepack.config.ResourcePackConfig;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Handles applying resource packs to players.
 */
public class ResourcePackHandler {
    private final ScorePackPlugin plugin;
    private final Map<UUID, Map<String, Integer>> appliedPacks;
    
    /**
     * Creates a new resource pack handler.
     * @param plugin The plugin instance
     */
    public ResourcePackHandler(ScorePackPlugin plugin) {
        this.plugin = plugin;
        this.appliedPacks = new HashMap<>();
    }
    
    /**
     * Applies a resource pack to a player.
     * @param player The player
     * @param packConfig The resource pack configuration
     * @param objectiveName The scoreboard objective name
     * @param scoreValue The current score value
     */
    public void applyResourcePack(Player player, ResourcePackConfig packConfig, String objectiveName, int scoreValue) {
        UUID playerUUID = player.getUniqueId();
        
        // Check if this pack was already applied for this objective
        Map<String, Integer> playerPacks = appliedPacks.computeIfAbsent(playerUUID, k -> new HashMap<>());
        Integer lastApplied = playerPacks.get(objectiveName);
        
        if (lastApplied != null && lastApplied == scoreValue) {
            // Already applied this pack for this score
            return;
        }
        
        try {
            // Apply the resource pack
            if (packConfig.hasSha256()) {
                // Apply with SHA-256 verification
                player.setResourcePack(packConfig.getUrl(), packConfig.getSha256());
                plugin.getLogger().info("Applied resource pack to " + player.getName() + 
                    " for objective '" + objectiveName + "' at value " + scoreValue + " (with SHA-256)");
            } else {
                // Apply without SHA-256 verification
                player.setResourcePack(packConfig.getUrl());
                plugin.getLogger().info("Applied resource pack to " + player.getName() + 
                    " for objective '" + objectiveName + "' at value " + scoreValue);
            }
            
            // Track that we applied this pack
            playerPacks.put(objectiveName, scoreValue);
            
        } catch (Exception e) {
            plugin.getLogger().warning("Failed to apply resource pack to " + player.getName() + 
                " for objective '" + objectiveName + "': " + e.getMessage());
        }
    }
    
    /**
     * Clears the applied pack tracking for a player.
     * @param playerUUID The player's UUID
     */
    public void clearPlayer(UUID playerUUID) {
        appliedPacks.remove(playerUUID);
    }
    
    /**
     * Clears all applied pack tracking.
     */
    public void clearAll() {
        appliedPacks.clear();
    }
}
