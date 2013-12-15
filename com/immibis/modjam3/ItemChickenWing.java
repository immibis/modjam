package com.immibis.modjam3;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemChickenWing extends Item {
	public ItemChickenWing(int id) {
		super(id);
		
		setCreativeTab(CreativeTabs.tabTools);
		setTextureName("immibis_modjam3:chickenwing");
		setUnlocalizedName("immibis_modjam3.chickenwing");
		setMaxStackSize(1);
	}
}
