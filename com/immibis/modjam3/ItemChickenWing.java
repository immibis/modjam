package com.immibis.modjam3;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemChickenWing extends Item {
	public ItemChickenWing() {
		super();
		
		setCreativeTab(CreativeTabs.tabTools);
		
		if(!Modjam3Mod.MODJAM)
			setTextureName("immibis_modjam3:chickenwing"); // texture by minerdave, so not acceptable for modjam
		else
			setTextureName("immibis_modjam3:chickenwing2");
		
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
		
		//if(player.motionY >= 0) player.fallDistance = 0;
		
		final double gravity = 0.08;
		// if the player slows down while falling, decrease their fall distance to be equivalent to their velocity
		// v = a*t
		// t = v/a
		// d = 0.5*a*t^2
		// d = 0.5*a*(v/a)^2
		// d = 0.5/a*v^2
		
		player.fallDistance = Math.min(player.fallDistance, (float)Math.max(0, 0.5 / gravity * player.motionY * player.motionY));
		
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
