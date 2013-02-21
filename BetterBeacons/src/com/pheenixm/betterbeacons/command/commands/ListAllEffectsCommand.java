package com.pheenixm.betterbeacons.command.commands;

import static com.untamedears.citadel.Utility.sendMessage;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.pheenixm.betterbeacons.BetterBeacons;
import com.pheenixm.betterbeacons.BetterBeaconsPlugin;
import com.pheenixm.betterbeacons.command.CommandUtils;
import com.pheenixm.betterbeacons.command.PlayerCommand;

public class ListAllEffectsCommand extends PlayerCommand {
	
	private BetterBeaconsPlugin instance;

	public ListAllEffectsCommand(BetterBeaconsPlugin plug) {
		super("List All Available Effects");
		instance = plug;
		setDescription("Lists all available effects for a beacon");
		setUsage("/bblistall");
		setIdentifiers(new String[] {"bblistnegatives", "bbla"});
	}

	public boolean execute(CommandSender sender, String[] args) {
        sender.sendMessage(new StringBuilder().append("�Available Positive Effects for All Beacons Are:§e ").toString());
        //TODO: Add More Effects to this in general
        String[] posEffects = {"Strength", "Regeneration"};
        for(String effect : posEffects)
        {
        	sender.sendMessage(new StringBuilder().append("§c ").append(effect).toString());
        }
        
        sender.sendMessage(new StringBuilder().append("�Available Negative Effects for All Beacons Are:§e ").toString());
        //TODO: Add More Effects to this in general
        String[] negEffects = {"Weakness", "Poison"};
        for(String effect : negEffects)
        {
        	sender.sendMessage(new StringBuilder().append("§c ").append(effect).toString());
        }

        return true;
	}

}
