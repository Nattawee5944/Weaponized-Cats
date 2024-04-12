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

public class AttacherModel extends CatEntityModel<CatEntity> {
	
	private final ModelPart attacher;
	public AttacherModel(ModelPart root) {
		super(root);
		this.attacher = root.getChild("body");
	}
	
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 0).cuboid(-3.0f, -4.0f, -4.0f, 6.0f, 6.0f, 6.0f, new Dilation(0.6f)), ModelTransform.pivot(0.0f, 6.0f, -8.0f));
        
        ModelPartBuilder bodyBone = ModelPartBuilder.create().uv(0, 0).cuboid(-2f, 6.0f, -5.0f, 4.0f, 10.0f, 3.0f, new Dilation(0.5f));
        modelPartData.addChild(EntityModelPartNames.BODY, bodyBone, ModelTransform.of(0.0f, 5.0f, 2.0f, 1.5707964f, 0.0f, 0.0f));
        ModelPartBuilder attacher2_bone = ModelPartBuilder.create().uv(10,10).cuboid(-2.0f, 9.0f, -3.0f, 4.0f, 4.0f, 3.0f, new Dilation(0.5f));
        modelPartData.getChild(EntityModelPartNames.BODY).addChild("attacher2", attacher2_bone, ModelTransform.NONE);
        
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
		//head.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		//body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		//front_left_leg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		//front_right_leg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		//back_left_leg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		//back_right_leg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		//tail1.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		//tail2.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		this.attacher.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		
	}
}