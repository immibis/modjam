package com.immibis.modjam3;

import net.minecraft.client.model.ModelChicken;
import net.minecraft.item.Item;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends Proxy {
	
	public static Vec3 nearestTrans = null;
	
	@SubscribeEvent
	public void onConnect(FMLNetworkEvent.ClientConnectedToServerEvent evt) {
		nearestTrans = null;
	}
	
	@Override
	public void init() {
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(Modjam3Mod.blockIChest), new BlockIChestRenderItem());
		MinecraftForgeClient.registerItemRenderer(Modjam3Mod.itemChicken, new ItemChickenRender());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityIChest.class, new TileEntityIChestRenderer());
		
		RenderingRegistry.registerEntityRenderingHandler(EntityBossChicken.class, new EntityBossChickenRender(new ModelChicken(), 0.3f));
		
		BlockChickenPipe.model = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(BlockChickenPipe.model, new BlockChickenPipeRender());
		
		FMLCommonHandler.instance().bus().register(this);
		
		NetHandlerChickenBones.initClient();
	}
}
