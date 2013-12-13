package com.immibis.modjam3;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.ChestItemRenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class BlockIChestRenderItem implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch(type) {
		case ENTITY: return true;
		case EQUIPPED: return true;
		case EQUIPPED_FIRST_PERSON: return true;
		case FIRST_PERSON_MAP: return true;
		case INVENTORY: return true;
		}
		return false;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		switch(helper) {
		case BLOCK_3D: return true;
		case ENTITY_BOBBING: return true;
		case ENTITY_ROTATION: return true;
		case EQUIPPED_BLOCK: return true;
		case INVENTORY_BLOCK: return true;
		}
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		ChestItemRenderHelper.instance.renderChest(Block.enderChest, 0, 1);
	}

}
