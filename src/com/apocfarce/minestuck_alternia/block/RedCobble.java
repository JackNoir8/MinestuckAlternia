package com.apocfarce.minestuck_alternia.block;

import com.apocfarce.minestuck_alternia.item.TabAlternia;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class RedCobble extends Block {
	public RedCobble() {
		super(Material.ROCK);
		setCreativeTab(TabAlternia.instance);
		setUnlocalizedName("red_cobble");
		setRegistryName("red_cobble");
		this.setSoundType(SoundType.GROUND);
		setHardness(1.5F);
		setResistance(10.0F);
		setHarvestLevel("pickaxe", 0);
		
	}
}
