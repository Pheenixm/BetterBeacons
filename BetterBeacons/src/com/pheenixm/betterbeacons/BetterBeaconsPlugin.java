package com.pheenixm.betterbeacons;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import com.pheenixm.betterbeacons.command.commands.ListAllEffectsCommand;
import com.pheenixm.betterbeacons.command.commands.ListNegativeEffectsCommand;
import com.pheenixm.betterbeacons.command.commands.ListPositiveEffectsCommand;
import com.pheenixm.betterbeacons.command.commands.SetNegativeEffectsCommand;
import com.pheenixm.betterbeacons.command.commands.SetPositiveEffectsCommand;
import com.pheenixm.betterbeacons.command.CommandHandler;

public class BetterBeaconsPlugin extends JavaPlugin 
{

	private BetterBeaconsManager manager; //The BetterBeacons manager
	public static Logger log;
	public static HashMap<Integer,BetterBeaconsProperties> Better_Beacons_Properties; //Map of properties for all tiers
    private static final com.pheenixm.betterbeacons.command.CommandHandler commandHandler = new com.pheenixm.betterbeacons.command.CommandHandler();

    public static final String PLUGIN_NAME = "BetterBeacons";
    public static final String VERSION = "0.1";
    public static final String PLUGIN_PREFIX = PLUGIN_NAME + " " + VERSION + ": "; 
    public static final String BETTER_BEACONS_SAVES_DIRECTORY = "BetterBeacons";
    public static final int TICKS_PER_SECOND = 20; 

	public static int UPDATE_CYCLE; //Update time in ticks
	public static int SAVE_CYCLE; //The time between periodic saves in minutes
	public static boolean CITADEL_ENABLED; //Whether the plugin 'Citadel' is enabled on this server

	public static final String CITADEL_NAME = "Citadel"; //The plugin name for 'Citadel'

    
    public void onEnable() 
    {
    	log = this.getLogger();
    	initializeBetterBeaconsProperties();
		if(properPluginsLoaded())
		{
			log.info(PLUGIN_NAME+" v"+VERSION+" enabled!");
			getConfig().options().copyDefaults(true);
			manager = new BetterBeaconsManager(this);
	    	registerCommands();
	    	BetterBeaconsIterator iter = new BetterBeaconsIterator(this);
	    	BukkitTask task = iter.runTaskTimer(this, 1L, 1L);
		}
		else
		{
		BetterBeaconsPlugin.sendConsoleMessage("The Citadel config value is not correct for loaded plugins! Disabling OreGin now!");
		getServer().getPluginManager().disablePlugin(this);
		}

    }
    
	public void onDisable() 
	{
		//TODO: NullPointerException here, probably with log not being set
		//log.info(PLUGIN_NAME+" v"+VERSION+" disabled!");
	}
	
	public void registerCommands()
	{
		commandHandler.addCommand(new ListPositiveEffectsCommand(this));
		commandHandler.addCommand(new ListNegativeEffectsCommand(this));
		commandHandler.addCommand(new ListAllEffectsCommand(this));
		commandHandler.addCommand(new SetNegativeEffectsCommand(this));
		commandHandler.addCommand(new SetPositiveEffectsCommand(this));
	}

	/**
	 * Initializes the default BetterBeaconsProperties from config
	 */
	@SuppressWarnings("unchecked")
	public void initializeBetterBeaconsProperties()
	{
		Better_Beacons_Properties = new HashMap<Integer,BetterBeaconsProperties>();

		//Load general config values
		
		//TODO: Load properly from Config
		/*BetterBeaconsPlugin.UPDATE_CYCLE = getConfig().getInt("general.update_cycle");
		BetterBeaconsPlugin.CITADEL_ENABLED = getConfig().getBoolean("general.citadel_enabled");
		BetterBeaconsPlugin.SAVE_CYCLE = getConfig().getInt("general.save_cycle");

		Material fuel_type = Material.valueOf("fuel_type");
		int fuel_amount= getConfig().getInt("fuel_amount");*/
		
		




        // Design conflict, I added Beacon dynamic variables into Properties
        //  and made the Beacon class itself immutable except for its assigned
        //  properties. You'll have to decide how to refractor this for your
        //  vision.



		//Better_Beacons_Properties.put(0, new BetterBeaconsProperties(null, null, fuel_amount, fuel_type, null, null));
		
		BetterBeaconsPlugin.sendConsoleMessage("Config values successfully loaded!");
		saveConfig();
	}
	
	/**
	 * Sets up scheduling for the ticking
	 */
	/*//TODO: NullPointerException occuring, should fix
	BukkitTask task = getServer().getScheduler().runTaskTimerAsynchronously(this, new Runnable() {
	    @Override  
	    public void run() {
	    	manager.iterate();
	    }
	}, 1L, 1L);*/

	
	/**
	 * Returns the BetterBeacons Saves file
	 */
	public File getBetterBeaconsSavesFile()
	{
		return new File(getDataFolder(), BETTER_BEACONS_SAVES_DIRECTORY + ".txt");
	}

	/**
	 * Returns whether the proper plugins are loaded based on config values
	 */
	public boolean properPluginsLoaded()
	{
		//TODO: Set up config properly
		return ( (getServer().getPluginManager().getPlugin(CITADEL_NAME) != null && BetterBeaconsPlugin.CITADEL_ENABLED)
				|| (getServer().getPluginManager().getPlugin(CITADEL_NAME) == null && !BetterBeaconsPlugin.CITADEL_ENABLED));
	}


	/**
	 * Sends a message to the console with appropriate prefix
	 */
	public static void sendConsoleMessage(String message)
	{
		Bukkit.getLogger().info(BetterBeaconsPlugin.PLUGIN_PREFIX + message);
	}
	
	public BetterBeaconsManager getManager()
	{
		return manager;
	}

}
