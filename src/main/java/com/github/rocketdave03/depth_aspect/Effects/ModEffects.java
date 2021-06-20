package com.github.rocketdave03.depth_aspect.Effects;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEffects {


	public static  final StatusEffect DROWNING = new Drowning();

	public static void register()
	{
		Registry.register(Registry.STATUS_EFFECT, new Identifier("depth_aspect", "drowning"), DROWNING);
	}
}
