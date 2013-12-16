package com.immibis.modjam3;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelChicken;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends Proxy {
	public void init() {
		MinecraftForgeClient.registerItemRenderer(Modjam3Mod.blockIChest.blockID, new BlockIChestRenderItem());
		MinecraftForgeClient.registerItemRenderer(Modjam3Mod.itemChicken.itemID, new ItemChickenRender());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityIChest.class, new TileEntityIChestRenderer());
		
		RenderingRegistry.registerEntityRenderingHandler(EntityBossChicken.class, new EntityBossChickenRender(new ModelChicken(), 0.3f));
		
		BlockChickenPipe.model = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(BlockChickenPipe.model, new BlockChickenPipeRender());
		
		Minecraft.getMinecraft().sndManager.soundPoolSounds.addSound("immibis_modjam3:ichest/doppler.ogg");
		Minecraft.getMinecraft().sndManager.soundPoolSounds.addSound("immibis_modjam3:ichest/open.ogg");
		Minecraft.getMinecraft().sndManager.soundPoolSounds.addSound("immibis_modjam3:ichest/close.ogg");
		Minecraft.getMinecraft().sndManager.soundPoolSounds.addSound("immibis_modjam3:ichest/spindown.ogg");
		Minecraft.getMinecraft().sndManager.soundPoolSounds.addSound("immibis_modjam3:ichest/hurt2_50percent.ogg");
		Minecraft.getMinecraft().sndManager.soundPoolStreaming.addSound("immibis_modjam3:oli_chang_chicken_techno.ogg");
		Minecraft.getMinecraft().sndManager.soundPoolStreaming.addSound("immibis_modjam3:dj_bewan_chicken_song_full.ogg");
		Minecraft.getMinecraft().sndManager.soundPoolStreaming.addSound("immibis_modjam3:dj_bewan_chicken_song_short.ogg");
		
	}
}
