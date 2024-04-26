package weapon.cats.main.client.Models;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.CatEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.CatEntity;

public class TeslaCoilAttachmentModel extends CatEntityModel<CatEntity>{

	private ModelPart rod;
	public TeslaCoilAttachmentModel(ModelPart root) {
		super(root);
		this.rod = root.getChild(EntityModelPartNames.BODY);
	}
	
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		
		modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-2.5F, -2.0F, -3.0F, 5.0F, 4.0F, 5.0F, new Dilation(0.0F))
		.uv(0, 24).cuboid(-1.5F, -0.02F, -4.0F, 3.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 10).cuboid(-2.0F, -3.0F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F))
		.uv(6, 10).cuboid(1.0F, -3.0F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 15.0F, -9.0F));
		modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 23).cuboid(-2.0F, 9.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(0.0F))
				.uv(0, 9).cuboid(-1.0F, 10.0F, 2.0F, 2.0F, 2.0F, 7.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(-3.0F, 8.0F, 9.0F, 6.0F, 6.0F, 2.0F, new Dilation(0.0F))
				.uv(16, 0).cuboid(-2.0F, 9.0F, 8.5F, 4.0F, 4.0F, 3.0F, new Dilation(0.0F))
				.uv(30, 0).cuboid(-1.0F, 10.0F, 8.75F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 12.0F, -10.0F, 1.5708F, 0.0F, 0.0F));
		
		ModelPartBuilder modelPartBuilder = ModelPartBuilder.create().uv(7, 2).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 6.0f, 4.0f, new Dilation(0.5f));
		modelPartData.addChild(EntityModelPartNames.RIGHT_HIND_LEG, modelPartBuilder, ModelTransform.pivot(-3.0f, 12.0f, 7.0f));
		modelPartData.addChild(EntityModelPartNames.LEFT_HIND_LEG, modelPartBuilder, ModelTransform.pivot(3.0f, 12.0f, 7.0f));
		modelPartData.addChild(EntityModelPartNames.RIGHT_FRONT_LEG, modelPartBuilder, ModelTransform.pivot(-3.0f, 12.0f, -5.0f));
		modelPartData.addChild(EntityModelPartNames.LEFT_FRONT_LEG, modelPartBuilder, ModelTransform.pivot(3.0f, 12.0f, -5.0f));
		modelPartData.addChild("tail1", modelPartBuilder, ModelTransform.pivot(3.0f, 12.0f, -5.0f));
        modelPartData.addChild("tail2", modelPartBuilder, ModelTransform.pivot(3.0f, 12.0f, -5.0f));
		return TexturedModelData.of(modelData, 64, 32);
	}
	
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		this.rod.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
	
}
