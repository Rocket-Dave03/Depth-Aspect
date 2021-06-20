package com.github.rocketdave03.depth_aspect.Util;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Comparator;

import static com.github.rocketdave03.depth_aspect.DepthAspect.LOGGER;
import static com.github.rocketdave03.depth_aspect.Util.BlockPosUtils.blockPosFromVec3AndInts;
import static com.github.rocketdave03.depth_aspect.Util.BlockPosUtils.vec3FromBlockPos;

public class FindNearestBlock
{
	public static ArrayList<BlockPos> findNearestBlocks(World world, BlockPos pos, String blockId, int horizontalLimit, int verticalLimit) {
		return findNearestBlocks(
		world,
		new Vec3d(
			pos.getX(),
			pos.getY(),
			pos.getZ()
		),
		blockId,
		horizontalLimit,
		verticalLimit
		);
	}



	public static ArrayList<BlockPos> findNearestBlocks(World world, Vec3d pos, String blockId, int horizontalLimit, int verticalLimit)
	{

		ArrayList<BlockPos> blocks = new ArrayList<>();
		for (int i = -horizontalLimit; i <= verticalLimit; i++) {
			for (int j = -verticalLimit; j <= verticalLimit; j++) {
				for (int k = -horizontalLimit; k <= verticalLimit; k++) {


					String key = world.getBlockState(blockPosFromVec3AndInts(pos,i,j,k)).getBlock().getTranslationKey();
					if( key.equals(blockId))
					{
						blocks.add(blockPosFromVec3AndInts(pos,i,j,k));
					}


				}
			}
		}
		blocks.sort((o1, o2) -> {
			Vec3d posA = vec3FromBlockPos(o1);
			Vec3d posB = vec3FromBlockPos(o2);

			double distA = pos.squaredDistanceTo(posA);
			double distB = pos.squaredDistanceTo(posB);


			double dif = (distA - distB);
			return (int) (Math.ceil(Math.abs(dif))*Math.signum(dif));
		});
		return blocks;
	}

}
