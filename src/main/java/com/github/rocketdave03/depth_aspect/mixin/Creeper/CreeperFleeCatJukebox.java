package com.github.rocketdave03.depth_aspect.mixin.Creeper;

import com.github.rocketdave03.depth_aspect.Util.FleeJukebox;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreeperEntity.class)
public abstract class CreeperFleeCatJukebox extends HostileEntity {


	protected CreeperFleeCatJukebox(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(at = @At("HEAD"), method = "initGoals()V")
	private void hasEnchantments(CallbackInfo ci) {
		this.goalSelector.add(3, new FleeJukebox(this, 6.0F, 1.0D));

	}

}
