package com.immibis.modjam3;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemStack;

public class ItemEggBomb extends ItemEgg {
	public ItemEggBomb(int id) {
		super(id);
		setUnlocalizedName("immibis_modjam3.eggbomb");
		setTextureName("egg");
	}
}
