# Minecraft Resource Pack Assignment System

A Minecraft datapack that assigns resource packs to players based on scoreboard values with JSON configuration support.

## Features

- ✅ **JSON Configuration**: Define resource packs with scoreboard values in JSON files
- ✅ **Auto Mode**: Automatically apply resource packs when scoreboard values change
- ✅ **Manual Mode**: Use commands to manually apply resource packs
- ✅ **SHA-256 Support**: Optional hash verification for resource pack security
- ✅ **Selector Support**: Works with all Minecraft selectors (@a, @p, @s, @r, @e)
- ✅ **Reload Compatible**: Use `/reload` to refresh configurations
- ✅ **Multi-Namespace**: Support for multiple resourcepack.json files across namespaces

## Installation

1. Download or clone this datapack
2. Place the `resourcepack_system` folder in your world's `datapacks/` directory
3. Run `/reload` in-game or restart the server
4. You should see a confirmation message that the system loaded successfully

## Quick Start

### Check Status
```mcfunction
/function rpsystem:help
/function rpsystem:gamerule/status
```

### Enable Auto Mode
```mcfunction
/function rpsystem:gamerule/enable_auto
```

### Manual Resource Pack Application
```mcfunction
/function rpsystem:set/starter_pack
/function rpsystem:set/advanced_pack
/function rpsystem:set/pro_pack
```

### Test Scoreboard Integration
```mcfunction
# Create a test player level
/scoreboard objectives add level dummy "Player Level"
/scoreboard players set @s level 1

# In auto mode, the pack will apply automatically
# In manual mode, use: /function rpsystem:set/starter_pack
```

## Configuration

### JSON Configuration Format

The configuration file is located at `data/minecraft/resourcepack.json`:

```json
{
  "scoreboard_objective": "level",
  "resourcepacks": {
    "starter_pack": {
      "value": 1,
      "url": "https://example.com/resourcepack1.zip",
      "sha256": "optional-hash-here"
    },
    "advanced_pack": {
      "value": 5,
      "url": "https://example.com/resourcepack2.zip",
      "sha256": "abc123..."
    },
    "pro_pack": {
      "value": 10,
      "url": "https://example.com/resourcepack3.zip"
    }
  }
}
```

### JSON Schema

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `scoreboard_objective` | string | Yes | The scoreboard objective to monitor |
| `resourcepacks` | object | Yes | Collection of resource pack definitions |
| `resourcepacks.<name>` | object | Yes | Individual resource pack configuration |
| `resourcepacks.<name>.value` | number | Yes | Scoreboard value that triggers this pack |
| `resourcepacks.<name>.url` | string | Yes | Direct download URL for the resource pack |
| `resourcepacks.<name>.sha256` | string | No | SHA-256 hash for verification (optional) |

### Multiple Namespaces

You can create multiple `resourcepack.json` files in different namespaces:

```
datapacks/
├── resourcepack_system/
│   └── data/
│       ├── minecraft/
│       │   └── resourcepack.json
│       └── myserver/
│           └── resourcepack.json
```

## Commands & Functions

### Help System
```mcfunction
/function rpsystem:help
```
Shows all available commands and current mode status.

### Gamerule Simulation (Auto Mode Control)

**Enable Auto Mode**
```mcfunction
/function rpsystem:gamerule/enable_auto
```
Enables automatic resource pack assignment when scoreboard values change.

**Disable Auto Mode**
```mcfunction
/function rpsystem:gamerule/disable_auto
```
Disables automatic assignment. Resource packs must be applied manually.

**Check Status**
```mcfunction
/function rpsystem:gamerule/status
```
Displays current auto mode status.

### Manual Resource Pack Commands

**Apply Starter Pack (Level 1)**
```mcfunction
/function rpsystem:set/starter_pack
```

**Apply Advanced Pack (Level 5)**
```mcfunction
/function rpsystem:set/advanced_pack
```

**Apply Pro Pack (Level 10)**
```mcfunction
/function rpsystem:set/pro_pack
```

### Using with Selectors

All commands support Minecraft selectors:

