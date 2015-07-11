package com.immibis.modjam3;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.event.world.ChunkDataEvent;

import com.immibis.modjam3.translocator.CommandTrans;
import com.immibis.modjam3.translocator.ItemTransLocator;
import com.immibis.modjam3.translocator.TransLocatorSystem;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;


@Mod(modid="ChickenBones", name="The Chicken Bones Mod: You can pick up chicks!", version="1.0")
public class Modjam3Mod implements IGuiHandler, IWorldGenerator {
	
	@Instance("ChickenBones")
	public static Modjam3Mod instance;
	
	public static int GUI_ICHEST = 0;
	
	public static final boolean CHICKENS_RIDE_STUFF = false;
	
	public static final double EGG_BOMB_CHANCE = 0.1;
	
	public static BlockIChest blockIChest;
	public static BlockChickenOre blockChickenOre;
	public static Block blockChickenBlock;
	public static Block blockChickenBlockBlock;
	public static Block blockChickenPipe;
	public static Block blockChickNT;
	public static Item itemChickenBone;
	public static Item itemEggStaff;
	public static ItemChicken itemChicken;
	public static Item itemChickenCore2;
	public static Item itemChickenChunk;
	public static Item itemChickenIngot;
	public static ItemChickenStaff itemChickenStaff;
	public static Item itemChickaxe;
	public static ItemFood itemChickenNugget;
	public static ItemChickenWing itemChickenWing;
	public static Item itemWRCBE;
	public static Item itemChickenBeak;
	public static Item itemLightningStaff;
	public static Item itemEggBomb;
	public static ItemTransLocator itemTransLocator;
	
	public static Item.ToolMaterial toolMaterialChicken = EnumHelper.addToolMaterial("IMMIBIS_MJ3", 1, 500, 16.0f, 0.0f, 35);
	
	@SidedProxy(clientSide="com.immibis.modjam3.ClientProxy", serverSide="com.immibis.modjam3.Proxy")
	public static Proxy proxy;
	
	private Configuration cfg;
	
	@EventHandler
	public void preinit(FMLPreInitializationEvent evt) {
		cfg = new Configuration(evt.getSuggestedConfigurationFile());
		cfg.load();
	}
	
	@EventHandler
	public void onServerStart(FMLServerStartingEvent evt) {
		evt.registerServerCommand(new CommandTrans());
	}
	
