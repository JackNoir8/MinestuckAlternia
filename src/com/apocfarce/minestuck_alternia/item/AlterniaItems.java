package com.apocfarce.minestuck_alternia.item;

import static com.apocfarce.minestuck_alternia.block.AlterniaBlocks.*;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemFood;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class AlterniaItems
{
	//the tab for future reference
	public static CreativeTabs modTab = TabAlternia.instance;
	
	//items
	public static Item item= new ExampleItem();
	
	//multi colored items
	public static Item[] bloodPotions;
	
	//food
	public static Item oblongMeatProduct=new ItemFood(6,true);
	
	
	
	
	
	
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		//get the registry
		IForgeRegistry<Item> registry = event.getRegistry();
		/**-----------------------------------
		 * items
		 *-------------------------------------*/
		//items
		registerItem(registry,item,"item");
		//food
		registerItem(registry,oblongMeatProduct,"oblong_meat_product");
		//blood colored items
		bloodPotions=new Item[13];
		for(int i=0;i<bloodPotions.length;i++) {
			bloodPotions[i]=new BloodBottle(i);
			registerItem(registry,bloodPotions[i],"blood_bottle_"+EnumBloodColors.values()[i].name().toLowerCase());
		}
		
		/**-----------------------------------
		 *blocks
		 -------------------------------------*/
		registerItemBlock(registry,new ItemBlock(block));
		registerItemBlock(registry,new ItemBlock(darkStone));
		registerItemBlock(registry,new ItemBlock(darkCobble));
		registerItemBlock(registry,new ItemBlock(redRock));
		registerItemBlock(registry,new ItemBlock(redCobble));
		for(int i=0;i<hiveGlass.length;i++) {
			registerItemBlock(registry,new ItemBlock(hiveGlass[i]));
		}
	}
	private static Item registerItem(IForgeRegistry<Item> registry,Item item, String name) {
		item.setCreativeTab(modTab);
		item.setUnlocalizedName(name);
		item.setRegistryName(name);
		registry.register(item);
		return item;
	}

	

	
	private static Item registerItemBlock(IForgeRegistry<Item> registry, ItemBlock item)
	{
		registry.register(item.setRegistryName(item.getBlock().getRegistryName()));
		return item;
	}
}
