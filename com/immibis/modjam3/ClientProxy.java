package com.immibis.modjam3;

import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends Proxy {
	public void init() {
		MinecraftForgeClient.registerItemRenderer(Modjam3Mod.blockIChest.blockID, new BlockIChestRenderItem());
	}
}
