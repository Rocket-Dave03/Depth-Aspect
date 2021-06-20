package com.github.rocketdave03.depth_aspect.Entity.Nautilus;

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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

import java.util.EnumSet;

public class NautilusEntity extends ShulkerEntity implements Monster {
	private static EntityAttributeModifier COVERED_ARMOR_BONUS;


	public NautilusEntity(EntityType<? extends ShulkerEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(1, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(4, new ShootBulletGoal());
		this.targetSelector.add(1, (new RevengeGoal(this)).setGroupRevenge());
		this.targetSelector.add(2, new SearchForPlayerGoal(this));
		this.targetSelector.add(3, new SearchForTargetGoal(this));
	}

	public static DefaultAttributeContainer.Builder createNautilusAttributes() {
		return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0D);
	}

	@Override
	protected BodyControl createBodyControl() {
		return new NautilusBodyControl(this);
	}


	void setPeekAmount() {
	}










	private static class SearchForTargetGoal extends FollowTargetGoal<LivingEntity> {
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

	private class SearchForPlayerGoal extends FollowTargetGoal<PlayerEntity> {
		public SearchForPlayerGoal(ShulkerEntity shulker) {
			super(shulker, PlayerEntity.class, true);
		}

		public boolean canStart() {
			return NautilusEntity.this.world.getDifficulty() != Difficulty.PEACEFUL && super.canStart();
		}

		protected Box getSearchBox(double distance) {
			Direction direction = ((ShulkerEntity)this.mob).getAttachedFace();
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
			NautilusEntity.this.setPeekAmount();
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
