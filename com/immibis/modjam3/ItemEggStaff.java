package com.immibis.modjam3;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class ItemEggStaff extends Item {
	public ItemEggStaff(int id) {
		super(id);
		
		setCreativeTab(CreativeTabs.tabMaterials);
		setTextureName("immibis_modjam3:eggstaff");
		setUnlocalizedName("immibis_modjam3.eggstaff");
		setMaxStackSize(1);
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		
		if(!par2World.isRemote) {
			par2World.playSoundAtEntity(par3EntityPlayer, "mob.chicken.say", 0.5F, 4.0f * itemRand.nextFloat());
            par2World.spawnEntityInWorld(new EntityEgg(par2World, par3EntityPlayer) {
            	protected void onImpact(net.minecraft.util.MovingObjectPosition par1MovingObjectPosition) {
            		if (par1MovingObjectPosition.entityHit != null)
                        par1MovingObjectPosition.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.0F);
                    
                    for (int j = 0; j < 8; ++j)
                        this.worldObj.spawnParticle("snowballpoof", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
                    
                    if (!this.worldObj.isRemote)
                        this.setDead();
            	}
            });
		}
        
		return par1ItemStack;
	}
}
