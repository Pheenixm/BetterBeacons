package com.pheenixm.betterbeacons;

import java.util.List;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class BetterBeacons {

	BetterBeacons(
			BetterBeaconsPlugin plugin,
			UUID beaconWorld,
			int x,
			int y,
			int z) {
		worldUuid = beaconWorld;
		xCoord = x;
		yCoord = y;
		zCoord = z;
		beaconKey = BetterBeaconsManager.blockKey(this);
		instance = plugin;
		beaconLocation = new Location(instance.getServer().getWorld(worldUuid), (double)x, (double)y, (double)z);
		properties = null;
		xMin = null;
		xMax = null;
		yMin = null;
		yMax = null;
		zMin = null;
		zMax = null;
    }

	BetterBeacons(
			BetterBeaconsPlugin plugin,
			UUID beaconWorld,
			int x,
			int y,
			int z,
			String faction,
			int radius,
			int fuel_amount,
			Material fuel_material) {
		worldUuid = beaconWorld;
        // TODO: As a Location is created, this doesn't need to explicitly track
        //  x, y, z as it's redundant.
		xCoord = x;
		yCoord = y;
		zCoord = z;
		beaconKey = BetterBeaconsManager.blockKey(this);
		instance = plugin;
		beaconLocation = new Location(instance.getServer().getWorld(worldUuid), (double)x, (double)y, (double)z);
        setProperties(faction, radius, fuel_amount, fuel_material);
	}

	public UUID getWorldUuid() {
		return worldUuid;
	}

	public int getX()
	{
		return xCoord;
	}

	public int getY()
	{
		return yCoord;
	}

	public int getZ()
	{
		return zCoord;
	}

	public BetterBeaconsProperties getProperties() {
		return properties;
	}

	public void setProperties(String faction, int radius, int fuel_amount, Material fuel_material) {
        setProperties(new BetterBeaconsProperties(faction, radius, fuel_amount, fuel_material));
    }

	public void setProperties(BetterBeaconsProperties newProperties) {
		properties = newProperties;
		xMin = xCoord - properties.getRadius();
		xMax = xCoord + properties.getRadius();
		yMin = yCoord - properties.getRadius();
		yMax = yCoord + properties.getRadius();
		zMin = zCoord - properties.getRadius();
		zMax = zCoord + properties.getRadius();
	}

	public String getKey() {
		return beaconKey;
	}

	public void save()
	{
		instance.getManager().save(this);
	}

	public void remove()
	{
		instance.getManager().remove(this);
	}

	public boolean isInRange(Player player) {
		Location location = player.getLocation();
		int xLoc = location.getBlockX();
		if (xLoc < xMin || xLoc > xMax) {
			return false;
		}
		int zLoc = location.getBlockZ();
		if (zLoc < zMin || zLoc > zMax) {
			return false;
		}
		return xzDistance(location, beaconLocation) <= (float)properties.getRadius();
	}

	public float xzDistance(Location one, Location two) {
		if (one.getWorld() != two.getWorld()) {
			throw new IllegalArgumentException(String.format(
				"Cannot measure distance between two worlds: '%s' '%s'",
				one.getWorld().getName(), two.getWorld().getName()));
		}
		double dubX = Math.pow(one.getX() - two.getX(), 2);
		double dubZ = Math.pow(one.getZ() - two.getZ(), 2);
		return (float) Math.sqrt(dubX + dubZ);
	}

	public void onUpdate(List<Player> players)
	{
		//CODE HERE TO GET GROUP AND APPLY AFFECTS. THE MEAT OF THE PLUGIN
		for (Player player : players) {
			if (properties.usePositiveEffect(player)) { //FUNCTION NON-EXISTENT
				// Apply positive effect

			} else {
				// Apply negative effect

			}
		}
	}

    // Immutable member variables
	private final UUID worldUuid;
	private final int xCoord;
	private final int yCoord;
	private final int zCoord;
	private final String beaconKey;
	private final BetterBeaconsPlugin instance;
	private final Location beaconLocation;

    // Mutable member variables
	private BetterBeaconsProperties properties;
	private Integer xMin;
	private Integer xMax;
	private Integer yMin;
	private Integer yMax;
	private Integer zMin;
	private Integer zMax;
}
