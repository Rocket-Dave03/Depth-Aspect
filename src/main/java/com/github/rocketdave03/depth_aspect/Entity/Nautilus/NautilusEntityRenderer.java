package com.github.rocketdave03.depth_aspect.Entity.Nautilus;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class NautilusEntityRenderer extends MobEntityRenderer<NautilusEntity, NautilusEntityModel> {

	public NautilusEntityRenderer(EntityRendererFactory.Context renderManager, NautilusEntityModel modelProvider) {
		super(renderManager, modelProvider,1f);
	}



	public Identifier getTexture(NautilusEntity entity) {
		return new Identifier("depth_aspect", "textures/entity/nautilus/nautilus.png");
	}
}