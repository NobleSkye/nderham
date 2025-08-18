# ScorePack (Fabric, server-only)

Server-side Fabric mod that applies a resource pack to players based on scoreboard values. Dynamic JSON config supports many objective/value -> pack mappings.

## Prerequisites

**Network Requirements:**
- This project requires network access to `maven.fabricmc.net` to download the Fabric Loom plugin and dependencies
- Ensure your build environment can access:
  - `https://maven.fabricmc.net/` (Fabric Maven repository)
  - `https://repo.maven.apache.org/maven2/` (Maven Central)
  - `https://plugins.gradle.org/` (Gradle Plugin Portal)

## Info
Depending on a player's scoreboard value, the server sends a specific resource pack; when the scoreboard changes, it can send a different pack.

## Features
- Server-only. No client mod required.
- Flexible rules: objective + equals value -> pack URL (+ optional SHA1, required flag, prompt).
- Poll-based detection of score changes, runs every `recheckTicks`.
- `/scorepack reload` to reload config without restarting.

## Config
- Generated at `config/scorepack.json` on first run.
- Shape:
	- `defaults.recheckTicks`: ticks between re-evaluations (default 40).
	- `defaults.firstMatchWins`: stop at first matching rule (default true).
	- `rules[]`: list of rules.
		- `objective` (string) – scoreboard objective name.
		- `equalsValue` (int) – value to match.
		- `packUrl` (string) – direct URL to the resource pack .zip.
		- `sha1` (string, optional) – 40-hex SHA1 of the pack (recommended).
		- `required` (bool, default false) – require acceptance.
		- `prompt` (string, optional) – shown to the user in the prompt.

## Build
- Requires JDK 21.
- Uses Gradle (Fabric Loom). Mappings: official Mojang for 1.21.1.
- **Important:** Requires network access to `maven.fabricmc.net`
- Build:

```sh
./gradlew build
```

Jar: `build/libs/scorepack-*.jar`.

## Troubleshooting

**Build Issues:**
- If you get `fabric-loom` plugin not found errors, ensure network access to `maven.fabricmc.net`
- Verify DNS resolution: `nslookup maven.fabricmc.net`
- Check firewall/proxy settings that might block Maven repositories

**Network-Restricted Environments:**
- In environments where `maven.fabricmc.net` is blocked, consider:
  - Using a corporate Maven mirror that includes Fabric repositories
  - Setting up a local Maven proxy/mirror
  - Building in a different environment with network access

## Install
- Use on a Fabric server for MC 1.21.1 and Fabric Loader 0.16.5+.
- Fabric API is included as dependency for events; keep it on server.

## Notes
- If you specifically need 1.21.8, update the `minecraft` and `fabric-api` versions in `build.gradle` once those artifacts exist or confirm compatibility. This template targets 1.21.1.
- The server cannot force-clear a resource pack; it only sends a new one when rules change.