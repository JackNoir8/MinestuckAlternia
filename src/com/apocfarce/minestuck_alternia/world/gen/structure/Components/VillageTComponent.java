package com.apocfarce.minestuck_alternia.world.gen.structure.Components;

import java.util.Random;

import net.minecraft.block.BlockTorch;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.TemplateManager;

public abstract  class VillageTComponent extends StructureComponent implements VillageComponentInterface{
	
	protected int averageGroundLvl = -1;
	private int structureType;
	private boolean isZombieInfested;
	private int VillagersSpawned;
	private AlterniaVillageStart startPiece;
	
	public VillageTComponent() {}
    protected VillageTComponent(AlterniaVillageStart start, int type)
    {
        super(type);

        if (start != null)
        {
            SetStructureType(start.GetStructureType());
            SetIsZombieInfested(start.GetIsZombieInfested());
            SetStartPiece(start);
        }
    }
	public void SetStructureType(int type) {
		this.structureType=type;
		
	}
	public void SetVillagersSpawned(int VillagersSpawned) {
		this.VillagersSpawned=VillagersSpawned;
		
	}
	public void SetAverageGroundLvl(int level) {
		this.averageGroundLvl=level;
	}
	public void SetIsZombieInfested(boolean zombie) {
		this.isZombieInfested=zombie;
	}
	public int GetStructureType() {
		return this.structureType;
	}
	public int GetVillagersSpawned() {
		return VillagersSpawned;
	}
	public boolean GetIsZombieInfested() {
		return isZombieInfested;
	}
	public int GetAverageGroundLvl() {
		return averageGroundLvl;
	}
	public AlterniaVillageStart GetStartPiece() {
		return startPiece;
	}
	public void SetStartPiece(AlterniaVillageStart start) {
		this.startPiece=start;
	}
	@Override
	public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn) {
		// TODO Auto-generated method stub
		return false;
	}
	protected void writeStructureToNBT(NBTTagCompound tagCompound)
	{
		tagCompound.setInteger("HPos", GetAverageGroundLvl());
		tagCompound.setInteger("VCount", GetVillagersSpawned());
		tagCompound.setByte("Type", (byte)GetStructureType());
		tagCompound.setBoolean("Zombie", GetIsZombieInfested());
	}


	protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager manager)
	{
		SetAverageGroundLvl(tagCompound.getInteger("HPos"));
		SetVillagersSpawned(tagCompound.getInteger("VCount"));
		SetStructureType(tagCompound.getByte("Type"));
		
		if (tagCompound.getBoolean("Desert"))
		{
			SetStructureType(1);
		}
		
		SetIsZombieInfested(tagCompound.getBoolean("Zombie"));
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
//            this.generateDoor(p_189927_1_, p_189927_2_, p_189927_3_, p_189927_4_, p_189927_5_, p_189927_6_, EnumFacing.NORTH, this.biomeDoor());
        }
    }






}


