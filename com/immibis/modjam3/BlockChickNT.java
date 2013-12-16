package com.immibis.modjam3;

import java.util.Random;

import net.minecraft.block.BlockTNT;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockChickNT extends BlockTNT {
    public BlockChickNT(int par1) {
        super(par1);
        
        setTextureName("tnt");
        setUnlocalizedName("immibis_modjam3.tnt");
    }
    
    private static class EntityChickNTPrimed extends EntityTNTPrimed {
    	public EntityChickNTPrimed(World w) {
    		super(w);
    	}
    	
    	public EntityChickNTPrimed(World w, double x, double y, double z, EntityLivingBase owner) {
    		super(w, x, y, z, owner);
    	}
    	
    	@Override
    	public void onUpdate() {
    		super.onUpdate();
    		
    		if(fuse < 0) {
    			for(int k = 0; k < 8; k++) {
    				EntityChicken c = new EntityChicken(worldObj);
    				c.setPosition(posX, posY, posZ);
    				c.motionX = worldObj.rand.nextDouble() - worldObj.rand.nextDouble();
    				c.motionY = worldObj.rand.nextDouble() - worldObj.rand.nextDouble();
    				c.motionZ = worldObj.rand.nextDouble() - worldObj.rand.nextDouble();
    				worldObj.spawnEntityInWorld(c);
    			}
    		}
    	}
    }

    public void primeTnt(World par1World, int par2, int par3, int par4, int par5, EntityLivingBase par6EntityLivingBase)
    {
        if (!par1World.isRemote)
        {
            if ((par5 & 1) == 1)
            {
                EntityTNTPrimed entitytntprimed = new EntityChickNTPrimed(par1World, (double)((float)par2 + 0.5F), (double)((float)par3 + 0.5F), (double)((float)par4 + 0.5F), par6EntityLivingBase);
                par1World.spawnEntityInWorld(entitytntprimed);
                par1World.playSoundAtEntity(entitytntprimed, "random.fuse", 1.0F, 1.0F);
            }
        }
    }
    
    @Override
    public void onBlockDestroyedByExplosion(World par1World, int par2, int par3, int par4, Explosion par5Explosion)
    {
        if (!par1World.isRemote)
        {
            EntityTNTPrimed entitytntprimed = new EntityChickNTPrimed(par1World, (double)((float)par2 + 0.5F), (double)((float)par3 + 0.5F), (double)((float)par4 + 0.5F), par5Explosion.getExplosivePlacedBy());
            entitytntprimed.fuse = par1World.rand.nextInt(entitytntprimed.fuse / 4) + entitytntprimed.fuse / 8;
            par1World.spawnEntityInWorld(entitytntprimed);
        }
    }
}