	@EventHandler
	public void init(FMLInitializationEvent evt) {
		
		
		
		blockIChest = new BlockIChest();
		blockChickenOre = new BlockChickenOre();
		blockChickenBlock = new BlockChickenBlock();
		blockChickenBlockBlock = new BlockChickenBlock().setBlockName("immibis_modjam3.chickenblockblock").setBlockTextureName("immibis_modjam3:compressed_chicken_block");
		blockChickenPipe = new BlockChickenPipe();
		blockChickNT = new BlockChickNT();
		itemEggStaff = new ItemEggStaff();
		itemChicken = new ItemChicken();
		itemChickenStaff = new ItemChickenStaff();
		itemChickaxe = new ItemChickaxe();
		itemChickenWing = new ItemChickenWing();
		itemWRCBE = new ItemWirelessRedstone();
		itemChickenBeak = new ItemChickenBeak();
		itemLightningStaff = new ItemLightningStaff();
		itemEggBomb = new ItemEggBomb();
		itemTransLocator = new ItemTransLocator();
		
		itemChickenCore2 = new Item().setCreativeTab(CreativeTabs.tabMaterials).setTextureName("immibis_modjam3:chickencore").setUnlocalizedName("immibis_modjam3.chickencore");
		itemChickenChunk = new Item().setCreativeTab(CreativeTabs.tabMaterials).setTextureName("immibis_modjam3:chickenchunk").setUnlocalizedName("immibis_modjam3.chickenchunk");
		itemChickenIngot = new Item().setCreativeTab(CreativeTabs.tabMaterials).setTextureName("immibis_modjam3:chickeningot").setUnlocalizedName("immibis_modjam3.chickeningot");
		itemChickenNugget = (ItemFood)new ItemFood(1, 0, false).setCreativeTab(CreativeTabs.tabMaterials).setTextureName("immibis_modjam3:chickennugget").setUnlocalizedName("immibis_modjam3.chickennugget");
		
		itemChickenBone = new Item();
		itemChickenBone.setCreativeTab(CreativeTabs.tabMaterials);
		itemChickenBone.setTextureName("immibis_modjam3:chickenbone");
		itemChickenBone.setUnlocalizedName("immibis_modjam3.chickenbone");
		
		GameRegistry.registerItem(itemChickenBone, "chickenbone");
		GameRegistry.registerItem(itemEggStaff, "eggstaff");
		GameRegistry.registerItem(itemChicken, "chicken");
		GameRegistry.registerItem(itemChickenCore2, "chickencore");
		GameRegistry.registerItem(itemChickenChunk, "chickenchunk");
		GameRegistry.registerItem(itemChickenIngot, "chickeningot");
		GameRegistry.registerItem(itemChickenNugget, "chickennugget");
		GameRegistry.registerItem(itemChickenStaff, "chickenstaff");
		GameRegistry.registerItem(itemChickaxe, "chickaxe");
		GameRegistry.registerItem(itemChickenWing, "chickenWing");
		GameRegistry.registerItem(itemLightningStaff, "lstaff");
		GameRegistry.registerItem(itemEggBomb, "eggbomb");
		GameRegistry.registerItem(itemWRCBE, "wrcbe");
		GameRegistry.registerItem(itemChickenBeak, "chickenbeak");
		GameRegistry.registerBlock(blockIChest, "ichest");
		GameRegistry.registerBlock(blockChickenOre, "chickenore");
		GameRegistry.registerBlock(blockChickenBlock, "chickenblock");
		GameRegistry.registerBlock(blockChickenBlockBlock, "chickenblockblock");
		GameRegistry.registerBlock(blockChickenPipe, "chickenpipe");
		GameRegistry.registerBlock(blockChickNT, "chicknt");
		GameRegistry.registerItem(itemTransLocator, "translocator");
		
		GameRegistry.registerTileEntity(TileEntityIChest.class, "immibis_modjam3.ichest");
		
		GameRegistry.addRecipe(new ItemStack(blockIChest), "###", "#C#", "###", 'C', Blocks.ender_chest, '#', itemChickenBone);
		GameRegistry.addRecipe(new ItemStack(itemEggStaff), "  #", " / ", "/  ", '#', Items.egg, '/', itemChickenBone);
		GameRegistry.addRecipe(new ItemStack(itemChickenBone), "#", '#', itemChicken);
		GameRegistry.addRecipe(new ItemStack(itemChickenChunk), "O O", " O ", "O O", 'O', Items.chicken);
		GameRegistry.addRecipe(new ItemStack(itemChickenCore2), "R#G", "#O#", "G#R", '#', itemChickenBone, 'O', itemChickenChunk, 'R', Items.redstone, 'G', Items.glowstone_dust);
		GameRegistry.addRecipe(new ItemStack(itemChickenStaff), "  #", " / ", "/  ", '#', itemChickenChunk, '/', itemChickenBone);
		GameRegistry.addRecipe(new ItemStack(itemChickaxe), "###", " | ", " | ", '#', itemChickenIngot, '|', itemChickenBone);
		GameRegistry.addRecipe(new ItemStack(itemChickenNugget, 9), "#", '#', itemChickenIngot);
		GameRegistry.addRecipe(new ItemStack(itemChickenIngot), "###", "###", "###", '#', itemChickenNugget);
		GameRegistry.addRecipe(new ItemStack(itemChickenIngot, 9), "#", '#', blockChickenBlock);
		GameRegistry.addRecipe(new ItemStack(blockChickenBlock), "###", "###", "###", '#', itemChickenIngot);
		GameRegistry.addRecipe(new ItemStack(blockChickenBlock, 9), "#", '#', blockChickenBlockBlock);
		GameRegistry.addRecipe(new ItemStack(blockChickenBlockBlock), "###", "###", "###", '#', blockChickenBlock);
		GameRegistry.addShapelessRecipe(new ItemStack(blockChickenOre), itemChicken, Blocks.stone);
		GameRegistry.addRecipe(new ItemStack(itemChickenWing), "/#.", "O#.", "/  ", '.', itemChickenNugget, '#', blockChickenBlockBlock, '/', itemChickenBone, 'O', itemChickenCore2);
		GameRegistry.addShapelessRecipe(new ItemStack(itemWRCBE), itemChickenBone, Items.redstone);
		GameRegistry.addRecipe(new ItemStack(itemChickenBeak), "X#X", "#O#", "X#X", '#', itemChickenNugget, 'X', blockChickenBlock, 'O', itemChickenCore2);
		GameRegistry.addRecipe(new ItemStack(itemLightningStaff), "RR#", "R/R", "/RR", '#', itemChicken, '/', itemChickenBone, 'R', Items.redstone);
		GameRegistry.addRecipe(new ItemStack(blockChickNT), "#O#", "O#O", "#O#", 'O', Items.gunpowder, '#', itemChicken);
		FurnaceRecipes.smelting().func_151396_a(itemChicken, new ItemStack(itemChickenIngot), 1.5f);
		GameRegistry.addShapelessRecipe(new ItemStack(itemTransLocator), itemChickenBone, Items.compass);
		
		EntityRegistry.registerModEntity(EntityAngryChicken.class, "angryChicken", 0, this, 100, 5, true);
		EntityRegistry.registerModEntity(EntityPipedItem.class, "pipedItem", 1, this, 30, 1, true); // TODO change to 20 or so
		EntityRegistry.registerModEntity(EntityBossChicken.class, "bossChicken", 2, this, 200, 5, true);
		EntityRegistry.registerModEntity(ItemEggBomb.EntityEggBomb.class, "eggbomb", 3, this, 50, 3, true);
		EntityRegistry.registerModEntity(BlockChickNT.EntityChickNTPrimed.class, "chicknt", 4, this, 50, 20, false);
		
		NetworkRegistry.INSTANCE.registerGuiHandler(this, this);
		FMLCommonHandler.instance().bus().register(this);
		MinecraftForge.EVENT_BUS.register(this);
		GameRegistry.registerWorldGenerator(this, 0);
		
		new TransLocatorSystem().init();
		NetHandlerChickenBones.initServer();
		
		chickenOreGen = new WorldGenMinable(blockChickenOre, 8);
		
		proxy.init();
	}
	
