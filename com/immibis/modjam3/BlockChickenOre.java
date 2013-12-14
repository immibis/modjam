package com.immibis.modjam3;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
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
	}
	
	@Override
	public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7) {
		if (par1World.rand.nextFloat() < par6 && !par1World.isRemote && par1World.getGameRules().getGameRuleBooleanValue("doTileDrops"))
        {
            float f = 0.7F;
            double d0 = (double)(par1World.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double d1 = (double)(par1World.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            double d2 = (double)(par1World.rand.nextFloat() * f) + (double)(1.0F - f) * 0.5D;
            
            EntityChicken chicken = new EntityChicken(par1World);
            chicken.setPosition(par2 + 0.5, par3, par4 + 0.5);
            chicken.motionX = d0;
            chicken.motionY = d1;
            chicken.motionZ = d2;
            par1World.spawnEntityInWorld(chicken);
        }
	}
}
