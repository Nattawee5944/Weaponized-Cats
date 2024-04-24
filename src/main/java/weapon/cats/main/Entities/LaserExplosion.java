package weapon.cats.main.Entities;

import java.util.List;
import java.util.function.Predicate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Tameable;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class LaserExplosion extends Entity{
	
	public int explosionTick = 0;
	public Entity owner;
	
	public LaserExplosion(EntityType<?> type, World world) {
		super(type, world);
	}
	
	public void setOwner(Entity entity) {
		owner = entity;
	}
	
	@Override
	protected void initDataTracker() {
		
	}

	@Override
	protected void readCustomDataFromNbt(NbtCompound nbt) {
		
	}

	@Override
	protected void writeCustomDataToNbt(NbtCompound nbt) {
		
	}
	
	@Override
	public void tick() {
		
		if(++explosionTick >= 15) {
			
			this.discard();
			
		}
		if(explosionTick < 5) {
			for(int i = 0;i < 100;i++) {
				Vec3d vector = new Vec3d(random.nextFloat()-0.5,random.nextFloat()-0.5,random.nextFloat()-0.5).normalize();
				this.getEntityWorld().addParticle(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), vector.getX(),vector.getY(),vector.getZ());
			}
		}
		if(explosionTick == 1) {

			Predicate<?super Entity> predicate = (entity)->{
				if(entity instanceof Tameable && this.owner instanceof Tameable && ((Tameable)(entity)).getOwner() == ((Tameable)this.owner).getOwner()) {
					
					return false;
					
				}
				if(entity == this.owner || (owner instanceof Tameable && ((Tameable)this.owner).getOwner() == entity)) {
					
					return false;
					
				}
				if(entity instanceof LaserExplosion) {
					return false;
				}
				return true;
			};
			List<Entity> list = this.getWorld().getOtherEntities(this, getBoundingBox().offset(0, -7.5, 0), predicate);
			
			for(Entity e : list) {
				
				float dmg = (float) (30f/(e.squaredDistanceTo(this)+1));
				e.damage(this.getDamageSources().explosion(this, owner), dmg);
				e.setOnFireFor(5);
			}
			
		}
	}
	
}