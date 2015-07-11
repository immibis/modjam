package com.immibis.modjam3;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemChickenStaff extends Item {
	public ItemChickenStaff() {
		super();
		
		setUnlocalizedName("immibis_modjam3.chickenstaff");
		setTextureName("immibis_modjam3:chickenstaff");
		setCreativeTab(CreativeTabs.tabTools);
		setMaxStackSize(1);
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		if(entity instanceof EntityChicken) {
			if(!entity.worldObj.isRemote) {
				entity.setDead();
				entity.worldObj.spawnEntityInWorld(new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, new ItemStack(Modjam3Mod.itemChicken)));
			}
			return true;
		}
		return super.onLeftClickEntity(stack, player, entity);
	}

}
