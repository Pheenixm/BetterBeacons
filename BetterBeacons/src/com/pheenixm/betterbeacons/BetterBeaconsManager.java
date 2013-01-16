package com.pheenixm.betterbeacons;

import java.util.Map;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;
import java.util.Vector;

import org.bukkit.Location;
import org.bukkit.block.Block;

public class BetterBeaconsManager
{

	private BetterBeaconsPlugin instance;
	private Map<String, BetterBeacons> tickMap;
	private Map<UUID, Map<String, BetterBeacons>> worldMap;

	public BetterBeaconsManager(BetterBeaconsPlugin plugin)
	{
		instance = plugin;
        plugin.getServer().getPluginManager().registerEvents(new BetterBeaconListener(plugin), plugin);
        tickMap = new TreeMap<String, BetterBeacons>();
        worldMap = new TreeMap<UUID, Map<String, BetterBeacons>>();
        for (World world : plugin.getServer().getWorlds()) {
            worldMap.put(world.getUID(), new Map<String, BetterBeacons>());
        }
	}

    public BetterBeacons newBeacon(Block block) {
        return newBeacon(block.getLocation());
    }

    public BetterBeacons newBeacon(Location location) {
        if (hasSave(location)) {
            return tickMap.get(BetterBeaconsManager.blockKey(location));
        }
        BetterBeacons beacon = new BetterBeacons(instance, location.getWorld().getUID(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
        save(beacon);
        return beacon;
    }

	public void iterate()
	{
		for(World world : worldMap.keySet()) {
            List<Player> players = world.getPlayers();
            Map<String, BetterBeacons> beacons = worldMap.get(world);
            for (BetterBeacons beacon : beacons.values()) {
                List<Player> inRangePlayers = new Vector<Player>();
                for (Player player : players) {
                    if (beacon.isInRange(player)) {
                        inRangePlayers.add(player);
                    }
                }
                if (!inRangePlayers.isEmpty()) {
                    players.removeAll(inRangePlayers);
                    beacon.onUpdate(inRangePlayers);
                }
            }
		}
	}

    public BetterBeacons get(Block block) {
        return tickMap.get(BetterBeaconsManager.blockKey(block));
    }

    public BetterBeacons get(Location location) {
        return tickMap.get(BetterBeaconsManager.blockKey(location));
    }

    public boolean hasSave(Block block) {
        return get(block) != null;
    }

    public boolean hasSave(Location location) {
        return get(location) != null;
    }

    public void save(BetterBeacons beacon) {
        tickMap.put(beacon.getKey(), beacon);
        worldMap.get(beacon.getWorldUuid()).put(beacon.getKey(), beacon);
        // Persist beacon
    }

    public void remove(BetterBeacons beacon) {
        // Remove beacon persistence
    }

    public static String blockKey(Block block) {
        return BetterBeaconsManager.blockKey(block.getWorld().getUID(), block.getX(), block.getY(), block.getZ());
    }

    public static String blockKey(Location location) {
        return BetterBeaconsManager.blockKey(location.getWorld().getUID(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public static String blockKey(BetterBeacons beacon) {
        return BetterBeaconsManager.blockKey(beacon.getWorldUuid(), beacon.getX(), beacon.getY(), beacon.getZ());
    }

    public static String blockKey(UUID worldUuid, int x, int y, int z) {
        return String.format("%s,%x,%x,%x", worldUuid.toString(), x, y, z);
    }
	
}
