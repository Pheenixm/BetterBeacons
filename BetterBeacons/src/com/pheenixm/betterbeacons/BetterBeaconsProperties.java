package com.pheenixm.betterbeacons;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

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
	private final double radius_squared; //Beacons effect square radius
	private final Faction owning_faction; // Owning Citadel group
	private final List<PotionEffect> positiveEffects;
	private final List<PotionEffect> negativeEffects;

	/**
	 * Constructor
	 */
	public BetterBeaconsProperties(String faction, Integer radius, int fuel_amount, Material fuel_material, List<PotionEffect> positive, List<PotionEffect> negative)
	{
		//TODO: Set properly
		this.owning_faction = Citadel.getGroupManager().getGroup(faction);
		
		this.radius = radius;
		this.radius_squared = ((double)radius * (double)radius);
		this.fuel_amount = fuel_amount;
		this.fuel_material = fuel_material;
		this.positiveEffects = positive;
		this.negativeEffects = negative;
	}

	public boolean usePositiveEffect(Player player) {
		return usePositiveEffect(player.getDisplayName());
	}

	public boolean usePositiveEffect(String name) {
		if(owning_faction != null)
			return name.equals(this.owning_faction.getFounder()) || this.owning_faction.isMember(name) || this.owning_faction.isModerator(name);
		else
			return false;
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
	 * 'radius_squared' public accessor
	 */
	public double getRadiusSquared() {
		return radius_squared;
	}

	/**
	 * 'owning_faction' public accessor
	 */
	public Faction getOwningFaction() {
		return owning_faction;
	}

	/**
	 * 'positiveEffects' public accessor
	 */
	public List<PotionEffect> getPositiveEffects(){
		return positiveEffects;
	}

	/**
	 * 'negativeEffects' public accessor
	 */
	public List<PotionEffect> getNegativeEffects(){
		return negativeEffects;
	}
}
