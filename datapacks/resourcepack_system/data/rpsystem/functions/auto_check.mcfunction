# Resource Pack System - Auto Check Function
# Automatically assigns resource packs based on scoreboard values

# Check for level 1 (starter_pack)
execute as @a[scores={level=1}] unless score @s rp_applied matches 1 run function rpsystem:set/starter_pack

# Check for level 5 (advanced_pack)
execute as @a[scores={level=5}] unless score @s rp_applied matches 5 run function rpsystem:set/advanced_pack

# Check for level 10 (pro_pack)
execute as @a[scores={level=10}] unless score @s rp_applied matches 10 run function rpsystem:set/pro_pack

# Reset applied pack ID if level changes to a value without a matching pack
# Only reset if they have starter_pack but level changed
execute as @a[scores={level=2..4}] if score @s rp_applied matches 1 run scoreboard players set @s rp_applied 0
# Only reset if they have advanced_pack but level changed
execute as @a[scores={level=6..9}] if score @s rp_applied matches 5 run scoreboard players set @s rp_applied 0
# Only reset if they have pro_pack but level changed
execute as @a[scores={level=11..}] if score @s rp_applied matches 10 run scoreboard players set @s rp_applied 0
