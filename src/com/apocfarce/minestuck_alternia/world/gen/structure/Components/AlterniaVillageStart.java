package com.apocfarce.minestuck_alternia.world.gen.structure.Components;

import java.util.List;
import java.util.Random;

import com.apocfarce.minestuck_alternia.world.gen.structure.PieceWeight;
import com.google.common.collect.Lists;

import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDesert;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.biome.BiomeSavanna;
import net.minecraft.world.biome.BiomeTaiga;
import net.minecraft.world.gen.structure.StructureComponent;

public class AlterniaVillageStart extends Well
{
    public BiomeProvider biomeProvider;
    /** World terrain type, 0 for normal, 1 for flap map */
    public int terrainType;
    public PieceWeight lastPlaced;
    /**
     * Contains List of all spawnable Structure Piece Weights. If no more Pieces of a type can be spawned, they
     * are removed from this list
     */
    public List<PieceWeight> structureVillageWeightedPieceList;
    public List<StructureComponent> pendingHouses = Lists.<StructureComponent>newArrayList();
    public List<StructureComponent> pendingRoads = Lists.<StructureComponent>newArrayList();
    public Biome biome;
    public World world;

    public AlterniaVillageStart()
    {
    }

    public AlterniaVillageStart(World worldIn, int p_i2104_2_, Random rand, int p_i2104_4_, int p_i2104_5_, List<PieceWeight> p_i2104_6_, int p_i2104_7_)
    {
        super((AlterniaVillageStart)null, 0, rand, p_i2104_4_, p_i2104_5_);
        this.biomeProvider = worldIn.getBiomeProvider();
        this.structureVillageWeightedPieceList = p_i2104_6_;
        this.terrainType = p_i2104_7_;
        Biome biome = worldIn.getBiomeProvider().getBiome(new BlockPos(p_i2104_4_, 0, p_i2104_5_), Biomes.DEFAULT);
        this.biome = biome;
        this.world=worldIn;
        this.startPiece=this;

        if (biome instanceof BiomeDesert)
        {
            this.structureType=1;
        }
        else if (biome instanceof BiomeSavanna)
        {
            this.structureType=2;
        }
        else if (biome instanceof BiomeTaiga)
        {
            this.structureType=3;
        }
        this.isZombieInfested=rand.nextInt(50) == 0;
    }
}
