# Resource Pack System - Help Function
# Displays help information to the player

tellraw @s [{"text":"\n========== Resource Pack System ==========\n","color":"gold","bold":true}]
tellraw @s [{"text":"Commands:\n","color":"yellow"}]
tellraw @s [{"text":"  • ","color":"gray"},{"text":"/function rpsystem:set/starter_pack","color":"aqua","clickEvent":{"action":"suggest_command","value":"/function rpsystem:set/starter_pack"}},{"text":" - Apply starter pack","color":"white"}]
tellraw @s [{"text":"  • ","color":"gray"},{"text":"/function rpsystem:set/advanced_pack","color":"aqua","clickEvent":{"action":"suggest_command","value":"/function rpsystem:set/advanced_pack"}},{"text":" - Apply advanced pack","color":"white"}]
tellraw @s [{"text":"  • ","color":"gray"},{"text":"/function rpsystem:set/pro_pack","color":"aqua","clickEvent":{"action":"suggest_command","value":"/function rpsystem:set/pro_pack"}},{"text":" - Apply pro pack","color":"white"}]
tellraw @s [{"text":"  • ","color":"gray"},{"text":"/function rpsystem:gamerule/enable_auto","color":"aqua","clickEvent":{"action":"suggest_command","value":"/function rpsystem:gamerule/enable_auto"}},{"text":" - Enable auto mode","color":"white"}]
tellraw @s [{"text":"  • ","color":"gray"},{"text":"/function rpsystem:gamerule/disable_auto","color":"aqua","clickEvent":{"action":"suggest_command","value":"/function rpsystem:gamerule/disable_auto"}},{"text":" - Disable auto mode","color":"white"}]
tellraw @s [{"text":"  • ","color":"gray"},{"text":"/function rpsystem:gamerule/status","color":"aqua","clickEvent":{"action":"suggest_command","value":"/function rpsystem:gamerule/status"}},{"text":" - Check auto mode status","color":"white"}]
tellraw @s [{"text":"\nAuto Mode: ","color":"yellow"},{"text":"Automatically applies packs when level changes","color":"white"}]
tellraw @s [{"text":"Manual Mode: ","color":"yellow"},{"text":"Use commands or functions to apply packs","color":"white"}]
tellraw @s [{"text":"==========================================\n","color":"gold","bold":true}]
