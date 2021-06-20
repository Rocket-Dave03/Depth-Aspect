package com.github.rocketdave03.depth_aspect.Items;

import com.github.rocketdave03.depth_aspect.DepthAspect;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.github.rocketdave03.depth_aspect.DepthAspect.LOGGER;

public class ModItems {

	public static final Item REQUIS = new Item(new FabricItemSettings());

	public static void register()
	{
		LOGGER.info("\n\n\nRegistering Items\n\n\n");
		Registry.register(Registry.ITEM, new Identifier("depth_aspect", "requis"), REQUIS);
	}
}