package weapon.cats.main.mixin.client;

import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;

@Mixin(EntityModelLayers.class)
public interface EntityModelLayerAccessor {
	
	@Accessor("LAYERS")
	public static Set<EntityModelLayer> getLayers() {
		
		throw new AssertionError();
		
	}
	
	@Invoker("register")
	public static EntityModelLayer invokeRegister(String id, String layer) {
		
		throw new AssertionError();
		
	}
	
	@Accessor("LAYERS")
	public static void setLayers(Set<EntityModelLayer> newLayers) {
		
		throw new AssertionError();
		
	}
	
}
