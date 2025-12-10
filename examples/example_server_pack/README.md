# Example Server Pack

This is an example datapack demonstrating how to integrate with the Resource Pack Assignment System.

## What This Example Shows

- Custom scoreboard objective (`player_rank`)
- Custom namespace (`myserver`)
- Multiple resource pack configurations
- Integration with the main resource pack system
- Testing commands for easy demonstration

## Installation

1. Ensure the main `resourcepack_system` datapack is installed
2. Place this `example_server_pack` folder in your world's `datapacks/` directory
3. Run `/reload` in-game
4. Run `/function myserver:test_ranks` to test the system

## Testing the System

### Step 1: Enable the Example Pack
```mcfunction
/reload
```

### Step 2: Run Test Function
```mcfunction
/function myserver:test_ranks
```

This will:
- Set your rank to Member (1)
- Show clickable buttons to test different ranks
- Display instructions for auto mode

### Step 3: Enable Auto Mode (Optional)
```mcfunction
/function rpsystem:gamerule/enable_auto
```

Now when you click the rank buttons, resource packs will automatically apply!

### Step 4: Test Manual Commands
```mcfunction
/function rpsystem:set/member_pack
/function rpsystem:set/vip_pack
/function rpsystem:set/admin_pack
```

## Configuration

The resource pack configuration is in `data/myserver/resourcepack.json`:

```json
{
  "scoreboard_objective": "player_rank",
  "resourcepacks": {
    "member_pack": {
      "value": 1,
      "url": "https://cdn.example.com/packs/member.zip",
      "sha256": "..."
    },
    "vip_pack": {
      "value": 5,
      "url": "https://cdn.example.com/packs/vip.zip",
      "sha256": "..."
    },
    "admin_pack": {
      "value": 10,
      "url": "https://cdn.example.com/packs/admin.zip"
    }
  }
}
```

## Customization

To adapt this for your server:

1. Replace the URLs with your actual resource pack URLs
2. Update the SHA-256 hashes (or remove them)
3. Modify the scoreboard objective name if needed
4. Adjust the rank values to match your system
5. Create corresponding function files in `data/rpsystem/functions/set/`

## Functions Included

- `myserver:load` - Initializes the scoreboard objective
- `myserver:test_ranks` - Interactive testing interface
- `myserver:manual_check` - Periodic check for manual mode

## Integration with Other Systems

This example pack can work alongside other datapacks:

```mcfunction
# In your custom progression system
execute as @a[scores={playtime=1000..}] run scoreboard players set @s player_rank 1
execute as @a[scores={playtime=10000..}] run scoreboard players set @s player_rank 5
execute as @a[scores={playtime=100000..}] run scoreboard players set @s player_rank 10
```

## Troubleshooting

- If resource packs don't apply, make sure the main `resourcepack_system` is loaded
- Check that auto mode is enabled if you expect automatic application
- Verify the URLs in the JSON file are direct download links
- Use `/function rpsystem:help` for command reference