```mcfunction
# Apply to all players with level 5
execute as @a[scores={level=5}] run function rpsystem:set/advanced_pack

# Apply to nearest player
execute as @p run function rpsystem:set/starter_pack

# Apply to random player
execute as @r run function rpsystem:set/pro_pack

# Apply to yourself
execute as @s run function rpsystem:set/advanced_pack

# Apply to players in specific area
execute as @a[x=0,y=64,z=0,distance=..50] run function rpsystem:set/starter_pack
```

## Modes of Operation

### Auto Mode (Recommended for Dynamic Systems)

When auto mode is enabled:
- The system monitors the configured scoreboard objective
- When a player's score matches a value in the JSON configuration
- The corresponding resource pack is automatically applied
- Manual commands still work alongside auto mode

**Example:**
```mcfunction
# Enable auto mode
/function rpsystem:gamerule/enable_auto

# Player's level changes to 5
/scoreboard players set @s level 5
# System automatically applies advanced_pack
```

### Manual Mode (Recommended for Custom Logic)

When auto mode is disabled:
- Resource packs only apply via function commands
- Gives full control to datapack creators
- Ideal for custom progression systems

**Example Datapack Function:**
```mcfunction
# File: data/mypack/functions/check_levels.mcfunction

# Check all players and apply appropriate packs
execute as @a[scores={level=1}] run function rpsystem:set/starter_pack
execute as @a[scores={level=5}] run function rpsystem:set/advanced_pack
execute as @a[scores={level=10}] run function rpsystem:set/pro_pack
```

## Integration Examples

### Example 1: Level-Based Progression

Create a function that runs periodically to check player levels:

```mcfunction
# File: data/myserver/functions/update_packs.mcfunction

# Starter tier (levels 1-4)
execute as @a[scores={level=1..4}] run function rpsystem:set/starter_pack

# Advanced tier (levels 5-9)
execute as @a[scores={level=5..9}] run function rpsystem:set/advanced_pack

# Pro tier (levels 10+)
execute as @a[scores={level=10..}] run function rpsystem:set/pro_pack
```

### Example 2: Achievement-Based Packs

```mcfunction
# File: data/myserver/functions/achievement_packs.mcfunction

# When player completes first achievement
execute as @a[scores={achievements=1}] run function rpsystem:set/starter_pack

# When player completes 10 achievements
execute as @a[scores={achievements=10}] run function rpsystem:set/advanced_pack

# When player completes all achievements
execute as @a[scores={achievements=50}] run function rpsystem:set/pro_pack
```

### Example 3: Time-Based Packs

```mcfunction
# File: data/myserver/functions/playtime_packs.mcfunction

# After 1 hour of playtime (72000 ticks)
execute as @a[scores={playtime=72000..}] run function rpsystem:set/starter_pack

# After 10 hours
execute as @a[scores={playtime=720000..}] run function rpsystem:set/advanced_pack

# After 100 hours
execute as @a[scores={playtime=7200000..}] run function rpsystem:set/pro_pack
```

## Customizing Resource Packs

### Adding New Resource Packs

1. Edit `data/minecraft/resourcepack.json`
2. Add a new entry under `resourcepacks`:

```json
{
  "scoreboard_objective": "level",
  "resourcepacks": {
    "starter_pack": { ... },
    "advanced_pack": { ... },
    "pro_pack": { ... },
    "elite_pack": {
      "value": 20,
      "url": "https://example.com/resourcepack4.zip",
      "sha256": "def456..."
    }
  }
}
```

3. Create a new function file:
```mcfunction
# File: data/rpsystem/functions/set/elite_pack.mcfunction

resource pack send @s "https://example.com/resourcepack4.zip" "def456..."
scoreboard players set @s rp_applied 20
tellraw @s [{"text":"[ResourcePack System] ","color":"green"},{"text":"Applied ","color":"white"},{"text":"elite_pack","color":"dark_purple","bold":true}]
```

4. Update the auto_check function if using auto mode:
```mcfunction
# Add to data/rpsystem/functions/auto_check.mcfunction
execute as @a[scores={level=20}] unless score @s rp_applied matches 20 run function rpsystem:set/elite_pack
```

5. Run `/reload` to apply changes

### Generating SHA-256 Hashes

To generate a SHA-256 hash for your resource pack:

