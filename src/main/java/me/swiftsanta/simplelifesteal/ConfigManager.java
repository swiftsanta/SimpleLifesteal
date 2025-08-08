package me.swiftsanta.simplelifesteal;

public class ConfigManager {

    private final SimpleLifesteal plugin;

    public ConfigManager(SimpleLifesteal plugin) {
        this.plugin = plugin;
    }

    void writeConfig() {
        plugin.saveDefaultConfig();
    }

    boolean lifestealEnabled;
    double minHealth;
    double maxHealth;
    double increment;

    void setDefault () {
        lifestealEnabled = true;
        minHealth = 10.0;
        maxHealth = 40.0;
        increment = 2.0;
    }

    void getConfig() {
        int count = 0;
        try {
            lifestealEnabled = plugin.getConfig().getBoolean("lifesteal-enabled");
        }
        catch (Exception e) {
            plugin.getLogger().severe("Config error. \"lifesteal-enabled\" must be true or false.");
            count += 1;
        }
        try {
            minHealth = plugin.getConfig().getDouble("min-health");
            if (minHealth < 1.0) {
                plugin.getLogger().severe("Config error. \"min-health\" cannot be less than 1 (0.5 hearts).");
                count += 1;
            }
            else if (minHealth > 100.0) {
                plugin.getLogger().severe("Config error. \"min-health\" cannot be more than 100 (50 hearts).");
                count += 1;
            }
        }
        catch (Exception e) {
            plugin.getLogger().severe("Config error. \"min-health\" must be a number.");
            count += 1;
        }
        try {
            maxHealth = plugin.getConfig().getDouble("max-health");
            if (maxHealth < 1.0) {
                plugin.getLogger().severe("Config error. \"max-health\" cannot be less than 1 (0.5 hearts).");
                count += 1;
            }
            else if (maxHealth > 100.0) {
                plugin.getLogger().severe("Config error. \"max-health\" cannot be more than 100 (50 hearts).");
                count += 1;
            }
            if (minHealth > maxHealth) {
                plugin.getLogger().severe("Config error. \"min-health\" cannot be more than \"max-health\"");
                count += 1;
            }
        }
        catch (Exception e) {
            plugin.getLogger().severe("Config error. \"max-health\" must be a number.");
            count += 1;
        }
        try {
            increment = plugin.getConfig().getDouble("increment");
            if (increment < 0) {
                plugin.getLogger().severe("Config error. \"increment\" cannot be less than 0.");
                count += 1;
            }
            else if (increment > 5.0) {
                plugin.getLogger().severe("Config error. \"increment\" cannot be more than 10 (5 hearts).");
                count += 1;
            }
        }
        catch (Exception e) {
            plugin.getLogger().severe("Config error. \"increment\" must be a number.");
        }
        if (count > 0) {
            setDefault();
            plugin.getLogger().severe("Errors were found in config.yml. Default values were applied.");
        }
    }
}
