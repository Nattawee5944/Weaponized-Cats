package weapon.cats.main.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.google.common.collect.ImmutableMap;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModels;
import weapon.cats.main.client.Models.AttacherModel;
import weapon.cats.main.client.Models.BombAttachmentModel;
import weapon.cats.main.client.Models.GunAttachmentModel;
import weapon.cats.main.client.Models.LaserAttachmentModel;
import weapon.cats.main.client.Models.SlimeAttachmentModel;
import weapon.cats.main.client.Models.TeslaCoilAttachmentModel;
import weapon.cats.main.client.Renderers.AttachmentsFeatureRenderer;

@Environment(EnvType.CLIENT)
@Mixin(EntityModels.class)
public class EntityModelsMixin {
	
	@Inject(method = "getModels", at = @At(value = "TAIL"),locals= LocalCapture.CAPTURE_FAILHARD, cancellable = true)
	private static void inject(CallbackInfoReturnable<ImmutableMap<EntityModelLayer, TexturedModelData>> cir, ImmutableMap.Builder<EntityModelLayer, TexturedModelData> builder) {
		
		builder.put(AttachmentsFeatureRenderer.attacher_layer, AttacherModel.getTexturedModelData());
		builder.put(AttachmentsFeatureRenderer.gun_attachment_layer, GunAttachmentModel.getTexturedModelData());
		builder.put(AttachmentsFeatureRenderer.slime_attachment_layer, SlimeAttachmentModel.getTexturedModelData());
		builder.put(AttachmentsFeatureRenderer.bomb_attachment_layer, BombAttachmentModel.getTexturedModelData());
		builder.put(AttachmentsFeatureRenderer.laser_attachment_layer, LaserAttachmentModel.getTexturedModelData());
		builder.put(AttachmentsFeatureRenderer.tesla_coil_attachment_layer, TeslaCoilAttachmentModel.getTexturedModelData());
		
		//builder.put(AttachmentsFeatureRenderer.attachment_layer, AttachmentModel.getTexturedModelData());
		
		cir.setReturnValue(builder.build());
		
	}
	
}
