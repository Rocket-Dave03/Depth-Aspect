package com.github.rocketdave03.depth_aspect.Entity.Nautilus;

import com.github.rocketdave03.depth_aspect.Effects.ModEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class NautilusBulletEntity extends ShulkerBulletEntity {
	public NautilusBulletEntity(World world, NautilusEntity nautilusEntity, LivingEntity livingEntity, Direction.Axis axis) {
		super(world, nautilusEntity, livingEntity, axis);
		this.move(MovementType.SELF, new Vec3d(0d,-0.25d,0.5d + 3d/16d));
		this.setVelocity(0,0,0.3);
	}


	@Override
	protected void onEntityHit(EntityHitResult entityHitResult) {
		Entity entity = entityHitResult.getEntity();
		Entity entity2 = this.getOwner();
		LivingEntity livingEntity = entity2 instanceof LivingEntity ? (LivingEntity)entity2 : null;
		boolean bl = entity.damage(DamageSource.mobProjectile(this, livingEntity).setProjectile(), 4.0F);
		if (bl) {
			this.applyDamageEffects(livingEntity, entity);
			if (entity instanceof LivingEntity) {
				((LivingEntity)entity).addStatusEffect(new StatusEffectInstance(ModEffects.DROWNING, 200));
			}
		}

	}

}
