package com.apocfarce.minestuck_alternia.block;

import java.util.Random;

import com.apocfarce.minestuck_alternia.item.TabAlternia;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

public class DarkStone extends Block {
	public DarkStone() {
		super(Material.ROCK);
		setCreativeTab(TabAlternia.instance);
		setUnlocalizedName("dark_stone");
		setRegistryName("dark_stone");
		setSoundType(SoundType.GROUND);
		setHardness(1.5F);
		setResistance(10.0F);
		setHarvestLevel("pickaxe", 0);
	}
	@Override
	public Item getItemDropped(IBlockState state,Random rand,int fortune){
		return AlterniaBlocks.darkCobble.getItemDropped(state, rand, fortune);
	}
}