**Linux/Mac:**
```bash
sha256sum yourpack.zip
```

**Windows (PowerShell):**
```powershell
Get-FileHash yourpack.zip -Algorithm SHA256
```

**Online Tools:**
- Use online SHA-256 calculators (ensure you trust the site)

Add the hash to your configuration:
```json
{
  "value": 1,
  "url": "https://example.com/pack.zip",
  "sha256": "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"
}
```

## Troubleshooting

### Resource Pack Not Applying

1. **Check the URL**: Ensure the resource pack URL is directly downloadable
2. **Verify the hash**: If using SHA-256, make sure it matches the file
3. **Check scoreboard**: Verify the player has the correct scoreboard value
   ```mcfunction
   /scoreboard players get @s level
   ```
4. **Check mode**: Verify auto mode is enabled if you expect automatic application
   ```mcfunction
   /function rpsystem:gamerule/status
   ```

### System Not Loading

1. **Check pack.mcmeta**: Ensure the pack format matches your Minecraft version
2. **Verify structure**: Make sure the datapack folder structure is correct
3. **Check logs**: Look at the server console for error messages
4. **Reload**: Try running `/reload` in-game

### Auto Mode Not Working

1. **Enable auto mode**: Run `/function rpsystem:gamerule/enable_auto`
2. **Check scoreboard objective**: Ensure the objective exists and has values
3. **Verify JSON config**: Make sure the scoreboard objective name matches

### SHA-256 Verification Failing

1. **Regenerate hash**: The file may have changed
2. **Remove hash**: SHA-256 is optional; remove it to skip verification
3. **Check encoding**: Ensure the hash is lowercase hexadecimal

## Technical Details

### Scoreboard Objectives

The system uses three scoreboard objectives:

- **`level`** (configurable): The objective monitored for resource pack assignment
- **`rp_auto`**: Stores the auto mode state (0 = manual, 1 = auto)
- **`rp_applied`**: Tracks which pack is currently applied to each player

### Performance Considerations

- **Auto mode** runs every game tick (20 times per second)
- Consider disabling auto mode for very large servers
- Use manual mode with less frequent function calls for better performance

### Version Compatibility

- **Pack Format 15**: Minecraft 1.20.2 - 1.20.4
- **Pack Format 18**: Minecraft 1.20.5 - 1.20.6
- **Pack Format 26**: Minecraft 1.21+

Update `pack_format` in `pack.mcmeta` for your Minecraft version.

### Resource Pack Command

The system uses Minecraft's built-in resource pack command:
```mcfunction
resource pack send <target> <url> [hash]
```

This command:
- Sends a resource pack download prompt to players
- Supports optional SHA-256 verification
- Works with direct download URLs only

## Advanced Usage

### Custom Gamerule Integration

For server mods/plugins that want to create a real gamerule:

```java
// Example using Paper API (Java)
public class ResourcePackSystem extends JavaPlugin {
    @Override
    public void onEnable() {
        // Register custom gamerule
        // This would integrate with the datapack system
    }
}
```

### Custom Command Integration

For server mods/plugins that want to create the `/resourcepack` command:

```java
// Example command registration
@Override
public boolean onCommand(CommandSender sender, Command command, 
                        String label, String[] args) {
    if (command.getName().equalsIgnoreCase("resourcepack")) {
        if (args.length == 2 && args[0].equals("set")) {
            String packName = args[1];
            // Call corresponding datapack function
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), 
                "function rpsystem:set/" + packName);
            return true;
        }
    }
    return false;
}
```

### API for Other Datapacks

Other datapacks can interact with this system:

```mcfunction
# Check if a player has a specific pack applied
execute if score @s rp_applied matches 1 run say I have starter pack!

# Force a pack change
function rpsystem:set/advanced_pack

# Check auto mode status
execute if score #global rp_auto matches 1 run say Auto mode is on!
```

## Credits & License

This datapack system is designed to be flexible and extensible. Feel free to modify and distribute according to your needs.

For questions and support, contact: mailto:contact@nobleskye.dev

## Changelog

### Version 1.0.0
- Initial release
- JSON configuration support
- Auto and manual modes
- SHA-256 hash support
- Selector compatibility
- Multi-namespace support
