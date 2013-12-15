package com.immibis.modjam3;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
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
		
		Tessellator.instance.setBrightness(world.getLightBrightnessForSkyBlocks(x, y, z, 0));
		Tessellator.instance.setColorOpaque(255, 255, 255);
		
		if(!nx && !px && !ny && !py && !nz && !pz) {
			renderer.setRenderBounds(min, min, min, max, max, max);
			renderStandardBlock(renderer, block, x, y, z);
		}
		
		if(nx || px) {
			renderer.setRenderBounds(nx ? 0 : min, min, min, px ? 1 : max, max, max);
			renderStandardBlock(renderer, block, x, y, z);
		}
		
		if(ny || py) {
			renderer.setRenderBounds(min, ny ? 0 : min, min, max, py ? 1 : max, max);
			renderStandardBlock(renderer, block, x, y, z);
		}
		
		if(nz || pz) {
			renderer.setRenderBounds(min, min, nz ? 0 : min, max, max, pz ? 1 : max);
			renderStandardBlock(renderer, block, x, y, z);
		}
		
		return true;
	}
	
	private void renderStandardBlock(RenderBlocks rb, Block block, int x, int y, int z) {
		Tessellator t = Tessellator.instance;
		Icon i = rb.hasOverrideBlockTexture() ? rb.overrideBlockTexture : Modjam3Mod.blockChickenPipe.getIcon(0, 0);
		
		if(rb.renderMinZ > 0) {
			t.addVertexWithUV(x+rb.renderMinX, y+rb.renderMaxY, z+rb.renderMinZ, i.getInterpolatedU(rb.renderMinX*16), i.getInterpolatedV(rb.renderMaxY*16));
			t.addVertexWithUV(x+rb.renderMaxX, y+rb.renderMaxY, z+rb.renderMinZ, i.getInterpolatedU(rb.renderMaxX*16), i.getInterpolatedV(rb.renderMaxY*16));
			t.addVertexWithUV(x+rb.renderMaxX, y+rb.renderMinY, z+rb.renderMinZ, i.getInterpolatedU(rb.renderMaxX*16), i.getInterpolatedV(rb.renderMinY*16));
			t.addVertexWithUV(x+rb.renderMinX, y+rb.renderMinY, z+rb.renderMinZ, i.getInterpolatedU(rb.renderMinX*16), i.getInterpolatedV(rb.renderMinY*16));
		}
		
		if(rb.renderMaxZ < 1) {
			t.addVertexWithUV(x+rb.renderMinX, y+rb.renderMinY, z+rb.renderMaxZ, i.getInterpolatedU(rb.renderMinX*16), i.getInterpolatedV(rb.renderMinY*16));
			t.addVertexWithUV(x+rb.renderMaxX, y+rb.renderMinY, z+rb.renderMaxZ, i.getInterpolatedU(rb.renderMaxX*16), i.getInterpolatedV(rb.renderMinY*16));
			t.addVertexWithUV(x+rb.renderMaxX, y+rb.renderMaxY, z+rb.renderMaxZ, i.getInterpolatedU(rb.renderMaxX*16), i.getInterpolatedV(rb.renderMaxY*16));
			t.addVertexWithUV(x+rb.renderMinX, y+rb.renderMaxY, z+rb.renderMaxZ, i.getInterpolatedU(rb.renderMinX*16), i.getInterpolatedV(rb.renderMaxY*16));
		}
		
		if(rb.renderMinY > 0) {
			t.addVertexWithUV(x+rb.renderMinX, y+rb.renderMinY, z+rb.renderMinZ, i.getInterpolatedU(rb.renderMinX*16), i.getInterpolatedV(rb.renderMinZ*16));
			t.addVertexWithUV(x+rb.renderMaxX, y+rb.renderMinY, z+rb.renderMinZ, i.getInterpolatedU(rb.renderMaxX*16), i.getInterpolatedV(rb.renderMinZ*16));
			t.addVertexWithUV(x+rb.renderMaxX, y+rb.renderMinY, z+rb.renderMaxZ, i.getInterpolatedU(rb.renderMaxX*16), i.getInterpolatedV(rb.renderMaxZ*16));
			t.addVertexWithUV(x+rb.renderMinX, y+rb.renderMinY, z+rb.renderMaxZ, i.getInterpolatedU(rb.renderMinX*16), i.getInterpolatedV(rb.renderMaxZ*16));
		}
		
		if(rb.renderMaxY < 1) {
			t.addVertexWithUV(x+rb.renderMinX, y+rb.renderMaxY, z+rb.renderMaxZ, i.getInterpolatedU(rb.renderMinX*16), i.getInterpolatedV(rb.renderMaxZ*16));
			t.addVertexWithUV(x+rb.renderMaxX, y+rb.renderMaxY, z+rb.renderMaxZ, i.getInterpolatedU(rb.renderMaxX*16), i.getInterpolatedV(rb.renderMaxZ*16));
			t.addVertexWithUV(x+rb.renderMaxX, y+rb.renderMaxY, z+rb.renderMinZ, i.getInterpolatedU(rb.renderMaxX*16), i.getInterpolatedV(rb.renderMinZ*16));
			t.addVertexWithUV(x+rb.renderMinX, y+rb.renderMaxY, z+rb.renderMinZ, i.getInterpolatedU(rb.renderMinX*16), i.getInterpolatedV(rb.renderMinZ*16));
		}
		
		if(rb.renderMinX > 0) {
			t.addVertexWithUV(x+rb.renderMinX, y+rb.renderMinY, z+rb.renderMinZ, i.getInterpolatedU(rb.renderMinZ*16), i.getInterpolatedV(rb.renderMinY*16));
			t.addVertexWithUV(x+rb.renderMinX, y+rb.renderMinY, z+rb.renderMaxZ, i.getInterpolatedU(rb.renderMaxZ*16), i.getInterpolatedV(rb.renderMinY*16));
			t.addVertexWithUV(x+rb.renderMinX, y+rb.renderMaxY, z+rb.renderMaxZ, i.getInterpolatedU(rb.renderMaxZ*16), i.getInterpolatedV(rb.renderMaxY*16));
			t.addVertexWithUV(x+rb.renderMinX, y+rb.renderMaxY, z+rb.renderMinZ, i.getInterpolatedU(rb.renderMinZ*16), i.getInterpolatedV(rb.renderMaxY*16));
		}
		
		if(rb.renderMaxX < 1) {
			t.addVertexWithUV(x+rb.renderMaxX, y+rb.renderMaxY, z+rb.renderMinZ, i.getInterpolatedU(rb.renderMinZ*16), i.getInterpolatedV(rb.renderMaxY*16));
			t.addVertexWithUV(x+rb.renderMaxX, y+rb.renderMaxY, z+rb.renderMaxZ, i.getInterpolatedU(rb.renderMaxZ*16), i.getInterpolatedV(rb.renderMaxY*16));
			t.addVertexWithUV(x+rb.renderMaxX, y+rb.renderMinY, z+rb.renderMaxZ, i.getInterpolatedU(rb.renderMaxZ*16), i.getInterpolatedV(rb.renderMinY*16));
			t.addVertexWithUV(x+rb.renderMaxX, y+rb.renderMinY, z+rb.renderMinZ, i.getInterpolatedU(rb.renderMinZ*16), i.getInterpolatedV(rb.renderMinY*16));
		}
		
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}
}
