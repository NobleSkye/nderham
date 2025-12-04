# Apply starter_pack resource pack
# Configuration: level=1, url=https://example.com/resourcepack1.zip

# Apply the resource pack to the executing player
# Note: Replace the URL with your actual resource pack URL
# SHA-256 hash is optional but recommended for security
resource pack send @s "https://example.com/resourcepack1.zip" "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"

# Mark this pack as applied
scoreboard players set @s rp_applied 1

# Notify the player
tellraw @s [{"text":"[ResourcePack System] ","color":"green"},{"text":"Applied ","color":"white"},{"text":"starter_pack","color":"aqua","bold":true},{"text":" (Level 1)","color":"gray"}]

# Log to server console
tellraw @a[tag=admin] [{"text":"[ResourcePack System] ","color":"green"},{"selector":"@s"},{"text":" received ","color":"white"},{"text":"starter_pack","color":"aqua"}]
