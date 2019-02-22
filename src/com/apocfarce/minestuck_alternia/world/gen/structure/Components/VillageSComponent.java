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
import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.TemplateManager;

public abstract  class VillageSComponent extends StructureComponent implements VillageComponentInterface{
	
	protected int averageGroundLvl = -1;
	protected int structureType;
	protected boolean isZombieInfested;
	private int VillagersSpawned;
	protected AlterniaVillageStart startPiece;
	
	public VillageSComponent() {}
    protected VillageSComponent(AlterniaVillageStart start, int type)
    {
        super(type);

        if (start != null)
        {
            this.structureType=start.GetStructureType();
            this.isZombieInfested=start.GetIsZombieInfested();
            this.startPiece=start;
            
        }
    }
    public int GetStructureType() {
    	return structureType;
    }
    public boolean GetIsZombieInfested() {
    	return isZombieInfested;
    }
	public int GetAverageGroundLvl() {
		return averageGroundLvl;
	}

	
	@Nullable
	public StructureComponent getNextComponentNN(AlterniaVillageStart start, List<StructureComponent> structureComponents, Random rand, int p_74891_4_, int p_74891_5_)
	{
		EnumFacing enumfacing = this.getCoordBaseMode();
		
		if (enumfacing != null)
		{
			switch (enumfacing)
			{
			case NORTH:
			default:
				return AlterniaVillageComponent.generateAndAddComponent(start, structureComponents, rand, getBoundingBox().minX - 1, getBoundingBox().minY + p_74891_4_, getBoundingBox().minZ + p_74891_5_, EnumFacing.WEST, this.GetStructureType());
			case SOUTH:
				return AlterniaVillageComponent.generateAndAddComponent(start, structureComponents, rand, getBoundingBox().minX - 1, getBoundingBox().minY + p_74891_4_, getBoundingBox().minZ + p_74891_5_, EnumFacing.WEST, this.GetStructureType());
            case WEST:
                return AlterniaVillageComponent.generateAndAddComponent(start, structureComponents, rand, getBoundingBox().minX + p_74891_5_, getBoundingBox().minY + p_74891_4_, getBoundingBox().minZ - 1, EnumFacing.NORTH, this.GetStructureType());
            case EAST:
                return AlterniaVillageComponent.generateAndAddComponent(start, structureComponents, rand, getBoundingBox().minX + p_74891_5_, getBoundingBox().minY + p_74891_4_, getBoundingBox().minZ - 1, EnumFacing.NORTH, this.GetStructureType());
			}
		}
		else
		{
			return null;
		}
	}




	@Nullable
	public StructureComponent getNextComponentPP(AlterniaVillageStart start, List<StructureComponent> structureComponents, Random rand, int p_74894_4_, int p_74894_5_)
	{
		EnumFacing enumfacing = this.getCoordBaseMode();
		
		if (enumfacing != null)
		{
			switch (enumfacing)
			{
			case NORTH:
            default:
                return AlterniaVillageComponent.generateAndAddComponent(start, structureComponents, rand, getBoundingBox().maxX + 1, getBoundingBox().minY + p_74894_4_, getBoundingBox().minZ + p_74894_5_, EnumFacing.EAST, this.GetStructureType());
            case SOUTH:
                return AlterniaVillageComponent.generateAndAddComponent(start, structureComponents, rand, getBoundingBox().maxX + 1, getBoundingBox().minY + p_74894_4_, getBoundingBox().minZ + p_74894_5_, EnumFacing.EAST, this.GetStructureType());
            case WEST:
                return AlterniaVillageComponent.generateAndAddComponent(start, structureComponents, rand, getBoundingBox().minX + p_74894_5_, getBoundingBox().minY + p_74894_4_, getBoundingBox().maxZ + 1, EnumFacing.SOUTH, this.GetStructureType());
            case EAST:
                return AlterniaVillageComponent.generateAndAddComponent(start, structureComponents, rand, getBoundingBox().minX + p_74894_5_, getBoundingBox().minY + p_74894_4_, getBoundingBox().maxZ + 1, EnumFacing.SOUTH, this.GetStructureType());
			}
		}
		else
		{
			return null;
		}
	}
	
	
	@Deprecated // Use Forge version below.
	public int chooseProfession(int villagersSpawnedIn, int currentVillagerProfession)
	{
		return currentVillagerProfession;
	}
	public net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession chooseForgeProfession(int count, net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession prof)
	{
		return net.minecraftforge.fml.common.registry.VillagerRegistry.getById(chooseProfession(count, net.minecraftforge.fml.common.registry.VillagerRegistry.getId(prof)));
	}
	
	
	public IBlockState getBiomeSpecificBlockState(IBlockState blockstateIn)
	{
		net.minecraftforge.event.terraingen.BiomeEvent.GetVillageBlockID event = new net.minecraftforge.event.terraingen.BiomeEvent.GetVillageBlockID(this.startPiece == null ? null : this.startPiece.biome, blockstateIn);
        net.minecraftforge.common.MinecraftForge.TERRAIN_GEN_BUS.post(event);
        if (event.getResult() == net.minecraftforge.fml.common.eventhandler.Event.Result.DENY)
        {
        	return event.getReplacement();
        }
        if (GetStructureType() == 1)
        {
            if (blockstateIn.getBlock() == Blocks.LOG || blockstateIn.getBlock() == Blocks.LOG2)
            {
                return Blocks.SANDSTONE.getDefaultState();
            }

            if (blockstateIn.getBlock() == Blocks.COBBLESTONE)
            {
                return Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.DEFAULT.getMetadata());
            }

            if (blockstateIn.getBlock() == Blocks.PLANKS)
            {
                return Blocks.SANDSTONE.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata());
            }

