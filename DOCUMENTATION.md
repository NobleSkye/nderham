# Minecraft Resource Pack Assignment System - Complete Documentation

## Table of Contents
1. [Overview](#overview)
2. [Installation](#installation)
3. [Quick Start Guide](#quick-start-guide)
4. [Configuration](#configuration)
5. [Command Reference](#command-reference)
6. [Modes of Operation](#modes-of-operation)
7. [Integration Examples](#integration-examples)
8. [Advanced Usage](#advanced-usage)
9. [Troubleshooting](#troubleshooting)
10. [Technical Details](#technical-details)

## Overview

The Minecraft Resource Pack Assignment System is a datapack that automatically or manually assigns resource packs to players based on scoreboard values. It provides a flexible, JSON-based configuration system that integrates seamlessly with existing Minecraft datapack workflows.

### Key Features

- **JSON Configuration**: Define resource packs with URLs and scoreboard triggers
- **Dual Mode Operation**: Auto and manual modes for different use cases
- **SHA-256 Support**: Optional cryptographic verification of resource packs
- **Selector Compatible**: Works with all Minecraft selectors (@a, @p, @s, @r, @e)
- **Multi-Namespace**: Support for multiple configurations across namespaces
- **Reload Compatible**: Full support for `/reload` command
- **Error Handling**: Comprehensive error messages and logging

### Use Cases

- **Progression Systems**: Assign different resource packs as players level up
- **Rank Systems**: Different visual themes for different player ranks
- **Achievement Rewards**: Unlock new resource packs through achievements
- **Time-Based Changes**: Different packs based on playtime or date
- **Custom Game Modes**: Unique visuals for different game modes or areas

## Installation

### Step 1: Download the Datapack

Download or clone this repository to your local machine.

### Step 2: Install in Your World

1. Locate your Minecraft world save folder
   - **Windows**: `%APPDATA%\.minecraft\saves\<world_name>\datapacks`
   - **Mac**: `~/Library/Application Support/minecraft/saves/<world_name>/datapacks`
   - **Linux**: `~/.minecraft/saves/<world_name>/datapacks`
   - **Server**: `<server_folder>/<world_name>/datapacks`

2. Copy the `resourcepack_system` folder into the `datapacks` directory

3. Start your world/server or run `/reload` if already running

### Step 3: Verify Installation

You should see a message in chat:
```
[ResourcePack System] Loaded successfully! Use /function rpsystem:help for help.
```

## Quick Start Guide

### First Time Setup

1. **Load the datapack**
   ```mcfunction
   /reload
   ```

2. **View available commands**
   ```mcfunction
   /function rpsystem:help
   ```

3. **Check current mode**
   ```mcfunction
   /function rpsystem:gamerule/status
   ```

### Quick Test

1. **Create a test scoreboard**
   ```mcfunction
   /scoreboard objectives add level dummy "Player Level"
   /scoreboard players set @s level 1
   ```

2. **Apply a resource pack manually**
   ```mcfunction
   /function rpsystem:set/starter_pack
   ```

3. **Enable auto mode and test**
   ```mcfunction
   /function rpsystem:gamerule/enable_auto
   /scoreboard players set @s level 5
   ```
   The advanced pack should automatically apply!

## Configuration

### JSON Configuration File

Resource packs are defined in `data/<namespace>/resourcepack.json` files.

**Location**: `datapacks/resourcepack_system/data/minecraft/resourcepack.json`

**Format**:
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

### JSON Schema Reference

| Field | Type | Required | Description |
|-------|------|----------|-------------|
| `scoreboard_objective` | string | ✅ Yes | Name of the scoreboard objective to monitor |
| `resourcepacks` | object | ✅ Yes | Collection of resource pack definitions |
| `resourcepacks.<name>` | object | ✅ Yes | Individual resource pack (name must be unique) |
| `resourcepacks.<name>.value` | number | ✅ Yes | Scoreboard value that triggers this pack |
| `resourcepacks.<name>.url` | string | ✅ Yes | Direct download URL for the resource pack ZIP |
| `resourcepacks.<name>.sha256` | string | ⚠️ Optional | SHA-256 hash for verification (recommended) |

### Creating Resource Pack Functions

For each resource pack in your JSON, create a corresponding function file:

**Location**: `data/rpsystem/functions/set/<pack_name>.mcfunction`

**Template**:
```mcfunction
# Apply <pack_name> resource pack
# Configuration: <objective>=<value>

# Apply the resource pack
resource pack send @s "<url>" "<sha256>"

# Mark as applied
scoreboard players set @s rp_applied <value>

# Notify player
tellraw @s [{"text":"[ResourcePack System] ","color":"green"},{"text":"Applied ","color":"white"},{"text":"<pack_name>","color":"aqua","bold":true}]

# Log to admins
tellraw @a[tag=admin] [{"text":"[ResourcePack System] ","color":"green"},{"selector":"@s"},{"text":" received ","color":"white"},{"text":"<pack_name>","color":"aqua"}]
```

### Multiple Namespaces

You can create multiple configuration files across different namespaces:

```
datapacks/
├── resourcepack_system/
│   └── data/
│       ├── minecraft/
│       │   └── resourcepack.json
│       └── rpsystem/
│           └── functions/...
├── my_server_pack/
│   └── data/
│       └── myserver/
│           └── resourcepack.json
```

This allows different datapacks to define their own resource pack configurations without conflicts.

## Command Reference

### Help Command

```mcfunction
/function rpsystem:help
```

Displays all available commands and current system status.

### Gamerule Commands (Auto Mode Control)

#### Enable Auto Mode
```mcfunction
/function rpsystem:gamerule/enable_auto
```

Enables automatic resource pack assignment when scoreboard values change.

**Effect**: 
- System monitors scoreboard objective every tick
- Automatically applies matching resource packs
- Manual commands still work

#### Disable Auto Mode
```mcfunction
/function rpsystem:gamerule/disable_auto
```

Disables automatic assignment. Resource packs only apply via commands.

**Effect**:
- Auto monitoring stops
- Only manual commands apply packs
- Better performance for large servers

#### Check Status
```mcfunction
/function rpsystem:gamerule/status
```

Shows whether auto mode is currently enabled or disabled.

### Resource Pack Commands

#### Apply Starter Pack
```mcfunction
/function rpsystem:set/starter_pack
```

Applies the starter pack (level 1) to the executing player.

#### Apply Advanced Pack
```mcfunction
/function rpsystem:set/advanced_pack
```

Applies the advanced pack (level 5) to the executing player.

#### Apply Pro Pack
```mcfunction
/function rpsystem:set/pro_pack
```

Applies the pro pack (level 10) to the executing player.

### Using with Selectors

All resource pack commands support Minecraft selectors:

```mcfunction
# Apply to all players
execute as @a run function rpsystem:set/starter_pack

# Apply to nearest player
execute as @p run function rpsystem:set/advanced_pack

# Apply to random player
execute as @r run function rpsystem:set/pro_pack

# Apply to yourself
execute as @s run function rpsystem:set/starter_pack

# Apply to players with specific scores
execute as @a[scores={level=5}] run function rpsystem:set/advanced_pack

# Apply to players in specific area
execute as @a[x=0,y=64,z=0,distance=..50] run function rpsystem:set/starter_pack

# Apply to players with specific tag
execute as @a[tag=vip] run function rpsystem:set/pro_pack
```

## Modes of Operation

### Auto Mode

**When to use**: Dynamic systems where scoreboard values change frequently.

**How it works**:
1. Enable with `/function rpsystem:gamerule/enable_auto`
2. System checks scoreboard every tick (20 times per second)
3. When a player's score matches a value in the JSON
4. The corresponding resource pack is automatically applied
5. Manual commands still work alongside auto mode

**Example**:
```mcfunction
# Enable auto mode
/function rpsystem:gamerule/enable_auto

# Change player level
/scoreboard players set @s level 5

# System automatically applies advanced_pack
```

**Performance Note**: Auto mode runs every tick. For very large servers (100+ players), consider manual mode with less frequent checks.

### Manual Mode

**When to use**: Custom logic, periodic checks, or performance-sensitive servers.

**How it works**:
1. Disable auto mode (default state) or use `/function rpsystem:gamerule/disable_auto`
2. Create your own functions to check and apply packs
3. Call these functions when needed (e.g., on events, periodically, etc.)

**Example**:
```mcfunction
# Create a function that runs periodically
# File: data/mypack/functions/check_packs.mcfunction

execute as @a[scores={level=1}] run function rpsystem:set/starter_pack
execute as @a[scores={level=5}] run function rpsystem:set/advanced_pack
execute as @a[scores={level=10}] run function rpsystem:set/pro_pack

# Call this function less frequently than every tick
# For example, once per second via scheduled function
```

## Integration Examples

### Example 1: Level-Based Progression

**Scenario**: Different resource packs for different player levels.

```mcfunction
# File: data/mypack/functions/level_packs.mcfunction

# Novice tier (levels 1-4)
execute as @a[scores={level=1..4}] run function rpsystem:set/starter_pack

# Intermediate tier (levels 5-9)
execute as @a[scores={level=5..9}] run function rpsystem:set/advanced_pack

# Expert tier (levels 10-19)
execute as @a[scores={level=10..19}] run function rpsystem:set/pro_pack

# Master tier (levels 20+)
execute as @a[scores={level=20..}] run function rpsystem:set/elite_pack
```

### Example 2: Rank-Based Resource Packs

**Scenario**: Different visual themes for different server ranks.

```json
// data/myserver/resourcepack.json
{
  "scoreboard_objective": "rank",
  "resourcepacks": {
    "default_pack": {
      "value": 0,
      "url": "https://cdn.myserver.com/packs/default.zip"
    },
    "vip_pack": {
      "value": 1,
      "url": "https://cdn.myserver.com/packs/vip.zip"
    },
    "mvp_pack": {
      "value": 2,
      "url": "https://cdn.myserver.com/packs/mvp.zip"
    },
    "admin_pack": {
      "value": 3,
      "url": "https://cdn.myserver.com/packs/admin.zip"
    }
  }
}
```

```mcfunction
# When player gets promoted
scoreboard players set @s rank 1
# In auto mode, VIP pack automatically applies
```

### Example 3: Achievement-Based Unlocks

**Scenario**: Unlock new resource packs by completing achievements.

```mcfunction
# File: data/mypack/functions/check_achievements.mcfunction

# First achievement
execute as @a[scores={achievements=1}] run function rpsystem:set/bronze_pack

# 10 achievements
execute as @a[scores={achievements=10}] run function rpsystem:set/silver_pack

# 50 achievements
execute as @a[scores={achievements=50}] run function rpsystem:set/gold_pack

# All achievements
execute as @a[scores={achievements=100}] run function rpsystem:set/platinum_pack
```

### Example 4: Time-Based Changes

**Scenario**: Different packs based on playtime.

```mcfunction
# File: data/mypack/functions/playtime_packs.mcfunction

# Convert playtime to hours (assuming playtime is in ticks)
# 72000 ticks = 1 hour

# New player (< 1 hour)
execute as @a[scores={playtime=..72000}] run function rpsystem:set/newbie_pack

# Regular player (1-10 hours)
execute as @a[scores={playtime=72000..720000}] run function rpsystem:set/regular_pack

# Veteran player (10-100 hours)
execute as @a[scores={playtime=720000..7200000}] run function rpsystem:set/veteran_pack

# Legend (100+ hours)
execute as @a[scores={playtime=7200000..}] run function rpsystem:set/legend_pack
```

### Example 5: Zone-Based Resource Packs

**Scenario**: Different visual styles for different areas of your map.

```mcfunction
# File: data/mypack/functions/zone_packs.mcfunction

# Spawn area
execute as @a[x=0,y=64,z=0,distance=..100] run function rpsystem:set/spawn_pack

# PvP arena
execute as @a[x=500,y=64,z=500,distance=..50] run function rpsystem:set/pvp_pack

# Creative world
execute as @a[x=-500,y=64,z=-500,distance=..200] run function rpsystem:set/creative_pack

# End dimension
execute as @a[predicate=mypack:in_the_end] run function rpsystem:set/end_pack
```

## Advanced Usage

### Custom Auto-Check Logic

Modify `auto_check.mcfunction` to add custom logic:

```mcfunction
# File: data/rpsystem/functions/auto_check.mcfunction

# Original checks
execute as @a[scores={level=1}] unless score @s rp_applied matches 1 run function rpsystem:set/starter_pack
execute as @a[scores={level=5}] unless score @s rp_applied matches 5 run function rpsystem:set/advanced_pack
execute as @a[scores={level=10}] unless score @s rp_applied matches 10 run function rpsystem:set/pro_pack

# Add range-based checks
execute as @a[scores={level=2..4}] unless score @s rp_applied matches 1 run function rpsystem:set/starter_pack
execute as @a[scores={level=6..9}] unless score @s rp_applied matches 5 run function rpsystem:set/advanced_pack
execute as @a[scores={level=11..}] unless score @s rp_applied matches 10 run function rpsystem:set/pro_pack
```

### Performance Optimization

For large servers, optimize auto-check by reducing frequency:

```mcfunction
# File: data/rpsystem/functions/tick.mcfunction

# Check every second instead of every tick
execute if score #timer rp_auto matches 0 if score #global rp_auto matches 1 run function rpsystem:auto_check

# Timer logic
scoreboard players add #timer rp_auto 1
execute if score #timer rp_auto matches 20.. run scoreboard players set #timer rp_auto 0
```

### Conditional Resource Packs

Apply packs based on multiple conditions:

```mcfunction
# Only apply pack if player meets multiple requirements
execute as @a[scores={level=10,playtime=720000..},tag=verified] run function rpsystem:set/elite_pack
```

### Resource Pack Rotation

Rotate between multiple packs:

```mcfunction
# File: data/mypack/functions/rotate_packs.mcfunction

# Check current pack and switch to next
execute as @s[scores={rp_applied=1}] run function rpsystem:set/advanced_pack
execute as @s[scores={rp_applied=5}] run function rpsystem:set/pro_pack
execute as @s[scores={rp_applied=10}] run function rpsystem:set/starter_pack
```

## Troubleshooting

### Resource Pack Not Applying

**Symptoms**: No resource pack download prompt appears.

**Solutions**:

1. **Check the URL**
   - Ensure it's a direct download link (ends in `.zip`)
   - Test the URL in a web browser
   - Verify the server allows direct downloads (no landing pages)

2. **Verify Scoreboard Values**
   ```mcfunction
   /scoreboard players get @s level
   ```
   Ensure the value matches what's in your JSON configuration.

3. **Check Function Exists**
   - Verify the function file exists
   - Check for typos in the function name
   - Run `/function rpsystem:set/<pack_name>` directly to test

4. **Test Manual Command**
   ```mcfunction
   /function rpsystem:set/starter_pack
   ```
   If manual command works, the issue is with auto mode logic.

### System Not Loading

**Symptoms**: No load message appears in chat.

**Solutions**:

1. **Verify Folder Structure**
   ```
   datapacks/
   └── resourcepack_system/
       ├── pack.mcmeta
       └── data/
           ├── minecraft/
           │   ├── resourcepack.json
           │   └── tags/
           │       └── functions/
           │           ├── load.json
           │           └── tick.json
           └── rpsystem/
               └── functions/
                   ├── load.mcfunction
                   └── ...
   ```

2. **Check pack.mcmeta Format**
   - Ensure pack_format matches your Minecraft version
   - Verify JSON syntax is correct

3. **Check Server Console**
   - Look for error messages about the datapack
   - Errors usually indicate JSON syntax issues

4. **Try Manual Reload**
   ```mcfunction
   /reload
   ```

### Auto Mode Not Working

**Symptoms**: Scoreboard changes but packs don't auto-apply.

**Solutions**:

1. **Verify Auto Mode is Enabled**
   ```mcfunction
   /function rpsystem:gamerule/status
   ```
   Should show "ENABLED". If not, enable it:
   ```mcfunction
   /function rpsystem:gamerule/enable_auto
   ```

2. **Check Scoreboard Objective Name**
   - Ensure JSON uses correct objective name
   - Verify objective exists:
   ```mcfunction
   /scoreboard objectives list
   ```

3. **Check Auto-Check Function**
   - Verify `auto_check.mcfunction` has entries for your packs
   - Ensure value ranges don't overlap incorrectly

4. **Test Tick Function**
   - Manually run:
   ```mcfunction
   /function rpsystem:tick
   ```
   Should trigger auto-check if enabled.

### SHA-256 Verification Failing

**Symptoms**: Resource pack download fails or shows hash mismatch error.

**Solutions**:

1. **Regenerate Hash**
   ```bash
   # Linux/Mac
   sha256sum yourpack.zip
   
   # Windows PowerShell
   Get-FileHash yourpack.zip -Algorithm SHA256
   ```

2. **Update JSON Configuration**
   - Copy the new hash into your JSON file
   - Ensure it's lowercase hexadecimal (64 characters)

3. **Remove Hash (Temporary)**
   - Remove the sha256 field from JSON
   - This disables verification (less secure)

4. **Verify File Integrity**
   - Ensure the ZIP file hasn't been modified
   - Re-upload if necessary

### Players Not Receiving Packs

**Symptoms**: Other players don't get resource packs.

**Solutions**:

1. **Check Server Settings**
   - `server.properties`: `resource-pack-prompt` should allow custom packs
   - Some servers restrict resource pack downloads

2. **Verify Player Settings**
   - Players must have "Server Resource Packs" enabled
   - Check in Settings > Resource Packs

3. **Test with Different Player**
   - Use a different account to test
   - Check if issue is player-specific

4. **Check File Size**
   - Large packs (>50MB) may fail
   - Consider compressing or splitting packs

## Technical Details

### Scoreboard Objectives

The system uses three scoreboard objectives:

1. **`level`** (or configured name)
   - Monitors player progression/state
   - Name configurable in JSON
   - Type: `dummy` objective

2. **`rp_auto`**
   - Stores auto mode state
   - 0 = manual mode
   - 1 = auto mode
   - Stored on `#global` fake player

3. **`rp_applied`**
   - Tracks which pack is currently applied
   - Value matches the scoreboard value of the applied pack
   - Prevents redundant applications

### Function Execution Flow

#### Load Sequence
1. `/reload` command executed
2. `minecraft:load` tag triggers
3. `rpsystem:load` function runs
4. Scoreboards created
5. Default values set
6. Load message displayed

#### Tick Sequence (Auto Mode)
1. Every game tick (20 per second)
2. `minecraft:tick` tag triggers
3. `rpsystem:tick` function runs
4. Checks if auto mode enabled
5. If yes, calls `auto_check`
6. `auto_check` compares scores
7. Applies appropriate packs

#### Manual Command Sequence
1. Player/system runs function
2. Function directly calls set function
3. Set function applies pack
4. Updates applied scoreboard
5. Notifies player
6. Logs to admins

### Resource Pack Command

The system uses Minecraft's native command:
```mcfunction
resource pack send <targets> <url> [hash] [force]
```

**Parameters**:
- `<targets>`: Player selector (e.g., @s, @a, @p)
- `<url>`: Direct download URL (must be HTTPS)
- `[hash]`: Optional SHA-256 hash
- `[force]`: Optional force download (requires special permissions)

### Pack Format Versions

Update `pack_format` in `pack.mcmeta` based on your Minecraft version:

| Minecraft Version | Pack Format |
|-------------------|-------------|
| 1.20.2 - 1.20.4   | 15          |
| 1.20.5 - 1.20.6   | 18          |
| 1.21+             | 26          |

### File Size Limits

- Default Minecraft limit: **100 MB**
- Server admins can increase via `max-resource-pack-size` (server.properties)
- Recommended max: **50 MB** for best compatibility

### Network Requirements

- Resource pack URLs must use **HTTPS**
- Must be direct download links (no redirects or landing pages)
- CDN hosting recommended for reliability
- Consider using dedicated pack hosting services

## Support & Contact

For questions, issues, or feedback:

**Email**: contact@nobleskye.dev

**Repository**: [NobleSkye/nderham](https://github.com/NobleSkye/nderham)

## License

This project is provided as-is for free use and modification. Feel free to adapt it for your server or project needs.

## Credits

Developed by NobleSkye for the Minecraft datapack community.

Special thanks to all contributors and testers!
