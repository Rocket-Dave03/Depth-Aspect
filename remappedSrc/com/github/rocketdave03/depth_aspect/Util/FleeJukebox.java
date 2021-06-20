package com.github.rocketdave03.depth_aspect.Util;

import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Objects;

import static com.github.rocketdave03.depth_aspect.Util.BlockPosUtils.vec3FromBlockPos;
import static com.github.rocketdave03.depth_aspect.Util.FindNearestBlock.findNearestBlocks;

public class FleeJukebox extends Goal {
	protected final PathAwareEntity mob;
	private final double slowSpeed;
	protected final float fleeDistance;
	protected Path fleePath;
	protected final EntityNavigation fleeingEntityNavigation;




	public FleeJukebox(PathAwareEntity mob, float distance, double slowSpeed) {

		System.out.println("Creating a new FleeBlockGoal");
		this.mob = mob;
		this.fleeDistance = distance;
		this.slowSpeed = slowSpeed;
		Blocks.AIR.getDefaultState().getBlock();

		this.fleeingEntityNavigation = mob.getNavigation();
		this.setControls(EnumSet.of(Goal.Control.MOVE));

	}



	public boolean canStart() {


		ArrayList<BlockPos> blocks = findNearestBlocks(this.mob.world,this.mob.getPos(),"block.minecraft.jukebox", 16,7);
		World world = this.mob.world;
		for(BlockPos p : blocks)
		{

			BlockEntity e = world.getBlockEntity(p);
			if (e != null)
			{

				String tag;
				try {
					tag = Objects.requireNonNull(e.writeNbt(new NbtCompound()).getCompound("RecordItem").get("id")).asString();
				} catch (NullPointerException exception)
				{
					tag = "";
				}
				if(tag == null) continue;
				if (tag.equals( "minecraft:music_disc_cat")) {

					Vec3d vec3d = TargetFinder.findTargetAwayFrom(this.mob, 16, 7, vec3FromBlockPos(p));
					if (vec3d == null) {
						return false;
					} else if ( vec3FromBlockPos(p).squaredDistanceTo(vec3d.x, vec3d.y, vec3d.z) < vec3FromBlockPos(p).squaredDistanceTo(this.mob.getPos())) {
						return false;
					} else {
						this.fleePath = this.fleeingEntityNavigation.findPathTo(vec3d.x, vec3d.y, vec3d.z, 0);
						return this.fleePath != null;
					}

				}
			}

		}

		return false;
	}


	public boolean shouldContinue() {
		return !this.fleeingEntityNavigation.isIdle();
	}

	public void start() {
		this.fleeingEntityNavigation.startMovingAlong(this.fleePath, this.slowSpeed);
	}

	public void stop() {
	}

	public void tick() {
		//if (this.mob.squaredDistanceTo(this.targetBlock) < 49.0D) {
		//	this.mob.getNavigation().setSpeed(this.fastSpeed);
		//} else {
			this.mob.getNavigation().setSpeed(this.slowSpeed);
		//}

	}
}
//*/
