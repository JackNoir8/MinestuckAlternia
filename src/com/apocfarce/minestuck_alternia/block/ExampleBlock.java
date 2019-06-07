/**
 * an example class to ensure that a block can be made
 * delete before releasing anything to the public
 */
package com.apocfarce.minestuck_alternia.block;

import com.apocfarce.minestuck_alternia.item.TabAlternia;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ExampleBlock extends Block {

	public ExampleBlock() {
		super(Material.CLAY);
		setCreativeTab(TabAlternia.instance);
		setUnlocalizedName("block");
		setRegistryName("block");
	}
}