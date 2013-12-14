package com.immibis.modjam3;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;

public class ItemChickaxe extends ItemPickaxe {
	public ItemChickaxe(int id) {
		super(id, Modjam3Mod.toolMaterialChicken);
		
		setMaxDamage(500);
		setCreativeTab(CreativeTabs.tabTools);
		setTextureName("immibis_modjam3:chickaxe");
		setUnlocalizedName("immibis_modjam3.pickaxe");
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		if(!player.worldObj.isRemote)
			player.worldObj.playSoundAtEntity(player, "mob.chicken.hurt", 1, (entity.worldObj.rand.nextFloat() - entity.worldObj.rand.nextFloat()) * 0.2F + 1.0F);
		return true;
	}
}
