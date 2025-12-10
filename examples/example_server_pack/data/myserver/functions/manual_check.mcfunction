# Manual check function for resource pack assignment
# Call this periodically if not using auto mode
# Example: Run from a repeating command block or scheduled function

# Check all players and assign appropriate resource packs based on rank
execute as @a[scores={player_rank=1}] run function rpsystem:set/member_pack
execute as @a[scores={player_rank=5}] run function rpsystem:set/vip_pack
execute as @a[scores={player_rank=10}] run function rpsystem:set/admin_pack
