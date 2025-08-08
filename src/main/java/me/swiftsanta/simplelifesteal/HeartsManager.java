package me.swiftsanta.simplelifesteal;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class HeartsManager implements Listener {

    boolean lifestealEnabled;
    double minHealth;
    double maxHealth;
    double increment;

    public HeartsManager(SimpleLifesteal simpleLifesteal) {
        ConfigManager configManager = new ConfigManager(simpleLifesteal);
        configManager.getConfig();
        lifestealEnabled = configManager.lifestealEnabled;
        minHealth = configManager.minHealth;
        maxHealth = configManager.maxHealth;
        increment = configManager.increment;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (lifestealEnabled) {
            Player victim = event.getPlayer();
            Player killer = victim.getKiller();

            if (killer != null) {
                AttributeInstance victimAttr = victim.getAttribute(Attribute.MAX_HEALTH);
                double victimHealth;
                if (victimAttr != null) {
                    victimHealth = victimAttr.getBaseValue();
                    if (victimHealth >= minHealth + increment) {
                        victimAttr.setBaseValue(victimHealth - increment);
                    }
                }
                AttributeInstance killerAttr = killer.getAttribute(Attribute.MAX_HEALTH);
                double killerHealth;
                if (killerAttr != null) {
                    killerHealth = killerAttr.getBaseValue();
                    if (killerHealth <= maxHealth - increment) {
                        killerAttr.setBaseValue(killerHealth + increment);
                    }
                }
            }
        }
    }
}
