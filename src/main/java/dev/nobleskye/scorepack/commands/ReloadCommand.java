package dev.nobleskye.scorepack.commands;

import dev.nobleskye.scorepack.ScorePackPlugin;
import dev.nobleskye.scorepack.config.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the /scorepack command.
 */
public class ReloadCommand implements CommandExecutor, TabCompleter {
    private final ScorePackPlugin plugin;
    private final ConfigManager configManager;
    
    /**
     * Creates a new reload command handler.
     * @param plugin The plugin instance
     * @param configManager The configuration manager
     */
    public ReloadCommand(ScorePackPlugin plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("scorepack.admin")) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return true;
        }
        
        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }
        
        String subCommand = args[0].toLowerCase();
        
        switch (subCommand) {
            case "reload":
                handleReload(sender);
                break;
                
            case "info":
                handleInfo(sender);
                break;
                
            case "help":
                sendHelp(sender);
                break;
                
            default:
                sender.sendMessage(ChatColor.RED + "Unknown subcommand. Use /scorepack help for help.");
                break;
        }
        
        return true;
    }
    
    /**
     * Handles the reload subcommand.
     * @param sender The command sender
     */
    private void handleReload(CommandSender sender) {
        sender.sendMessage(ChatColor.YELLOW + "Reloading ScorePack configurations...");
        
        try {
            configManager.loadAllConfigurations();
            int count = configManager.getLoadedObjectives().size();
            sender.sendMessage(ChatColor.GREEN + "Successfully reloaded " + count + " scoreboard configuration(s)!");
        } catch (Exception e) {
            sender.sendMessage(ChatColor.RED + "Failed to reload configurations: " + e.getMessage());
            plugin.getLogger().warning("Error during config reload: " + e.getMessage());
        }
    }
    
    /**
     * Handles the info subcommand.
     * @param sender The command sender
     */
    private void handleInfo(CommandSender sender) {
        sender.sendMessage(ChatColor.GOLD + "=== ScorePack Info ===");
        sender.sendMessage(ChatColor.YELLOW + "Version: " + ChatColor.WHITE + plugin.getDescription().getVersion());
        sender.sendMessage(ChatColor.YELLOW + "Loaded objectives: " + ChatColor.WHITE + configManager.getLoadedObjectives().size());
        
        if (!configManager.getLoadedObjectives().isEmpty()) {
            sender.sendMessage(ChatColor.YELLOW + "Objectives: " + ChatColor.WHITE + String.join(", ", configManager.getLoadedObjectives()));
        }
    }
    
    /**
     * Sends help information to the sender.
     * @param sender The command sender
     */
    private void sendHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.GOLD + "=== ScorePack Commands ===");
        sender.sendMessage(ChatColor.YELLOW + "/scorepack reload" + ChatColor.WHITE + " - Reload all configurations");
        sender.sendMessage(ChatColor.YELLOW + "/scorepack info" + ChatColor.WHITE + " - Display plugin information");
        sender.sendMessage(ChatColor.YELLOW + "/scorepack help" + ChatColor.WHITE + " - Show this help message");
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        
        if (args.length == 1) {
            if (sender.hasPermission("scorepack.admin")) {
                completions.add("reload");
                completions.add("info");
                completions.add("help");
            }
        }
        
        return completions;
    }
}