	@SubscribeEvent
	public void onLivingDrops(LivingDropsEvent evt) {
		if(evt.entity instanceof EntityChicken) {
			if(evt.entity instanceof EntityAngryChicken) {
				evt.drops.clear();
				return;
			}
			
			if(evt.entity.worldObj.rand.nextInt(3) == 0)
				evt.drops.add(new EntityItem(evt.entity.worldObj, evt.entity.posX, evt.entity.posY, evt.entity.posZ, new ItemStack(itemChickenBone, 1)));
			
			Entity source = evt.source.getSourceOfDamage();
			
			if(source instanceof EntityPlayer && evt.entity.worldObj.rand.nextInt(7) == 0 && !evt.source.getDamageType().equals("explosion.player"))
				for(int k = 0; k < 30; k++)
					evt.entity.worldObj.spawnEntityInWorld(new EntityAngryChicken(evt.entity.worldObj, (EntityPlayer)source));
		}
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == GUI_ICHEST)
			return new GuiChest(player.inventory, ((TileEntityIChest)world.getTileEntity(x, y, z)));
		return null;
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == GUI_ICHEST)
			return new ContainerChest(player.inventory, ((TileEntityIChest)world.getTileEntity(x, y, z)));
		return null;
	}
	
	@SubscribeEvent
	public void onCrafting(PlayerEvent.ItemCraftedEvent evt) {
		ItemStack item = evt.crafting;
		if(!evt.player.worldObj.isRemote) { 
			if(item.getItem() == Item.getItemFromBlock(blockIChest))
				evt.player.worldObj.playSoundAtEntity(evt.player, "immibis_modjam3:immibis_modjam3.ichest.doppler", 1, 1);
		}
	}
	
	/*@Override
	public void onSmelting(EntityPlayer player, ItemStack item) {
		//if(item.itemID == itemChickenIngot.itemID && !player.worldObj.isRemote)
			//player.worldObj.playSoundAtEntity(player, "immibis_modjam3:ichest.doppler", 1, 1);
	}*/
	
	@SubscribeEvent
	public void onToolBreak(PlayerDestroyItemEvent evt) {
		if(evt.original.getItem() == itemChickaxe && !evt.entity.worldObj.isRemote) {
			EntityChicken ch = new EntityChicken(evt.entity.worldObj);
			ch.setPosition(evt.entity.posX, evt.entity.posY, evt.entity.posZ);
			evt.entity.worldObj.spawnEntityInWorld(ch);
			evt.entity.worldObj.playSoundAtEntity(evt.entity, "immibis_modjam3:ichest.close", 0.5F, 1.0f);
		}
	}
	
	private Queue<Chunk> toGenerate = new LinkedList<Chunk>();
	
	private WorldGenMinable chickenOreGen;
	@SubscribeEvent
	public void onChunkLoad(ChunkDataEvent.Load evt) {
		if(!evt.getData().getBoolean("ImmibisMJ3Gen")) {
			toGenerate.add(evt.getChunk());
		}
	}
	
	@SubscribeEvent
	public void onChunkSave(ChunkDataEvent.Save evt) {
		evt.getData().setBoolean("ImmibisMJ3Gen", true);
	}
	
	@SubscribeEvent
	public void retroGenHandleTick(TickEvent.ServerTickEvent evt) {
		if(evt.phase != TickEvent.Phase.END) return;
		
		Chunk c;
		while((c = toGenerate.poll()) != null) {
			Random r = new Random(c.worldObj.getSeed() ^ 0xC1C2C3C4C5C6C7C8L ^ ((long)c.xPosition << 16) ^ (long)c.zPosition);
			generate(r, c.xPosition, c.zPosition, c.worldObj, c.worldObj.getChunkProvider(), c.worldObj.getChunkProvider());
		}
	}
	
	@Override
	public void generate(Random r, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		for(int k = 0; k < 6; k++) {
			int x = r.nextInt(16) + (chunkX << 4), y = r.nextInt(128), z = r.nextInt(16) + (chunkZ << 4);
			//System.out.println(x+" "+y+" "+z);
			chickenOreGen.generate(world, r, x, y, z);
		}
	}
	
	@SubscribeEvent
	public void makeChickensRideStuff(LivingSpawnEvent.SpecialSpawn evt) {
		if(CHICKENS_RIDE_STUFF && evt.world.rand.nextInt(8) == 0) {
			EntityChicken ec = new EntityChicken(evt.world);
			ec.setPosition(evt.entityLiving.posX, evt.entityLiving.posY, evt.entityLiving.posZ);
			evt.world.spawnEntityInWorld(ec);
			ec.mountEntity(evt.entityLiving);
		}
	}
	
	@SubscribeEvent
	public void chickenLightning(EntityStruckByLightningEvent evt) {
		if(evt.entity instanceof EntityChicken && !(evt.entity instanceof EntityAngryChicken) && !(evt.entity instanceof EntityBossChicken) && !evt.entity.worldObj.isRemote) {
			evt.entity.setDead();
			
			EntityBossChicken b = new EntityBossChicken(evt.entity.worldObj);
			b.setPosition(evt.entity.posX, evt.entity.posY, evt.entity.posZ);
			b.rotationPitch = b.prevRotationPitch = evt.entity.rotationPitch;
			b.rotationYawHead = b.prevRotationYawHead = ((EntityChicken)evt.entity).rotationYawHead;
			b.renderYawOffset = b.prevRenderYawOffset = ((EntityChicken)evt.entity).renderYawOffset;
			b.rotationYaw = b.prevRotationYaw = evt.entity.rotationYaw;
			evt.entity.worldObj.spawnEntityInWorld(b);
		}
	}
	
	private boolean droppingEgg;
	@SubscribeEvent
	public void onEggSoundPlay(PlaySoundAtEntityEvent evt) {
		if(!evt.entity.worldObj.isRemote && evt.entity instanceof EntityChicken && evt.name.equals("mob.chicken.plop"))
			droppingEgg = true;
	}
	
	@SubscribeEvent
	public void onEggDrop(EntityJoinWorldEvent evt) {
		if(evt.world.isRemote)
			return;
		
		if(evt.entity instanceof EntityItem && ((EntityItem)evt.entity).getEntityItem().getItem() == Items.egg && droppingEgg && Math.random() < EGG_BOMB_CHANCE) {
			((EntityItem)evt.entity).setEntityItemStack(new ItemStack(itemEggBomb));
		}
		droppingEgg = false;
	}
	
	@SubscribeEvent
	public void onPickUpEggBomb(EntityItemPickupEvent evt) {
		if(!evt.entity.worldObj.isRemote && evt.item.getEntityItem().getItem() == itemEggBomb) {
			evt.item.setDead();
			evt.setCanceled(true);
			ItemEggBomb.explode(evt.entity);
		}
	}
	
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent evt) {
		if(evt.phase == TickEvent.Phase.END && !evt.player.worldObj.isRemote) {
			EntityPlayer ply = evt.player;
			if(ply.getFoodStats().getFoodLevel() < 18
					&& ply.inventory.hasItem(Modjam3Mod.itemChickenBeak)
					&& ply.inventory.consumeInventoryItem(Modjam3Mod.itemChickenNugget)) {
				ply.getFoodStats().func_151686_a(Modjam3Mod.itemChickenNugget, new ItemStack(Modjam3Mod.itemChickenNugget));
			}
			
			if(ply.inventory.hasItem(Modjam3Mod.itemTransLocator)
				&& (ply.worldObj.getTotalWorldTime() % 6000) == 0)
				ply.addChatMessage(new ChatComponentTranslation("immibis_modjam3.translocator.reminder"));
				
				
		}
	}
}
