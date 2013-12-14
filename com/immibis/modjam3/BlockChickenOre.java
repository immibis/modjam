package com.immibis.modjam3;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockChickenOre extends Block {
	public BlockChickenOre(int id) {
		super(id, Material.rock);
		
		setCreativeTab(CreativeTabs.tabBlock);
		setUnlocalizedName("immibis_modjam3.chickenore");
		setTextureName("immibis_modjam3:chicken_ore");
		setHardness(3.0F);
		setResistance(5.0F);
		setStepSound(soundStoneFootstep);
	}
	
	@Override
	public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7) {
		if (par1World.rand.nextFloat() < par6 && !par1World.isRemote && par1World.getGameRules().getGameRuleBooleanValue("doTileDrops"))
        {
            EntityChicken chicken = new EntityChicken(par1World);
            chicken.setPosition(par2 + 0.5, par3, par4 + 0.5);
            par1World.spawnEntityInWorld(chicken);
            par1World.playSoundAtEntity(chicken, "mob.chicken.say", 1, 1);
            
            Entity closestEntity = chicken.worldObj.getClosestPlayerToEntity(chicken, 16);
            if(closestEntity != null)
            	chicken.getLookHelper().setLookPosition(closestEntity.posX, closestEntity.posY + (double)closestEntity.getEyeHeight(), closestEntity.posZ, 10.0F, (float)chicken.getVerticalFaceSpeed());
        }
	}
}
