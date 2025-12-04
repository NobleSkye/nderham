# ScorePack - Quick Start Guide

## 🚀 Installation (3 Steps)

1. **Download** the plugin JAR file
2. **Place** it in your `plugins/` folder
3. **Restart** your server

## ⚙️ Basic Configuration (5 Minutes)

### Step 1: Locate Config Directory
After first run, find: `plugins/ScorePack/`

### Step 2: Create Your First Config
Create a file named after your scoreboard objective (e.g., `level.yml`):

```yaml
scoreboard_objective: "level"  # Must match your scoreboard name

resource_packs:
  - value: 1
    url: "https://your-server.com/pack1.zip"
    
  - value: 5
    url: "https://your-server.com/pack2.zip"
    sha256: "abc123..."  # Optional
```

### Step 3: Reload
Run in-game: `/scorepack reload`

## 📋 Common Scoreboard Commands

```bash
# Create a scoreboard objective
/scoreboard objectives add level dummy "Player Level"

# Set a player's score
/scoreboard players set PlayerName level 1

# Add to a player's score
/scoreboard players add PlayerName level 1

# View all objectives
/scoreboard objectives list

# View player's scores
/scoreboard players list PlayerName
```

## 🎮 Plugin Commands

| Command | Description |
|---------|-------------|
| `/scorepack reload` | Reload all configurations |
| `/scorepack info` | Show loaded objectives |
| `/scorepack help` | Display help |

**Alias:** `/sp` (works the same as `/scorepack`)

## 📁 File = Objective Name

The configuration file name **must match** your scoreboard objective:

- `level.yml` → monitors "level" objective
- `rank.yml` → monitors "rank" objective
- `coins.yml` → monitors "coins" objective
- `kills.yml` → monitors "kills" objective

## 💡 Quick Tips

1. **Multiple Objectives**: Create multiple YAML files to monitor different scoreboards
2. **SHA-256**: Optional - leave blank or omit if not needed
3. **Reload**: Use `/scorepack reload` after config changes
4. **URLs**: Must be direct download links to ZIP files
5. **Permissions**: Only OPs can use `/scorepack` by default

## 🔍 Troubleshooting

### Pack Not Applying?
- ✅ Check URL is accessible and points to a ZIP file
- ✅ Verify scoreboard objective exists: `/scoreboard objectives list`
- ✅ Confirm player score matches config value
- ✅ Check server logs for errors

### Config Not Loading?
- ✅ Ensure file is named `objectivename.yml` (not `config.yml`)
- ✅ Check YAML syntax is correct (spaces matter!)
- ✅ Run `/scorepack reload` after changes

## 📝 Example Setup

### 1. Create Scoreboard
```
/scoreboard objectives add level dummy "Level"
```

### 2. Create `level.yml`
```yaml
scoreboard_objective: "level"
resource_packs:
  - value: 1
    url: "https://example.com/starter.zip"
  - value: 10
    url: "https://example.com/expert.zip"
```

### 3. Set Player Score
```
/scoreboard players set Steve level 1
```

### 4. Steve Gets Resource Pack!
The plugin automatically applies `starter.zip` to Steve.

## 🎯 Common Use Cases

### Level System
```yaml
# level.yml
scoreboard_objective: "level"
resource_packs:
  - value: 1
    url: "https://example.com/lvl1.zip"
  - value: 25
    url: "https://example.com/lvl25.zip"
  - value: 50
    url: "https://example.com/lvl50.zip"
```

### Rank System
```yaml
# rank.yml
scoreboard_objective: "rank"
resource_packs:
  - value: 1  # Default
    url: "https://example.com/default.zip"
  - value: 2  # VIP
    url: "https://example.com/vip.zip"
  - value: 3  # Admin
    url: "https://example.com/admin.zip"
```

## 🔐 Generating SHA-256 Hash

**Linux/Mac:**
```bash
sha256sum pack.zip
```

**Windows PowerShell:**
```powershell
Get-FileHash pack.zip -Algorithm SHA256
```

Then copy the hash into your config:
```yaml
sha256: "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855"
```

## 📚 Need More Help?

See the complete `README.md` for:
- Detailed explanations
- Advanced configurations
- Technical specifications
- Building from source

---

**Ready to go!** Create your first config file and start assigning resource packs! 🎉
