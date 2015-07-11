package com.immibis.modjam3;

import net.minecraft.client.model.ModelChicken;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends Proxy {
	@Override
	public void init() {
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Modjam3Mod.blockIChest), new BlockIChestRenderItem());
		MinecraftForgeClient.registerItemRenderer(Modjam3Mod.itemChicken, new ItemChickenRender());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityIChest.class, new TileEntityIChestRenderer());
		
		RenderingRegistry.registerEntityRenderingHandler(EntityBossChicken.class, new EntityBossChickenRender(new ModelChicken(), 0.3f));
		
		BlockChickenPipe.model = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(BlockChickenPipe.model, new BlockChickenPipeRender());
		
		// TODO sounds
		/*Minecraft.getMinecraft().getSoundHandler().soundPoolSounds.addSound("immibis_modjam3:ichest/doppler.ogg");
		Minecraft.getMinecraft().getSoundHandler().soundPoolSounds.addSound("immibis_modjam3:ichest/open.ogg");
		Minecraft.getMinecraft().getSoundHandler().soundPoolSounds.addSound("immibis_modjam3:ichest/close.ogg");
		Minecraft.getMinecraft().getSoundHandler().soundPoolSounds.addSound("immibis_modjam3:ichest/spindown.ogg");
		Minecraft.getMinecraft().getSoundHandler().soundPoolSounds.addSound("immibis_modjam3:ichest/hurt2_50percent.ogg");
		Minecraft.getMinecraft().getSoundHandler().soundPoolStreaming.addSound("immibis_modjam3:oli_chang_chicken_techno.ogg");
		Minecraft.getMinecraft().getSoundHandler().soundPoolStreaming.addSound("immibis_modjam3:dj_bewan_chicken_song_full.ogg");
		Minecraft.getMinecraft().getSoundHandler().soundPoolStreaming.addSound("immibis_modjam3:dj_bewan_chicken_song_short.ogg");
		*/
	}
}
