package com.pheenixm.betterbeacons.command.commands;

import org.bukkit.command.CommandSender;

import com.pheenixm.betterbeacons.command.PlayerCommand;

public class ListPositiveEffectsCommand extends PlayerCommand {

	public ListPositiveEffectsCommand() {
		super("List Positive Effects");
		setDescription("Lists positive effects of a beacon");
		setUsage("/bblistpositives");
		setIdentifiers(new String[] {"bblistpositives", "bblp"});
	}

	public boolean execute(CommandSender sender, String[] args) {
		
		return false;
	}

}
