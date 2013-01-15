package com.pheenixm.betterbeacons;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class BetterBeacons {
	
	BetterBeacons(BetterBeaconsPlugin plugin, int x, int y, int z)
	{
		xCoord = x;
		yCoord = y;
		zCoord = z;
		instance = plugin;
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
	
	public void remove()
	{
		instance.getManager().tickList.remove(this);
		//ADD MORE HERE TO ERASE IT FROM SAVE LIST AS WELL
	}
	
	public void onUpdate()
	{
		//CODE HERE TO CHECK WHO IS NEARBY, GET GROUP AND APPLY AFFECTS. THE MEAT OF THE PLUGIN
	}

	
    private float getDistance(Location p, Location q) 
    {
        return (float) Math.sqrt(Math.pow(p.getBlockX() - q.getBlockX(), 2) + Math.pow(p.getBlockY() - q.getBlockY(), 2) + Math.pow(p.getBlockZ() - q.getBlockZ(), 2));
    }

	private int xCoord;
	private int yCoord;
	private int zCoord;
	private BetterBeaconsPlugin instance;
}
