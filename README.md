# ScorePack

A Minecraft Bukkit/Spigot plugin that dynamically assigns resource packs to players based on their scoreboard values.

## Features

- 🎯 **Dynamic Resource Pack Assignment** - Automatically apply resource packs based on scoreboard values
- 📊 **Multiple Scoreboard Support** - Monitor multiple different scoreboard objectives simultaneously
- 🔄 **Hot Reload** - Reload configurations without restarting the server
- 🔒 **SHA-256 Verification** - Optional hash verification for resource pack integrity
- ⚡ **Event-Driven** - Automatically detects score changes and applies appropriate resource packs
- 📝 **Easy Configuration** - Simple YAML-based configuration system

## Installation

1. Download the latest `ScorePack-x.x.x.jar` file from the releases page
2. Place the JAR file in your server's `plugins` folder
3. Restart your server or use a plugin manager to load ScorePack
4. Configure your scoreboard objectives (see Configuration section)
5. Use `/scorepack reload` to load your configurations

## How It Works

ScorePack monitors scoreboard objectives and applies resource packs to players when their score matches configured thresholds. Each scoreboard objective has its own configuration file.

### Configuration System

The plugin uses a file-based configuration system where:
- **File name** = Scoreboard objective name
- **File contents** = Resource pack assignments for that objective

For example:
- `level.yml` → monitors the "level" scoreboard objective
- `rank.yml` → monitors the "rank" scoreboard objective
- `coins.yml` → monitors the "coins" scoreboard objective

## Configuration

### Creating a Configuration File

1. Navigate to `plugins/ScorePack/` directory
2. Create a new YAML file named after your scoreboard objective (e.g., `level.yml`)
3. Use the structure shown below

### YAML File Structure

```yaml
# Example: level.yml (for a scoreboard objective called "level")
scoreboard_objective: "level"  # The scoreboard objective to monitor

resource_packs:
  - value: 1  # When player's score equals this value
    url: "https://example.com/resourcepack1.zip"
    sha256: "optional-hash-here"  # Optional SHA-256 hash
    
  - value: 5
    url: "https://example.com/resourcepack2.zip"
    sha256: "abc123..."  # Optional
    
  - value: 10
    url: "https://example.com/resourcepack3.zip"
    # sha256 can be omitted if not needed
```

### Configuration Fields

#### `scoreboard_objective` (required)
The name of the scoreboard objective to monitor. This must match an existing scoreboard objective on your server.

#### `resource_packs` (required)
A list of resource pack configurations. Each entry contains:

- **`value`** (required) - The scoreboard value that triggers this resource pack
- **`url`** (required) - The direct download URL for the resource pack ZIP file
- **`sha256`** (optional) - SHA-256 hash for resource pack verification

### Example Configurations

#### Example 1: Level-Based Resource Packs

Create `level.yml`:
```yaml
scoreboard_objective: "level"

resource_packs:
  - value: 1
    url: "https://example.com/packs/starter.zip"
    
  - value: 10
    url: "https://example.com/packs/advanced.zip"
    
  - value: 25
    url: "https://example.com/packs/expert.zip"
    sha256: "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"
```

#### Example 2: Rank-Based Resource Packs

Create `rank.yml`:
```yaml
scoreboard_objective: "rank"

resource_packs:
  - value: 1  # Member
    url: "https://example.com/packs/member.zip"
    
  - value: 2  # VIP
    url: "https://example.com/packs/vip.zip"
    
  - value: 3  # Admin
    url: "https://example.com/packs/admin.zip"
```

#### Example 3: Achievement-Based Resource Packs

Create `achievements.yml`:
```yaml
scoreboard_objective: "achievements"

resource_packs:
  - value: 10
    url: "https://example.com/packs/bronze.zip"
    
  - value: 50
    url: "https://example.com/packs/silver.zip"
    
  - value: 100
    url: "https://example.com/packs/gold.zip"
```

## SHA-256 Hash Verification

SHA-256 hashes provide integrity verification for resource packs, ensuring players download the correct, unmodified files.

### Why Use SHA-256?

- **Security** - Prevents tampering with resource pack files
- **Integrity** - Ensures players get the exact resource pack you intended
- **Caching** - Minecraft clients can cache packs with matching hashes

### Generating SHA-256 Hashes

