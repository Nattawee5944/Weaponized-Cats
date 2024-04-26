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
import weapon.cats.main.Items.Attachments.BombAttachment;
import weapon.cats.main.Items.Attachments.GunAttachment;
import weapon.cats.main.Items.Attachments.LaserAttachment;
import weapon.cats.main.Items.Attachments.SlimeAttachment;
import weapon.cats.main.Items.Attachments.TeslaCoilAttachment;
import weapon.cats.main.client.Models.AttacherModel;
import weapon.cats.main.client.Models.BombAttachmentModel;
import weapon.cats.main.client.Models.GunAttachmentModel;
import weapon.cats.main.client.Models.LaserAttachmentModel;
import weapon.cats.main.client.Models.SlimeAttachmentModel;
import weapon.cats.main.client.Models.TeslaCoilAttachmentModel;

@Environment(value = EnvType.CLIENT)
public class AttachmentsFeatureRenderer extends FeatureRenderer<CatEntity, CatEntityModel<CatEntity>>{
	
	AttacherModel attacherModel;
	GunAttachmentModel gunAttachmentModel;
	SlimeAttachmentModel slimeAttachmentModel;
	BombAttachmentModel bombAttachmentModel;
	LaserAttachmentModel laserAttachmentModel;
	TeslaCoilAttachmentModel teslaCoilAttachmentModel;
	
	public static Identifier ATTACHER_LAYER_ID = new Identifier(WeaponizedCats.MOD_ID,"attacher_model_layer");
	public static EntityModelLayer attacher_layer = new EntityModelLayer(ATTACHER_LAYER_ID,"attacher");
	public static Identifier Attacher_Texture = new Identifier(WeaponizedCats.MOD_ID,"textures/entity/attacher_model_texture.png");
	
	public static Identifier ATTACHMENT_LAYER_ID = new Identifier(WeaponizedCats.MOD_ID,"attachment_model_layer");
	public static EntityModelLayer attachment_layer = new EntityModelLayer(ATTACHMENT_LAYER_ID,"attachment");
	public static Identifier Attachment_Texture = new Identifier(WeaponizedCats.MOD_ID,"textures/entity/attachment_model_texture.png");
	
	public static Identifier GUN_ATTACHMENT_LAYER_ID = new Identifier(WeaponizedCats.MOD_ID,"gun_attachment_model_layer");
	public static EntityModelLayer gun_attachment_layer = new EntityModelLayer(GUN_ATTACHMENT_LAYER_ID,"gun_attachment");
	public static Identifier Gun_Attachment_Texture = new Identifier(WeaponizedCats.MOD_ID,"textures/entity/gun_attachment_model_texture.png");
	
	public static Identifier SLIME_ATTACHMENT_LAYER_ID = new Identifier(WeaponizedCats.MOD_ID,"slime_attachment_model_layer");
	public static EntityModelLayer slime_attachment_layer = new EntityModelLayer(SLIME_ATTACHMENT_LAYER_ID,"slime_attachment");
	public static Identifier Slime_Attachment_Texture = new Identifier(WeaponizedCats.MOD_ID,"textures/entity/slime_attachment_model_texture.png");
	
	public static Identifier BOMB_ATTACHMENT_LAYER_ID = new Identifier(WeaponizedCats.MOD_ID,"bomb_attachment_model_layer");
	public static EntityModelLayer bomb_attachment_layer = new EntityModelLayer(BOMB_ATTACHMENT_LAYER_ID,"bomb_attachment");
	public static Identifier Bomb_Attachment_Texture = new Identifier(WeaponizedCats.MOD_ID,"textures/entity/bomb_attachment_model_texture.png");
	
	public static Identifier LASER_ATTACHMENT_LAYER_ID = new Identifier(WeaponizedCats.MOD_ID,"laser_attachment_model_layer");
	public static EntityModelLayer laser_attachment_layer = new EntityModelLayer(LASER_ATTACHMENT_LAYER_ID,"laser_attachment");
	public static Identifier Laser_Attachment_Texture = new Identifier(WeaponizedCats.MOD_ID,"textures/entity/laser_attachment_model_texture.png");
	
	public static Identifier TESLA_COIL_ATTACHMENT_LAYER_ID = new Identifier(WeaponizedCats.MOD_ID,"tesla_coil_attachment_model_layer");
	public static EntityModelLayer tesla_coil_attachment_layer = new EntityModelLayer(TESLA_COIL_ATTACHMENT_LAYER_ID,"tesla_coil_attachment");
	public static Identifier Tesla_Coil_Attachment_Texture = new Identifier(WeaponizedCats.MOD_ID,"textures/entity/tesla_coil_attachment_model_texture.png");
	
	
	public AttachmentsFeatureRenderer(FeatureRendererContext<CatEntity, CatEntityModel<CatEntity>> livingEntityRenderer,
			EntityModelLoader modelLoader) {
		super(livingEntityRenderer);
		
		this.attacherModel = new AttacherModel(modelLoader.getModelPart(attacher_layer));
		this.gunAttachmentModel = new GunAttachmentModel(modelLoader.getModelPart(gun_attachment_layer));
		this.slimeAttachmentModel = new SlimeAttachmentModel(modelLoader.getModelPart(slime_attachment_layer));
		this.bombAttachmentModel = new BombAttachmentModel(modelLoader.getModelPart(bomb_attachment_layer));
		this.laserAttachmentModel = new LaserAttachmentModel(modelLoader.getModelPart(laser_attachment_layer));
		this.teslaCoilAttachmentModel = new TeslaCoilAttachmentModel(modelLoader.getModelPart(tesla_coil_attachment_layer));
		
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
			if(attachmentItem instanceof SlimeAttachment) {
				
				AttachmentsFeatureRenderer.render(this.getContextModel(), this.slimeAttachmentModel, Slime_Attachment_Texture, matrices, vertexConsumers, light, entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch, tickDelta, 1.0f, 1.0f, 1.0f);
				
			}
			if(attachmentItem instanceof BombAttachment) {
				
				AttachmentsFeatureRenderer.render(this.getContextModel(), this.bombAttachmentModel, Bomb_Attachment_Texture, matrices, vertexConsumers, light, entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch, tickDelta, 1.0f, 1.0f, 1.0f);
				
			}
			if(attachmentItem instanceof LaserAttachment) {
				
				AttachmentsFeatureRenderer.render(this.getContextModel(), this.laserAttachmentModel, Laser_Attachment_Texture, matrices, vertexConsumers, light, entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch, tickDelta, 1.0f, 1.0f, 1.0f);
				
			}
			if(attachmentItem instanceof TeslaCoilAttachment) {
				
				AttachmentsFeatureRenderer.render(this.getContextModel(), this.teslaCoilAttachmentModel, Tesla_Coil_Attachment_Texture, matrices, vertexConsumers, light, entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch, tickDelta, 1.0f, 1.0f, 1.0f);
				
			}
			
		}
		matrices.pop();
		
	}
	
}
