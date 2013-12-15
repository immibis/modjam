package com.immibis.modjam3;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.world.World;

public class ItemWirelessRedstone extends Item {
	public ItemWirelessRedstone(int id) {
		super(id);
		
		setUnlocalizedName("immibis_modjam3.wirelessredstone");
		setTextureName("redstone_dust");
		setCreativeTab(CreativeTabs.tabRedstone);
	}
	
	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		if(par3World.isRemote)
			par2EntityPlayer.sendChatToPlayer(ChatMessageComponent.createFromTranslationKey("immibis_modjam3.use_wrcbe"));
		return true;
	}
}
