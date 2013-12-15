package com.immibis.modjam3;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BlockChickenPipeRender implements ISimpleBlockRenderingHandler {
	@Override
	public int getRenderId() {
		return BlockChickenPipe.model;
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		// TODO Auto-generated method stub
		
	}
	
	private static final double min = 5.5/16f, max = 10.5/16f;
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		boolean nx = BlockChickenPipe.connects(world, x-1, y, z);
		boolean px = BlockChickenPipe.connects(world, x+1, y, z);
		boolean ny = BlockChickenPipe.connects(world, x, y-1, z);
		boolean py = BlockChickenPipe.connects(world, x, y+1, z);
		boolean nz = BlockChickenPipe.connects(world, x, y, z-1);
		boolean pz = BlockChickenPipe.connects(world, x, y, z+1);
		
		if(!nx && !px && !ny && !py && !nz && !pz) {
			renderer.setRenderBounds(min, min, min, max, max, max);
			renderer.renderStandardBlock(block, x, y, z);
		}
		
		if(nx || px) {
			renderer.setRenderBounds(nx ? 0 : min, min, min, px ? 1 : max, max, max);
			renderer.renderStandardBlock(block, x, y, z);
		}
		
		if(ny || py) {
			renderer.setRenderBounds(min, ny ? 0 : min, min, max, py ? 1 : max, max);
			renderer.renderStandardBlock(block, x, y, z);
		}
		
		if(nz || pz) {
			renderer.setRenderBounds(min, min, nz ? 0 : min, max, max, pz ? 1 : max);
			renderer.renderStandardBlock(block, x, y, z);
		}
		
		return true;
	}
	
	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}
}
