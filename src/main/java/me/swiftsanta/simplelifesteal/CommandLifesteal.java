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

public class CommandLifesteal implements CommandExecutor {

    private final SimpleLifesteal plugin;

    public CommandLifesteal(SimpleLifesteal plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (sender instanceof Player || sender instanceof ConsoleCommandSender) {

            if (args.length == 0 || (args[0].equalsIgnoreCase("help") && args.length == 1)) {
                if (sender instanceof Player player) {
                    player.sendMessage(ChatColor.RED + "/lifesteal help" + ChatColor.WHITE + ": Shows a list of commands.");
                    player.sendMessage(ChatColor.RED + "/lifesteal reload" + ChatColor.WHITE + ": Reloads the config file.");
                    player.sendMessage(ChatColor.RED + "/lifesteal reset <player>" + ChatColor.WHITE + ": Resets a player's health.");
                    player.sendMessage(ChatColor.RED + "/lifesteal set <player> <health>" + ChatColor.WHITE + ": Sets a player's health.");
                    player.sendMessage(ChatColor.RED + "/hearts" + ChatColor.WHITE + ": Shows your hearts.");
                    player.sendMessage(ChatColor.RED + "/hearts <player>" + ChatColor.WHITE + ": Shows a player's hearts.");
                    player.sendMessage(ChatColor.RED + "/lifesteal give <player> <health>" + ChatColor.WHITE + ": Gives health to a player.");
                    player.sendMessage(ChatColor.RED + "/lifesteal take <player> <health>" + ChatColor.WHITE + ": Takes health from a player.");
                    player.sendMessage(ChatColor.RED + "/lifesteal get <player>" + ChatColor.WHITE + ": Views health for a player.");
                    player.sendMessage(ChatColor.RED + "/lifesteal toggle" + ChatColor.WHITE + ": Toggles lifesteal.");
                }
                else {
                    plugin.getLogger().info("/lifesteal help: Shows a list of commands.");
                    plugin.getLogger().info("/lifesteal reload: Reloads the config file.");
                    plugin.getLogger().info("/lifesteal reset <player>: Resets a player's health.");
                    plugin.getLogger().info("/lifesteal set <player> <health>: Sets a player's health.");
                    plugin.getLogger().info("/hearts: Shows your hearts.");
                    plugin.getLogger().info("/hearts <player>: Shows a player's hearts.");
                    plugin.getLogger().info("/lifesteal give <player> <health>: Gives health to a player.");
                    plugin.getLogger().info("/lifesteal take <player> <health>: Takes health from a player.");
                    plugin.getLogger().info("/lifesteal get <player>: Views health for a player.");
                    plugin.getLogger().info("/lifesteal toggle: Toggles lifesteal.");
                }
            }

            else if (args[0].equalsIgnoreCase("reload") && args.length == 1) {
                plugin.reloadConfig();
                new ConfigManager(plugin).getConfig();
                if (sender instanceof Player player) {
                    player.sendMessage(ChatColor.GREEN + "Lifesteal config reloaded.");
                    plugin.getLogger().info("Lifesteal config reloaded.");
                }
                else {
                    plugin.getLogger().info("Lifesteal config reloaded.");
                }
            }

            else if (args[0].equalsIgnoreCase("reset") && args.length == 2) {
                Player target = Bukkit.getPlayer(args[1]);
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
                    attribute.setBaseValue(20.0);
                    if (sender instanceof Player player) {
                        player.sendMessage(args[1] + "'s health reset to 20 (10" + ChatColor.RED + "❤" + ChatColor.WHITE + ").");
                    }
                    else {
                        plugin.getLogger().info(args[1] + "'s health reset to 20 (10 hearts).");
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

            else if (args[0].equalsIgnoreCase("set") && args.length == 3) {
                Player target = Bukkit.getPlayer(args[1]);
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
                    double health;
                    try {
                        health = Double.parseDouble(args[2]);
                        if (health < 1) {
                            if (sender instanceof Player player) {
                                player.sendMessage("Cannot set health below 1 (0.5" + ChatColor.RED + "❤" + ChatColor.WHITE + ").");
                            }
                            else {
                                plugin.getLogger().warning("Cannot set health below 1 (0.5 hearts).");
                            }
                            return true;
                        }
                        else if (health > 100) {
                            if (sender instanceof Player player) {
                                player.sendMessage("Cannot set health above 100 (50" + ChatColor.RED + "❤" + ChatColor.WHITE + ").");
                            }
                            else {
                                plugin.getLogger().warning("Cannot set health above 100 (50 hearts).");
                            }
                            return true;
                        }
                    }
                    catch (Exception e) {
                        if (sender instanceof Player player) {
                            player.sendMessage("Health must be a number.");
                        }
                        else {
                            plugin.getLogger().warning("Health must be a number.");
                        }
                        return true;
                    }
                    double rounded = (double)Math.round(health * 2) / 2;
                    String hearts = ((rounded / 2) % 1 == 0) ?
                            String.format("%.0f", (rounded / 2)) :
                            String.format("%.1f", (rounded / 2));
                    attribute.setBaseValue(rounded);
                    if (sender instanceof Player player) {
                        player.sendMessage(args[1] + "'s health set to " + (int)rounded + " (" + hearts + ChatColor.RED + "❤" + ChatColor.WHITE + ").");
                    }
                    else {
                        plugin.getLogger().info(args[1] + "'s health set to " + (int)rounded + " (" + hearts + " hearts).");
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

            else if (args[0].equalsIgnoreCase("give") && args.length == 3) {
                Player target = Bukkit.getPlayer(args[1]);
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
                    double health;
                    try {
                        health = attribute.getBaseValue() + Double.parseDouble(args[2]);
                        if (health < 1) {
                            if (sender instanceof Player player) {
                                player.sendMessage("Cannot set health below 1 (0.5" + ChatColor.RED + "❤" + ChatColor.WHITE + ").");
                            }
                            else {
                                plugin.getLogger().warning("Cannot set health below 1 (0.5 hearts).");
                            }
                            return true;
                        }
                        else if (health > 100) {
                            if (sender instanceof Player player) {
                                player.sendMessage("Cannot set health above 100 (50" + ChatColor.RED + "❤" + ChatColor.WHITE + ").");
                            }
                            else {
                                plugin.getLogger().warning("Cannot set health above 100 (50 hearts).");
                            }
                            return true;
                        }
                    }
                    catch (Exception e) {
                        if (sender instanceof Player player) {
                            player.sendMessage("Health must be a number.");
                        }
                        else {
                            plugin.getLogger().warning("Health must be a number.");
                        }
                        return true;
                    }
                    double rounded = (double)Math.round(Double.parseDouble(args[2]) * 2) / 2;
                    String hearts = ((rounded / 2) % 1 == 0) ?
                            String.format("%.0f", (rounded / 2)) :
                            String.format("%.1f", (rounded / 2));
                    attribute.setBaseValue(health);
                    if (sender instanceof Player player) {
                        player.sendMessage("Gave " + (int)rounded + " health (" + hearts + ChatColor.RED + "❤" + ChatColor.WHITE + ") to " + args[1] + ".");
                    }
                    else {
                        plugin.getLogger().info("Gave " + (int)rounded + " health (" + hearts + "hearts) to " + args[1] + ".");
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

            else if (args[0].equalsIgnoreCase("take") && args.length == 3) {
                Player target = Bukkit.getPlayer(args[1]);
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
                    double health;
                    try {
                        health = attribute.getBaseValue() - Double.parseDouble(args[2]);
                        if (health < 1) {
                            if (sender instanceof Player player) {
                                player.sendMessage("Cannot set health below 1 (0.5" + ChatColor.RED + "❤" + ChatColor.WHITE + ").");
                            }
                            else {
                                plugin.getLogger().warning("Cannot set health below 1 (0.5 hearts).");
                            }
                            return true;
                        }
                        else if (health > 100) {
                            if (sender instanceof Player player) {
                                player.sendMessage("Cannot set health above 100 (50" + ChatColor.RED + "❤" + ChatColor.WHITE + ").");
                            }
                            else {
                                plugin.getLogger().warning("Cannot set health above 100 (50 hearts).");
                            }
                            return true;
                        }
                    }
                    catch (Exception e) {
                        if (sender instanceof Player player) {
                            player.sendMessage("Health must be a number.");
                        }
                        else {
                            plugin.getLogger().warning("Health must be a number.");
                        }
                        return true;
                    }
                    double rounded = (double)Math.round(Double.parseDouble(args[2]) * 2) / 2;
                    String hearts = ((rounded / 2) % 1 == 0) ?
                            String.format("%.0f", (rounded / 2)) :
                            String.format("%.1f", (rounded / 2));
                    attribute.setBaseValue(health);
                    if (sender instanceof Player player) {
                        player.sendMessage("Took " + (int)rounded + " health (" + hearts + ChatColor.RED + "❤" + ChatColor.WHITE + ") from " + args[1] + ".");
                    }
                    else {
                        plugin.getLogger().info("Took " + (int)rounded + " health (" + hearts + "hearts) from " + args[1] + ".");
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

            else if (args[0].equalsIgnoreCase("get") && args.length == 2) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null) {
                    AttributeInstance attribute = target.getAttribute(Attribute.MAX_HEALTH);
                    if (attribute == null) {
                        if (sender instanceof Player player) {
                            player.sendMessage("Player not found.");
                        } else {
                            plugin.getLogger().warning("Player not found.");
                        }
                        return true;
                    }
                    double health = attribute.getBaseValue();
                    String hearts = ((health / 2) % 1 == 0) ?
                            String.format("%.0f", (health / 2)) :
                            String.format("%.1f", (health / 2));
                    if (sender instanceof Player player) {
                        player.sendMessage(args[1] + " has " + (int)health + " health (" + hearts + ChatColor.RED + "❤" + ChatColor.WHITE + ").");
                    }
                    else {
                        plugin.getLogger().info(args[1] + " has " + (int)health + " health (" + hearts + ChatColor.RED + "❤" + ChatColor.WHITE + ").");
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

            else if (args[0].equalsIgnoreCase("toggle") && args.length == 1) {
                boolean newValue;
                try {
                    boolean oldValue = plugin.getConfig().getBoolean("lifesteal-enabled");
                    newValue = !oldValue;
                    plugin.getConfig().set("lifesteal-enabled", newValue);
                    plugin.saveConfig();
                }
                catch (Exception e) {
                    if (sender instanceof Player player) {
                        player.sendMessage(ChatColor.RED + "Error accessing \"lifesteal-enabled\" in config.yml. Make sure it exists and is set to either true or false.");
                        plugin.getLogger().severe("Error accessing \"lifesteal-enabled\" in config.yml. Make sure it exists and is set to either true or false.");
                    }
                    else {
                        plugin.getLogger().severe("Error accessing \"lifesteal-enabled\" in config.yml. Make sure it exists and is set to either true or false.");
                    }
                    return true;
                }
                if (newValue) {
                    if (sender instanceof Player player) {
                        player.sendMessage(ChatColor.GREEN + "Lifesteal enabled.");
                        plugin.getLogger().info("Lifesteal enabled.");
                    }
                    else {
                        plugin.getLogger().info("Lifesteal enabled.");
                    }
                }
                else {
                    if (sender instanceof Player player) {
                        player.sendMessage(ChatColor.RED + "Lifesteal disabled.");
                        plugin.getLogger().info("Lifesteal disabled.");
                    }
                    else {
                        plugin.getLogger().info("Lifesteal disabled.");
                    }
                }
            }
        }
        return true;
    }
}
