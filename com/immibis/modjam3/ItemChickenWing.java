package com.immibis.modjam3;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
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
		setMaxDamage(3000);
	}
	
	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		if(!entityLiving.isSwingInProgress && entityLiving instanceof EntityPlayer)
			zoom((EntityPlayer)entityLiving, stack, -1);
		return false;
	}
	
	private void zoom(EntityPlayer player, ItemStack par1ItemStack, int mul) {
		double speed = 1.5 * mul;
		
		Vec3 vec = player.getLookVec();
		vec.xCoord *= speed;
		vec.yCoord *= speed;
		vec.zCoord *= speed;
		
		player.motionX = adjustSpeed(player.motionX, vec.xCoord);
		player.motionY = adjustSpeed(player.motionY, vec.yCoord);
		player.motionZ = adjustSpeed(player.motionZ, vec.zCoord);
		if(player.motionY >= 0)
			player.fallDistance = 0;
		
		par1ItemStack.damageItem(1, player);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		zoom(par3EntityPlayer, par1ItemStack, 1);
		return par1ItemStack;
	}

	private double adjustSpeed(double prev, double add) {
		return prev * 0.8 + add * 0.5;
	}
}
