package com.immibis.modjam3;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemEggBomb extends ItemEgg {
	public ItemEggBomb() {
		super();
		setUnlocalizedName("immibis_modjam3.eggbomb");
		setTextureName("egg");
	}
	
	public static final float EXPLOSION_POWER = 2.0f;
	
	public static class EntityEggBomb extends EntityEgg {
		public EntityEggBomb(World par1World) {
			super(par1World);
		}

		public EntityEggBomb(World world, EntityPlayer thrower) {
			super(world, thrower);
		}
		
		@Override
		protected void onImpact(MovingObjectPosition par1MovingObjectPosition)
	    {
	        if (!this.worldObj.isRemote)
	        {
	        	worldObj.newExplosion(this, posX, posY, posZ, EXPLOSION_POWER, false, true);
	            this.setDead();
	        }
	    }
	
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        if (!par3EntityPlayer.capabilities.isCreativeMode)
        {
            --par1ItemStack.stackSize;
        }

        par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!par2World.isRemote)
        {
            par2World.spawnEntityInWorld(new EntityEggBomb(par2World, par3EntityPlayer));
        }

        return par1ItemStack;
    }

	public static void explode(Entity player) {
		player.worldObj.newExplosion(null, player.posX, player.posY, player.posZ, EXPLOSION_POWER, false, true);
	}
}
