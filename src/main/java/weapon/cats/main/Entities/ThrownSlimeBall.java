package weapon.cats.main.Entities;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import weapon.cats.main.Entities.StatusEffects.EffectManager;

public class ThrownSlimeBall extends ThrownItemEntity{

	public ThrownSlimeBall(EntityType<? extends ThrownItemEntity> entityType, World world) {
		super(entityType, world);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Item getDefaultItem() {
		// TODO Auto-generated method stub
		return Items.SLIME_BALL;
	}
	
	 @Override
	 protected void onCollision(HitResult hitResult) {
		
		super.onCollision(hitResult);
		List<Entity> affectedEntities = this.getWorld().getOtherEntities(this.getOwner(), getBoundingBox().expand(2.0));
		
		for(Entity e: affectedEntities) {
			
			if(e instanceof LivingEntity) {
				
				LivingEntity le = ((LivingEntity)e);
				if(this.getOwner() instanceof Tameable) {
					if(((Tameable)this.getOwner()).getOwner()==le){
						continue;
					}
					if(le instanceof Tameable && ((Tameable)this.getOwner()).getOwner() == ((Tameable)le).getOwner()) {
						continue;
					}
				}
				int slimelvl = 1;
				if(le.hasStatusEffect(EffectManager.SLIMED)) {
					slimelvl += le.getStatusEffect(EffectManager.SLIMED).getAmplifier();
				}
				le.removeStatusEffect(EffectManager.SLIMED);
				le.addStatusEffect(new StatusEffectInstance(EffectManager.SLIMED,200,slimelvl), this);
				
			}
			
		}
		
		for(int i = 0;i < 10;i++) {

			Vec3d vector = Vec3d.ZERO.addRandom(random, 1.0f).normalize();
			this.getEntityWorld().addParticle(ParticleTypes.ITEM_SLIME, getX(), getY(), getZ(), vector.x,vector.y,vector.z);
			
		}
		
		this.playSoundIfNotSilent(SoundEvents.BLOCK_SLIME_BLOCK_BREAK);
		this.discard();
	 }
	
}
