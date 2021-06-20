package com.github.rocketdave03.depth_aspect.mixin;


import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.GrindstoneScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.github.rocketdave03.depth_aspect.Items.ModItems.REQUIS;

@Mixin(GrindstoneScreenHandler.class)
public abstract class GrindGoldenApple {


	@Inject(at = @At("HEAD"), method = "grind", cancellable = true)
	private void grind(ItemStack item, int damage, int amount, CallbackInfoReturnable<ItemStack> cir) {
		if(item.copy().isItemEqual(Items.ENCHANTED_GOLDEN_APPLE.getDefaultStack()))
		{
			ItemStack stack = REQUIS.getDefaultStack().copy();
			stack.setCount(1);



			cir.setReturnValue(stack);
			cir.cancel();
		}
	}

}
