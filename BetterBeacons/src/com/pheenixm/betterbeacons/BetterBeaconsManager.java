package com.pheenixm.betterbeacons;

import org.bukkit.event.Listener;

public class BetterBeaconsManager implements Listener
{

	private BetterBeaconsPlugin instance;
	
	public BetterBeaconsManager(BetterBeaconsPlugin plugin)
	{
		instance = plugin;
        plugin.getServer().getPluginManager().registerEvents(new BetterBeaconListener(plugin), plugin);

	}
}
