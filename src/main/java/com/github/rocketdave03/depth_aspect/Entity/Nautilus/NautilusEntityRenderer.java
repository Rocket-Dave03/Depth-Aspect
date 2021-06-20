package com.github.rocketdave03.depth_aspect.Entity.Nautilus;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class NautilusEntityRenderer extends MobEntityRenderer<NautilusEntity, NautilusEntityModel> {

	public NautilusEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
		super(entityRenderDispatcher, new NautilusEntityModel(), 0.5f);
	}


	public Identifier getTexture(NautilusEntity entity) {
		return new Identifier("depth_aspect", "textures/entity/nautilus/nautilus.png");
	}
}