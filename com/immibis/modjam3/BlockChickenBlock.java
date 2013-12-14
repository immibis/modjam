package com.immibis.modjam3;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockChickenBlock extends Block {
	public BlockChickenBlock(int id) {
		super(id, Material.iron);
		
		setCreativeTab(CreativeTabs.tabBlock);
		setUnlocalizedName("immibis_modjam3.chickenblock");
		setTextureName("immibis_modjam3:chicken_block");
		setHardness(3.0F);
		setResistance(10.0F);
		setStepSound(soundMetalFootstep);
	}
	
}
