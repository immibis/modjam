package com.immibis.modjam3;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
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

	public static boolean connects(IBlockAccess w, int x, int y, int z) {
		int id = w.getBlockId(x, y, z);
		if(id == Modjam3Mod.blockChickenPipe.blockID)
			return true;
		
		if(w.getBlockTileEntity(x, y, z) instanceof IInventory)
			return true;
		
		return false;
	}
}
