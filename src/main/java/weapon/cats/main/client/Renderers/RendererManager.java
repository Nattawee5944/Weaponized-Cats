package weapon.cats.main.client.Renderers;

import java.util.Set;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.fabricmc.fabric.mixin.client.rendering.EntityModelLayersAccessor;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.CatEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.CatEntity;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import weapon.cats.main.Entities.EntityManager;
import weapon.cats.main.mixin.client.EntityModelLayerAccessor;

@Environment(EnvType.CLIENT)
public class RendererManager {

	@SuppressWarnings("unchecked")
	public static void InitializeRenderers() {
		
		EntityRendererRegistry.register(
				EntityManager.LASER_CURSOR,
				context->{
					return new LaserCursorRenderer(context);
				}
			);
		
		LivingEntityFeatureRendererRegistrationCallback.EVENT.register((livingEntityType, livingEntityRenderer, registrationHelper, context)->
		{
			if(livingEntityType.equals(EntityType.CAT)) {
				registrationHelper.register(new AttachmentsFeatureRenderer((FeatureRendererContext<CatEntity, CatEntityModel<CatEntity>>) livingEntityRenderer, context.getModelLoader()));
				
			}
		});
		
		InitializeModelLayers();
		
	}
	
	public static void InitializeModelLayers() {
		
	}
	
}
