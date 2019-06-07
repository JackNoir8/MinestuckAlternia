package com.apocfarce.minestuck_alternia.proxy;

import com.apocfarce.minestuck_alternia.block.AlterniaBlocks;
import com.apocfarce.minestuck_alternia.client.util.ModdleManager;
import com.apocfarce.minestuck_alternia.item.AlterniaItems;

import net.minecraftforge.common.MinecraftForge;

public abstract class CommonProxy {

	public void preInit() {
		//MinecraftForge.EVENT_BUS.register(AlterniaSoundHandler.instance);
		MinecraftForge.EVENT_BUS.register(AlterniaBlocks.class);
		MinecraftForge.EVENT_BUS.register(AlterniaItems.class);
		MinecraftForge.EVENT_BUS.register(ModdleManager.class);
		//MinecraftForge.EVENT_BUS.register(BiomeAlternia.class);
		
		//AlterniaSoundHandler.initSound();
		
		//AlterniaCriteriaTriggers.register();
	};
	public void init() {		
		//register ore generation
//		OreHandler oreHandler = new OreHandler();
//		GameRegistry.registerWorldGenerator(oreHandler, 0);
		
		//register GUI handler
//		NetworkRegistry.INSTANCE.registerGuiHandler(Minestuck.instance, new GuiHandler());
		
//		AlterniaEntities.registerEntities();
		//Register structures
//		MapGenStructureIO.registerStructure(StructureCastleStart.class, "SkaiaCastle");
//		MapGenLandStructure.registerStructures();
	}
	public void postInit() {
		
	}
}
