package com.immibis.modjam3;

import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockEnderChest;

public class BlockIChest extends BlockEnderChest {
	public BlockIChest(int id) {
		super(id);
		
		setUnlocalizedName("immibis_modjam3.ichest");
		setStepSound(soundStoneFootstep);
		setHardness(3.5F);
		setResistance(1000.0F);
	}
}
