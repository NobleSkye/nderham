package dev.nobleskye.scorepack.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the configuration for a single scoreboard objective.
 */
public class ScoreboardConfig {
    private final String objectiveName;
    private final Map<Integer, ResourcePackConfig> resourcePacks;
    
    /**
     * Creates a new scoreboard configuration.
     * @param objectiveName The name of the scoreboard objective
     */
    public ScoreboardConfig(String objectiveName) {
        this.objectiveName = objectiveName;
        this.resourcePacks = new HashMap<>();
    }
    
    /**
     * Gets the scoreboard objective name.
     * @return The objective name
     */
    public String getObjectiveName() {
        return objectiveName;
    }
    
    /**
     * Adds a resource pack configuration.
     * @param config The resource pack configuration to add
     */
    public void addResourcePack(ResourcePackConfig config) {
        resourcePacks.put(config.getValue(), config);
    }
    
    /**
     * Gets the resource pack for a specific value.
     * @param value The scoreboard value
     * @return The resource pack configuration, or null if not found
     */
    public ResourcePackConfig getResourcePack(int value) {
        return resourcePacks.get(value);
    }
    
    /**
     * Gets all configured resource packs.
     * @return List of all resource pack configurations
     */
    public List<ResourcePackConfig> getAllResourcePacks() {
        return new ArrayList<>(resourcePacks.values());
    }
    
    /**
     * Gets the number of configured resource packs.
     * @return The count
     */
    public int size() {
        return resourcePacks.size();
    }
}
