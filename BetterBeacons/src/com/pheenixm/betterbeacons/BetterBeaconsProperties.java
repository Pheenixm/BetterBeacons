package com.pheenixm.betterbeacons;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.untamedears.citadel.Citadel;
import com.untamedears.citadel.entity.Faction;

/**
 *
 * @author Pheenixm
 */
public class BetterBeaconsProperties 
{
	private final Material fuel_material; //The type of fuel consumed
	private final int fuel_amount; //The amount of fuel consumed per mining operation
    private final int radius; //Beacons effect radius
    private final Faction owning_faction; // Owning Citadel group

	/**
	 * Constructor
	 */
	public BetterBeaconsProperties(String faction, int radius, int fuel_amount, Material fuel_material)
	{
        this.owning_faction = Citadel.getGroupManager().getGroup(faction);
        this.radius = radius;
		this.fuel_amount = fuel_amount;
		this.fuel_material = fuel_material;
	}

    public boolean usePositiveEffect(Player player) {
        return usePositiveEffect(player.getDisplayName());
    }

    public boolean usePositiveEffect(String name) {
        return name.equals(this.owning_faction.getFounder()) || this.owning_faction.isMember(name) || this.owning_faction.isModerator(name);
    }

	/*
	 ----------PUBLIC ACCESSORS--------
	 */
	
	
	/**
	 * 'fuel_amount' public accessor
	 */
	public int getFuelAmount()
	{
		return fuel_amount;
	}
	
	/**
	 * 'fuel_material' public accessor
	 */
	public Material getFuelMaterial()
	{
		return fuel_material;
	}

	/**
	 * 'radius' public accessor
	 */
    public int getRadius() {
        return radius;
    }

	/**
	 * 'owning_faction' public accessor
	 */
    public Faction getOwningFaction() {
        return owning_faction;
    }

}
