package dev.nobleskye.scorepack.config;

/**
 * Represents a single resource pack configuration.
 */
public class ResourcePackConfig {
    private final int value;
    private final String url;
    private final String sha256;
    
    /**
     * Creates a new resource pack configuration.
     * @param value The scoreboard value that triggers this resource pack
     * @param url The URL of the resource pack
     * @param sha256 Optional SHA-256 hash for verification (can be null)
     */
    public ResourcePackConfig(int value, String url, String sha256) {
        this.value = value;
        this.url = url;
        this.sha256 = sha256;
    }
    
    /**
     * Gets the scoreboard value for this resource pack.
     * @return The value
     */
    public int getValue() {
        return value;
    }
    
    /**
     * Gets the URL of the resource pack.
     * @return The URL
     */
    public String getUrl() {
        return url;
    }
    
    /**
     * Gets the SHA-256 hash for verification.
     * @return The SHA-256 hash, or null if not specified
     */
    public String getSha256() {
        return sha256;
    }
    
    /**
     * Checks if this resource pack has a SHA-256 hash.
     * @return true if SHA-256 hash is present
     */
    public boolean hasSha256() {
        return sha256 != null && !sha256.isEmpty();
    }
}
