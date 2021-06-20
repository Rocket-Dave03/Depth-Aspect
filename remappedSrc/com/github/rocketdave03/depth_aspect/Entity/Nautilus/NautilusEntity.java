package com.github.rocketdave03.depth_aspect.Entity.Nautilus;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PistonBlock;
import net.minecraft.block.PistonHeadBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.control.BodyControl;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.mob.ShulkerLidCollisions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import com.github.rocketdave03.depth_aspect.Entity.Nautilus.NautilusEntity.NautilusBodyControl;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;

public class NautilusEntity extends ShulkerEntity implements Monster {
	private static EntityAttributeModifier COVERED_ARMOR_BONUS;
	private int teleportLerpTimer;
	private BlockPos prevAttachedBlock = null;

	public NautilusEntity(EntityType<? extends ShulkerEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(1, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(4, new NautilusEntity.ShootBulletGoal());
		this.targetSelector.add(1, (new RevengeGoal(this)).setGroupRevenge());
		this.targetSelector.add(2, new NautilusEntity.SearchForPlayerGoal(this));
		this.targetSelector.add(3, new NautilusEntity.SearchForTargetGoal(this));
	}

	public static DefaultAttributeContainer.Builder createNautilusAttributes() {
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0D);
	}

	@Override
	protected BodyControl createBodyControl() {
		return new NautilusBodyControl(this);
	}


	public void setPeekAmount(int peekAmount) {}


	private boolean canStay(BlockPos pos, Direction attachSide) {
		return this.world.isDirectionSolid(pos.offset(attachSide), this, attachSide.getOpposite()) && this.world.isSpaceEmpty(this, ShulkerLidCollisions.getLidCollisionBox(pos, attachSide.getOpposite()));
	}

	@Override
	public void tick() {
		super.tick();
		BlockPos blockPos = this.dataTracker.get(ATTACHED_BLOCK).orElse(null);
		if (blockPos == null && !this.world.isClient) {
			blockPos = this.getBlockPos();
			this.dataTracker.set(ATTACHED_BLOCK, Optional.of(blockPos));
		}

		float g;
		if (this.hasVehicle()) {
			blockPos = null;
			g = this.getVehicle().yaw;
			this.yaw = g;
			this.bodyYaw = g;
			this.prevBodyYaw = g;
			this.teleportLerpTimer = 0;
		} else if (!this.world.isClient) {
			BlockState blockState = this.world.getBlockState(blockPos);
			Direction direction2;
			if (!blockState.isAir()) {
				if (blockState.isOf(Blocks.MOVING_PISTON)) {
					direction2 = blockState.get(PistonBlock.FACING);
					assert blockPos != null;
					if (this.world.isAir(blockPos.offset(direction2))) {
						blockPos = blockPos.offset(direction2);
						this.dataTracker.set(ATTACHED_BLOCK, Optional.of(blockPos));
					} else {
						this.tryTeleport();
					}
				} else if (blockState.isOf(Blocks.PISTON_HEAD)) {
					direction2 = blockState.get(PistonHeadBlock.FACING);
					assert blockPos != null;
					if (this.world.isAir(blockPos.offset(direction2))) {
						blockPos = blockPos.offset(direction2);
						this.dataTracker.set(ATTACHED_BLOCK, Optional.of(blockPos));
					} else {
						this.tryTeleport();
					}
				} else {
					this.tryTeleport();
				}
			}

			direction2 = this.getAttachedFace();
			assert blockPos != null;
			if (!this.canStay(blockPos, direction2)) {
				Direction direction4 = this.findAttachSide(blockPos);
				if (direction4 != null) {
					this.dataTracker.set(ATTACHED_FACE, direction4);
				} else {
					this.tryTeleport();
				}
			}
		}


		if (blockPos != null) {
			if (this.world.isClient) {
				if (this.teleportLerpTimer > 0 && this.prevAttachedBlock != null) {
					--this.teleportLerpTimer;
				} else {
					this.prevAttachedBlock = blockPos;
				}
			}

			this.resetPosition((double)blockPos.getX() + 0.5D, blockPos.getY(), (double)blockPos.getZ() + 0.5D);
			this.setBoundingBox(
				(
					new Box
					(
						 this.getX() - 0.5D,
							 this.getY(),
						 this.getZ() - 0.5D,

						 this.getX() + 0.5D,
						 this.getY() + 1.0D,
						 this.getZ() + 0.5D
					)
				)
			);
		}
	}






