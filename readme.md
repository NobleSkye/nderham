# ScorePack (Fabric, server-only)

Server-side Fabric mod that applies a resource pack to players based on scoreboard values. Dynamic JSON config supports many objective/value -> pack mappings.

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
- Build:

```sh
./gradlew build
```

Jar: `build/libs/scorepack-*.jar`.

## Install
- Use on a Fabric server for MC 1.21.1 and Fabric Loader 0.16.5+.
- Fabric API is included as dependency for events; keep it on server.

## Notes
- If you specifically need 1.21.8, update the `minecraft` and `fabric-api` versions in `build.gradle` once those artifacts exist or confirm compatibility. This template targets 1.21.1.
- The server cannot force-clear a resource pack; it only sends a new one when rules change.