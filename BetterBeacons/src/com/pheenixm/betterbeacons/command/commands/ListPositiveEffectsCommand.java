package com.pheenixm.betterbeacons.command.commands;

import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.pheenixm.betterbeacons.command.PlayerCommand;

public class ListPositiveEffectsCommand extends PlayerCommand {

	public ListPositiveEffectsCommand() {
		super("List Positive Effects");
		setDescription("Lists positive effects of a beacon");
		setUsage("/bblistpositives");
		setIdentifiers(new String[] {"bblistpositives", "bblp"});
	}

	public boolean execute(CommandSender sender, String[] args) {
        Player player = null;

        if (sender instanceof Player)
        {
            player = (Player) sender;
        }
        int reachDistance = 4;
        if(player.getGameMode().equals(GameMode.CREATIVE))
        {
        	reachDistance = 
        }
        player.getTargetBlock(null, )
        return false;
	}

}
