# Apply admin_pack resource pack
# Configuration from myserver namespace: player_rank=10

# Apply the resource pack to the executing player
resource pack send @s "https://cdn.example.com/packs/admin.zip"

# Mark this pack as applied
scoreboard players set @s rp_applied 10

# Notify the player
tellraw @s [{"text":"[ResourcePack System] ","color":"green"},{"text":"Applied ","color":"white"},{"text":"admin_pack","color":"red","bold":true},{"text":" (Rank 10)","color":"gray"}]

# Log to server console
tellraw @a[tag=admin] [{"text":"[ResourcePack System] ","color":"green"},{"selector":"@s"},{"text":" received ","color":"white"},{"text":"admin_pack","color":"red"}]
