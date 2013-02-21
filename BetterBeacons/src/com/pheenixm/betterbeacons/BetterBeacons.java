package com.pheenixm.betterbeacons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
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
//		properties = null;
		int radius = getRadius(plugin, beaconWorld, x, y, z);
		setProperties(null, radius, 0, Material.EMERALD, new ArrayList<PotionEffect>(), new ArrayList<PotionEffect>());
		
		// TODO: Call initializeInventory(INVENTORY) if
		// loading Beacon from file, whether INVENTORY is the
		// previously saved copy of Beacon inventory
		initializeInventory();
	}

	/**
	 * TODO: TRACE RADIUS TO FIND OUT WHERE IT IS INITIALLY SET
	 * @param plugin
	 * @param beaconWorld
	 * @param x
	 * @param y
	 * @param z
	 * @param faction
	 * @param radius
	 * @param fuel_amount
	 * @param fuel_material
	 * @param positive
	 * @param negative
	 */
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
		
		// TODO: Call initializeInventory(INVENTORY) if
		// loading Beacon from file, whether INVENTORY is the
		// previously saved copy of Beacon inventory
		initializeInventory();
	}

	public int getRadius(BetterBeaconsPlugin plugin, UUID worldNum, int x, int y, int z)
	{
		int radius = 25;
		World world = plugin.getServer().getWorld(worldNum);
		int lvl1 = 0;
		for(int i = -1; i < 1; i++)
		{
			for(int j = -1; j < 1; j++)
			{
				Location loc = new Location(world, x + i, y - 1, z + j);
				Material block = world.getBlockAt(loc).getType();
				if(block.equals(Material.DIAMOND) || block.equals(Material.EMERALD_BLOCK) || block.equals(Material.IRON_BLOCK) || block.equals(Material.GOLD_BLOCK))
					lvl1++;
			}
		}
		if(lvl1 == 9)
		{
			radius = 50;
			int lvl2 = 0;
			for(int i = -2; i < 2; i++)
			{
				for(int j = -2; j < 2; j++)
				{
					Location loc = new Location(world, x + i, y - 2, z + j);
					Material block = world.getBlockAt(loc).getType();
					if(block.equals(Material.DIAMOND) || block.equals(Material.EMERALD_BLOCK) || block.equals(Material.IRON_BLOCK) || block.equals(Material.GOLD_BLOCK))
						lvl2++;
				}
			}
			if(lvl2 == 25)
			{
				radius = 100;
				int lvl3 = 0;
				for(int i = -3; i < 3; i++)
				{
					for(int j = -3; j < 3; j++)
					{
						Location loc = new Location(world, x + i, y - 3, z + j);
						Material block = world.getBlockAt(loc).getType();
						if(block.equals(Material.DIAMOND) || block.equals(Material.EMERALD_BLOCK) || block.equals(Material.IRON_BLOCK) || block.equals(Material.GOLD_BLOCK))
							lvl3++;
					}
				}
				if(lvl3 == 49)
				{
					radius = 150;
					int lvl4 = 0;
					for(int i = -4; i < 4; i++)
					{
						for(int j = -4; j < 4; j++)
						{
							Location loc = new Location(world, x + i, y - 4, z + j);
							Material block = world.getBlockAt(loc).getType();
							if(block.equals(Material.DIAMOND) || block.equals(Material.EMERALD_BLOCK) || block.equals(Material.IRON_BLOCK) || block.equals(Material.GOLD_BLOCK))
								lvl4++;
						}
					}
					if(lvl4 == 81)
					{
						radius = 300;
					}
				}
			}
		}
		
		return radius;
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
	 * Initialize inventory using a pre-existing saved inventory copy
	 */
	private void initializeInventory(Inventory inventory) 
	{
		this.inventory = inventory;
	}
	
	/**
	 * Initialize a brand new inventory for beacon
	 */
	private void initializeInventory()
	{
		inventory = Bukkit.getServer().createInventory(null, inventoryRows*9, "Beacon Inventory");
	}

	/**
	 * Opens the beacon inventory for given player
	 */
	public void openInventory(Player player) 
	{
		if (inventory != null)
			player.openInventory(inventory);
	}
	
	/**
	 * Attempts to remove a specific material of given amount from Beacon inventory
	 */
	public boolean removeMaterial(int amount, Material material)
	{
		HashMap<Integer,? extends ItemStack> inventoryMaterials = inventory.all(material);
		
		int materialsToRemove = amount;
		for(Entry<Integer,? extends ItemStack> entry : inventoryMaterials.entrySet())
		{
			if (materialsToRemove <= 0)
				break;
			
			if(entry.getValue().getAmount() == materialsToRemove)
			{
				inventory.setItem(entry.getKey(), new ItemStack(Material.AIR, 0));
				materialsToRemove = 0;
			}
			else if(entry.getValue().getAmount() > materialsToRemove)
			{
				inventory.setItem(entry.getKey(), new ItemStack(material, (entry.getValue().getAmount() - materialsToRemove)));
				materialsToRemove = 0;
			}
			else
			{
				int inStack = entry.getValue().getAmount();
				inventory.setItem(entry.getKey(), new ItemStack(Material.AIR, 0));
				materialsToRemove -= inStack;
			}
		}
		
		return materialsToRemove == 0;
	}

	/**
	 * Checks if players are in range, applies effects to them
	 * @param players
	 */
	public void onUpdate(List<Player> players)
	{
		for (Player player : players) {
			if(isInRange(player))
			{
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
	private Inventory inventory;
	private Integer xMin;
	private Integer xMax;
	private Integer yMin;
	private Integer yMax;
	private Integer zMin;
	private Integer zMax;

	//TODO: Change these values to config loading values!
	private final int inventoryRows = 3;
}
