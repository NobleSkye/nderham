# Test function to set player ranks
# This demonstrates the resource pack system in action

tellraw @s [{"text":"\n==== Testing Resource Pack System ====\n","color":"gold","bold":true}]
tellraw @s [{"text":"Setting your rank to Member (1)...\n","color":"white"}]

# Set player rank to 1 (member)
scoreboard players set @s player_rank 1

tellraw @s [{"text":"Click to test different ranks:\n","color":"yellow"}]
tellraw @s [{"text":"[Set Member Rank (1)]","color":"green","clickEvent":{"action":"run_command","value":"/scoreboard players set @s player_rank 1"},"hoverText":"Click to set rank to Member"}]
tellraw @s [{"text":"[Set VIP Rank (5)]","color":"gold","clickEvent":{"action":"run_command","value":"/scoreboard players set @s player_rank 5"},"hoverText":"Click to set rank to VIP"}]
tellraw @s [{"text":"[Set Admin Rank (10)]","color":"red","clickEvent":{"action":"run_command","value":"/scoreboard players set @s player_rank 10"},"hoverText":"Click to set rank to Admin"}]

tellraw @s [{"text":"\nNote: ","color":"gray"},{"text":"Enable auto mode with ","color":"white"},{"text":"/function rpsystem:gamerule/enable_auto","color":"aqua","clickEvent":{"action":"suggest_command","value":"/function rpsystem:gamerule/enable_auto"}}]
tellraw @s [{"text":"=====================================\n","color":"gold","bold":true}]
