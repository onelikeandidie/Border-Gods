package net.onelikeandidie.bordergods.util.multiblock;

import net.minecraft.block.Block;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public interface Multiblock {
    List<Pair<BlockPos, Block>> getStructure();
    boolean checkStructure(World world, BlockPos pos, Block block);
}
