package com.immibis.modjam3;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockIChest extends BlockContainer {
	public BlockIChest() {
		super(Material.rock);
		
		setCreativeTab(CreativeTabs.tabDecorations);
        setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
		setBlockName("immibis_modjam3.ichest");
		setStepSound(soundTypeStone);
		setHardness(3.5F);
		setResistance(1000.0F);
	}

    @Override
	public boolean isOpaqueCube() {return false;}
    @Override
	public boolean renderAsNormalBlock() {return false;}
    @Override
	public int getRenderType() {return 22;}

    /**
     * Called when the block is placed in the world.
     */
    @Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
    {
        byte b0 = 0;
        int l = MathHelper.floor_double((double)(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (l == 0)
            b0 = 2;

        if (l == 1)
            b0 = 5;

        if (l == 2)
            b0 = 3;

        if (l == 3)
            b0 = 4;

        par1World.setBlockMetadataWithNotify(par2, par3, par4, b0, 2);
        
        GameProfile profile = (par5EntityLivingBase instanceof EntityPlayer ? ((EntityPlayer)par5EntityLivingBase).getGameProfile() : null);
        
        ((TileEntityIChest)par1World.getTileEntity(par2, par3, par4)).owner = (profile == null ? null : profile.getId());
    }

    /**
     * Called upon block activation (right click on the block.)
     */
    @Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
    	if(!par1World.getBlock(par2, par3 + 1, par4).isSideSolid(par1World, par2, par3+1, par4, ForgeDirection.DOWN) && !par1World.isRemote)
    		par5EntityPlayer.openGui(Modjam3Mod.instance, Modjam3Mod.GUI_ICHEST, par1World, par2, par3, par4);
    	return true;
    }

    @Override
	public TileEntity createNewTileEntity(World par1World, int meta)
    {
        return new TileEntityIChest();
    }

    //@SideOnly(Side.CLIENT)
    /*public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        for (int l = 0; l < 3; ++l)
        {
            double d0 = (double)((float)par2 + par5Random.nextFloat());
            double d1 = (double)((float)par3 + par5Random.nextFloat());
            d0 = (double)((float)par4 + par5Random.nextFloat());
            double d2 = 0.0D;
            double d3 = 0.0D;
            double d4 = 0.0D;
            int i1 = par5Random.nextInt(2) * 2 - 1;
            int j1 = par5Random.nextInt(2) * 2 - 1;
            d2 = ((double)par5Random.nextFloat() - 0.5D) * 0.125D;
            d3 = ((double)par5Random.nextFloat() - 0.5D) * 0.125D;
            d4 = ((double)par5Random.nextFloat() - 0.5D) * 0.125D;
            double d5 = (double)par4 + 0.5D + 0.25D * (double)j1;
            d4 = (double)(par5Random.nextFloat() * 1.0F * (float)j1);
            double d6 = (double)par2 + 0.5D + 0.25D * (double)i1;
            d2 = (double)(par5Random.nextFloat() * 1.0F * (float)i1);
            par1World.spawnParticle("portal", d6, d1, d5, d2, d3, d4);
        }
    }*/

    @Override
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon("obsidian");
    }
}
