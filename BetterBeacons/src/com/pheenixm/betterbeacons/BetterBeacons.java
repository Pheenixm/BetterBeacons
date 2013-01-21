package com.pheenixm.betterbeacons;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
			Material fuel_material,
			ArrayList<PotionEffect> positive,
			ArrayList<PotionEffect> negative
			) {
		worldUuid = beaconWorld;
		// TODO: As a Location is created, this doesn't need to explicitly track
		//  x, y, z as it's redundant.
		xCoord = x;
		yCoord = y;
		zCoord = z;
		beaconKey = BetterBeaconsManager.blockKey(this);
		instance = plugin;
		beaconLocation = new Location(instance.getServer().getWorld(worldUuid), (double)x, (double)y, (double)z);
		setProperties(faction, radius, fuel_amount, fuel_material, positive, negative);
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

	public void setProperties(String faction, Integer radius, int fuel_amount, Material fuel_material, List<PotionEffect> positive, List<PotionEffect> negative) {
		setProperties(new BetterBeaconsProperties(faction, radius, fuel_amount, fuel_material, positive, negative));
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
		return xzDistanceSquared(location, beaconLocation) <= properties.getRadiusSquared();
	}

	public double xzDistanceSquared(Location one, Location two) {
		if (one.getWorld() != two.getWorld()) {
			throw new IllegalArgumentException(String.format(
				"Cannot measure distance between two worlds: '%s' '%s'",
				one.getWorld().getName(), two.getWorld().getName()));
		}
		double dubX = Math.pow(one.getX() - two.getX(), 2);
		double dubZ = Math.pow(one.getZ() - two.getZ(), 2);
		return dubX + dubZ;
	}

	public double xzDistance(Location one, Location two) {
		return Math.sqrt(xzDistanceSquared(one, two));
	}

	/**
	 * Applies this beacon's effects to the player
	 * @param player
	 */
	public void onUpdate(Player player)
	{
	    // Caller has responsibility to verify player is in range via isInRange
		if (properties.usePositiveEffect(player))
		{
			player.addPotionEffects(properties.getPositiveEffects());
		}
		else
		{
			player.addPotionEffects(properties.getNegativeEffects());
			//TODO: Configure this to allow for blacklists as well

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
