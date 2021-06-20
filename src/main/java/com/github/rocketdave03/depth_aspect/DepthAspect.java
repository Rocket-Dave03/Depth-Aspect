package com.github.rocketdave03.depth_aspect;

import com.github.rocketdave03.depth_aspect.Commands.ModCommands;
import com.github.rocketdave03.depth_aspect.Effects.ModEffects;
import com.github.rocketdave03.depth_aspect.Entity.ModEntities;
import com.github.rocketdave03.depth_aspect.Items.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ItemEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class DepthAspect implements ModInitializer
{
	public static final Logger LOGGER = LogManager.getLogger("Depth Aspect");


	@Override
	public void onInitialize()
	{

		System.out.println("Hello Fabric world!");
		//Items
		ModItems.register();
		//Blocks

		//Effects
		ModEffects.register();
		//Entities
		ModEntities.register();

		//Fuels
		FuelRegistry.INSTANCE.add(Blocks.MAGMA_BLOCK,600);

		//Commands
		ModCommands.register();

	}
}
