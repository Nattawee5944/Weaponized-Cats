package weapon.cats.main.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.util.Hand;
import weapon.cats.main.Items.ItemManager;

@Environment(EnvType.CLIENT)
@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {
	
	@Inject(method = "getArmPose", at = @At("TAIL"), cancellable = true)
	private static void laserPointerPose(AbstractClientPlayerEntity player, Hand hand, CallbackInfoReturnable<BipedEntityModel.ArmPose> ci) {
		
		if(player.isUsingItem() && player.getActiveItem().isOf(ItemManager.LASER_POINTER)) {
			
			ci.setReturnValue(BipedEntityModel.ArmPose.CROSSBOW_HOLD);
			return;
			
		}
		
	}
	
}
