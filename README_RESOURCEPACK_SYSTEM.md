# Minecraft Resource Pack Assignment System

A comprehensive Minecraft datapack system that dynamically assigns resource packs to players based on scoreboard values.

## 🎮 What is This?

This datapack provides a flexible system for automatically or manually assigning resource packs to players based on their scoreboard values. Perfect for:

- **Progression systems** - Different packs as players level up
- **Rank systems** - Unique visuals for different player ranks  
- **Achievement rewards** - Unlock new packs through gameplay
- **Time-based changes** - Different themes based on playtime
- **Custom game modes** - Unique visuals for different areas or modes

## ✨ Features

- ✅ **JSON Configuration** - Easy-to-edit resource pack definitions
- ✅ **Auto Mode** - Automatically apply packs when scores change
- ✅ **Manual Mode** - Full control via commands and functions
- ✅ **SHA-256 Support** - Optional cryptographic verification
- ✅ **Selector Compatible** - Works with @a, @p, @s, @r, @e
- ✅ **Multi-Namespace** - Multiple configurations across datapacks
- ✅ **Reload Compatible** - Full support for /reload command
- ✅ **Comprehensive Logging** - Track pack applications and errors

## 📦 Installation

### Quick Start

1. Download or clone this repository
2. Copy `datapacks/resourcepack_system` to your world's `datapacks/` folder
3. Run `/reload` in-game
4. See the success message and run `/function rpsystem:help`

### Folder Structure

```
your_minecraft_world/
└── datapacks/
    └── resourcepack_system/
        ├── pack.mcmeta
        ├── README.md
        └── data/
            ├── minecraft/
            │   ├── resourcepack.json
            │   └── tags/functions/
            └── rpsystem/functions/
```

## 🚀 Quick Examples

### Enable Auto Mode
```mcfunction
/function rpsystem:gamerule/enable_auto
```

### Manually Apply a Pack
```mcfunction
/function rpsystem:set/starter_pack
/function rpsystem:set/advanced_pack
/function rpsystem:set/pro_pack
```

### Test with Scoreboard
```mcfunction
/scoreboard objectives add level dummy "Player Level"
/scoreboard players set @s level 5
# In auto mode, advanced_pack automatically applies!
```

### Use in Datapack Functions
```mcfunction
# Apply packs to players by level
execute as @a[scores={level=1}] run function rpsystem:set/starter_pack
execute as @a[scores={level=5}] run function rpsystem:set/advanced_pack
execute as @a[scores={level=10}] run function rpsystem:set/pro_pack
```

## ⚙️ Configuration

### JSON Configuration File

Edit `data/minecraft/resourcepack.json`:

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

### Adding New Resource Packs

1. Add entry to JSON configuration
2. Create function file: `data/rpsystem/functions/set/<pack_name>.mcfunction`
3. Update auto_check if using auto mode
4. Run `/reload`

## 📖 Documentation

- **[Complete Documentation](./DOCUMENTATION.md)** - Full technical guide
- **[Datapack README](./datapacks/resourcepack_system/README.md)** - Installation and usage
- **[Example Pack](./examples/example_server_pack/README.md)** - Working example
- **[JSON Schema](./resourcepack-schema.json)** - Configuration schema

## 🎯 Modes of Operation

### Auto Mode (Automatic Assignment)

When enabled, the system automatically monitors scoreboard changes and applies matching resource packs.

```mcfunction
# Enable auto mode
/function rpsystem:gamerule/enable_auto

# Change player level - pack applies automatically!
/scoreboard players set @s level 5
```

**Best for**: Dynamic progression systems, real-time changes

### Manual Mode (Command-Based)

Resource packs only apply via explicit function calls, giving you full control.

```mcfunction
# Disable auto mode (or leave it disabled by default)
/function rpsystem:gamerule/disable_auto

# Apply packs manually in your datapack functions
execute as @a[scores={level=1..4}] run function rpsystem:set/starter_pack
execute as @a[scores={level=5..9}] run function rpsystem:set/advanced_pack
```

**Best for**: Custom logic, performance-sensitive servers, complex conditions

## 📂 Examples

### Example 1: Level-Based Progression

```mcfunction
# Check all players and assign packs by level range
execute as @a[scores={level=1..4}] run function rpsystem:set/starter_pack
execute as @a[scores={level=5..9}] run function rpsystem:set/advanced_pack
execute as @a[scores={level=10..}] run function rpsystem:set/pro_pack
```

