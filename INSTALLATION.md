# Installation Guide - Minecraft Resource Pack Assignment System

## Quick Install (5 minutes)

### Step 1: Download the Datapack

Download the `datapacks/resourcepack_system` folder from this repository.

### Step 2: Install in Your World

**For Single Player:**
1. Open your Minecraft saves folder:
   - **Windows**: `%APPDATA%\.minecraft\saves\<world_name>\datapacks`
   - **Mac**: `~/Library/Application Support/minecraft/saves/<world_name>/datapacks`
   - **Linux**: `~/.minecraft/saves/<world_name>/datapacks`

2. Copy the `resourcepack_system` folder into the `datapacks` directory

3. Start your world (or run `/reload` if already playing)

**For Multiplayer Server:**
1. Navigate to your server folder
2. Go to `<server_folder>/<world_name>/datapacks`
3. Copy the `resourcepack_system` folder here
4. Restart server or run `/reload` from console/in-game

### Step 3: Verify Installation

You should see this message in chat:
```
[ResourcePack System] Loaded successfully! Use /function rpsystem:help for help.
```

### Step 4: Configure Your Resource Packs

1. Edit `datapacks/resourcepack_system/data/minecraft/resourcepack.json`
2. Replace example URLs with your actual resource pack URLs
3. Update SHA-256 hashes (optional but recommended)
4. Run `/reload` to apply changes

Example configuration:
```json
{
  "scoreboard_objective": "level",
  "resourcepacks": {
    "starter_pack": {
      "value": 1,
      "url": "https://your-server.com/packs/starter.zip",
      "sha256": "your-sha256-hash-here"
    },
    "advanced_pack": {
      "value": 5,
      "url": "https://your-server.com/packs/advanced.zip"
    }
  }
}
```

### Step 5: Choose Your Mode

**For Automatic Mode (Recommended for most users):**
```mcfunction
/function rpsystem:gamerule/enable_auto
```

**For Manual Mode (Better performance for large servers):**
```mcfunction
/function rpsystem:gamerule/disable_auto
```
Then create your own functions to call the set commands when needed.

## Advanced Installation

### Installing the Example Pack

The example pack demonstrates multi-namespace support and custom integrations.

1. Copy `examples/example_server_pack` to your world's `datapacks` folder
2. Run `/reload`
3. Test with `/function myserver:test_ranks`

### Creating Your Own Integration

1. Create a new datapack folder:
   ```
   datapacks/
   └── my_custom_pack/
       ├── pack.mcmeta
       └── data/
           ├── minecraft/tags/functions/...
           └── mycustom/
               ├── resourcepack.json
               └── functions/...
   ```

2. Create your own `resourcepack.json` with custom namespace

3. Create corresponding set functions in `datapacks/resourcepack_system/data/rpsystem/functions/set/`

4. Reload and test!

## Hosting Your Resource Packs

### Requirements
- Resource packs must be hosted with HTTPS
- URLs must be direct download links (ending in .zip)
- No redirects or landing pages
- Maximum size: 100MB (configurable on server)

### Hosting Options

**1. GitHub Releases (Free)**
- Upload your pack as a release asset
- Use the direct download URL
- Good for version control

**2. Dropbox**
- Upload pack to Dropbox
- Use direct download link (change `dl=0` to `dl=1`)
- Example: `https://www.dropbox.com/s/xxxxx/pack.zip?dl=1`

**3. CDN Services**
- Amazon S3
- Cloudflare R2
- DigitalOcean Spaces
- Best performance and reliability

**4. Self-Hosted**
- Your own web server with HTTPS
- Direct file access required
- Full control over availability

### Generating SHA-256 Hashes

**Linux/Mac:**
```bash
sha256sum yourpack.zip
```

**Windows (PowerShell):**
```powershell
Get-FileHash yourpack.zip -Algorithm SHA256
```

**Online (use trusted sites only):**
- Many online SHA-256 calculators available
- Upload your pack and get the hash

