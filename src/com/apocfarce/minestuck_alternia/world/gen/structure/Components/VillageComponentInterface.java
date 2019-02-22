package com.apocfarce.minestuck_alternia.world.gen.structure.Components;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.apocfarce.minestuck_alternia.world.gen.structure.AlterniaVillageComponent;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

public interface VillageComponentInterface 
{
	//our variable's setters
//	void SetStructureType(int type);
	
//	void SetVillagersSpawned(int VillagersSpawned);
	
//	void SetAverageGroundLvl(int Level);
	
//	void SetIsZombieInfested(boolean zombie);
	
	//our variable's getters
//	int GetStructureType();
	
//	int GetVillagersSpawned();
	
//	int GetAverageGroundLvl();
	
//	boolean GetIsZombieInfested();
	
	//structure component specific variables

//	EnumFacing getCoordBaseMode();

	StructureBoundingBox getBoundingBox();

//	AlterniaVillageStart GetStartPiece();
	
//	void SetStartPiece(AlterniaVillageStart start);
	
	
	default int getAverageGroundLevel(World worldIn, StructureBoundingBox structurebb)
	{
		int i = 0;
		int j = 0;
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

		for (int k = getBoundingBox().minZ; k <= getBoundingBox().maxZ; ++k)
		{
			for (int l = getBoundingBox().minX; l <= getBoundingBox().maxX; ++l)
			{
				blockpos$mutableblockpos.setPos(l, 64, k);
				
				if (structurebb.isVecInside(blockpos$mutableblockpos))
				{
					i += Math.max(worldIn.getTopSolidOrLiquidBlock(blockpos$mutableblockpos).getY(), worldIn.provider.getAverageGroundLevel() - 1);
					++j;
				}	
			}
		}

		if (j == 0)
		{
			return -1;
		}
		else
		{
			return i / j;
		}
	}

	static boolean canVillageGoDeeper(StructureBoundingBox structurebb)
	{
		return structurebb != null && structurebb.minY > 10;
	}	


	

	




    
}

