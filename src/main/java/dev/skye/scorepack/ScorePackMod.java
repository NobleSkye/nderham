package dev.skye.scorepack;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ScorePackMod implements ModInitializer {
    public static final String MOD_ID = "scorepack";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private Config config;
    private final Map<UUID, String> lastAppliedPack = new ConcurrentHashMap<>();
    private int tickCounter = 0;

    @Override
    public void onInitialize() {
        loadConfig(null);

        // /scorepack reload
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
            dispatcher.register(CommandManager.literal("scorepack")
                .requires(src -> src.hasPermissionLevel(2))
                .then(CommandManager.literal("reload").executes(ctx -> {
                    MinecraftServer server = ctx.getSource().getServer();
                    loadConfig(server);
                    ctx.getSource().sendFeedback(() -> Text.literal("[ScorePack] Config reloaded."), true);
                    return 1;
                }))
            )
        );

        // Evaluate on join
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            evaluateAndApply(server, handler.player);
        });

        // Lightweight polling to catch score changes (server-only)
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            tickCounter++;
            if (config == null) return;
            if (tickCounter % Math.max(1, config.defaults.recheckTicks) != 0) return;
            for (ServerWorld world : server.getWorlds()) {
                for (ServerPlayerEntity player : world.getPlayers()) {
                    evaluateAndApply(server, player);
                }
            }
        });
    }

    private void loadConfig(MinecraftServer server) {
        try {
            Path configDir = getConfigDir(server);
            Files.createDirectories(configDir);
            Path configFile = configDir.resolve("scorepack.json");
            if (Files.notExists(configFile)) {
                this.config = Config.defaultConfig();
                try (Writer w = Files.newBufferedWriter(configFile)) {
                    GSON.toJson(this.config, w);
                }
            } else {
                try (Reader r = Files.newBufferedReader(configFile)) {
                    this.config = GSON.fromJson(r, Config.class);
                }
                if (this.config == null) this.config = Config.defaultConfig();
            }
        } catch (IOException e) {
            e.printStackTrace();
            this.config = Config.defaultConfig();
        }
    }

    private Path getConfigDir(MinecraftServer server) {
        // Use the standard config folder
        return Path.of("config");
    }

    private void evaluateAndApply(MinecraftServer server, ServerPlayerEntity player) {
        if (config == null || config.rules.isEmpty()) return;
        String currentPackKey = lastAppliedPack.get(player.getUuid());

        String matchedKey = null;
        Config.Rule matchedRule = null;

        var scoreboard = server.getScoreboard();
        for (Config.Rule rule : config.rules) {
            // Skip rules with null equalsValue to avoid confusion
            if (rule.equalsValue == null) continue; 
            var objective = scoreboard.getNullableObjective(rule.objective);
            if (objective == null) continue;
            var score = scoreboard.getPlayerScore(player, objective);
            int value = score.getScore();
            boolean ok = Objects.equals(rule.equalsValue, value);
            if (ok) {
                matchedRule = rule;
                matchedKey = rule.packUrl;
                if (config.defaults.firstMatchWins) break;
            }
        }

        if (matchedRule == null) {
            if (config.defaults.clearWhenNoMatch && currentPackKey != null) {
                // Vanilla has no direct server->client pack clear API; re-sending default server pack isn't trivial.
                // We simply stop re-sending when no rule matches.
                lastAppliedPack.remove(player.getUuid());
            }
            return;
        }

        if (Objects.equals(currentPackKey, matchedKey)) return; // already applied

        // Send resource pack to player
        try {
            if (matchedRule.packUrl == null || matchedRule.packUrl.isBlank()) {
                // Skip rules with invalid pack URLs
                return;
            }
            String sha1 = normalizeSha1(matchedRule.sha1);
            Text prompt = matchedRule.prompt == null || matchedRule.prompt.isBlank() ? null : Text.literal(matchedRule.prompt);
            // Signature for 1.20+ style: (String url, @Nullable String hash, boolean required, @Nullable Text prompt)
            player.sendResourcePack(matchedRule.packUrl, sha1, matchedRule.required, prompt);
            lastAppliedPack.put(player.getUuid(), matchedKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String normalizeSha1(String hex) {
        if (hex == null) return null;
        String clean = hex.replaceAll("[^0-9A-Fa-f]", "");
        if (clean.length() != 40) return null;
        return clean.toLowerCase(Locale.ROOT);
    }

    // --- Config model ---
    public static class Config {
        public Defaults defaults = new Defaults();
        public List<Rule> rules = new ArrayList<>();

        public static class Defaults {
            public int recheckTicks = 40; // 2s at 20tps
            public boolean firstMatchWins = true;
            public boolean clearWhenNoMatch = false;
        }
        public static class Rule {
            public String objective;
            public Integer equalsValue;
            public String packUrl;
            public String sha1; // optional hex
            public boolean required = false;
            public String prompt; // optional
        }

        static Config defaultConfig() {
            Config c = new Config();
            c.rules.add(rule("mode", 1, "https://example.com/pvp.zip", null, true, "PvP Pack"));
            c.rules.add(rule("mode", 2, "https://example.com/build.zip", null, false, "Builder Pack"));
            return c;
        }

        static Rule rule(String obj, int eq, String url, String sha1, boolean req, String prompt) {
            Rule r = new Rule();
            r.objective = obj;
            r.equalsValue = eq;
            r.packUrl = url;
            r.sha1 = sha1;
            r.required = req;
            r.prompt = prompt;
            return r;
        }
    }
}
