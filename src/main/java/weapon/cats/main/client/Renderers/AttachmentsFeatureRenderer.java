package weapon.cats.main.client.Renderers;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.CatEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.util.Identifier;
import weapon.cats.main.WeaponizedCats;
import weapon.cats.main.Items.ItemManager;
import weapon.cats.main.client.Models.AttacherModel;

@Environment(value = EnvType.CLIENT)
public class AttachmentsFeatureRenderer extends FeatureRenderer<CatEntity, CatEntityModel<CatEntity>>{
	
	AttacherModel attachermodel;
	public static Identifier ATTACHER_LAYER_ID = new Identifier(WeaponizedCats.MOD_ID,"attacher_model_layer");
	public static EntityModelLayer attacher_layer = new EntityModelLayer(ATTACHER_LAYER_ID,"attacher");
	public static Identifier Attacher_Texture = new Identifier(WeaponizedCats.MOD_ID,"textures/entity/attacher_model_texture.png");
	
	public static Identifier ATTACHMENT_LAYER_ID = new Identifier(WeaponizedCats.MOD_ID,"attachment_model_layer");
	public static EntityModelLayer attachment_layer = new EntityModelLayer(ATTACHMENT_LAYER_ID,"attachment");
	public static Identifier Attachment_Texture = new Identifier(WeaponizedCats.MOD_ID,"textures/entity/attachment_model_texture.png");
	
	public AttachmentsFeatureRenderer(FeatureRendererContext<CatEntity, CatEntityModel<CatEntity>> livingEntityRenderer,
			EntityModelLoader modelLoader) {
		super(livingEntityRenderer);
		
		this.attachermodel = new AttacherModel(modelLoader.getModelPart(attacher_layer));
		
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CatEntity entity,
			float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw,
			float headPitch) {
		
		if(!entity.getEquippedStack(EquipmentSlot.CHEST).isOf(ItemManager.ATTACHER)) {
			return;
		}
		
		matrices.push();
		
		AttachmentsFeatureRenderer.render(this.getContextModel(), this.attachermodel, Attacher_Texture, matrices, vertexConsumers, light, entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch, tickDelta, 1.0f, 1.0f, 1.0f);
		
		matrices.pop();
		
	}
	
}
