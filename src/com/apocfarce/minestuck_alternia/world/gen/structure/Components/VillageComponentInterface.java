package com.apocfarce.minestuck_alternia.world.gen.structure.Components;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.apocfarce.minestuck_alternia.world.gen.structure.AlterniaVillageComponent;
import com.apocfarce.minestuck_alternia.world.gen.structure.AlterniaVillageGen.Start;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.gen.structure.template.TemplateManager;

public abstract class VillageComponentInterface extends StructureComponent
        {
            protected int averageGroundLvl = -1;
            /** The number of villagers that have been spawned in this component. */
            private int villagersSpawned;
            protected int structureType;
            protected boolean isZombieInfested;
            protected AlterniaVillageStart startPiece;

            public VillageComponentInterface()
            {
            }

            protected VillageComponentInterface(AlterniaVillageStart start, int type)
            {
                super(type);

                if (start != null)
                {
                    this.structureType = start.structureType;
                    this.isZombieInfested = start.isZombieInfested;
                    startPiece = start;
                }
            }

            /**
             * (abstract) Helper method to write subclass data to NBT
             */
            protected void writeStructureToNBT(NBTTagCompound tagCompound)
            {
                tagCompound.setInteger("HPos", this.averageGroundLvl);
                tagCompound.setInteger("VCount", this.villagersSpawned);
                tagCompound.setByte("Type", (byte)this.structureType);
                tagCompound.setBoolean("Zombie", this.isZombieInfested);
            }

            /**
             * (abstract) Helper method to read subclass data from NBT
             */
            protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
            {
                this.averageGroundLvl = tagCompound.getInteger("HPos");
                this.villagersSpawned = tagCompound.getInteger("VCount");
                this.structureType = tagCompound.getByte("Type");

                if (tagCompound.getBoolean("Desert"))
                {
                    this.structureType = 1;
                }

                this.isZombieInfested = tagCompound.getBoolean("Zombie");
            }

            /**
             * Gets the next village component, with the bounding box shifted -1 in the X and Z direction.
             */
            @Nullable
            protected StructureComponent getNextComponentNN(AlterniaVillageStart start, List<StructureComponent> structureComponents, Random rand, int p_74891_4_, int p_74891_5_)
            {
                EnumFacing enumfacing = this.getCoordBaseMode();

                if (enumfacing != null)
                {
                    switch (enumfacing)
                    {
                        case NORTH:
                        default:
                            return AlterniaVillageComponent.generateAndAddComponent(start, structureComponents, rand, this.boundingBox.minX - 1, this.boundingBox.minY + p_74891_4_, this.boundingBox.minZ + p_74891_5_, EnumFacing.WEST, this.getComponentType());
                        case SOUTH:
                            return AlterniaVillageComponent.generateAndAddComponent(start, structureComponents, rand, this.boundingBox.minX - 1, this.boundingBox.minY + p_74891_4_, this.boundingBox.minZ + p_74891_5_, EnumFacing.WEST, this.getComponentType());
                        case WEST:
                            return AlterniaVillageComponent.generateAndAddComponent(start, structureComponents, rand, this.boundingBox.minX + p_74891_5_, this.boundingBox.minY + p_74891_4_, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());
                        case EAST:
                            return AlterniaVillageComponent.generateAndAddComponent(start, structureComponents, rand, this.boundingBox.minX + p_74891_5_, this.boundingBox.minY + p_74891_4_, this.boundingBox.minZ - 1, EnumFacing.NORTH, this.getComponentType());
                    }
                }
                else
                {
                    return null;
                }
            }

            /**
             * Gets the next village component, with the bounding box shifted +1 in the X and Z direction.
             */
            @Nullable
            protected StructureComponent getNextComponentPP(AlterniaVillageStart start, List<StructureComponent> structureComponents, Random rand, int p_74894_4_, int p_74894_5_)
            {
                EnumFacing enumfacing = this.getCoordBaseMode();

                if (enumfacing != null)
                {
                    switch (enumfacing)
                    {
                        case NORTH:
                        default:
                            return AlterniaVillageComponent.generateAndAddComponent(start, structureComponents, rand, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74894_4_, this.boundingBox.minZ + p_74894_5_, EnumFacing.EAST, this.getComponentType());
                        case SOUTH:
                            return AlterniaVillageComponent.generateAndAddComponent(start, structureComponents, rand, this.boundingBox.maxX + 1, this.boundingBox.minY + p_74894_4_, this.boundingBox.minZ + p_74894_5_, EnumFacing.EAST, this.getComponentType());
                        case WEST:
                            return AlterniaVillageComponent.generateAndAddComponent(start, structureComponents, rand, this.boundingBox.minX + p_74894_5_, this.boundingBox.minY + p_74894_4_, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
                        case EAST:
                            return AlterniaVillageComponent.generateAndAddComponent(start, structureComponents, rand, this.boundingBox.minX + p_74894_5_, this.boundingBox.minY + p_74894_4_, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, this.getComponentType());
                    }
                }
                else
                {
                    return null;
                }
            }

            /**
             * Discover the y coordinate that will serve as the ground level of the supplied BoundingBox. (A median of
             * all the levels in the BB's horizontal rectangle).
             */
            protected int getAverageGroundLevel(World worldIn, StructureBoundingBox structurebb)
            {
                int i = 0;
                int j = 0;
                BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                for (int k = this.boundingBox.minZ; k <= this.boundingBox.maxZ; ++k)
                {
                    for (int l = this.boundingBox.minX; l <= this.boundingBox.maxX; ++l)
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

            protected static boolean canVillageGoDeeper(StructureBoundingBox structurebb)
            {
                return structurebb != null && structurebb.minY > 10;
            }

            /**
             * Spawns a number of villagers in this component. Parameters: world, component bounding box, x offset, y
             * offset, z offset, number of villagers
             */
            protected void spawnVillagers(World worldIn, StructureBoundingBox structurebb, int x, int y, int z, int count)
            {
                if (this.villagersSpawned < count)
                {
                    for (int i = this.villagersSpawned; i < count; ++i)
                    {
                        int j = this.getXWithOffset(x + i, z);
                        int k = this.getYWithOffset(y);
                        int l = this.getZWithOffset(x + i, z);

                        if (!structurebb.isVecInside(new BlockPos(j, k, l)))
                        {
                            break;
                        }

                        ++this.villagersSpawned;

                        if (this.isZombieInfested)
                        {
                            EntityZombieVillager entityzombievillager = new EntityZombieVillager(worldIn);
                            entityzombievillager.setLocationAndAngles((double)j + 0.5D, (double)k, (double)l + 0.5D, 0.0F, 0.0F);
                            entityzombievillager.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(entityzombievillager)), (IEntityLivingData)null);
                            entityzombievillager.enablePersistence();
                            worldIn.spawnEntity(entityzombievillager);
                        }
                        else
                        {
                            EntityVillager entityvillager = new EntityVillager(worldIn);
                            entityvillager.setLocationAndAngles((double)j + 0.5D, (double)k, (double)l + 0.5D, 0.0F, 0.0F);
                            entityvillager.setProfession(this.chooseForgeProfession(i, entityvillager.getProfessionForge()));
                            entityvillager.finalizeMobSpawn(worldIn.getDifficultyForLocation(new BlockPos(entityvillager)), (IEntityLivingData)null, false);
                            worldIn.spawnEntity(entityvillager);
                        }
                    }
                }
            }

            @Deprecated // Use Forge version below.
            protected int chooseProfession(int villagersSpawnedIn, int currentVillagerProfession)
            {
                return currentVillagerProfession;
            }
            protected net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession chooseForgeProfession(int count, net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession prof)
            {
                return net.minecraftforge.fml.common.registry.VillagerRegistry.getById(chooseProfession(count, net.minecraftforge.fml.common.registry.VillagerRegistry.getId(prof)));
            }

            protected IBlockState getBiomeSpecificBlockState(IBlockState blockstateIn)
            {
                net.minecraftforge.event.terraingen.BiomeEvent.GetVillageBlockID event = new net.minecraftforge.event.terraingen.BiomeEvent.GetVillageBlockID(startPiece == null ? null : startPiece.biome, blockstateIn);
                net.minecraftforge.common.MinecraftForge.TERRAIN_GEN_BUS.post(event);
                if (event.getResult() == net.minecraftforge.fml.common.eventhandler.Event.Result.DENY) return event.getReplacement();
                if (this.structureType == 1)
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
                else if (this.structureType == 3)
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
                else if (this.structureType == 2)
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

            protected BlockDoor biomeDoor()
            {
                switch (this.structureType)
                {
                    case 2:
                        return Blocks.ACACIA_DOOR;
                    case 3:
                        return Blocks.SPRUCE_DOOR;
                    default:
                        return Blocks.OAK_DOOR;
                }
            }

            protected void createVillageDoor(World p_189927_1_, StructureBoundingBox p_189927_2_, Random p_189927_3_, int p_189927_4_, int p_189927_5_, int p_189927_6_, EnumFacing p_189927_7_)
            {
                if (!this.isZombieInfested)
                {
                    this.generateDoor(p_189927_1_, p_189927_2_, p_189927_3_, p_189927_4_, p_189927_5_, p_189927_6_, EnumFacing.NORTH, this.biomeDoor());
                }
            }

            protected void placeTorch(World p_189926_1_, EnumFacing p_189926_2_, int p_189926_3_, int p_189926_4_, int p_189926_5_, StructureBoundingBox p_189926_6_)
            {
                if (!this.isZombieInfested)
                {
                    this.setBlockState(p_189926_1_, Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, p_189926_2_), p_189926_3_, p_189926_4_, p_189926_5_, p_189926_6_);
                }
            }

            /**
             * Replaces air and liquid from given position downwards. Stops when hitting anything else than air or
             * liquid
             */
            protected void replaceAirAndLiquidDownwards(World worldIn, IBlockState blockstateIn, int x, int y, int z, StructureBoundingBox boundingboxIn)
            {
                IBlockState iblockstate = this.getBiomeSpecificBlockState(blockstateIn);
                super.replaceAirAndLiquidDownwards(worldIn, iblockstate, x, y, z, boundingboxIn);
            }

            protected void setStructureType(int p_189924_1_)
            {
                this.structureType = p_189924_1_;
            }
        }
