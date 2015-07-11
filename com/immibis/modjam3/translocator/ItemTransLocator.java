package com.immibis.modjam3.translocator;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTransLocator extends Item {
	public ItemTransLocator() {
		setUnlocalizedName("immibis_modjam3.translocator");
		setCreativeTab(CreativeTabs.tabMisc);
	}
	
	@Override
	public void addInformation(ItemStack p_77624_1_, EntityPlayer p_77624_2_, List p_77624_3_, boolean p_77624_4_) {
		super.addInformation(p_77624_1_, p_77624_2_, p_77624_3_, p_77624_4_);
		p_77624_3_.add("Points towards nearest trans player");
		p_77624_3_.add("except you. Use '/cbtrans trans'");
		p_77624_3_.add("to identify as trans.");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister reg) {
		if(reg instanceof TextureMap)
			((TextureMap)reg).setTextureEntry("immibis_modjam3:translocator", new TextureTransLocator("immibis_modjam3:translocator"));
		this.itemIcon = reg.registerIcon("immibis_modjam3:translocator");
	}
}
