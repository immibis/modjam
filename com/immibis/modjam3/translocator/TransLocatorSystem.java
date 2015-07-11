package com.immibis.modjam3.translocator;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;

import com.immibis.modjam3.NetHandlerChickenBones;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class TransLocatorSystem {
	
	public static boolean isPlayerTrans(EntityPlayer ply) {
		TransFlagEntityProperties tfep = (TransFlagEntityProperties)ply.getExtendedProperties(TransFlagEntityProperties.PROPS_ID);
		return tfep != null && tfep.isTrans;
	}
	
	
	
	
	public void init() {
		FMLCommonHandler.instance().bus().register(this);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing evt) {
		evt.entity.registerExtendedProperties(TransFlagEntityProperties.PROPS_ID, new TransFlagEntityProperties());
	}
	
	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent evt) {
		if(evt.phase != TickEvent.Phase.END)
			return;
		
		List<EntityPlayer> transPlayersInWorld = new ArrayList<EntityPlayer>();
		for(WorldServer world : DimensionManager.getWorlds()) {
			transPlayersInWorld.clear();
			for(EntityPlayer ply : (List<EntityPlayer>)world.playerEntities) {
				if(isPlayerTrans(ply)) {
					transPlayersInWorld.add(ply);
				}
			}
			
			for(EntityPlayer ply : (List<EntityPlayer>)world.playerEntities) {
				TransFlagEntityProperties cached = (TransFlagEntityProperties)ply.getExtendedProperties(TransFlagEntityProperties.PROPS_ID);
				if(cached == null) continue;
				EntityPlayer closestPlayer = getClosestPlayerTo(transPlayersInWorld, ply);
				if(closestPlayer == null) {
					if(cached.lastClosestTransSent != null) {
						sendClosestTrans(ply, null);
					}
				} else {
					Vec3 closest = closestPlayer.getPosition(1);
					Vec3 cacheval = cached.lastClosestTransSent;
					
					if(cacheval == null || (closest.xCoord != cacheval.xCoord || closest.yCoord != cacheval.yCoord || closest.zCoord != cacheval.zCoord)) {
						sendClosestTrans(ply, closest);
					}
				}
			}
		}
	}




	private EntityPlayer getClosestPlayerTo(List<EntityPlayer> list, EntityPlayer to) {
		EntityPlayer best = null;
		double bestdist = -1;
		for(EntityPlayer p : list) {
			if(p == to) continue;
			double dist = p.getDistanceSqToEntity(to);
			if(dist < bestdist || best == null) {
				best = p;
				bestdist = dist;
			}
		}
		return best;
	}




	private void sendClosestTrans(EntityPlayer ply, Vec3 val) {
		TransFlagEntityProperties tfep = (TransFlagEntityProperties)ply.getExtendedProperties(TransFlagEntityProperties.PROPS_ID);
		if(tfep == null) return;
		tfep.lastClosestTransSent = val;
		
		if(ply instanceof EntityPlayerMP) {
			PacketClosestTrans pkt = new PacketClosestTrans();
			pkt.val = val;
			NetHandlerChickenBones.snw.sendTo(pkt, (EntityPlayerMP)ply);
		}
	}
}
