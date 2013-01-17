package com.pheenixm.betterbeacons.data;

import java.util.List;
import java.util.LinkedList;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

import com.pheenixm.betterbeacons.BetterBeacons;
import com.pheenixm.betterbeacons.data.IBeaconStorage;

public class PluginConfigBeaconStorage implements IBeaconStorage {
    private Plugin plugin_;

    public PluginConfigBeaconStorage(Plugin plugin) {
        plugin_ = plugin;
        plugin_.getConfig().options().copyDefaults(true);
    }

    private ConfigurationSection getConfigSection() {
        return plugin_.getConfig().getConfigurationSection("beaconList");
    }

    public boolean contains(BetterBeacons beacon) {
        return getConfigSection().contains(beacon.getKey());
    }

    public BetterBeacons get(UUID worldUuid, int x, int y, int z) {
        String key = BetterBeaconsManager.blockKey(worldUuid, x, y, z);
        return get(key);
    }

    public BetterBeacons get(String beaconKey) {
        ConfigurationSection root = getConfigSection();
        ConfigurationSection cfg = root.getConfigurationSection(key);
        if (cfg == null) {
            return null;
        }
        BetterBeacons beacon = plugin_.getManager().newBeaconNoSave(worldUuid, x, y, z);
        BetterBeaconsProperties properties = new BetterBeaconsProperties(
            cfg.getString("owningFaction"),
            cfg.getInt("radius"),
            cfg.getInt("fuel_amount"),
            Material.getMaterial(cfg.getString("fuelMaterial")));
        beacon.setProperties(properties);
        return beacon;
    }

    public List<BetterBeacons> getAll() {
        List<BetterBeacons> beacons = new LinkedList<BetterBeacons>();
        ConfigurationSection root = getConfigSection();
        for (String key : root.getKeys) {
            beacons.add(get(key));
        }
        return beacons;
    }

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

