package com.apocfarce.minestuck_alternia.item;

import com.apocfarce.minestuck_alternia.Minestuck_alternia;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabAlternia extends CreativeTabs
{
	public static final TabAlternia instance = new TabAlternia("tab_"+Minestuck_alternia.name);
	
	private TabAlternia(String label)
	{
		super(label);
	}
	
	@Override
	public ItemStack getTabIconItem()
	{
		return new ItemStack(AlterniaItems.item);
	}
}