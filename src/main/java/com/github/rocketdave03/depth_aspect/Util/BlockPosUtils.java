package com.github.rocketdave03.depth_aspect.Util;

import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos;

public class BlockPosUtils {

	public static BlockPos blockPosFromVec3AndInts(Vec3d pos, int i, int j, int k)
	{
		return new
			net.minecraft.util.math.BlockPos(
			pos.add(
				i,
				j,
				k
			)
		);
	}

	public static BlockPos blockPosFromVec3(Vec3d pos)
	{
		return new BlockPos(pos);
	}

	public static Vec3d vec3FromBlockPos(BlockPos pos)
	{
		return new Vec3d(pos.getX(), pos.getY(), pos.getZ());
	}

}
