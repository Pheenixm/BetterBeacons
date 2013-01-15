package com.pheenixm.betterbeacons;

import org.bukkit.Material;

/**
 *
 * @author Pheenixm
 */
public class BetterBeaconsProperties 
{
	private final Material fuel_material; //The type of fuel consumed
	private final int fuel_amount; //The amount of fuel consumed per mining operation

	/**
	 * Constructor
	 */
	public BetterBeaconsProperties(int fuel_amount, Material fuel_material)
	{
		this.fuel_amount = fuel_amount;
		this.fuel_material = fuel_material;
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
	

}
