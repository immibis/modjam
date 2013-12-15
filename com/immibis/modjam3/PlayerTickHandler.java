package com.immibis.modjam3;

import java.util.EnumSet;

import net.minecraft.entity.player.EntityPlayer;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class PlayerTickHandler implements ITickHandler {

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		EntityPlayer ply = (EntityPlayer)tickData[0];
		if(ply.getFoodStats().getFoodLevel() < 18 && ply.inventory.hasItem(Modjam3Mod.itemChickenBeak.itemID) && ply.inventory.consumeInventoryItem(Modjam3Mod.itemChickenNugget.itemID)) {
			ply.getFoodStats().addStats(Modjam3Mod.itemChickenNugget);
		}
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.PLAYER);
	}

	@Override
	public String getLabel() {
		return "ChickenBones player tick";
	}

}
