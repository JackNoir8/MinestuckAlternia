package com.apocfarce.minestuck_alternia.world.gen.structure;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.apocfarce.minestuck_alternia.world.gen.structure.Components.AlterniaVillageStart;
import com.apocfarce.minestuck_alternia.world.gen.structure.Components.Path;

import net.minecraft.init.Biomes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;

public class AlterniaVillageGen extends MapGenStructure
{
    /** A list of all the biomes villages can spawn in. */
    public static List<Biome> VILLAGE_SPAWN_BIOMES = Arrays.<Biome>asList(Biomes.PLAINS, Biomes.DESERT, Biomes.SAVANNA, Biomes.TAIGA);
    /** None */
    private int size;
    private int distance;

    public AlterniaVillageGen()
    {
        this.distance = 16;
    }

    public AlterniaVillageGen(Map<String, String> map)
    {
        this();

        for (Entry<String, String> entry : map.entrySet())
        {
            if (((String)entry.getKey()).equals("size"))
            {
                this.size = MathHelper.getInt(entry.getValue(), this.size, 0);
            }
            else if (((String)entry.getKey()).equals("distance"))
            {
                this.distance = MathHelper.getInt(entry.getValue(), this.distance, 9);
            }
        }
    }

    public String getStructureName()
    {
        return "AlterniaVillage";
    }

    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ)
    {
        int i = chunkX;
        int j = chunkZ;

        if (chunkX < 0)
        {
            chunkX -= this.distance - 1;
        }

        if (chunkZ < 0)
        {
            chunkZ -= this.distance - 1;
        }

        int k = chunkX / this.distance;
        int l = chunkZ / this.distance;
        Random random = this.world.setRandomSeed(k, l, 12397312);
        k = k * this.distance;
        l = l * this.distance;
        k = k + random.nextInt(this.distance - 8);
        l = l + random.nextInt(this.distance - 8);

        if (i == k && j == l)
        {
            boolean flag = this.world.getBiomeProvider().areBiomesViable(i * 16 + 8, j * 16 + 8, 0, VILLAGE_SPAWN_BIOMES);

            if (flag)
            {
                return true;
            }
        }

        return false;
    }
  
    
    

    public BlockPos getNearestStructurePos(World worldIn, BlockPos pos, boolean findUnexplored)
    {
        this.world = worldIn;
        return findNearestStructurePosBySpacing(worldIn, this, pos, this.distance, 8, 10387312, false, 100, findUnexplored);
    }

    protected StructureStart getStructureStart(int chunkX, int chunkZ)
    {
        return new AlterniaVillageGen.Start(this.world, this.rand, chunkX, chunkZ, this.size);
    }

    public static class Start extends StructureStart
        {
            /** well ... thats what it does */
            private boolean hasMoreThanTwoComponents;

            public Start()
            {
            }

            public Start(World worldIn, Random rand, int chunkx, int chunkz, int size)
            {
                super(chunkx, chunkz);
                List<PieceWeight> list = AlterniaVillageComponent.getStructureVillageWeightedPieceList(rand, size);
                AlterniaVillageStart Alterniapieces$start = new AlterniaVillageStart(worldIn, 0, rand, (chunkx << 4) + 2, (chunkz << 4) + 2, list, size);
                this.components.add(Alterniapieces$start);
                Alterniapieces$start.buildComponent(Alterniapieces$start, this.components, rand);
                List<StructureComponent> list1 = Alterniapieces$start.pendingRoads;
                List<StructureComponent> list2 = Alterniapieces$start.pendingHouses;

                while (!list1.isEmpty() || !list2.isEmpty())
                {
                    if (list1.isEmpty())
                    {
                        int i = rand.nextInt(list2.size());
                        StructureComponent structurecomponent = list2.remove(i);
                        structurecomponent.buildComponent(Alterniapieces$start, this.components, rand);
                    }
                    else
                    {
                        int j = rand.nextInt(list1.size());
                        StructureComponent structurecomponent2 = list1.remove(j);
                        structurecomponent2.buildComponent(Alterniapieces$start, this.components, rand);
                    }
                }

                this.updateBoundingBox();
                int k = 0;

                for (StructureComponent structurecomponent1 : this.components)
                {
                    if (!(structurecomponent1 instanceof Path))
                    {
                        ++k;
                    }
                }

                this.hasMoreThanTwoComponents = true;
            }

            /**
             * currently only defined for Villages, returns true if Village has more than 2 non-road components
             */
            public boolean isSizeableStructure()
            {
                return this.hasMoreThanTwoComponents;
            }

            public void writeToNBT(NBTTagCompound tagCompound)
            {
                super.writeToNBT(tagCompound);
                tagCompound.setBoolean("Valid", this.hasMoreThanTwoComponents);
            }

            public void readFromNBT(NBTTagCompound tagCompound)
            {
                super.readFromNBT(tagCompound);
                this.hasMoreThanTwoComponents = tagCompound.getBoolean("Valid");
            }
        }
}