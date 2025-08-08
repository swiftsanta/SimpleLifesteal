package me.swiftsanta.simplelifesteal;

import org.bukkit.plugin.java.JavaPlugin;

public final class SimpleLifesteal extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("SimpleLifesteal has started.");
        new ConfigManager(this).writeConfig();
        getServer().getPluginManager().registerEvents(new HeartsManager(this), this);
        getCommand("lifesteal").setExecutor(new CommandLifesteal(this));
        getCommand("hearts").setExecutor(new CommandHearts(this));
        getCommand("lifesteal").setTabCompleter(new TabCompletionLifesteal());
        getCommand("hearts").setTabCompleter(new TabCompletionHearts());
    }

    @Override
    public void onDisable() {
        getLogger().info("SimpleLifesteal has stopped.");
    }
}
