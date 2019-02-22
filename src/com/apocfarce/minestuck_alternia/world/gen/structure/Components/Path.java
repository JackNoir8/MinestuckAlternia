package com.apocfarce.minestuck_alternia.world.gen.structure.Components;

import java.util.List;
import java.util.Random;

import com.apocfarce.minestuck_alternia.world.gen.structure.AlterniaVillageComponent;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.TemplateManager;


public class Path extends VillageSComponent
{
   
       private int length;

       public Path()
       {
       }

       public Path(AlterniaVillageStart start, int p_i45562_2_, Random rand, StructureBoundingBox p_i45562_4_, EnumFacing facing)
       {
           super(start, p_i45562_2_);
           this.setCoordBaseMode(facing);
           this.boundingBox = p_i45562_4_;
           this.length = Math.max(p_i45562_4_.getXSize(), p_i45562_4_.getZSize());
       }

       /**
        * (abstract) Helper method to write subclass data to NBT
        */
       public void writeStructureToNBT(NBTTagCompound tagCompound)
       {
           super.writeStructureToNBT(tagCompound);
           tagCompound.setInteger("Length", this.length);
       }

       /**
        * (abstract) Helper method to read subclass data from NBT
        */
       public void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
       {
           super.readStructureFromNBT(tagCompound, p_143011_2_);
           this.length = tagCompound.getInteger("Length");
       }

       /**
        * Initiates construction of the Structure Component picked, at the current Location of StructGen
        */
       public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand)
       {
           boolean flag = false;

           for (int i = rand.nextInt(5); i < this.length - 8; i += 2 + rand.nextInt(5))
           {
               StructureComponent structurecomponent = this.getNextComponentNN((AlterniaVillageStart)componentIn, listIn, rand, 0, i);

               if (structurecomponent != null)
               {
                   i += Math.max(structurecomponent.getBoundingBox().getXSize(), structurecomponent.getBoundingBox().getZSize());
                   flag = true;
               }
           }

           for (int j = rand.nextInt(5); j < this.length - 8; j += 2 + rand.nextInt(5))
           {
               StructureComponent structurecomponent1 = this.getNextComponentPP((AlterniaVillageStart)componentIn, listIn, rand, 0, j);

               if (structurecomponent1 != null)
               {
                   j += Math.max(structurecomponent1.getBoundingBox().getXSize(), structurecomponent1.getBoundingBox().getZSize());
                   flag = true;
               }
           }

           EnumFacing enumfacing = this.getCoordBaseMode();

           if (flag && rand.nextInt(3) > 0 && enumfacing != null)
           {
               switch (enumfacing)
               {
                   case NORTH:
                   default:
                	   AlterniaVillageComponent.generateAndAddRoadPiece((AlterniaVillageStart)componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.WEST, this.getComponentType());
                       break;
                   case SOUTH:
                	   AlterniaVillageComponent.generateAndAddRoadPiece((AlterniaVillageStart)componentIn, listIn, rand, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, EnumFacing.WEST, this.getComponentType());
                       break;
                   case WEST:
                	   AlterniaVillageComponent.generateAndAddRoadPiece((AlterniaVillageStart)componentIn, listIn, rand, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());
                       break;
                   case EAST:
                	   AlterniaVillageComponent.generateAndAddRoadPiece((AlterniaVillageStart)componentIn, listIn, rand, this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());
               }
           }

           if (flag && rand.nextInt(3) > 0 && enumfacing != null)
           {
               switch (enumfacing)
               {
                   case NORTH:
                   default:
                       AlterniaVillageComponent.generateAndAddRoadPiece((AlterniaVillageStart)componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.EAST, this.getComponentType());
                       break;
                   case SOUTH:
                	   AlterniaVillageComponent.generateAndAddRoadPiece((AlterniaVillageStart)componentIn, listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.maxZ - 2, EnumFacing.EAST, this.getComponentType());
                       break;
                   case WEST:
                	   AlterniaVillageComponent.generateAndAddRoadPiece((AlterniaVillageStart)componentIn, listIn, rand, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
                       break;
                   case EAST:
                	   AlterniaVillageComponent.generateAndAddRoadPiece((AlterniaVillageStart)componentIn, listIn, rand, this.boundingBox.maxX - 2, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
               }
           }
       }

       public static StructureBoundingBox findPieceBox(AlterniaVillageStart start, List<StructureComponent> p_175848_1_, Random rand, int p_175848_3_, int p_175848_4_, int p_175848_5_, EnumFacing facing)
       {
           for (int i = 7 * MathHelper.getInt(rand, 3, 5); i >= 7; i -= 7)
           {
               StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175848_3_, p_175848_4_, p_175848_5_, 0, 0, 0, 3, 3, i, facing);

               if (StructureComponent.findIntersecting(p_175848_1_, structureboundingbox) == null)
               {
                   return structureboundingbox;
               }
           }

           return null;
       }

       /**
        * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
        * Mineshafts at the end, it adds Fences...
        */
       public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
       {
           IBlockState iblockstate = this.getBiomeSpecificBlockState(Blocks.GRASS_PATH.getDefaultState());
           IBlockState iblockstate1 = this.getBiomeSpecificBlockState(Blocks.PLANKS.getDefaultState());
           IBlockState iblockstate2 = this.getBiomeSpecificBlockState(Blocks.GRAVEL.getDefaultState());
           IBlockState iblockstate3 = this.getBiomeSpecificBlockState(Blocks.COBBLESTONE.getDefaultState());

           for (int i = this.boundingBox.minX; i <= this.boundingBox.maxX; ++i)
           {
               for (int j = this.boundingBox.minZ; j <= this.boundingBox.maxZ; ++j)
               {
                   BlockPos blockpos = new BlockPos(i, 64, j);

                   if (structureBoundingBoxIn.isVecInside(blockpos))
                   {
                       blockpos = worldIn.getTopSolidOrLiquidBlock(blockpos).down();

                       if (blockpos.getY() < worldIn.getSeaLevel())
                       {
                           blockpos = new BlockPos(blockpos.getX(), worldIn.getSeaLevel() - 1, blockpos.getZ());
                       }

                       while (blockpos.getY() >= worldIn.getSeaLevel() - 1)
                       {
                           IBlockState iblockstate4 = worldIn.getBlockState(blockpos);

                           if (iblockstate4.getBlock() == Blocks.GRASS && worldIn.isAirBlock(blockpos.up()))
                           {
                               worldIn.setBlockState(blockpos, iblockstate, 2);
                               break;
                           }

                           if (iblockstate4.getMaterial().isLiquid())
                           {
                               worldIn.setBlockState(blockpos, iblockstate1, 2);
                               break;
                           }

                           if (iblockstate4.getBlock() == Blocks.SAND || iblockstate4.getBlock() == Blocks.SANDSTONE || iblockstate4.getBlock() == Blocks.RED_SANDSTONE)
                           {
                               worldIn.setBlockState(blockpos, iblockstate2, 2);
                               worldIn.setBlockState(blockpos.down(), iblockstate3, 2);
                               break;
                           }

                           blockpos = blockpos.down();
                       }
                   }
               }
           }

           return true;
       }
   }

