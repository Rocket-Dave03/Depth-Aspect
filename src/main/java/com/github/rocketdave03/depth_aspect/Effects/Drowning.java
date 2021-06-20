package com.github.rocketdave03.depth_aspect.Effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;




public class Drowning extends StatusEffect {
	public Drowning() {
		super(
			StatusEffectType.HARMFUL, // whether beneficial or harmful for entities
			0x2f345e); // color in RGB
	}

	// This method is called every tick to check weather it should apply the status effect or not
	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		// In our case, we just make it return true so that it applies the status effect every tick.
		return true;
	}

	// This method is called when it applies the status effect. We implement custom functionality here.
	@Override
	public void applyUpdateEffect(LivingEntity entity, int amplifier) {
		amplifier = amplifier == 0 ? 1: amplifier;

		if (entity instanceof PlayerEntity) {

			if ( !entity.isSpectator() )
			{
				if (entity.isInsideWaterOrBubbleColumn())
				{
					entity.move(MovementType.SHULKER, new Vec3d(0d, -amplifier * 0.37, 0d));
				}

			}
		}
	}
}