#### Using Command Line (Linux/Mac)
```bash
sha256sum resourcepack.zip
```

#### Using Command Line (Windows PowerShell)
```powershell
Get-FileHash resourcepack.zip -Algorithm SHA256
```

#### Using Online Tools
Many online SHA-256 calculators are available, but ensure you trust the source.

### Using SHA-256 in Configuration

```yaml
resource_packs:
  - value: 1
    url: "https://example.com/pack.zip"
    sha256: "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"
```

You can also omit the `sha256` field entirely if you don't need verification:
```yaml
resource_packs:
  - value: 1
    url: "https://example.com/pack.zip"
```

## Commands

### `/scorepack` (or `/sp`)
Main plugin command. Requires `scorepack.admin` permission.

#### Subcommands:
- `/scorepack reload` - Reload all configuration files
- `/scorepack info` - Display plugin information and loaded objectives
- `/scorepack help` - Show help message

## Permissions

- `scorepack.admin` - Access to all ScorePack commands (default: op)

## Use Cases

### 1. Progressive Gameplay
Apply different resource packs as players level up, revealing new textures, items, or UI elements at each milestone.

### 2. Rank-Based Cosmetics
Give different ranks unique visual experiences through custom resource packs.

### 3. Story Progression
Change the game's appearance as players complete story objectives tracked via scoreboards.

### 4. Seasonal Events
Apply themed resource packs when players reach certain event milestones.

### 5. Achievement Rewards
Unlock special texture packs when players reach achievement thresholds.

### 6. Dynamic Difficulty
Apply different UI elements or textures based on difficulty level tracked in scoreboards.

## Setting Up Scoreboards

ScorePack requires existing scoreboard objectives. Here's how to create them:

### Creating a Scoreboard Objective
```
/scoreboard objectives add level dummy "Player Level"
```

### Setting a Player's Score
```
/scoreboard players set PlayerName level 5
```

### Adding to a Player's Score
```
/scoreboard players add PlayerName level 1
```

## Technical Details

### Compatibility
- **Minecraft Version**: 1.20.1+
- **Server Software**: Bukkit, Spigot, Paper
- **Java Version**: 17+

### Performance
- Checks player scores every 5 seconds (configurable in future versions)
- Minimal performance impact even with many players and objectives
- Resource packs are only sent when scores change to matching values

### Resource Pack Requirements
- Resource packs must be hosted at a publicly accessible URL
- URLs must point directly to a ZIP file
- HTTPS is recommended for security
- Maximum file size depends on client settings (typically 100-250 MB)

## Troubleshooting

### Resource Pack Not Applying

1. **Check the URL** - Ensure it's accessible and points to a ZIP file
2. **Check the scoreboard** - Verify the objective exists: `/scoreboard objectives list`
3. **Check the player's score** - Verify the score matches a configured value: `/scoreboard players list PlayerName`
4. **Check logs** - Look for errors in `logs/latest.log`
5. **Reload configs** - Try `/scorepack reload` after making changes

### "No scoreboard configuration files found" Warning

This means no YAML files (except config.yml) were found in the plugin directory. Create at least one configuration file named after a scoreboard objective.

### Player Stuck with Old Resource Pack

Resource packs are only updated when the score changes to a new matching value. If you update a URL, the player needs to disconnect and reconnect, or their score needs to change.

## Building from Source

### Prerequisites
- JDK 17 or higher
- Gradle 8.0+

### Build Commands
```bash
# Clone the repository
git clone https://github.com/NobleSkye/nderham.git
cd nderham

# Switch to the plugin branch
git checkout copilot/add-resource-pack-assigner-plugin

# Build the plugin
./gradlew build

# The compiled JAR will be in build/libs/
```

## Support

For issues, questions, or suggestions:
- **Email**: contact@nobleskye.dev
- **Issue Tracker**: GitHub Issues

## License

This plugin is provided as-is for use by friends and community members.

## Contributing

Contributions are welcome! Please follow these guidelines:
1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Submit a pull request

## Changelog

### Version 1.0.0
- Initial release
- Dynamic resource pack assignment based on scoreboard values
- Multiple scoreboard objective support
- SHA-256 hash verification
- Hot reload functionality
- Comprehensive configuration system

---

**Made with ❤️ by NobleSkye**
