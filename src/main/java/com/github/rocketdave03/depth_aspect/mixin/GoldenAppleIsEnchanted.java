package com.github.rocketdave03.depth_aspect.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class GoldenAppleIsEnchanted {
	@Shadow public abstract Item getItem();

	@Inject(at = @At("HEAD"), method = "hasEnchantments()Z", cancellable = true)
	private void hasEnchantments(CallbackInfoReturnable<Boolean> cir) {

		if(ItemStack.areEqual(this.getItem().getDefaultStack(), Items.ENCHANTED_GOLDEN_APPLE.getDefaultStack()))
		{
			cir.setReturnValue(true);
			cir.cancel();
		}
	}

}
