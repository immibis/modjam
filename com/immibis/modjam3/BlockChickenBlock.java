package com.immibis.modjam3;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockChickenBlock extends Block {
	public BlockChickenBlock(int id) {
		super(id, Material.iron);
		
		setCreativeTab(CreativeTabs.tabBlock);
		setUnlocalizedName("immibis_modjam3.chickenblock");
		setTextureName("immibis_modjam3:chicken_block");
		setHardness(3.0F);
		setResistance(10.0F);
		setStepSound(soundMetalFootstep);
	}
	
	@Override
	public void onNeighborBlockChange(World w, int x, int y, int z, int par5) {
		int oldMeta = w.getBlockMetadata(x, y, z);
		boolean wasPowered = (oldMeta & 1) == 1;
		
		if(wasPowered != (w.getBlockPowerInput(x, y, z) > 0)) {
			w.setBlockMetadataWithNotify(x, y, z, oldMeta ^ 1, 0);
			
			if(!wasPowered) {
				w.addBlockEvent(x, y, z, blockID, 0, 0);
			}
		}
	}
	
	@Override
	public boolean onBlockEventReceived(World w, int x, int y, int z, int par5, int par6) {
		w.spawnParticle("note", x + 0.5, y + 1.2D, z + 0.5D, 0 /* note number */, 0, 0);
		w.playSound(x + 0.5, y + 0.5, z + 0.5, "mob.chicken.hurt", 1.0f, (w.rand.nextFloat() - w.rand.nextFloat()) * 0.2f + 1.0f, false);
		return true;
	}
	
}
