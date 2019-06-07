package com.apocfarce.minestuck_alternia.block;

import com.apocfarce.minestuck_alternia.item.EnumBloodColors;

import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class AlterniaBlocks
{
	//Blocks
	public static Block block = new ExampleBlock();
	public static Block darkStone = new DarkStone();
	public static Block darkCobble = new DarkCobble();
	public static Block redRock = new RedRock();
	public static Block redCobble = new RedCobble();
	//BloodColoredBlocks
	public static Block hiveGlass[];
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		
		IForgeRegistry<Block> registry = event.getRegistry();
		//blood colored blocks
		hiveGlass=new Block[13];
		for(int i=0;i<hiveGlass.length-2;i++) {
			hiveGlass[i]=new HiveGlass(i);
			registry.register(hiveGlass[i]);
		}
		hiveGlass[hiveGlass.length-2]=new HiveGlass(EnumBloodColors.LIME);
		registry.register(hiveGlass[hiveGlass.length-2]);
		//grey hive glass
		hiveGlass[hiveGlass.length-1]=new HiveGlass(EnumBloodColors.GREY);
		registry.register(hiveGlass[hiveGlass.length-1]);

			
		//blocks
		final Block[] blocks = {block,darkStone,darkCobble,redRock,redCobble};
		
		
		for(Block block : blocks) {
			registry.register(block);
		}

	}

}