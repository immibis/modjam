package com.immibis.modjam3;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends Proxy {
	public void init() {
		MinecraftForgeClient.registerItemRenderer(Modjam3Mod.blockIChest.blockID, new BlockIChestRenderItem());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityIChest.class, new TileEntityIChestRenderer());
	}
}
