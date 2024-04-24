package weapon.cats.main.Entities.ai;

import java.util.EnumSet;

import org.joml.Vector3f;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.ParticleS2CPacket;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.Vec3d;
import weapon.cats.main.Items.ItemManager;
import weapon.cats.main.Items.Attachments.Attachment;
import weapon.cats.main.Items.Attachments.LaserAttachment;

public class LaserAttachmentAttackGoal extends Goal {
	private final MobEntity mob;
	private int beamTicks;
	private int outOfSight = 0;
	 
	public LaserAttachmentAttackGoal(MobEntity mob) {
		this.mob = mob;
		this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
	}
	
	@Override
	public boolean canStart() {
		if(mob.getEquippedStack(EquipmentSlot.CHEST).isOf(ItemManager.ATTACHER)) {
					
			ItemStack attachmentStack = Attachment.getAttachment(mob.getEquippedStack(EquipmentSlot.CHEST));
			if(attachmentStack != null && attachmentStack.isOf(ItemManager.LASER_ATTACHMENT)) {
						 			
				LivingEntity livingEntity = this.mob.getTarget();
				return livingEntity != null && livingEntity.isAlive();
			}
		}
		return false;
	}
	
	@Override
	public boolean shouldContinue() {
		return super.shouldContinue() && (this.mob.getTarget() != null);
	}
	
	@Override
	public void start() {
		if(mob.getEquippedStack(EquipmentSlot.CHEST).isOf(ItemManager.ATTACHER)) {
			
			ItemStack attachmentStack = Attachment.getAttachment(mob.getEquippedStack(EquipmentSlot.CHEST));
			if(attachmentStack != null && attachmentStack.isOf(ItemManager.LASER_ATTACHMENT)) {
		 			
				this.beamTicks = -50;
				this.mob.getNavigation().stop();
				LivingEntity livingEntity = this.mob.getTarget();
				if (livingEntity != null) {
				this.mob.getLookControl().lookAt(livingEntity, 90.0f, 90.0f);
				}
				this.mob.velocityDirty = true;
				outOfSight = 0;
	 		}
		}
	}
	
	@Override
	public void stop() {
		this.mob.setTarget(null);
	 
	}
	
	@Override
	public boolean shouldRunEveryTick() {
		return true;
	}
	
	@Override
	public void tick() {
		
		if(mob.getEquippedStack(EquipmentSlot.CHEST).isOf(ItemManager.ATTACHER)) {
				
			ItemStack attachmentStack = Attachment.getAttachment(mob.getEquippedStack(EquipmentSlot.CHEST));
				if(attachmentStack != null && attachmentStack.isOf(ItemManager.LASER_ATTACHMENT)) {
				
				LivingEntity livingEntity = this.mob.getTarget();
				if (livingEntity == null) {
					return;
				}
				this.mob.getNavigation().stop();
				this.mob.getLookControl().lookAt(livingEntity, 90.0f, 90.0f);
				if (!this.mob.canSee(livingEntity) && outOfSight  > 10) {
					this.mob.setTarget(null);
					return;
				}else {
					if(!this.mob.canSee(livingEntity)) {
						outOfSight++;
				 	}else {
				 		outOfSight = 0;
				 	}
				}
				Vec3d origin = mob.getEyePos().add(0, 0.5, 0);
				Vec3d vec3d = livingEntity.getEyePos().subtract(origin);
				Vec3d direction = vec3d.normalize().multiply(0.2);
				double l2 = vec3d.lengthSquared();
				ParticleEffect particleEffect = new DustParticleEffect(new Vector3f(0.5f-beamTicks/30f,0f,0f), 0.3f);
				
				for(Vec3d vector = Vec3d.ZERO;vector.lengthSquared() < l2;vector = vector.add(direction)) {
					
				 	Vec3d v = vector.add(origin);
				 	mob.getWorld().addParticle(particleEffect, v.x,v.y,v.z, 0, 0, 0);
				 	ParticleS2CPacket packet = new ParticleS2CPacket(particleEffect, false, v.x,v.y,v.z, 0,0,0, 0, 1);
				 	mob.getWorld().getServer().getPlayerManager().sendToAll(packet);
				 	
				}
				 
				if(++beamTicks == 0) {
				
					LaserAttachment.CastBeam(mob, livingEntity);
					beamTicks = -50;
				}
			}
		}
	}
}