package weapon.cats.main.mixin.Entities;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.goal.AttackWithOwnerGoal;
import net.minecraft.entity.ai.goal.TrackOwnerAttackerGoal;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import weapon.cats.main.Entities.LaserCursor;
import weapon.cats.main.Entities.ai.BombAttachmentAttackGoal;
import weapon.cats.main.Entities.ai.FollowEntityGoal;
import weapon.cats.main.Entities.ai.GunAttachmentAttackGoal;
import weapon.cats.main.Entities.ai.LaserAttachmentAttackGoal;
import weapon.cats.main.Entities.ai.LookAtLaserGoal;
import weapon.cats.main.Entities.ai.SlimeAttachmentAttackGoal;
import weapon.cats.main.Entities.ai.TeslaCoilAttachmentAttackGoal;
import weapon.cats.main.Items.ItemManager;
import weapon.cats.main.Items.Attachments.Attachment;

@Mixin(CatEntity.class)
public class CatEntityMixin{
	
	@Inject(method = "initGoals()V", at = @At("TAIL"))
	public void LookAtLaser(CallbackInfo ci) {
		
		((MobAccessorMixin)((CatEntity)(Object)this)).getGoalSelector().add(11, new LookAtLaserGoal((CatEntity)(Object)this, LaserCursor.class, 20.0f));
		((MobAccessorMixin)((CatEntity)(Object)this)).getGoalSelector().add(2, new FollowEntityGoal((CatEntity)(Object)this, 1.0, 0.5f, 5.0f, LaserCursor.class));
		((MobAccessorMixin)((CatEntity)(Object)this)).getGoalSelector().add(5, new GunAttachmentAttackGoal((CatEntity)(Object)this, 1.0, 0, 200));
		((MobAccessorMixin)((CatEntity)(Object)this)).getGoalSelector().add(5, new SlimeAttachmentAttackGoal((CatEntity)(Object)this, 1.0, 0, 20));
		((MobAccessorMixin)((CatEntity)(Object)this)).getGoalSelector().add(5, new BombAttachmentAttackGoal((CatEntity)(Object)this, 1.0, 0, 30));
		((MobAccessorMixin)((CatEntity)(Object)this)).getGoalSelector().add(5, new LaserAttachmentAttackGoal((CatEntity)(Object)this));
		((MobAccessorMixin)((CatEntity)(Object)this)).getGoalSelector().add(5, new TeslaCoilAttachmentAttackGoal((CatEntity)(Object)this));
		
		((MobAccessorMixin)((CatEntity)(Object)this)).getTargetSelector().add(1, new TrackOwnerAttackerGoal(((CatEntity)(Object)this)));
		((MobAccessorMixin)((CatEntity)(Object)this)).getTargetSelector().add(2, new AttackWithOwnerGoal(((CatEntity)(Object)this)));
		
	}
	
	@Inject(method = "interactMob(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;",
			at = @At(value = "HEAD"),
			cancellable = true)
	public void addAttachment(PlayerEntity player, Hand hand ,CallbackInfoReturnable<ActionResult> ci) {
		
        ItemStack itemStack = player.getStackInHand(hand);
		CatEntity cat = (CatEntity)((Object)this);
		
		if(cat.isBaby()) {
			return;
		}
		
        if(cat.isOwner(player)){
        	if(itemStack.isOf(ItemManager.ATTACHER)) {
	        	if(cat.getEquippedStack(EquipmentSlot.CHEST).isEmpty()) {
	        		
	        		cat.equipStack(EquipmentSlot.CHEST, itemStack.copy());
	        		if (!player.getAbilities().creativeMode) {
	                    itemStack.decrement(1);
	                }
	        		ci.setReturnValue(ActionResult.SUCCESS);
	        	}
        	}
        	if(itemStack.isOf(ItemManager.DETACHER)){
        	
	        	if(cat.getEquippedStack(EquipmentSlot.CHEST).isOf(ItemManager.ATTACHER)) {
	        		
        			cat.dropStack(Attachment.getAttachment(cat.getEquippedStack(EquipmentSlot.CHEST)).copyAndEmpty());
        			Attachment.clearAttachment(cat.getEquippedStack(EquipmentSlot.CHEST));
	        		cat.dropStack(cat.getEquippedStack(EquipmentSlot.CHEST).copyAndEmpty());
	        		ci.setReturnValue(ActionResult.SUCCESS);
	        	}
        	}
        	if(itemStack.getItem() instanceof Attachment) {
        		
        		if(cat.getEquippedStack(EquipmentSlot.CHEST).isOf(ItemManager.ATTACHER)){
        			
        			cat.dropStack(Attachment.getAttachment(cat.getEquippedStack(EquipmentSlot.CHEST)).copyAndEmpty());
        			Attachment.clearAttachment(cat.getEquippedStack(EquipmentSlot.CHEST));
        			Attachment.addAttachment(cat.getEquippedStack(EquipmentSlot.CHEST), itemStack.copyWithCount(1));
        			if (!player.getAbilities().creativeMode) {
	                    itemStack.decrement(1);
	                }
        			
        		}else {
        			
        			ci.setReturnValue(ActionResult.FAIL);
        			
        		}
        		ci.setReturnValue(ActionResult.SUCCESS);
        	}
        	
        }
        if(itemStack.isEmpty() && player.isSneaking()) {
    		
    		cat.playSoundIfNotSilent(SoundEvents.ENTITY_CAT_STRAY_AMBIENT);
    		
    		for (int i = 0; i < 5; ++i) {
                double d = cat.getRandom().nextGaussian() * 0.02;
                double e = cat.getRandom().nextGaussian() * 0.02;
                double f = cat.getRandom().nextGaussian() * 0.02;
                cat.getWorld().addParticle(ParticleTypes.HEART, cat.getParticleX(1.0), cat.getRandomBodyY() + 1.0, cat.getParticleZ(1.0), d, e, f);
            }
    		
    		ci.setReturnValue(ActionResult.SUCCESS);
    		
    	}
	}
	
}
