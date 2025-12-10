# Enable auto resource pack reload mode
# Simulates: /gamerule doAutoResourcepackReload true

scoreboard players set #global rp_auto 1
tellraw @a [{"text":"[ResourcePack System] ","color":"green"},{"text":"Auto mode ","color":"white"},{"text":"ENABLED","color":"green","bold":true},{"text":". Resource packs will automatically apply when level changes.","color":"white"}]
