package me.swiftsanta.simplelifesteal;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TabCompletionLifesteal implements TabCompleter {

    private static final List<String> SUBCOMMANDS = List.of("help", "reload", "reset", "set", "give", "take", "get", "toggle");

    private static final List<String> PLAYER_COMMANDS = List.of("reset", "set", "give", "take", "get");

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (args.length == 1) {
            return filter(SUBCOMMANDS, args[0]);
        }
        if (args.length == 2 && PLAYER_COMMANDS.contains(args[0].toLowerCase())) {
            List<String> playerNames = new ArrayList<>();
            Bukkit.getOnlinePlayers().forEach(player -> playerNames.add(player.getName()));
            return filter(playerNames, args[1]);
        }
        return List.of();
    }

    private List<String> filter(List<String> options, String input) {
        List<String> results = new ArrayList<>();
        String lowerInput = input.toLowerCase();
        for (String option : options) {
            if (option.toLowerCase().startsWith(lowerInput)) {
                results.add(option);
            }
        }
        return results;
    }
}