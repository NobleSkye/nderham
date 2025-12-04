# Resource Pack System - Auto Check Function
# Automatically assigns resource packs based on scoreboard values

# Check for level 1 (starter_pack)
execute as @a[scores={level=1}] unless score @s rp_applied matches 1 run function rpsystem:set/starter_pack

# Check for level 5 (advanced_pack)
execute as @a[scores={level=5}] unless score @s rp_applied matches 5 run function rpsystem:set/advanced_pack

# Check for level 10 (pro_pack)
execute as @a[scores={level=10}] unless score @s rp_applied matches 10 run function rpsystem:set/pro_pack

# Reset applied pack ID if level changes to a value without a matching pack
execute as @a[scores={level=2..4}] unless score @s rp_applied matches 0 run scoreboard players set @s rp_applied 0
execute as @a[scores={level=6..9}] unless score @s rp_applied matches 0 run scoreboard players set @s rp_applied 0
execute as @a[scores={level=11..}] unless score @s rp_applied matches 0 run scoreboard players set @s rp_applied 0
