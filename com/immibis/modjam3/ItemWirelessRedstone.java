package com.immibis.modjam3;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemWirelessRedstone extends Item {
	public ItemWirelessRedstone(int id) {
		super(id);
		
		setUnlocalizedName("immibis_modjam3.wirelessredstone");
		setTextureName("redstone_dust");
		setCreativeTab(CreativeTabs.tabRedstone);
	}
}
