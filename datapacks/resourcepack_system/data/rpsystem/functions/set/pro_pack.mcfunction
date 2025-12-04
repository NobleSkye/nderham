# Apply pro_pack resource pack
# Configuration: level=10, url=https://example.com/resourcepack3.zip

# Apply the resource pack to the executing player
# Note: Replace the URL with your actual resource pack URL
resource pack send @s "https://example.com/resourcepack3.zip"

# Mark this pack as applied
scoreboard players set @s rp_applied 10

# Notify the player
tellraw @s [{"text":"[ResourcePack System] ","color":"green"},{"text":"Applied ","color":"white"},{"text":"pro_pack","color":"light_purple","bold":true},{"text":" (Level 10)","color":"gray"}]

# Log to server console
tellraw @a[tag=admin] [{"text":"[ResourcePack System] ","color":"green"},{"selector":"@s"},{"text":" received ","color":"white"},{"text":"pro_pack","color":"light_purple"}]
