package com.immibis.modjam3;

import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid="ChickenBones", name="The Chicken Bones Mod", version="1.0")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class Modjam3Mod implements IGuiHandler {
	
	@Instance("ChickenBones")
	public static Modjam3Mod instance;
	
	public static int GUI_ICHEST = 0;
	
	public static BlockIChest blockIChest;
	public static Item itemChickenBone;
	
	@SidedProxy(clientSide="com.immibis.modjam3.ClientProxy", serverSide="com.immibis.modjam3.Proxy")
	public static Proxy proxy;
	
	private Configuration cfg;
	private int blockid_ichest = -1;
	private int itemid_chickenBone = -1;
	
	@EventHandler
	public void preinit(FMLPreInitializationEvent evt) {
		cfg = new Configuration(evt.getSuggestedConfigurationFile());
		cfg.load();
		
		if(cfg.getCategory(Configuration.CATEGORY_BLOCK).keySet().contains("ichest"))
			blockid_ichest = cfg.getBlock("ichest", 2345).getInt(2345);
		if(cfg.getCategory(Configuration.CATEGORY_ITEM).keySet().contains("chickenbone"))
			itemid_chickenBone = cfg.getItem("chickenbone", 23456).getInt(23456);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent evt) {
		if(blockid_ichest == -1)
			blockid_ichest = cfg.getBlock("ichest", 2345).getInt(2345);
		if(itemid_chickenBone == -1)
			itemid_chickenBone = cfg.getItem("chickenbone", 23456).getInt(23456);
			
		if(cfg.hasChanged())
			cfg.save();
		
		
		
		
		blockIChest = new BlockIChest(blockid_ichest);
		itemChickenBone = new Item(itemid_chickenBone);
		
		GameRegistry.registerItem(itemChickenBone, "chickenbone");
		itemChickenBone.setCreativeTab(CreativeTabs.tabMaterials);
		itemChickenBone.setTextureName("immibis_modjam3:chickenbone");
		itemChickenBone.setUnlocalizedName("immibis_modjam3.chickenbone");
		
		GameRegistry.registerBlock(blockIChest, "ichest");
		
		GameRegistry.registerTileEntity(TileEntityIChest.class, "immibis_modjam3.ichest");
		
		
		GameRegistry.addRecipe(new ItemStack(blockIChest), "###", "#C#", "###", 'C', Block.enderChest, '#', itemChickenBone);
		
		NetworkRegistry.instance().registerGuiHandler(this, this);
		
		proxy.init();
		
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@ForgeSubscribe
	public void onLivingDrops(LivingDropsEvent evt) {
		if(evt.entity instanceof EntityChicken) {
			evt.drops.add(new EntityItem(evt.entity.worldObj, evt.entity.posX, evt.entity.posY, evt.entity.posZ, new ItemStack(itemChickenBone, evt.entity.worldObj.rand.nextInt(4))));
		}
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == GUI_ICHEST)
			return new GuiChest(player.inventory, ((TileEntityIChest)world.getBlockTileEntity(x, y, z)));
		return null;
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == GUI_ICHEST)
			return new ContainerChest(player.inventory, ((TileEntityIChest)world.getBlockTileEntity(x, y, z)));
		return null;
	}
}
