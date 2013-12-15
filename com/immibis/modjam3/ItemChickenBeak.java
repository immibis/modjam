package com.immibis.modjam3;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemChickenBeak extends Item {
	public ItemChickenBeak(int id) {
		super(id);
		
		setUnlocalizedName("immibis_modjam3.chickenbeak");
		setTextureName("immibis_modjam3:chickenbeak");
		setCreativeTab(CreativeTabs.tabTools);
		setMaxStackSize(1);
	}
}
