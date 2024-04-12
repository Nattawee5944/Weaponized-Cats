package weapon.cats.main.Entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class Bullet extends ProjectileEntity {
	
	private int lifeLeft;
	
	public Bullet(EntityType<? extends ProjectileEntity> entityType, World world) {
		super(entityType, world);
		lifeLeft = 40;
	}
	
	@Override
	protected void initDataTracker() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void tick() {
		
		super.tick();
		if(--lifeLeft <= 0) {
			
			this.kill();
			
		}
	}
	
	@Override
	public void onEntityHit(EntityHitResult event) {
		
		DamageSource damageSource;
        super.onEntityHit(event);
        Entity entity = event.getEntity();
        
        damageSource = this.getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner());
    	entity.damage(damageSource, 0.125f*lifeLeft);
        if(entity instanceof LivingEntity) {
        	
        	((LivingEntity) entity).hurtTime--;
        	
        }
        
	}
	
	@Override
	public void onBlockHit(BlockHitResult event) {
		
		this.getWorld().addParticle(
				new BlockStateParticleEffect(ParticleTypes.BLOCK,this.getWorld().getBlockState(event.getBlockPos())),
				event.getPos().x, event.getPos().y, event.getPos().z,
				0,0,0
			);
		
	}
	
}