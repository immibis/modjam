package com.immibis.modjam3;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
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
	
	private static final double min = 4/16f, max = 12/16f;
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		boolean nx = BlockChickenPipe.connects(world, x-1, y, z);
		boolean px = BlockChickenPipe.connects(world, x+1, y, z);
		boolean ny = BlockChickenPipe.connects(world, x, y-1, z);
		boolean py = BlockChickenPipe.connects(world, x, y+1, z);
		boolean nz = BlockChickenPipe.connects(world, x, y, z-1);
		boolean pz = BlockChickenPipe.connects(world, x, y, z+1);
		
		Tessellator.instance.setBrightness(world.getLightBrightnessForSkyBlocks(x, y, z, 0));
		
		if(nx && px && !ny && !py && !nz && !pz) {
			renderer.setRenderBounds(0, min, min, 1, max, max);
			renderStandardBlock(renderer, block, x, y, z, false, false, true, true, true, true, BlockChickenPipe.iconCH, BlockChickenPipe.iconCH);
			return true;
		}

		if(!nx && !px && ny && py && !nz && !pz) {
			renderer.setRenderBounds(min, 0, min, max, 1, max);
			renderStandardBlock(renderer, block, x, y, z, true, true, false, false, true, true, BlockChickenPipe.iconCV, null);
			return true;
		}

		if(!nx && !px && !ny && !py && nz && pz) {
			renderer.setRenderBounds(min, min, 0, max, max, 1);
			renderStandardBlock(renderer, block, x, y, z, true, true, true, true, false, false, BlockChickenPipe.iconCH, BlockChickenPipe.iconCV);
			return true;
		}

		if(!(nx && px && ny && py && nz && pz)) {
			renderer.setRenderBounds(min, min, min, max, max, max);
			renderStandardBlock(renderer, block, x, y, z, !nx, !px, !ny, !py, !nz, !pz, BlockChickenPipe.icon, BlockChickenPipe.icon);
		}
		
		if(nx) {
			renderer.setRenderBounds(0, min, min, min, max, max);
			renderStandardBlock(renderer, block, x, y, z, false, false, true, true, true, true, BlockChickenPipe.iconCH, BlockChickenPipe.iconCH);
		}
		
		if(px) {
			renderer.setRenderBounds(max, min, min, 1, max, max);
			renderStandardBlock(renderer, block, x, y, z, false, false, true, true, true, true, BlockChickenPipe.iconCH, BlockChickenPipe.iconCH);
		}
		
		if(ny) {
			renderer.setRenderBounds(min, 0, min, max, min, max);
			renderStandardBlock(renderer, block, x, y, z, true, true, false, false, true, true, BlockChickenPipe.iconCV, null);
		}
		
		if(py) {
			renderer.setRenderBounds(min, max, min, max, 1, max);
			renderStandardBlock(renderer, block, x, y, z, true, true, false, false, true, true, BlockChickenPipe.iconCV, null);
		}
		
		if(nz) {
			renderer.setRenderBounds(min, min, 0, max, max, min);
			renderStandardBlock(renderer, block, x, y, z, true, true, true, true, false, false, BlockChickenPipe.iconCH, BlockChickenPipe.iconCV);
		}
		
		if(pz) {
			renderer.setRenderBounds(min, min, max, max, max, 1);
			renderStandardBlock(renderer, block, x, y, z, true, true, true, true, false, false, BlockChickenPipe.iconCH, BlockChickenPipe.iconCV);
		}
		
		return true;
	}
	
	private void renderStandardBlock(RenderBlocks rb, Block block, int x, int y, int z, boolean nx, boolean px, boolean ny, boolean py, boolean nz, boolean pz, IIcon iXZ, IIcon iY) {
		Tessellator t = Tessellator.instance;
		
		if(rb.hasOverrideBlockTexture()) {
			iXZ = rb.overrideBlockTexture;
			iY = rb.overrideBlockTexture;
		}
		
		if(nz) {
			t.setColorOpaque_F(0.8f, 0.8f, 0.8f);
			t.addVertexWithUV(x+rb.renderMinX, y+rb.renderMaxY, z+rb.renderMinZ, iXZ.getInterpolatedU(rb.renderMinX*16), iXZ.getInterpolatedV(rb.renderMaxY*16));
			t.addVertexWithUV(x+rb.renderMaxX, y+rb.renderMaxY, z+rb.renderMinZ, iXZ.getInterpolatedU(rb.renderMaxX*16), iXZ.getInterpolatedV(rb.renderMaxY*16));
			t.addVertexWithUV(x+rb.renderMaxX, y+rb.renderMinY, z+rb.renderMinZ, iXZ.getInterpolatedU(rb.renderMaxX*16), iXZ.getInterpolatedV(rb.renderMinY*16));
			t.addVertexWithUV(x+rb.renderMinX, y+rb.renderMinY, z+rb.renderMinZ, iXZ.getInterpolatedU(rb.renderMinX*16), iXZ.getInterpolatedV(rb.renderMinY*16));
		}
		
		if(pz) {
			t.setColorOpaque_F(0.8f, 0.8f, 0.8f);
			t.addVertexWithUV(x+rb.renderMinX, y+rb.renderMinY, z+rb.renderMaxZ, iXZ.getInterpolatedU(rb.renderMinX*16), iXZ.getInterpolatedV(rb.renderMinY*16));
			t.addVertexWithUV(x+rb.renderMaxX, y+rb.renderMinY, z+rb.renderMaxZ, iXZ.getInterpolatedU(rb.renderMaxX*16), iXZ.getInterpolatedV(rb.renderMinY*16));
			t.addVertexWithUV(x+rb.renderMaxX, y+rb.renderMaxY, z+rb.renderMaxZ, iXZ.getInterpolatedU(rb.renderMaxX*16), iXZ.getInterpolatedV(rb.renderMaxY*16));
			t.addVertexWithUV(x+rb.renderMinX, y+rb.renderMaxY, z+rb.renderMaxZ, iXZ.getInterpolatedU(rb.renderMinX*16), iXZ.getInterpolatedV(rb.renderMaxY*16));
		}
		
		if(ny) {
			t.setColorOpaque_F(0.5f, 0.5f, 0.5f);
			t.addVertexWithUV(x+rb.renderMinX, y+rb.renderMinY, z+rb.renderMinZ, iY.getInterpolatedU(rb.renderMinX*16), iY.getInterpolatedV(rb.renderMinZ*16));
			t.addVertexWithUV(x+rb.renderMaxX, y+rb.renderMinY, z+rb.renderMinZ, iY.getInterpolatedU(rb.renderMaxX*16), iY.getInterpolatedV(rb.renderMinZ*16));
			t.addVertexWithUV(x+rb.renderMaxX, y+rb.renderMinY, z+rb.renderMaxZ, iY.getInterpolatedU(rb.renderMaxX*16), iY.getInterpolatedV(rb.renderMaxZ*16));
			t.addVertexWithUV(x+rb.renderMinX, y+rb.renderMinY, z+rb.renderMaxZ, iY.getInterpolatedU(rb.renderMinX*16), iY.getInterpolatedV(rb.renderMaxZ*16));
		}
		
		if(py) {
			t.setColorOpaque_F(1.0f, 1.0f, 1.0f);
			t.addVertexWithUV(x+rb.renderMinX, y+rb.renderMaxY, z+rb.renderMaxZ, iY.getInterpolatedU(rb.renderMinX*16), iY.getInterpolatedV(rb.renderMaxZ*16));
			t.addVertexWithUV(x+rb.renderMaxX, y+rb.renderMaxY, z+rb.renderMaxZ, iY.getInterpolatedU(rb.renderMaxX*16), iY.getInterpolatedV(rb.renderMaxZ*16));
			t.addVertexWithUV(x+rb.renderMaxX, y+rb.renderMaxY, z+rb.renderMinZ, iY.getInterpolatedU(rb.renderMaxX*16), iY.getInterpolatedV(rb.renderMinZ*16));
			t.addVertexWithUV(x+rb.renderMinX, y+rb.renderMaxY, z+rb.renderMinZ, iY.getInterpolatedU(rb.renderMinX*16), iY.getInterpolatedV(rb.renderMinZ*16));
		}
		
		if(nx) {
			t.setColorOpaque_F(0.6f, 0.6f, 0.6f);
			t.addVertexWithUV(x+rb.renderMinX, y+rb.renderMinY, z+rb.renderMinZ, iXZ.getInterpolatedU(rb.renderMinZ*16), iXZ.getInterpolatedV(rb.renderMinY*16));
			t.addVertexWithUV(x+rb.renderMinX, y+rb.renderMinY, z+rb.renderMaxZ, iXZ.getInterpolatedU(rb.renderMaxZ*16), iXZ.getInterpolatedV(rb.renderMinY*16));
			t.addVertexWithUV(x+rb.renderMinX, y+rb.renderMaxY, z+rb.renderMaxZ, iXZ.getInterpolatedU(rb.renderMaxZ*16), iXZ.getInterpolatedV(rb.renderMaxY*16));
			t.addVertexWithUV(x+rb.renderMinX, y+rb.renderMaxY, z+rb.renderMinZ, iXZ.getInterpolatedU(rb.renderMinZ*16), iXZ.getInterpolatedV(rb.renderMaxY*16));
		}
		
		if(px) {
			t.setColorOpaque_F(0.6f, 0.6f, 0.6f);
			t.addVertexWithUV(x+rb.renderMaxX, y+rb.renderMaxY, z+rb.renderMinZ, iXZ.getInterpolatedU(rb.renderMinZ*16), iXZ.getInterpolatedV(rb.renderMaxY*16));
			t.addVertexWithUV(x+rb.renderMaxX, y+rb.renderMaxY, z+rb.renderMaxZ, iXZ.getInterpolatedU(rb.renderMaxZ*16), iXZ.getInterpolatedV(rb.renderMaxY*16));
			t.addVertexWithUV(x+rb.renderMaxX, y+rb.renderMinY, z+rb.renderMaxZ, iXZ.getInterpolatedU(rb.renderMaxZ*16), iXZ.getInterpolatedV(rb.renderMinY*16));
			t.addVertexWithUV(x+rb.renderMaxX, y+rb.renderMinY, z+rb.renderMinZ, iXZ.getInterpolatedU(rb.renderMinZ*16), iXZ.getInterpolatedV(rb.renderMinY*16));
		}
		
	}

	@Override
	public boolean shouldRender3DInInventory(int model) {
		return true;
	}
}
