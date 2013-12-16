package com.immibis.modjam3;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

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
	
	public static Icon icon, iconCV, iconCH;
	
	public void registerIcons(IconRegister par1IconRegister) {
		super.registerIcons(par1IconRegister);
		icon = blockIcon;
		iconCH = par1IconRegister.registerIcon("immibis_modjam3:chicken_pipe_ch");
		iconCV = par1IconRegister.registerIcon("immibis_modjam3:chicken_pipe_cv");
	}

	public static boolean connects(IBlockAccess w, int x, int y, int z) {
		int id = w.getBlockId(x, y, z);
		if(id == Modjam3Mod.blockChickenPipe.blockID)
			return true;
		
		if(w.getBlockTileEntity(x, y, z) instanceof IInventory)
			return true;
		
		return false;
	}
	
	@Override
	public void addCollisionBoxesToList(World par1World, int x, int y, int z, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity) {
		addCollisionBoxToList(0.25, 0.25, 0.25, 0.75, 0.75, 0.75, x, y, z, par5AxisAlignedBB, par6List);
	}
	
	private void addCollisionBoxToList(double nx, double ny, double nz, double px, double py, double pz, int x, int y, int z, AxisAlignedBB mask, List list) {
		AxisAlignedBB bb = AxisAlignedBB.getAABBPool().getAABB(nx, ny, nz, px, py, pz).offset(x, y, z);
		if(mask.intersectsWith(bb))
			list.add(bb);
	}

	@Override
	public void onEntityCollidedWithBlock(World w, int x, int y, int z, Entity e) {
		if(!w.isRemote && e instanceof EntityItem) {
			ItemStack stack = ((EntityItem)e).getEntityItem();
			EntityPipedItem epi = new EntityPipedItem(w);
			epi.setPosition(x + 0.5, y + 0.5, z + 0.5);
			epi.setStack(stack);
			
			double speed = 0.1;
			int dir = chooseNextDirection(w, x, y, z, -1);
			System.out.println("initial dir "+dir);
			switch(dir) {
			case 4: epi.motionX = -speed; break;
			case 5: epi.motionX = +speed; break;
			case 0: epi.motionY = -speed; break;
			case 1: epi.motionY = +speed; break;
			case 2: epi.motionZ = -speed; break;
			case 3: epi.motionZ = +speed; break;
			}
			
			w.spawnEntityInWorld(epi);
			//e.setDead();
		}
	}
	
	private static List<Integer> nextDirs = new ArrayList<Integer>(6);
	public static int chooseNextDirection(World w, int x, int y, int z, int prev) {
		boolean nx = BlockChickenPipe.connects(w, x-1, y, z);
		boolean px = BlockChickenPipe.connects(w, x+1, y, z);
		boolean ny = BlockChickenPipe.connects(w, x, y-1, z);
		boolean py = BlockChickenPipe.connects(w, x, y+1, z);
		boolean nz = BlockChickenPipe.connects(w, x, y, z-1);
		boolean pz = BlockChickenPipe.connects(w, x, y, z+1);
		
		nextDirs.clear();
		if(nx && prev != 4) nextDirs.add(4);
		if(px && prev != 5) nextDirs.add(5);
		if(ny && prev != 0) nextDirs.add(0);
		if(py && prev != 1) nextDirs.add(1);
		if(nz && prev != 2) nextDirs.add(2);
		if(pz && prev != 3) nextDirs.add(3);
		
		if(nextDirs.size() == 0)
			return prev;
		
		return nextDirs.get(w.rand.nextInt(nextDirs.size()));
	}
}
