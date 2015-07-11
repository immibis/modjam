package com.immibis.modjam3;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockChickenBlock extends Block {
	public BlockChickenBlock() {
		super(Material.iron);
		
		setCreativeTab(CreativeTabs.tabBlock);
		setBlockName("immibis_modjam3.chickenblock");
		setBlockTextureName("immibis_modjam3:chicken_block");
		setHardness(3.0F);
		setResistance(10.0F);
		setStepSound(soundTypeMetal);
	}
	
	@Override
	public void onNeighborBlockChange(World w, int x, int y, int z, Block par5) {
		int oldMeta = w.getBlockMetadata(x, y, z);
		boolean wasPowered = (oldMeta & 1) == 1;
		
		if(wasPowered != (w.getBlockPowerInput(x, y, z) > 0)) {
			w.setBlockMetadataWithNotify(x, y, z, oldMeta ^ 1, 0);
			
			if(!wasPowered) {
				w.addBlockEvent(x, y, z, this, 0, 0);
			}
		}
	}
	
	private static String[] names = {
		"mob.chicken.hurt", "mob.chicken.hurt", "mob.chicken.hurt", "mob.chicken.hurt",
		"mob.chicken.say","mob.chicken.say",
		"immibis_modjam3:ichest.hurt2_50percent",
		"immibis_modjam3:ichest.spindown",
	};
	private static float[] pitches = {
		1.0f, 1.2f, 1.4f, 0.8f,
		1.0f, 1.4f,
		1.0f,
		1.0f
	};
	
	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
		if(w.isRemote)
			return true;
		
		w.setBlockMetadataWithNotify(x, y, z, (w.getBlockMetadata(x, y, z) + 2) & 15, 3);
		w.addBlockEvent(x, y, z, this, 0, 0);
		return true;
	}
	
	@Override
	public boolean onBlockEventReceived(World w, int x, int y, int z, int par5, int par6) {
		int soundNum = w.getBlockMetadata(x, y, z) >> 1;
		w.spawnParticle("note", x + 0.5, y + 1.2D, z + 0.5D, soundNum / 7f, 0, 0);
		w.playSound(x + 0.5, y + 0.5, z + 0.5, names[soundNum], 1.0f, ((w.rand.nextFloat() - w.rand.nextFloat()) * 0.2f + 1.0f) * pitches[soundNum], false);
		return true;
	}
	
}
