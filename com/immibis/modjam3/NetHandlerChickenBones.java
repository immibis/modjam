package com.immibis.modjam3;

import com.immibis.modjam3.translocator.PacketClosestTrans;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class NetHandlerChickenBones {
	
	public static final SimpleNetworkWrapper snw = new SimpleNetworkWrapper("ImmibisMJ3");

	private static final int PKT_NEAREST_TRANS = 0;
	
	public static void initServer() {
	}
	
	@SideOnly(Side.CLIENT)
	public static void initClient() {
		snw.registerMessage(new IMessageHandler<PacketClosestTrans, IMessage>() {
			@Override
			public IMessage onMessage(PacketClosestTrans message, MessageContext ctx) {
				ClientProxy.nearestTrans = message.val;
				return null;
			}
		}, PacketClosestTrans.class, PKT_NEAREST_TRANS, Side.CLIENT);
	}

}
