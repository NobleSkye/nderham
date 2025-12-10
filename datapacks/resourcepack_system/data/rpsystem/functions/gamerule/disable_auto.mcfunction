# Disable auto resource pack reload mode
# Simulates: /gamerule doAutoResourcepackReload false

scoreboard players set #global rp_auto 0
tellraw @a [{"text":"[ResourcePack System] ","color":"green"},{"text":"Auto mode ","color":"white"},{"text":"DISABLED","color":"red","bold":true},{"text":". Use manual commands to apply resource packs.","color":"white"}]
