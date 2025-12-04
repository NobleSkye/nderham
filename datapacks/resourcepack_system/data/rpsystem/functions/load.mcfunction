# Resource Pack System - Load Function
# This runs when the datapack is loaded or /reload is executed

# Create scoreboard objectives if they don't exist
scoreboard objectives add level dummy "Player Level"
scoreboard objectives add rp_auto dummy "Auto Resource Pack Reload"
scoreboard objectives add rp_applied dummy "Applied Resource Pack ID"

# Set default value for auto mode (0 = manual, 1 = auto)
# This simulates the doAutoResourcepackReload gamerule
execute unless score #global rp_auto = #global rp_auto run scoreboard players set #global rp_auto 0

# Notify that the system has loaded
tellraw @a [{"text":"[ResourcePack System] ","color":"green"},{"text":"Loaded successfully! Use ","color":"white"},{"text":"/function rpsystem:help","color":"yellow"},{"text":" for help.","color":"white"}]
