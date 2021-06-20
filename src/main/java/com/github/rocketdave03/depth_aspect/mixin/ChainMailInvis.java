package com.github.rocketdave03.depth_aspect.mixin;

import com.github.rocketdave03.depth_aspect.Effects.ModEffects;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorFeatureRenderer.class)
public abstract class ChainMailInvis<T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>> extends FeatureRenderer<T, M> {


	public ChainMailInvis(FeatureRendererContext<T, M> context) {
		super(context);
	}


	@Inject(at = @At("HEAD"), method = "renderArmor", cancellable = true)
	private void grind(MatrixStack matrices, VertexConsumerProvider vertexConsumers, T livingEntity, EquipmentSlot equipmentSlot, int i, A bipedEntityModel, CallbackInfo ci) {
		ItemStack itemStack = livingEntity.getEquippedStack(equipmentSlot);
		if (itemStack.getItem() instanceof ArmorItem) {
			ArmorItem armorItem = (ArmorItem)itemStack.getItem();
			if(armorItem.getMaterial() == ArmorMaterials.CHAIN)
			{
				if ( livingEntity.getStatusEffect(StatusEffects.INVISIBILITY) != null ) ci.cancel();
			}
		}
	}
}