	static class SearchForTargetGoal extends FollowTargetGoal<LivingEntity> {
		public SearchForTargetGoal(NautilusEntity nautilus) {
			super(nautilus, LivingEntity.class, 10, true, false, (entity) -> entity instanceof Monster);
		}

		public boolean canStart() {
			return this.mob.getScoreboardTeam() != null && super.canStart();
		}

		protected Box getSearchBox(double distance) {
			Direction direction = ((NautilusEntity)this.mob).getAttachedFace();
			if (direction.getAxis() == Direction.Axis.X) {
				return this.mob.getBoundingBox().expand(4.0D, distance, distance);
			} else {
				return direction.getAxis() == Direction.Axis.Z ? this.mob.getBoundingBox().expand(distance, distance, 4.0D) : this.mob.getBoundingBox().expand(distance, 4.0D, distance);
			}
		}
	}

	class SearchForPlayerGoal extends FollowTargetGoal<PlayerEntity> {
		public SearchForPlayerGoal(NautilusEntity Nautilus) {
			super(Nautilus, PlayerEntity.class, true);
		}

		public boolean canStart() {
			return NautilusEntity.this.world.getDifficulty() != Difficulty.PEACEFUL && super.canStart();
		}

		protected Box getSearchBox(double distance) {
			Direction direction = ((NautilusEntity)this.mob).getAttachedFace();
			if (direction.getAxis() == Direction.Axis.X) {
				return this.mob.getBoundingBox().expand(4.0D, distance, distance);
			} else {
				return direction.getAxis() == Direction.Axis.Z ? this.mob.getBoundingBox().expand(distance, distance, 4.0D) : this.mob.getBoundingBox().expand(distance, 4.0D, distance);
			}
		}
	}

	class ShootBulletGoal extends Goal {
		private int counter;

		public ShootBulletGoal() {
			this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
		}

		public boolean canStart() {
			LivingEntity livingEntity = NautilusEntity.this.getTarget();
			if (livingEntity != null && livingEntity.isAlive()) {
				return NautilusEntity.this.world.getDifficulty() != Difficulty.PEACEFUL;
			} else {
				return false;
			}
		}

		public void start() {
			this.counter = 20;
		}

		public void stop() {

		}

		public void tick() {
			NautilusEntity.this.setPeekAmount(0);
			if (NautilusEntity.this.world.getDifficulty() != Difficulty.PEACEFUL) {
				--this.counter;
				LivingEntity livingEntity = NautilusEntity.this.getTarget();
				NautilusEntity.this.getLookControl().lookAt(livingEntity, 180.0F, 180.0F);
				double d = NautilusEntity.this.squaredDistanceTo(livingEntity);
				if (d < 400.0D) {
					if (this.counter <= 0) {
						this.counter = 20 + NautilusEntity.this.random.nextInt(10) * 20 / 2;
						NautilusEntity.this.world.spawnEntity(new NautilusBulletEntity(NautilusEntity.this.world, NautilusEntity.this, livingEntity, NautilusEntity.this.getAttachedFace().getAxis()));
						NautilusEntity.this.playSound(SoundEvents.ENTITY_SHULKER_SHOOT, 2.0F, (NautilusEntity.this.random.nextFloat() - NautilusEntity.this.random.nextFloat()) * 0.2F + 1.0F);
					}
				} else {
					NautilusEntity.this.setTarget(null);
				}

				super.tick();
			}
		}
	}


	static class NautilusBodyControl extends BodyControl {
		public NautilusBodyControl(MobEntity entity) {
			super(entity);
		}

		public void tick() {
		}
	}

}
