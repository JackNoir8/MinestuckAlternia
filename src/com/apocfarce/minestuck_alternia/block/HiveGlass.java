package com.apocfarce.minestuck_alternia.block;

import com.apocfarce.minestuck_alternia.item.EnumBloodColors;
import com.apocfarce.minestuck_alternia.item.TabAlternia;

import net.minecraft.block.BlockPane;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;

public class HiveGlass  extends BlockPane {
	
	public EnumBloodColors color;
	
	public HiveGlass(EnumBloodColors bloodColor) {
		super(Material.GLASS, false);
		color=bloodColor;
		setCreativeTab(TabAlternia.instance);
		setUnlocalizedName("hiveglass_"+bloodColor.name().toLowerCase());
		setRegistryName("hiveglass_"+bloodColor.name().toLowerCase());
		this.setSoundType(SoundType.GLASS);
	}
	public HiveGlass(int rank) {
		this(EnumBloodColors.values()[rank]);
	}
	public EnumBloodColors GetBloodColor() {
		return color;
	}
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}

	
	
}
