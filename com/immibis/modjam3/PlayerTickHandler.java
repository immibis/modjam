package com.immibis.modjam3;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class PlayerTickHandler {
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent evt) {
		if(evt.phase == TickEvent.Phase.END) {
			EntityPlayer ply = evt.player;
			if(ply.getFoodStats().getFoodLevel() < 18
					&& ply.inventory.hasItem(Modjam3Mod.itemChickenBeak)
					&& ply.inventory.consumeInventoryItem(Modjam3Mod.itemChickenNugget)) {
				ply.getFoodStats().func_151686_a(Modjam3Mod.itemChickenNugget, new ItemStack(Modjam3Mod.itemChickenNugget));
			}
		}
	}
}
