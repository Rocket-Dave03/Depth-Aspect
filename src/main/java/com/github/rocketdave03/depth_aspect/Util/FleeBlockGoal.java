package com.github.rocketdave03.depth_aspect.Util;

import java.util.EnumSet;
import java.util.function.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.apache.logging.log4j.core.jmx.Server;

import static com.github.rocketdave03.depth_aspect.Util.FindNearestBlock.findNearestBlock;

public class FleeBlockGoal<T extends Block> extends Goal {
	protected final PathAwareEntity mob;
	private final double slowSpeed;
	private final double fastSpeed;
	protected T targetBlock;
	protected final float fleeDistance;
	protected Path fleePath;
	protected final EntityNavigation fleeingEntityNavigation;
	protected final Class<T> classToFleeFrom;




	public FleeBlockGoal(PathAwareEntity mob, Class<T> fleeFromType, float distance, double slowSpeed, double fastSpeed) {
		System.out.println("Creating a new FleeBlockGoal");
		this.mob = mob;
		this.classToFleeFrom = fleeFromType;
		this.fleeDistance = distance;
		this.slowSpeed = slowSpeed;
		Blocks.AIR.getDefaultState().getBlock();

		this.fastSpeed = fastSpeed;
		this.fleeingEntityNavigation = mob.getNavigation();
		this.setControls(EnumSet.of(Goal.Control.MOVE));

	}



	public boolean canStart() {
		System.out.println(Blocks.JUKEBOX.getStateManager().getStates());
		CompoundTag tag = new CompoundTag();
		//this.mob.world.getBlockEntity(this.mob.getBlockPos().add(0,-1,0)).toTag(tag);
		//BlockPos targetBlockPos = findNearestBlock(this.mob.getPos(),);
		//this.targetBlock = this.mob.world.getClosestEntityIncludingUngeneratedChunks(this.classToFleeFrom, this.withinRangePredicate, this.mob, this.mob.getX(), this.mob.getY(), this.mob.getZ(), this.mob.getBoundingBox().expand((double)this.fleeDistance, 3.0D, (double)this.fleeDistance));
		if (this.targetBlock == null) {
			return false;
		} //else {
//			Vec3d vec3d = TargetFinder.findTargetAwayFrom(this.mob, 16, 7, this.targetBlock.);
//			if (vec3d == null) {
//				return false;
//			} else if (this.targetBlock.squaredDistanceTo(vec3d.x, vec3d.y, vec3d.z) < this.targetBlock.squaredDistanceTo(this.mob)) {
//				return false;
//			} else {
//				this.fleePath = this.fleeingEntityNavigation.findPathTo(vec3d.x, vec3d.y, vec3d.z, 0);
//				return this.fleePath != null;
//			}
//		}
		return true;
	}


	public boolean shouldContinue() {
		return !this.fleeingEntityNavigation.isIdle();
	}

	public void start() {
		this.fleeingEntityNavigation.startMovingAlong(this.fleePath, this.slowSpeed);
	}

	public void stop() {
		this.targetBlock = null;
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
