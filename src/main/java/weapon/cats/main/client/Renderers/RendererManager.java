package weapon.cats.main.client.Renderers;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.CatEntityModel;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.CatEntity;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import weapon.cats.main.Entities.EntityManager;

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
		EntityRendererRegistry.register(
				EntityManager.BULLET,
				context->{
					return new BulletRenderer(context);
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
