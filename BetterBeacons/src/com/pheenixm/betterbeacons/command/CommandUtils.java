package com.pheenixm.betterbeacons.command;

import java.util.HashMap;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.fusesource.jansi.Ansi.Color;

import com.untamedears.citadel.Citadel;
import com.untamedears.citadel.GroupManager;
import com.untamedears.citadel.MemberManager;
import com.untamedears.citadel.ReinforcementManager;
import com.untamedears.citadel.entity.Faction;
import com.untamedears.citadel.entity.FactionMember;
import com.untamedears.citadel.entity.IReinforcement;
import com.untamedears.citadel.entity.Moderator;
import com.untamedears.citadel.entity.PlayerReinforcement;

public final class CommandUtils {
	
	public static Block getBlockLookingAt(CommandSender sender)
	{
        Player player = getPlayer(sender);

        if(player == null)
        {
        	sendMessage(sender, ChatColor.RED, "You are not a player");
        	return null;
        }
        int reachDistance = 4;
        if(player.getGameMode().equals(GameMode.CREATIVE))
        {
        	reachDistance = 5;
        }
        Block target = player.getTargetBlock(null, reachDistance);
        
        if(target != null)
        {
        	return target;
        }
        else
        {
        	return null;
        }
	}
	
	public static Player getPlayer(CommandSender sender)
	{
        if (sender instanceof Player)
        {
            return (Player) sender;
        }
        return null;
	}
	
    public static void sendMessage(CommandSender sender, ChatColor color, String messageFormat, Object... params) {
        sender.sendMessage(color + String.format(messageFormat, params));
    }


}
