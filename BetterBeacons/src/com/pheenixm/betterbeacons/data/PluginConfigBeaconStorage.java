package com.pheenixm.betterbeacons.data;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.pheenixm.betterbeacons.*;
import com.pheenixm.betterbeacons.BetterBeaconsProperties;
import com.pheenixm.betterbeacons.data.IBeaconStorage;

public class PluginConfigBeaconStorage implements IBeaconStorage {
    private BetterBeaconsPlugin plugin_;

    public PluginConfigBeaconStorage(BetterBeaconsPlugin plugin) {
        plugin_ = plugin;
        plugin_.getConfig().options().copyDefaults(true);
    }

    private ConfigurationSection getConfigSection() {
        return plugin_.getConfig().getConfigurationSection("beaconList");
    }

    public boolean contains(BetterBeacons beacon) {
        return getConfigSection().contains(beacon.getKey());
    }

    public BetterBeacons get(ConfigurationSection cfg) {
        UUID worldUuid = UUID.fromString(cfg.getString("worldUuid"));
        int x = cfg.getInt("x");
        int y = cfg.getInt("y");
        int z = cfg.getInt("z");
        return get(worldUuid, x, y, z);
    }

    public BetterBeacons get(UUID worldUuid, int x, int y, int z) {
        String key = BetterBeaconsManager.blockKey(worldUuid, x, y, z);
        ConfigurationSection root = getConfigSection();
        ConfigurationSection cfg = root.getConfigurationSection(key);
        if (cfg == null) {
            return null;
        }
        BetterBeacons beacon = plugin_.getManager().newBeaconNoSave(worldUuid, x, y, z);
        beacon.setProperties(
            cfg.getString("owningFaction"),
            cfg.getInt("radius"),
            cfg.getInt("fuel_amount"),
            Material.getMaterial(cfg.getString("fuelMaterial")),
            loadEffects(cfg, "positive_effects"),
            loadEffects(cfg, "negative_effects"));
        return beacon;
    }

    private List<PotionEffect> loadEffects(
            ConfigurationSection root, String effects_category) {
        List<PotionEffect> effects = new LinkedList<PotionEffect>();
        ConfigurationSection cfg = root.getConfigurationSection(effects_category);
        for (String effect_name : cfg.getKeys(false)) {
            PotionEffectType effect_type = PotionEffectType.getByName(effect_name);
            if (effect_type == null) {
                // Effect name incorrect
                // TODO: Log error
                continue;
            }
            ConfigurationSection effect_cfg = cfg.getConfigurationSection(effect_name);
            int amplifier = effect_cfg.getInt("amplifier");
            int duration = effect_cfg.getInt("duration");
            PotionEffect pot_effect = new PotionEffect(
                    effect_type, duration, amplifier, false);
            effects.add(pot_effect);
        }
        return Collections.unmodifiableList(effects);
    }

    public List<BetterBeacons> getAll() {
        List<BetterBeacons> beacons = new LinkedList<BetterBeacons>();
        ConfigurationSection root = getConfigSection();
        for (String key : root.getKeys(false)) {
            beacons.add(get(root.getConfigurationSection(key)));
        }
        return beacons;
    }

    // Does not write the config to disk. Call flush() to finish saving once
    //  all plugin configuration changes are made.
    public void save(BetterBeacons beacon) {
        ConfigurationSection root = getConfigSection();
        ConfigurationSection cfg = root.getConfigurationSection(beacon.getKey());
        if (cfg == null) {
            cfg = root.createSection(beacon.getKey());
        }
        cfg.set("worldUuid", beacon.getWorldUuid());
        cfg.set("x", beacon.getX());
        cfg.set("y", beacon.getY());
        cfg.set("z", beacon.getZ());

        BetterBeaconsProperties properties = beacon.getProperties();
        cfg.set("fuelAmount", properties.getFuelAmount());
        cfg.set("fuelMaterial", properties.getFuelMaterial().toString());
        cfg.set("radius", properties.getRadius());
        cfg.set("owningFaction", properties.getOwningFaction().getName());

        ConfigurationSection positive_effect_store = cfg.getConfigurationSection(
                "positive_effects");
        saveEffects(positive_effect_store, properties.getPositiveEffects());

        ConfigurationSection negative_effect_store = cfg.getConfigurationSection(
                "negative_effects");
        saveEffects(negative_effect_store, properties.getNegativeEffects());
    }

    private void saveEffects(ConfigurationSection cfg, List<PotionEffect> effects) {
        for (PotionEffect effect : effects) {
            ConfigurationSection effect_cfg = cfg.getConfigurationSection(
                    effect.getType().getName());
            effect_cfg.set("amplifier", effect.getAmplifier());
            effect_cfg.set("duration", effect.getDuration());
        }
    }

    public void remove(BetterBeacons beacon) {
        ConfigurationSection root = getConfigSection();
        if (root.contains(beacon.getKey())) {
            root.set(beacon.getKey(), null);
        }
    }

    public void flush() {
        plugin_.saveConfig();
    }

}