### Example 2: Rank System

```json
{
  "scoreboard_objective": "rank",
  "resourcepacks": {
    "member_pack": { "value": 0, "url": "https://..." },
    "vip_pack": { "value": 1, "url": "https://..." },
    "admin_pack": { "value": 2, "url": "https://..." }
  }
}
```

### Example 3: Achievement Unlocks

```mcfunction
# Unlock new packs as players complete achievements
execute as @a[scores={achievements=1}] run function rpsystem:set/bronze_pack
execute as @a[scores={achievements=10}] run function rpsystem:set/silver_pack
execute as @a[scores={achievements=50}] run function rpsystem:set/gold_pack
```

## 🔧 Commands Reference

| Command | Description |
|---------|-------------|
| `/function rpsystem:help` | Show all available commands |
| `/function rpsystem:gamerule/enable_auto` | Enable automatic mode |
| `/function rpsystem:gamerule/disable_auto` | Disable automatic mode |
| `/function rpsystem:gamerule/status` | Check current mode status |
| `/function rpsystem:set/starter_pack` | Apply starter pack (level 1) |
| `/function rpsystem:set/advanced_pack` | Apply advanced pack (level 5) |
| `/function rpsystem:set/pro_pack` | Apply pro pack (level 10) |

## 🛠️ Technical Details

### Scoreboard Objectives Used

- **`level`** (or configured name) - Monitored for resource pack triggers
- **`rp_auto`** - Stores auto mode state (0=manual, 1=auto)
- **`rp_applied`** - Tracks currently applied pack per player

### Pack Format Versions

Update `pack_format` in `pack.mcmeta` for your Minecraft version:

| Minecraft Version | Pack Format |
|-------------------|-------------|
| 1.20.2 - 1.20.4   | 15          |
| 1.20.5 - 1.20.6   | 18          |
| 1.21+             | 26          |

### Performance

- **Auto mode**: Runs every tick (20 times/second)
- **Manual mode**: Only runs when you call functions
- For large servers (100+ players), consider manual mode with less frequent checks

## 🐛 Troubleshooting

### Resource Pack Not Applying?

1. Check URL is HTTPS and directly downloadable
2. Verify scoreboard value matches JSON configuration
3. Test manual command: `/function rpsystem:set/<pack_name>`
4. Check auto mode status: `/function rpsystem:gamerule/status`

### System Not Loading?

1. Verify folder structure is correct
2. Check `pack_format` matches your Minecraft version
3. Look for errors in server console
4. Try `/reload` command

### SHA-256 Failing?

1. Regenerate hash: `sha256sum yourpack.zip`
2. Update hash in JSON (must be 64 lowercase hex characters)
3. Or remove sha256 field to skip verification

## 📝 JSON Schema Validation

Use the included `resourcepack-schema.json` with your IDE for validation and autocomplete:

```json
{
  "$schema": "./resourcepack-schema.json",
  "scoreboard_objective": "level",
  "resourcepacks": { ... }
}
```

## 🤝 Contributing

This is a personal repository, but suggestions and improvements are welcome! Contact: contact@nobleskye.dev

## 📜 License

Free to use and modify for your Minecraft servers and projects. No attribution required but appreciated!

## 🌟 Credits

Developed by **NobleSkye** for the Minecraft datapack community.

Special thanks to all testers and contributors!

---

## 📚 Additional Resources

- [Complete Documentation](./DOCUMENTATION.md) - Full technical reference
- [Example Server Pack](./examples/example_server_pack/) - Working demonstration
- [JSON Schema](./resourcepack-schema.json) - Configuration validation

## 🎓 Learning Resources

New to Minecraft datapacks? Check out these resources:

- [Minecraft Wiki - Data Packs](https://minecraft.wiki/w/Data_pack)
- [Minecraft Commands Documentation](https://minecraft.wiki/w/Commands)
- [Resource Pack Guide](https://minecraft.wiki/w/Resource_pack)

## 🔗 Links

- **Repository**: [github.com/NobleSkye/nderham](https://github.com/NobleSkye/nderham)
- **Contact**: contact@nobleskye.dev
- **Branch**: `copilot/add-datapack-resource-system`

---

*Last Updated: December 2024*
