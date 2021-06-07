package com.github.rocketdave03.depth_aspect.mixin;

import com.github.rocketdave03.depth_aspect.DepthAspect;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.GrindstoneScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GrindstoneScreenHandler.class)
public abstract class GrindGoldenApple {


	@Inject(at = @At("HEAD"), method = "grind", cancellable = true)
	private void grind(ItemStack item, int damage, int amount, CallbackInfoReturnable<ItemStack> cir) {
		if(item.copy().isItemEqual(Items.ENCHANTED_GOLDEN_APPLE.getDefaultStack()))
		{
			ItemStack stack = DepthAspect.REQUIS.getDefaultStack().copy();
			stack.setCount(1);

			cir.setReturnValue(stack);
			cir.cancel();
		}
	}

}
