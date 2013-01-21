package com.pheenixm.betterbeacons;

import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;
import java.util.Vector;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.pheenixm.betterbeacons.data.IBeaconStorage;
import com.pheenixm.betterbeacons.data.PluginConfigBeaconStorage;

public class BetterBeaconsManager
{
	private BetterBeaconsPlugin instance;
	private IBeaconStorage storage;
	private Map<String, BetterBeacons> tickMap;
	private Map<UUID, Map<String, BetterBeacons>> worldMap;

	public BetterBeaconsManager(BetterBeaconsPlugin plugin)
	{
		instance = plugin;
		//TODO: NullPointException here
		//storage = (IBeaconStorage)new PluginConfigBeaconStorage(plugin);
		plugin.getServer().getPluginManager().registerEvents(new BetterBeaconListener(plugin), plugin);
		tickMap = new TreeMap<String, BetterBeacons>();
		worldMap = new TreeMap<UUID, Map<String, BetterBeacons>>();
		for (World world : plugin.getServer().getWorlds()) {
			worldMap.put(world.getUID(), new TreeMap<String, BetterBeacons>());
		}
		loadBeacons();
		
		instance.getServer().getPluginManager().registerEvents(new BetterBeaconListener(instance), instance);
	}

	public BetterBeacons newBeacon(Block block) {
		return newBeacon(block.getLocation());
	}

	public BetterBeacons newBeacon(Location location) {
		return newBeacon(location.getWorld().getUID(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
	}

	public BetterBeacons newBeacon(UUID worldUuid, int x, int y, int z) {
		BetterBeacons beacon = newBeaconNoSave(worldUuid, x, y, z);
		save(beacon);
		return beacon;
	}

	public BetterBeacons newBeaconNoSave(UUID worldUuid, int x, int y, int z) {
		Location location = new Location(this.instance.getServer().getWorld(worldUuid), x, y, z);
		if (hasSave(location)) {
			return tickMap.get(BetterBeaconsManager.blockKey(location));
		}
		return new BetterBeacons(this.instance, worldUuid, x, y, z);
	}

	private void loadBeacons() {
		for (BetterBeacons beacon : storage.getAll()) {
			tickMap.put(beacon.getKey(), beacon);
			worldMap.get(beacon.getWorldUuid()).put(beacon.getKey(), beacon);
		}
	}

	/**
	 * TODO: Cleanup this method as it could be more streamlined
	 */
	public void iterate()
	{
		for(UUID uuid : worldMap.keySet()) {
			World world = instance.getServer().getWorld(uuid);
			List<Player> players = world.getPlayers();
			Map<String, BetterBeacons> beacons = worldMap.get(world);
			for (Player player : players) {
				List<BetterBeacons> inRangeBeacons = new Vector<BetterBeacons>();
				for (BetterBeacons beacon : beacons.values()) {
					if (beacon.isInRange(player)) {
						inRangeBeacons.add(beacon);
					}
				}
				if (!inRangeBeacons.isEmpty()) {
					// TODO: Calculate the effective potion effects and apply to player
					//Nuetral Effects
					for(BetterBeacons beacon : inRangeBeacons)
					{
						beacon.onUpdate(beacon);
					}
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
		storage.save(beacon);
	}

	public void remove(Block block) {
		remove(get(block));
	}

	public void remove(Location location) {
		remove(get(location));
	}

	public void remove(BetterBeacons beacon) {
		tickMap.remove(beacon.getKey());
		worldMap.get(beacon.getWorldUuid()).remove(beacon.getKey());
		storage.remove(beacon);
	}

	public static String blockKey(Block block) {
		return BetterBeaconsManager.blockKey(block.getWorld().getUID(), block.getX(), block.getY(), block.getZ());
	}

	public static String blockKey(Location location) {
		return BetterBeaconsManager.blockKey(
			location.getWorld().getUID(),
			location.getBlockX(),
			location.getBlockY(),
			location.getBlockZ());
	}

	public static String blockKey(BetterBeacons beacon) {
		return BetterBeaconsManager.blockKey(beacon.getWorldUuid(), beacon.getX(), beacon.getY(), beacon.getZ());
	}

	public static String blockKey(UUID worldUuid, int x, int y, int z) {
		return String.format("%s,%x,%x,%x", worldUuid.toString(), x, y, z);
	}
}
