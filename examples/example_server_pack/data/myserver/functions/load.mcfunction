# Example Server Pack - Load Function
# This demonstrates how to integrate with the resource pack system

# Create custom scoreboard objective for player ranks
scoreboard objectives add player_rank dummy "Player Rank"

# Set some example ranks for testing
# In production, these would be set by your rank system
tellraw @a [{"text":"[Example Pack] ","color":"aqua"},{"text":"Loaded! Use ","color":"white"},{"text":"/function myserver:test_ranks","color":"yellow"},{"text":" to test.","color":"white"}]
