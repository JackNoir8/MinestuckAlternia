package com.apocfarce.minestuck_alternia.world.gen.structure;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.apocfarce.minestuck_alternia.world.gen.structure.Components.AlterniaVillageStart;
import com.apocfarce.minestuck_alternia.world.gen.structure.Components.Church;
import com.apocfarce.minestuck_alternia.world.gen.structure.Components.Path;
import com.apocfarce.minestuck_alternia.world.gen.structure.Components.Torch;
import com.apocfarce.minestuck_alternia.world.gen.structure.Components.VillageComponentInterface;
import com.apocfarce.minestuck_alternia.world.gen.structure.Components.Well;
import com.google.common.collect.Lists;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;



public class AlterniaVillageComponent {




	public static void registerAlterniaVillageComponents(){
		MapGenStructureIO.registerStructure(AlterniaVillageGen.Start.class, "AlterniaStart");
		
        MapGenStructureIO.registerStructureComponent(AlterniaVillageStart.class, "AViStart");
        MapGenStructureIO.registerStructureComponent(Well.class, "AViW");
        MapGenStructureIO.registerStructureComponent(Path.class, "AViSR");
        MapGenStructureIO.registerStructureComponent(Torch.class, "AViL");
        MapGenStructureIO.registerStructureComponent(Church.class, "AViCh");
		
	}
	
	public static List<PieceWeight> getStructureVillageWeightedPieceList(Random rand, int size) {
		List<PieceWeight> list = Lists.<PieceWeight>newArrayList();
        list.add(new PieceWeight(Church.class, 20, MathHelper.getInt(rand, 0 + size, 1 + size)));
        
		Iterator<PieceWeight> iterator = list.iterator();
		while (iterator.hasNext())	{
			if ((iterator.next()).villagePiecesLimit == 0){
				iterator.remove();
			}
		}
		
		return list;
	}

	private static int updatePieceWeight(List<PieceWeight> p_75079_0_)
	{
		boolean flag = false;
		int i = 0;
		
		for (PieceWeight AlterniaVillageComponent$pieceweight : p_75079_0_)
		{
			if (AlterniaVillageComponent$pieceweight.villagePiecesLimit > 0 && AlterniaVillageComponent$pieceweight.villagePiecesSpawned < AlterniaVillageComponent$pieceweight.villagePiecesLimit)
			{
				flag = true;
			}
			
			i += AlterniaVillageComponent$pieceweight.villagePieceWeight;
		}
		
		return flag ? i : -1;
	}
	
	private static VillageComponentInterface findAndCreateComponentFactory(AlterniaVillageStart start,PieceWeight weight, List<StructureComponent> structureComponents, Random rand, int structureMinX, int structureMinY, int structureMinZ, EnumFacing facing, int componentType)
	{
        Class <? extends VillageComponentInterface > oclass = weight.villagePieceClass;
        VillageComponentInterface AlterniaVillageComponent$village = null;
        if (oclass == Church.class)
        {            
        	AlterniaVillageComponent$village = Church.createPiece(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);


        }
		
		return AlterniaVillageComponent$village;
	}
	
	private static VillageComponentInterface generateComponent(AlterniaVillageStart start, List<StructureComponent> structureComponents, Random rand, int structureMinX, int structureMinY, int structureMinZ, EnumFacing facing, int componentType)
	{
		int i = updatePieceWeight(start.structureVillageWeightedPieceList);
		
		if (i <= 0)
		{
			return null;
		}
		else
		{
			int j = 0;
			
			while (j < 5)
			{
				++j;
				int k = rand.nextInt(i);
				
				for (PieceWeight AlterniaVillageComponent$pieceweight : start.structureVillageWeightedPieceList)
				{
					k -= AlterniaVillageComponent$pieceweight.villagePieceWeight;
					
					if (k < 0)
					{
						if (!AlterniaVillageComponent$pieceweight.canSpawnMoreVillagePiecesOfType(componentType) || AlterniaVillageComponent$pieceweight == start.lastPlaced && start.structureVillageWeightedPieceList.size() > 1)
						{
							break;
						}
						
						VillageComponentInterface AlterniaVillageComponent$village = findAndCreateComponentFactory(start, AlterniaVillageComponent$pieceweight, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType);
						
						if (AlterniaVillageComponent$village != null)
						{
							++AlterniaVillageComponent$pieceweight.villagePiecesSpawned;
							start.lastPlaced = AlterniaVillageComponent$pieceweight;
							
							if (!AlterniaVillageComponent$pieceweight.canSpawnMoreVillagePieces())
							{
								start.structureVillageWeightedPieceList.remove(AlterniaVillageComponent$pieceweight);
							}
							
							return AlterniaVillageComponent$village;
						}
					}
				}
			}
			
			StructureBoundingBox structureboundingbox = Torch.findPieceBox(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing);
			
			if (structureboundingbox != null)
			{
				return new Torch(start,componentType, rand, structureboundingbox, facing);
			}
			else
			{
				return null;
			}
		}
	}
	
	public static StructureComponent generateAndAddComponent(AlterniaVillageStart start, List<StructureComponent> structureComponents, Random rand, int structureMinX, int structureMinY, int structureMinZ, EnumFacing facing, int componentType)
	{
		if (componentType > 50)
		{
			return null;
		}
		else if (Math.abs(structureMinX - start.getBoundingBox().minX) <= 112 && Math.abs(structureMinZ - start.getBoundingBox().minZ) <= 112)
		{
			VillageComponentInterface component= generateComponent(start, structureComponents, rand, structureMinX, structureMinY, structureMinZ, facing, componentType + 1);
			if(component instanceof StructureComponent) {
				StructureComponent structurecomponent = (StructureComponent)component;
				structureComponents.add(structurecomponent);
				start.pendingHouses.add(structurecomponent);
				return structurecomponent;
			}
			else
			{
				return null;
			}
		}
		else
		{
			return null;
		}
	}
	
	public static StructureComponent generateAndAddRoadPiece(AlterniaVillageStart start, List<StructureComponent> p_176069_1_, Random rand, int structureMinX, int structureMinY, int structureMinZ, EnumFacing facing, int p_176069_7_)
	{
		if (p_176069_7_ > 3 + start.terrainType)
		{
			return null;
		}
		else if (Math.abs(structureMinX - start.getBoundingBox().minX) <= 112 && Math.abs(structureMinZ - start.getBoundingBox().minZ) <= 112)
		{
			StructureBoundingBox structureboundingbox = Path.findPieceBox(start, p_176069_1_, rand, structureMinX, structureMinY, structureMinZ, facing);
			
			if (structureboundingbox != null && structureboundingbox.minY > 10)
			{
				StructureComponent structurecomponent = new Path(start, p_176069_7_, rand, structureboundingbox, facing);
				p_176069_1_.add(structurecomponent);
				start.pendingRoads.add(structurecomponent);
				return structurecomponent;
			}
			else
			{
				return null;
			}
		}
		else
		{
			return null;
		}
	}
	
	
	
	


  




}
