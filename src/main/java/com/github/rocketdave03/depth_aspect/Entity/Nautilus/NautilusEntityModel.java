package com.github.rocketdave03.depth_aspect.Entity.Nautilus;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

import java.util.ArrayList;

public class NautilusEntityModel extends EntityModel<NautilusEntity> {

	private final ModelPart base;

	public NautilusEntityModel() {
		ModelPart.Cuboid cuboid = new ModelPart.Cuboid(-0, -0, -0.5f, -0.5f, -0.5f, 1, 1 ,1, 0, 0,0, false,64,32);
		ArrayList<ModelPart.Cuboid> cuboids = new ArrayList<>();
		cuboids.add(cuboid);

		base = new ModelPart(cuboids, null);
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
