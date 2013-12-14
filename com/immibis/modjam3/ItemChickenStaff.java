package com.immibis.modjam3;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemChickenStaff extends Item {
	public ItemChickenStaff(int id) {
		super(id);
		
		setUnlocalizedName("immibis_modjam3.chickenstaff");
		setTextureName("immibis_modjam3:chickenstaff");
		setCreativeTab(CreativeTabs.tabTools);
	}

}