Copy the 64-character hexadecimal hash into your JSON configuration.

## Testing Your Installation

### Test 1: Basic Functionality
```mcfunction
/function rpsystem:help
```
Should display the help menu.

### Test 2: Manual Pack Application
```mcfunction
/function rpsystem:set/starter_pack
```
Should prompt you to download a resource pack.

### Test 3: Scoreboard Integration
```mcfunction
/scoreboard objectives add level dummy "Player Level"
/scoreboard players set @s level 1
/function rpsystem:set/starter_pack
```

### Test 4: Auto Mode
```mcfunction
/function rpsystem:gamerule/enable_auto
/scoreboard players set @s level 5
```
Should automatically apply advanced_pack.

## Troubleshooting Installation

### "Unknown or incomplete command" Error

**Problem**: Datapack not loaded properly.

**Solution**:
1. Check folder structure matches exactly
2. Verify `pack.mcmeta` has correct format
3. Check server console for errors
4. Try `/reload` command

### No Load Message Appears

**Problem**: Load function not executing.

**Solution**:
1. Verify `data/minecraft/tags/functions/load.json` exists
2. Check it contains: `{"values": ["rpsystem:load"]}`
3. Ensure `data/rpsystem/functions/load.mcfunction` exists
4. Try `/function rpsystem:load` manually

### Resource Pack Not Downloading

**Problem**: Pack URL not accessible.

**Solution**:
1. Test URL in web browser
2. Ensure it's HTTPS (not HTTP)
3. Verify it's a direct download link
4. Check file is actually a .zip file
5. Verify file size is under 100MB

### SHA-256 Verification Failing

**Problem**: Hash mismatch error.

**Solution**:
1. Regenerate hash from the actual file
2. Ensure hash is 64 lowercase hexadecimal characters
3. Or remove `sha256` field from JSON to skip verification

### Players Not Receiving Packs

**Problem**: Other players don't get prompted.

**Solution**:
1. Check `server.properties`: `resource-pack-prompt=true`
2. Ensure players have "Server Resource Packs" enabled
3. Verify URL is publicly accessible
4. Test with different player/account

## Upgrading

### Updating to New Version

1. Backup your current configuration files
2. Replace the `resourcepack_system` folder
3. Restore your custom configurations
4. Run `/reload`
5. Test functionality

### Migrating Configurations

If you've customized the datapack:
1. Back up your JSON configurations
2. Back up any custom function files
3. Install new version
4. Merge your customizations back
5. Test thoroughly

## Uninstalling

To remove the datapack:

1. Delete the `resourcepack_system` folder from datapacks
2. Run `/reload` or restart world/server
3. Optionally remove scoreboard objectives:
   ```mcfunction
   /scoreboard objectives remove level
   /scoreboard objectives remove rp_auto
   /scoreboard objectives remove rp_applied
   ```

## Getting Help

If you encounter issues:

1. Check the [Troubleshooting Guide](./DOCUMENTATION.md#troubleshooting) in main documentation
2. Verify your configuration against the [JSON Schema](./resourcepack-schema.json)
3. Review the [Example Pack](./examples/example_server_pack/) for working reference
4. Contact: contact@nobleskye.dev

## Next Steps

After installation:
- Read the [Complete Documentation](./DOCUMENTATION.md)
- Review the [Example Pack](./examples/example_server_pack/)
- Check the [Command Reference](./README_RESOURCEPACK_SYSTEM.md#-commands-reference)
- Customize for your server needs

## Version Compatibility

| Minecraft Version | Pack Format | Status |
|-------------------|-------------|--------|
| 1.20.2 - 1.20.4   | 15          | ✅ Supported |
| 1.20.5 - 1.20.6   | 18          | ✅ Supported |
| 1.21+             | 26          | ✅ Supported |

Update `pack_format` in `pack.mcmeta` to match your Minecraft version.

---

Ready to use the system? Start with the [Quick Start Guide](./README_RESOURCEPACK_SYSTEM.md#-quick-examples)!