            if (blockstateIn.getBlock() == Blocks.OAK_STAIRS)
            {
                return Blocks.SANDSTONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, blockstateIn.getValue(BlockStairs.FACING));
            }

            if (blockstateIn.getBlock() == Blocks.STONE_STAIRS)
            {
                return Blocks.SANDSTONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, blockstateIn.getValue(BlockStairs.FACING));
            }

            if (blockstateIn.getBlock() == Blocks.GRAVEL)
            {
                return Blocks.SANDSTONE.getDefaultState();
            }
        }
        else if (GetStructureType() == 3)
        {
            if (blockstateIn.getBlock() == Blocks.LOG || blockstateIn.getBlock() == Blocks.LOG2)
            {
                return Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.SPRUCE).withProperty(BlockLog.LOG_AXIS, blockstateIn.getValue(BlockLog.LOG_AXIS));
            }

            if (blockstateIn.getBlock() == Blocks.PLANKS)
            {
                return Blocks.PLANKS.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.SPRUCE);
            }

            if (blockstateIn.getBlock() == Blocks.OAK_STAIRS)
            {
                return Blocks.SPRUCE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, blockstateIn.getValue(BlockStairs.FACING));
            }

            if (blockstateIn.getBlock() == Blocks.OAK_FENCE)
            {
                return Blocks.SPRUCE_FENCE.getDefaultState();
            }
        }
        else if (GetStructureType() == 2)
        {
            if (blockstateIn.getBlock() == Blocks.LOG || blockstateIn.getBlock() == Blocks.LOG2)
            {
                return Blocks.LOG2.getDefaultState().withProperty(BlockNewLog.VARIANT, BlockPlanks.EnumType.ACACIA).withProperty(BlockLog.LOG_AXIS, blockstateIn.getValue(BlockLog.LOG_AXIS));
            }

            if (blockstateIn.getBlock() == Blocks.PLANKS)
            {
                return Blocks.PLANKS.getDefaultState().withProperty(BlockPlanks.VARIANT, BlockPlanks.EnumType.ACACIA);
            }

            if (blockstateIn.getBlock() == Blocks.OAK_STAIRS)
            {
                return Blocks.ACACIA_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, blockstateIn.getValue(BlockStairs.FACING));
            }

            if (blockstateIn.getBlock() == Blocks.COBBLESTONE)
            {
                return Blocks.LOG2.getDefaultState().withProperty(BlockNewLog.VARIANT, BlockPlanks.EnumType.ACACIA).withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y);
            }

            if (blockstateIn.getBlock() == Blocks.OAK_FENCE)
            {
                return Blocks.ACACIA_FENCE.getDefaultState();
            }
        }

        return blockstateIn;
    }
	
	
	public BlockDoor biomeDoor()
    {
        switch (GetStructureType())
        {
            case 2:
                return Blocks.ACACIA_DOOR;
            case 3:
                return Blocks.SPRUCE_DOOR;
            default:
                return Blocks.OAK_DOOR;
        }
    }

	
	
	
	
	
	
	
	
	
	
	
	@Override
	public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
		// TODO Auto-generated method stub
		return false;
	}
	protected void writeStructureToNBT(NBTTagCompound tagCompound)
	{
		tagCompound.setInteger("HPos", GetAverageGroundLvl());
		tagCompound.setInteger("VCount", this.VillagersSpawned);
		tagCompound.setByte("Type", (byte)GetStructureType());
		tagCompound.setBoolean("Zombie", GetIsZombieInfested());
	}


	protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager manager)
	{
		this.averageGroundLvl=tagCompound.getInteger("HPos");
		this.VillagersSpawned=tagCompound.getInteger("VCount");
		this.structureType=tagCompound.getByte("Type");
		
		if (tagCompound.getBoolean("Desert"))
		{
			this.structureType=1;
		}
		
		this.isZombieInfested=tagCompound.getBoolean("Zombie");
	}
	
	
	
	
	
    protected void placeTorch(World p_189926_1_, EnumFacing p_189926_2_, int p_189926_3_, int p_189926_4_, int p_189926_5_, StructureBoundingBox p_189926_6_)
    {
    	
        if (!this.isZombieInfested)
        {
            this.setBlockState(p_189926_1_, Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, p_189926_2_), p_189926_3_, p_189926_4_, p_189926_5_, p_189926_6_);
        }
    }
    protected void createVillageDoor(World p_189927_1_, StructureBoundingBox p_189927_2_, Random p_189927_3_, int p_189927_4_, int p_189927_5_, int p_189927_6_, EnumFacing p_189927_7_)
    {
        if (!this.isZombieInfested)
        {
            this.generateDoor(p_189927_1_, p_189927_2_, p_189927_3_, p_189927_4_, p_189927_5_, p_189927_6_, EnumFacing.NORTH, this.biomeDoor());
        }
    }






}


