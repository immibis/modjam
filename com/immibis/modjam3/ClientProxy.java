package com.immibis.modjam3;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends Proxy {
	public void init() {
		MinecraftForgeClient.registerItemRenderer(Modjam3Mod.blockIChest.blockID, new BlockIChestRenderItem());
		MinecraftForgeClient.registerItemRenderer(Modjam3Mod.itemChicken.itemID, new ItemChickenRender());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityIChest.class, new TileEntityIChestRenderer());
		
		Minecraft.getMinecraft().sndManager.soundPoolSounds.addSound("immibis_modjam3:ichest/doppler.ogg");
		Minecraft.getMinecraft().sndManager.soundPoolSounds.addSound("immibis_modjam3:ichest/open.ogg");
		Minecraft.getMinecraft().sndManager.soundPoolSounds.addSound("immibis_modjam3:ichest/close.ogg");
		Minecraft.getMinecraft().sndManager.soundPoolSounds.addSound("immibis_modjam3:ichest/spindown.ogg");
		Minecraft.getMinecraft().sndManager.soundPoolSounds.addSound("immibis_modjam3:ichest/hurt2_50percent.ogg");
		Minecraft.getMinecraft().sndManager.soundPoolStreaming.addSound("immibis_modjam3:oli_chang_chicken_techno.ogg");
	}
}
