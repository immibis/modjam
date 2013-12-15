package com.immibis.modjam3;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockChickenPipe extends Block {
	
	static int model;
	
	public BlockChickenPipe(int id) {
		super(id, Material.iron);
		
		setCreativeTab(CreativeTabs.tabTransport);
		setTextureName("immibis_modjam3:chicken_pipe");
		setUnlocalizedName("immibis_modjam3:chickenpipe");
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return model;
	}
}
