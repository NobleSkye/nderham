# Apply advanced_pack resource pack
# Configuration: level=5, url=https://example.com/resourcepack2.zip

# Apply the resource pack to the executing player
# Note: Replace the URL with your actual resource pack URL
# SHA-256 hash is optional but recommended for security
resource pack send @s "https://example.com/resourcepack2.zip" "d4735e3a265e16eee03f59718b9b5d03019c07d8b6c51f90da3a666eec13ab35"

# Mark this pack as applied
scoreboard players set @s rp_applied 5

# Notify the player
tellraw @s [{"text":"[ResourcePack System] ","color":"green"},{"text":"Applied ","color":"white"},{"text":"advanced_pack","color":"gold","bold":true},{"text":" (Level 5)","color":"gray"}]

# Log to server console
tellraw @a[tag=admin] [{"text":"[ResourcePack System] ","color":"green"},{"selector":"@s"},{"text":" received ","color":"white"},{"text":"advanced_pack","color":"gold"}]
