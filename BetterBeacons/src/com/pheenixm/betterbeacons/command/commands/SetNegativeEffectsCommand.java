package com.pheenixm.betterbeacons.command.commands;

import static com.untamedears.citadel.Utility.sendMessage;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.pheenixm.betterbeacons.BetterBeacons;
import com.pheenixm.betterbeacons.BetterBeaconsPlugin;
import com.pheenixm.betterbeacons.BetterBeaconsProperties;
import com.pheenixm.betterbeacons.command.CommandUtils;
import com.pheenixm.betterbeacons.command.PlayerCommand;

public class SetNegativeEffectsCommand extends PlayerCommand {
	
	private BetterBeaconsPlugin instance;

	public SetNegativeEffectsCommand(BetterBeaconsPlugin plug) {
		super("Set Negative Effects");
		instance = plug;
		setDescription("Sets negative effects of a beacon");
		setUsage("/bbsetnegative §8<effect-name>");
		setIdentifiers(new String[] {"bbsetnegative", "bbsn"});
	}

	public boolean execute(CommandSender sender, String[] args) {
		if(!(sender instanceof Player)){
			return true;
		}
		String newEffect = args[0];
		Block block = CommandUtils.getBlockLookingAt(sender);
        if (block == null || !block.getType().equals(Material.BEACON))
        {
        	CommandUtils.sendMessage(sender, ChatColor.RED, "You are not looking at a beacon");
        	return true;
        }
        BetterBeacons beacon = instance.getManager().get(block);
        BetterBeaconsProperties oldProp = beacon.getProperties();
        ArrayList<PotionEffect> effects = (ArrayList<PotionEffect>) oldProp.getNegativeEffects();
        
        PotionEffect potEffect = null;
        //TODO: Base the strength of the potion effect on the material that they pyramid is made of
        if(newEffect.equalsIgnoreCase("weakness"))
        {
        	potEffect = new PotionEffect(PotionEffectType.WEAKNESS, 3, 1, true);
        	
        }
        else
        {
        	if(newEffect.equalsIgnoreCase("poison") || newEffect.equalsIgnoreCase("p"))
        	{
        		potEffect = new PotionEffect(PotionEffectType.POISON, 3, 1, true);
        	}
        	else
        	{
            	CommandUtils.sendMessage(sender, ChatColor.RED, "You must use a valid negative potion effect. To view valid effects, enter /bbla");
        	}
        }
        if(potEffect != null)
        {
        	effects.add(potEffect);
            BetterBeaconsProperties newProp = new BetterBeaconsProperties(
            		oldProp.getOwningFaction().getName(),
            		oldProp.getRadius(),
            		oldProp.getFuelAmount(),
            		oldProp.getFuelMaterial(),
            		oldProp.getPositiveEffects(),
            		effects
            		);
            
            beacon.setProperties(newProp);

        }
        
        return true;
	}

}
