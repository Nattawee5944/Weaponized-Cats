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
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import weapon.cats.main.WeaponizedCats;
import weapon.cats.main.Items.ItemManager;
import weapon.cats.main.Items.Attachments.Attachment;
import weapon.cats.main.Items.Attachments.GunAttachment;
import weapon.cats.main.client.Models.AttacherModel;
import weapon.cats.main.client.Models.GunAttachmentModel;

@Environment(value = EnvType.CLIENT)
public class AttachmentsFeatureRenderer extends FeatureRenderer<CatEntity, CatEntityModel<CatEntity>>{
	
	AttacherModel attacherModel;
	GunAttachmentModel gunAttachmentModel;
	public static Identifier ATTACHER_LAYER_ID = new Identifier(WeaponizedCats.MOD_ID,"attacher_model_layer");
	public static EntityModelLayer attacher_layer = new EntityModelLayer(ATTACHER_LAYER_ID,"attacher");
	public static Identifier Attacher_Texture = new Identifier(WeaponizedCats.MOD_ID,"textures/entity/attacher_model_texture.png");
	
	public static Identifier ATTACHMENT_LAYER_ID = new Identifier(WeaponizedCats.MOD_ID,"attachment_model_layer");
	public static EntityModelLayer attachment_layer = new EntityModelLayer(ATTACHMENT_LAYER_ID,"attachment");
	public static Identifier Attachment_Texture = new Identifier(WeaponizedCats.MOD_ID,"textures/entity/attachment_model_texture.png");
	
	public static Identifier GUN_ATTACHMENT_LAYER_ID = new Identifier(WeaponizedCats.MOD_ID,"gun_attachment_model_layer");
	public static EntityModelLayer gun_attachment_layer = new EntityModelLayer(GUN_ATTACHMENT_LAYER_ID,"gun_attachment");
	public static Identifier Gun_Attachment_Texture = new Identifier(WeaponizedCats.MOD_ID,"textures/entity/gun_attachment_model_texture.png");
	
	public AttachmentsFeatureRenderer(FeatureRendererContext<CatEntity, CatEntityModel<CatEntity>> livingEntityRenderer,
			EntityModelLoader modelLoader) {
		super(livingEntityRenderer);
		
		this.attacherModel = new AttacherModel(modelLoader.getModelPart(attacher_layer));
		this.gunAttachmentModel = new GunAttachmentModel(modelLoader.getModelPart(gun_attachment_layer));
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CatEntity entity,
			float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw,
			float headPitch) {
		
		if(!entity.getEquippedStack(EquipmentSlot.CHEST).isOf(ItemManager.ATTACHER)) {
			return;
		}
		
		matrices.push();
		
		AttachmentsFeatureRenderer.render(this.getContextModel(), this.attacherModel, Attacher_Texture, matrices, vertexConsumers, light, entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch, tickDelta, 1.0f, 1.0f, 1.0f);
		
		if(Attachment.getAttachment(entity.getEquippedStack(EquipmentSlot.CHEST)).getItem()!= null) {
			Item attachmentItem = Attachment.getAttachment(entity.getEquippedStack(EquipmentSlot.CHEST)).getItem();
			
			if(attachmentItem instanceof GunAttachment) {
				
				AttachmentsFeatureRenderer.render(this.getContextModel(), this.gunAttachmentModel, Gun_Attachment_Texture, matrices, vertexConsumers, light, entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch, tickDelta, 1.0f, 1.0f, 1.0f);
				
			}
			
		}
		matrices.pop();
		
	}
	
}
