package com.immibis.modjam3;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemChickenWing extends Item {
	public ItemChickenWing(int id) {
		super(id);
		
		setCreativeTab(CreativeTabs.tabTools);
		setTextureName("immibis_modjam3:chickenwing");
		setUnlocalizedName("immibis_modjam3.chickenwing");
		setMaxStackSize(1);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		
		double speed = 1.5;
		
		Vec3 vec = par3EntityPlayer.getLookVec();
		vec.xCoord *= speed;
		vec.yCoord *= speed;
		vec.zCoord *= speed;
		
		par3EntityPlayer.motionX = clampSpeed(par3EntityPlayer.motionX, vec.xCoord);
		par3EntityPlayer.motionY = clampSpeed(par3EntityPlayer.motionY, vec.yCoord);
		par3EntityPlayer.motionZ = clampSpeed(par3EntityPlayer.motionZ, vec.zCoord);
		
		return par1ItemStack;
	}

	private double clampSpeed(double prev, double add) {
		return prev * 0.8 + add * 0.5;
	}
}
