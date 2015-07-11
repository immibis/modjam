package com.immibis.modjam3;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockChickenOre extends Block {
	public BlockChickenOre() {
		super(Material.rock);
		
		setCreativeTab(CreativeTabs.tabBlock);
		setBlockName("immibis_modjam3.chickenore");
		setBlockTextureName("immibis_modjam3:chicken_ore");
		setHardness(3.0F);
		setResistance(5.0F);
		setStepSound(soundTypeStone);
	}
	
	private IIcon iSide;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		super.registerBlockIcons(par1IconRegister);
		iSide = par1IconRegister.registerIcon("immibis_modjam3:chicken_ore_side");
	}
	
	@Override
	public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9) {
		return par1World.isRemote ? 15 : par1World.rand.nextInt(4) + 2;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int par1, int par2) {
		if(par1 == 4)
			return blockIcon;
		else
			return iSide;
	}
	
	private int getHashedFakeMeta(int x, int y, int z) {
		return ((7964532*x + 234356*y + 23431*z) >> 5 & 3) + 2;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5) {
		int meta = par1iBlockAccess.getBlockMetadata(par2, par3, par4);
		return (meta != 0 ? meta : getHashedFakeMeta(par2, par3, par4)) == par5 ? blockIcon : iSide;
	}
	
	@Override
	public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7) {
		if (par1World.rand.nextFloat() < par6 && !par1World.isRemote && par1World.getGameRules().getGameRuleBooleanValue("doTileDrops"))
        {
            EntityChicken chicken = new EntityChicken(par1World);
            chicken.setPosition(par2 + 0.5, par3, par4 + 0.5);
            par1World.spawnEntityInWorld(chicken);
            par1World.playSoundAtEntity(chicken, "mob.chicken.say", 1, 1);
            
            Entity closestEntity = chicken.worldObj.getClosestPlayerToEntity(chicken, 16);
            if(closestEntity != null)
            	chicken.getLookHelper().setLookPosition(closestEntity.posX, closestEntity.posY + (double)closestEntity.getEyeHeight(), closestEntity.posZ, 10.0F, (float)chicken.getVerticalFaceSpeed());
        }
	}
	
	@Override
	public void onBlockHarvested(World w, int x, int y, int z, int par5, EntityPlayer par6EntityPlayer) {
		if(!w.isRemote) {
			Vec3 look = par6EntityPlayer.getLookVec();
			look.yCoord = 0;
			look.xCoord = -look.xCoord;
			look.zCoord = -look.zCoord;
			look.normalize();
			look.xCoord += par6EntityPlayer.posX;
			look.yCoord += par6EntityPlayer.posY + par6EntityPlayer.getEyeHeight();
			look.zCoord += par6EntityPlayer.posZ;
			w.playSoundEffect(look.xCoord, look.yCoord, look.zCoord, "random.fuse", 1.0f, 0.5f);
		}
	}
}
