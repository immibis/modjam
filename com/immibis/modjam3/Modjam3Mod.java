package com.immibis.modjam3;

import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
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
import cpw.mods.fml.common.ICraftingHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid="ChickenBones", name="The Chicken Bones Mod", version="1.0")
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class Modjam3Mod implements IGuiHandler, ICraftingHandler {
	
	@Instance("ChickenBones")
	public static Modjam3Mod instance;
	
	public static int GUI_ICHEST = 0;
	
	public static BlockIChest blockIChest;
	public static BlockChickenOre blockChickenOre;
	public static Item itemChickenBone;
	public static Item itemEggStaff;
	
	@SidedProxy(clientSide="com.immibis.modjam3.ClientProxy", serverSide="com.immibis.modjam3.Proxy")
	public static Proxy proxy;
	
	private Configuration cfg;
	private int blockid_ichest = -1;
	private int blockid_chore = -1;
	private int itemid_chickenBone = -1;
	private int itemid_eggstaff = -1;
	
	private int preinit_block(String name) {
		if(cfg.getCategory(Configuration.CATEGORY_BLOCK).keySet().contains(name))
			return cfg.getBlock(name, 2345).getInt(2345);
		else
			return -1;
	}
	
	@EventHandler
	public void preinit(FMLPreInitializationEvent evt) {
		cfg = new Configuration(evt.getSuggestedConfigurationFile());
		cfg.load();
		
		blockid_ichest = preinit_block("ichest");
		blockid_chore = preinit_block("chickenOre");
		if(cfg.getCategory(Configuration.CATEGORY_ITEM).keySet().contains("chickenbone"))
			itemid_chickenBone = cfg.getItem("chickenbone", 23456).getInt(23456);
		if(cfg.getCategory(Configuration.CATEGORY_ITEM).keySet().contains("eggstaff"))
			itemid_eggstaff = cfg.getItem("eggstaff", 23457).getInt(23457);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent evt) {
		if(blockid_ichest == -1)
			blockid_ichest = cfg.getBlock("ichest", 2345).getInt(2345);
		if(blockid_chore == -1)
			blockid_chore = cfg.getBlock("chickenOre", 2345).getInt(2345);
		if(itemid_chickenBone == -1)
			itemid_chickenBone = cfg.getItem("chickenbone", 23456).getInt(23456);
		if(itemid_eggstaff == -1)
			itemid_eggstaff = cfg.getItem("eggstaff", 23457).getInt(23457);
			
		if(cfg.hasChanged())
			cfg.save();
		
		
		
		
		blockIChest = new BlockIChest(blockid_ichest);
		blockChickenOre = new BlockChickenOre(blockid_chore);
		itemEggStaff = new ItemEggStaff(itemid_eggstaff);
		
		itemChickenBone = new Item(itemid_chickenBone);
		GameRegistry.registerItem(itemChickenBone, "chickenbone");
		itemChickenBone.setCreativeTab(CreativeTabs.tabMaterials);
		itemChickenBone.setTextureName("immibis_modjam3:chickenbone");
		itemChickenBone.setUnlocalizedName("immibis_modjam3.chickenbone");
		
		GameRegistry.registerItem(itemEggStaff, "eggstaff");
		GameRegistry.registerBlock(blockIChest, "ichest");
		GameRegistry.registerBlock(blockChickenOre, "chickenore");
		
		GameRegistry.registerTileEntity(TileEntityIChest.class, "immibis_modjam3.ichest");
		
		
		GameRegistry.addRecipe(new ItemStack(blockIChest), "###", "#C#", "###", 'C', Block.enderChest, '#', itemChickenBone);
		GameRegistry.addRecipe(new ItemStack(itemEggStaff), "  #", " / ", "/  ", '#', Item.egg, '/', itemChickenBone);
		EntityRegistry.registerModEntity(EntityAngryChicken.class, "immibis_modjam3.angryChicken", 0, this, 100, 5, true);
		
		GameRegistry.registerCraftingHandler(this);
		NetworkRegistry.instance().registerGuiHandler(this, this);
		
		proxy.init();
		
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@ForgeSubscribe
	public void onLivingDrops(LivingDropsEvent evt) {
		if(evt.entity instanceof EntityChicken) {
			if(evt.entity instanceof EntityAngryChicken) {
				evt.drops.clear();
				return;
			}
			
			if(evt.entity.worldObj.rand.nextInt(3) == 0)
				evt.drops.add(new EntityItem(evt.entity.worldObj, evt.entity.posX, evt.entity.posY, evt.entity.posZ, new ItemStack(itemChickenBone, 1)));
			
			Entity source = evt.source.getEntity();
			
			if(source instanceof EntityPlayer && evt.entity.worldObj.rand.nextInt(7) == 0)
				for(int k = 0; k < 30; k++)
					evt.entity.worldObj.spawnEntityInWorld(new EntityAngryChicken(evt.entity.worldObj, (EntityPlayer)source));
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
	
	@Override
	public void onCrafting(EntityPlayer player, ItemStack item, IInventory craftMatrix) {
		if(item.itemID == blockIChest.blockID && !player.worldObj.isRemote)
			player.worldObj.playSoundAtEntity(player, "immibis_modjam3:ichest.doppler", 1, 1);
	}
	
	@Override
	public void onSmelting(EntityPlayer player, ItemStack item) {
		
	}
}
