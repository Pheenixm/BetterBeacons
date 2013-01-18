package com.pheenixm.betterbeacons;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class BetterBeaconListener implements Listener {


	private BetterBeaconsPlugin instance;

	public BetterBeaconListener(BetterBeaconsPlugin plugin) {
		instance = plugin;
	}

	@EventHandler
	public void onBlockInteract(PlayerInteractEvent event)
	{
		Block block = event.getClickedBlock();
		if(!block.getType().equals(Material.BEACON))
		{
			return;
		}
		BetterBeaconsManager manager = instance.getManager();
		BetterBeacons beacon = manager.newBeacon(block);

		// TODO: Stuff




	}

	@EventHandler
	public void onBlockRemoved(BlockBreakEvent event)
	{
		Block block = event.getBlock();
		if(!block.getType().equals(Material.BEACON))
		{
			return;
		}


		// TODO: If Citadel integrated, check for reinforcement
		// It may be easier to make this a higher event priority than Citadel's block break handler


		instance.getManager().remove(block);
	}
}
