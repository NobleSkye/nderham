# Check the status of auto resource pack reload mode

execute if score #global rp_auto matches 1 run tellraw @s [{"text":"[ResourcePack System] ","color":"green"},{"text":"Auto mode is currently ","color":"white"},{"text":"ENABLED","color":"green","bold":true}]
execute if score #global rp_auto matches 0 run tellraw @s [{"text":"[ResourcePack System] ","color":"green"},{"text":"Auto mode is currently ","color":"white"},{"text":"DISABLED","color":"red","bold":true}]
