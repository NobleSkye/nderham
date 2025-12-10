# Apply member_pack resource pack
# Configuration from myserver namespace: player_rank=1

# Apply the resource pack to the executing player
resource pack send @s "https://cdn.example.com/packs/member.zip" "1a2b3c4d5e6f7890abcdef1234567890abcdef1234567890abcdef1234567890"

# Mark this pack as applied
scoreboard players set @s rp_applied 1

# Notify the player
tellraw @s [{"text":"[ResourcePack System] ","color":"green"},{"text":"Applied ","color":"white"},{"text":"member_pack","color":"green","bold":true},{"text":" (Rank 1)","color":"gray"}]

# Log to server console
tellraw @a[tag=admin] [{"text":"[ResourcePack System] ","color":"green"},{"selector":"@s"},{"text":" received ","color":"white"},{"text":"member_pack","color":"green"}]
