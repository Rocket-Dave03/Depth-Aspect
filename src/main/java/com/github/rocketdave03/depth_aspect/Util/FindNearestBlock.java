package com.github.rocketdave03.depth_aspect.Util;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class FindNearestBlock
{
	public static BlockPos findNearestBlock(BlockPos pos, BlockState state) {
		return findNearestBlock(
		new Vec3d(
			pos.getX(),
			pos.getY(),
			pos.getZ()
		),state);
	}

	private static BlockPos findNearestBlock(Vec3d vec3d, BlockState state)
	{
		return new BlockPos(0, 0, 0);
	}

}
