package com.pheenixm.betterbeacons;

import java.util.List;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class BetterBeacons {
	
	BetterBeacons(BetterBeaconsPlugin plugin, UUID beaconWorld, int x, int y, int z, String faction, int radius, int fuel_amount, Material fuel_material)
	{
        worldUuid = beaconWorld;
		xCoord = x;
		yCoord = y;
		zCoord = z;
        properties = new BetterBeaconsProperties(faction, radius, fuel_amount, fuel_material);
        beaconKey = BetterBeaconsManager.blockKey(this);
		instance = plugin;
        xMin = xCoord - radius;
        xMax = xCoord + radius;
        yMin = yCoord - radius;
        yMax = yCoord + radius;
        zMin = zCoord - radius;
        zMax = zCoord + radius;
        beaconLocation = new Location(instance.getServer().getWorld(worldUuid), (double)x, (double)y, (double)z, 0.0, 0.0);
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
        int yLoc = location.getBlockY();
        if (yLoc < yMin || yLoc > yMax) {
            return false;
        }
        int zLoc = location.getBlockZ();
        if (zLoc < zMin || zLoc > zMax) {
            return false;
        }
        return location.distance(beaconLocation) <= (float)radius;
    }

	public void onUpdate(List<Player> players)
	{
		//CODE HERE TO GET GROUP AND APPLY AFFECTS. THE MEAT OF THE PLUGIN
        for (Player player : players) {
            if (properties.usePositiveEffect(Player)) {
                // Apply positive effect

            } else {
                // Apply negative effect

            }
        }
	}

    private final UUID worldUuid;
	private final int xCoord;
	private final int yCoord;
	private final int zCoord;
    private BetterBeaconsProperties properties;
    private final String beaconKey;
	private final BetterBeaconsPlugin instance;
    private final int xMin;
    private final int xMax;
    private final int yMin;
    private final int yMax;
    private final int zMin;
    private final int zMax;
    private final Location beaconLocation;
}
