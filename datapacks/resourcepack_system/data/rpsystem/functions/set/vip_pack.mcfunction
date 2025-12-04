# Apply vip_pack resource pack
# Configuration from myserver namespace: player_rank=5

# Apply the resource pack to the executing player
resource pack send @s "https://cdn.example.com/packs/vip.zip" "fedcba0987654321fedcba0987654321fedcba0987654321fedcba0987654321"

# Mark this pack as applied
scoreboard players set @s rp_applied 5

# Notify the player
tellraw @s [{"text":"[ResourcePack System] ","color":"green"},{"text":"Applied ","color":"white"},{"text":"vip_pack","color":"gold","bold":true},{"text":" (Rank 5)","color":"gray"}]

# Log to server console
tellraw @a[tag=admin] [{"text":"[ResourcePack System] ","color":"green"},{"selector":"@s"},{"text":" received ","color":"white"},{"text":"vip_pack","color":"gold"}]
