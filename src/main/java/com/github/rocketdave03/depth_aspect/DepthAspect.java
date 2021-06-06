package com.github.rocketdave03.depth_aspect;

import com.github.rocketdave03.depth_aspect.Commands.Commands;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;



public class DepthAspect implements ModInitializer
{

	public static final Item REQUIS = new Item(new FabricItemSettings().group(ItemGroup.MISC));

	private static int run(CommandContext<Object> context) {
		System.out.println("foo");
		return 1;
	}

	@Override
	public void onInitialize()
	{
		System.out.println("Hello Fabric world!");

		Registry.register(Registry.ITEM, new Identifier("depth_aspect", "requis"), REQUIS);
		FuelRegistry.INSTANCE.add(Blocks.MAGMA_BLOCK,600);

		Commands.registerCommands();

	}
}
