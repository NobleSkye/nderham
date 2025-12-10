# Resource Pack System - Tick Function
# This runs every game tick (20 times per second)

# Auto mode: Check if auto resource pack reload is enabled
execute if score #global rp_auto matches 1 run function rpsystem:auto_check
