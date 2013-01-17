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
    	if(block.getType().equals(Material.BEACON))
    	{
    		if(!hasSave(block.getX(), block.getY(), block.getZ()))
    		{
    			BetterBeacons beacon = new BetterBeacons(instance, block.getX(), block.getY(), block.getZ());
    			instance.getManager().tickList.add(beacon);
    		}
    	}
    }
    
    @EventHandler
    public void onBlockRemoved(BlockBreakEvent event)
    {
    	Block block = event.getBlock();
    	if(block.getType().equals(Material.BEACON))
    	{
    		for(BetterBeacons beacon : instance.getManager().tickList)
    		{
    			if(block.getX() == beacon.getX() && block.getY() == beacon.getY() && block.getZ() == beacon.getZ())
    			{
    				beacon.remove();
    			}
    				
    		}
    	}
    }

    public boolean hasSave(int x, int y, int z)
    {
    	//ADD CODE HERE TO GET WHETHER A BEACON OBJECT ALREADY EXISTS AT GIVEN COORDS
    	return true; //Temporary Measure
    }
}
