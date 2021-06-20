package com.github.rocketdave03.depth_aspect.Entity;

import com.github.rocketdave03.depth_aspect.Entity.Nautilus.NautilusEntity;
import com.github.rocketdave03.depth_aspect.Entity.Nautilus.NautilusEntityModel;
import com.github.rocketdave03.depth_aspect.Entity.Nautilus.NautilusEntityRenderer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEntities {

	//Nautilus
	public static final EntityType<NautilusEntity> NAUTILUS = Registry.register(
		Registry.ENTITY_TYPE,
		new Identifier("depth_aspect", "nautilus"),
		FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, NautilusEntity::new).dimensions(EntityDimensions.fixed(0.75f, 0.75f)).build()
	);



	public static void register()
	{

		//Nautilus
		FabricDefaultAttributeRegistry.register(NAUTILUS, NautilusEntity.createNautilusAttributes());
		EntityRendererRegistry.INSTANCE.register(NAUTILUS, context -> new NautilusEntityRenderer(context, new NautilusEntityModel()));

	}
}
