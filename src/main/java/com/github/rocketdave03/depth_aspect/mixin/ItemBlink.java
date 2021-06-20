package com.github.rocketdave03.depth_aspect.mixin;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.github.rocketdave03.depth_aspect.DepthAspect.LOGGER;

@Mixin(ItemEntityRenderer.class)
public abstract class ItemBlink {



	@Inject(at = @At("HEAD"), method = "render(Lnet/minecraft/entity/ItemEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", cancellable = true)
	private void render(ItemEntity itemEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
		/*
		if(itemEntity.age >= (  6000  - (20 * 60 * 5)))
		{
			if (itemEntity.age % 20 > 10)
			{
				ci.cancel();
			}
		}
		 */
		float age = itemEntity.age/20f;
		// 2.5 minutes left to 1
		if( age > 2.5 * 60 && age < 4*60)
		{
			if (itemEntity.age % 60 < 10)
			{
				ci.cancel();
			}
		}
		// 1 minute left to 30 seconds
		if( age > 4*460 && age < 4.5f*60)
		{
			if (itemEntity.age % 40 < 10)
			{
				ci.cancel();
			}
		}
		// 30 seconds left to 15
		if( age > 4.5*60 && age < 4.75*60)
		{
			if (itemEntity.age % 20 < 10)
			{
				ci.cancel();
			}
		}
		//Last 15 Seconds
		if( age > 4.75 * 60 && age < 5*60)
		{
			if (itemEntity.age % 10 < 5)
			{
				ci.cancel();
			}
		}
	}
}
