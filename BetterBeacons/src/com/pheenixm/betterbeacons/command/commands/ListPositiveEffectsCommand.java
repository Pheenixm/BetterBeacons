package com.pheenixm.betterbeacons.command.commands;

import static com.untamedears.citadel.Utility.sendMessage;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.potion.PotionEffect;

import com.pheenixm.betterbeacons.BetterBeacons;
import com.pheenixm.betterbeacons.BetterBeaconsPlugin;
import com.pheenixm.betterbeacons.command.CommandUtils;
import com.pheenixm.betterbeacons.command.PlayerCommand;

public class ListPositiveEffectsCommand extends PlayerCommand {
	
	private BetterBeaconsPlugin instance;

	public ListPositiveEffectsCommand(BetterBeaconsPlugin plug) {
		super("List Positive Effects");
		instance = plug;
		setDescription("Lists positive effects of a beacon");
		setUsage("/bblistpositives");
		setIdentifiers(new String[] {"bblistpositives", "bblp"});
	}

	public boolean execute(CommandSender sender, String[] args) {
		Block block = CommandUtils.getBlockLookingAt(sender);
        if (block == null || !block.getType().equals(Material.BEACON))
        {
        	CommandUtils.sendMessage(sender, ChatColor.RED, "You are not looking at a beacon");
        	return true;
        }
        BetterBeacons beacon = instance.getManager().get(block);
        sender.sendMessage(new StringBuilder().append("§cPositive Effects for this Beacon Are:§e ").toString());
        for(PotionEffect effect : beacon.getProperties().getPositiveEffects())
        {
        	sender.sendMessage(new StringBuilder().append("§c ").append(effect.getType().getName()).toString());
        }
        return true;
	}

}
