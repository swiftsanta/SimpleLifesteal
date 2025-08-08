package me.swiftsanta.simplelifesteal;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandHearts implements CommandExecutor {

    private final SimpleLifesteal plugin;

    public CommandHearts(SimpleLifesteal plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (sender instanceof Player || sender instanceof ConsoleCommandSender) {
            if (args.length == 0) {
                if (sender instanceof Player player) {
                    AttributeInstance attribute = player.getAttribute(Attribute.MAX_HEALTH);
                    double health = attribute.getBaseValue();
                    String hearts = ((health / 2) % 1 == 0) ?
                            String.format("%.0f", (health / 2)) :
                            String.format("%.1f", (health / 2));
                    player.sendMessage("You have " + hearts + ChatColor.RED + "❤");
                }
                else {
                    plugin.getLogger().warning("/hearts is only for players. Console must specify a name.");
                }
            }

            else if (args.length == 1 && sender.hasPermission("lifesteal.viewothers")) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    AttributeInstance attribute = target.getAttribute(Attribute.MAX_HEALTH);
                    if (attribute == null) {
                        if (sender instanceof Player player) {
                            player.sendMessage("Player not found.");
                        }
                        else {
                            plugin.getLogger().warning("Player not found.");
                        }
                        return true;
                    }
                    double health = attribute.getBaseValue();
                    String hearts = ((health / 2) % 1 == 0) ?
                            String.format("%.0f", (health / 2)) :
                            String.format("%.1f", (health / 2));
                    if (sender instanceof Player player) {
                        player.sendMessage(args[0] + " has " + hearts + ChatColor.RED + "❤");
                    }
                    else {
                        plugin.getLogger().info(args[0] + " has " + hearts + " hearts.");
                    }
                }
                else {
                    if (sender instanceof Player player) {
                        player.sendMessage("Player not found.");
                    }
                    else {
                        plugin.getLogger().warning("Player not found.");
                    }
                }
            }
        }
        return true;
    }
}
