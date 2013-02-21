package com.pheenixm.betterbeacons;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
 
public class BetterBeaconsIterator extends BukkitRunnable {
 
	public BetterBeaconsIterator(BetterBeaconsPlugin instance)
	{
		plugin = instance;
	}
	
    public void run() 
    {
    	if(plugin.getManager() != null)
    		plugin.getManager().iterate();
    	else
    		System.out.println("Something has gone wrong");
    }
 
    
    private BetterBeaconsPlugin plugin;
}
