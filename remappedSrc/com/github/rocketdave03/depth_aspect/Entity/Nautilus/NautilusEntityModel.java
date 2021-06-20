package com.github.rocketdave03.depth_aspect.Entity.Nautilus;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class NautilusEntityModel extends EntityModel<NautilusEntity> {

	private final ModelPart base;

	public NautilusEntityModel() {
		base = new ModelPart(this, 0, 0);
		base.addCuboid(-8, -8, -8, 16, 16, 16);
	}

	@Override
	public void setAngles(NautilusEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		matrices.translate(0, 1, 0);

		// render cube
		base.render(matrices, vertices, light, overlay);

	}
}
