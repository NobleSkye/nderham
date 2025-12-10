# GitHub Actions Workflows

This directory contains automated workflows for building and releasing the Resource Pack Plugin.

## Available Workflows

### 1. Build Plugin (`build-plugin.yml`)

**Trigger**: Automatic on push/PR to main branch, or manual via workflow_dispatch

**Purpose**: Builds the plugin and creates artifacts for testing

**What it does**:
- Checks out the repository
- Sets up Java 17 environment
- Detects and uses Maven or Gradle build system
- Builds the plugin JAR
- Uploads the JAR as a GitHub Actions artifact
- On release events, automatically attaches the JAR to the release

**Artifacts**: 
- Name: `ResourcePackPlugin-<version>`
- Contains: Built plugin JAR file
- Retention: 90 days (GitHub default)

**How to use**:
1. Push code to main branch or create a PR
2. GitHub Actions will automatically build
3. Download artifacts from the Actions tab

**Manual trigger**:
1. Go to Actions tab
2. Select "Build Plugin" workflow
3. Click "Run workflow"
4. Select branch and run

### 2. Create Release (`release-plugin.yml`)

**Trigger**: Manual via workflow_dispatch only

**Purpose**: Creates a versioned release with the plugin JAR attached

**What it does**:
- Updates version number in pom.xml or build.gradle
- Builds the plugin with the new version
- Creates a Git tag (e.g., `v1.0.0`)
- Creates a GitHub Release with:
  - Release notes
  - Plugin JAR attachment
  - Installation instructions
  - Feature list

**Inputs**:
- `version` (required): Release version number (e.g., `1.0.0`)
- `prerelease` (optional): Mark as pre-release (default: false)

**How to use**:
1. Go to Actions tab
2. Select "Create Release" workflow
3. Click "Run workflow"
4. Enter version number (e.g., `1.0.0`)
5. Optionally check "Mark as pre-release"
6. Click "Run workflow"

**Result**:
- Creates tag: `v<version>`
- Creates release: `ResourcePackPlugin v<version>`
- Attaches plugin JAR to release
- Published on GitHub Releases page

## Build System Support

Both workflows automatically detect and support:

### Maven (pom.xml)
- Uses Maven to build
- Caches Maven dependencies
- Finds JAR in `target/` directory

### Gradle (build.gradle)
- Uses Gradle to build
- Caches Gradle dependencies
- Finds JAR in `build/libs/` directory

## Requirements

For the workflows to work, your repository needs:

1. **Plugin source code** in the root directory
2. **Build configuration**:
   - `pom.xml` for Maven, OR
   - `build.gradle` for Gradle
3. **Plugin metadata**:
   - `plugin.yml` (for Spigot/Paper plugins)
   - Proper package structure

## Setting up the Plugin Project

If you haven't created the plugin yet, here's the minimal structure needed:

### For Maven:
```
.
├── pom.xml
└── src/
    └── main/
        ├── java/
        │   └── dev/nobleskye/resourcepack/
        │       └── ResourcePackPlugin.java
        └── resources/
            └── plugin.yml
```

### For Gradle:
```
.
├── build.gradle
└── src/
    └── main/
        ├── java/
        │   └── dev/nobleskye/resourcepack/
        │       └── ResourcePackPlugin.java
        └── resources/
            └── plugin.yml
```

## Workflow Permissions

The workflows require these permissions:
- `contents: write` - To create releases and tags
- `actions: read` - To read workflow status

These are automatically available via `GITHUB_TOKEN`.

## Artifact Downloads

After a successful build:

1. Go to the Actions tab
2. Click on the completed workflow run
3. Scroll to "Artifacts" section
4. Download the plugin JAR

## Release Downloads

After creating a release:

1. Go to the Releases page (right sidebar)
2. Find your release version
3. Download the JAR from "Assets" section

## Troubleshooting

### "No JAR file found" error
- Ensure your build configuration produces a JAR file
- Check that Maven/Gradle build completes successfully
- Verify `plugin.yml` exists in `src/main/resources/`

### Version number not updating
- For Maven: Ensure `pom.xml` has valid `<version>` tag
- For Gradle: Ensure `build.gradle` has `version = '...'` line
- Manually update version before triggering workflow

### Build failures
- Check Java version compatibility (JDK 17 required)
- Verify dependencies are accessible
- Check Maven/Gradle configuration syntax

## Customization

You can customize these workflows by:

1. Editing the YAML files in `.github/workflows/`
2. Changing Java version in `setup-java` step
3. Adding additional build steps
4. Modifying artifact/release naming
5. Adjusting release notes template

## Next Steps

To use these workflows with a plugin:

1. Create the plugin source code
2. Add `pom.xml` (Maven) or `build.gradle` (Gradle)
3. Push to repository
4. Workflows will automatically build on push
5. Use "Create Release" workflow for versioned releases

## Example: Creating First Release

```bash
# 1. Ensure plugin code exists with build config
# 2. Go to GitHub Actions tab
# 3. Select "Create Release"
# 4. Click "Run workflow"
# 5. Enter version: 1.0.0
# 6. Click "Run workflow" button
# 7. Wait for completion
# 8. Check Releases page for the new release
```

## Notes

- Builds use cached dependencies for faster execution
- Artifacts are kept for 90 days by default
- Releases are permanent (can be deleted manually)
- Pre-releases don't trigger notifications to watchers
- Tags are permanent (can be deleted manually if needed)
