package me.swiftsanta.simplelifesteal;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TabCompletionHearts implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (args.length == 1 && sender.hasPermission("lifesteal.viewothers")) {
            List<String> playerNames = new ArrayList<>();
            Bukkit.getOnlinePlayers().forEach(player -> playerNames.add(player.getName()));
            return filter(playerNames, args[0]);
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
