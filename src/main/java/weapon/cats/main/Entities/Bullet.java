package weapon.cats.main.Entities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.RaycastContext;
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
		
		boolean bl = this.noClip;
        Vec3d vec3d = this.getVelocity();
        BlockPos blockPos = this.getBlockPos();
        BlockState blockState = this.getWorld().getBlockState(blockPos);
        VoxelShape voxelShape;
		if (!(blockState.isAir() || bl || (voxelShape = blockState.getCollisionShape(this.getWorld(), blockPos)).isEmpty())) {
            Vec3d vec3d2 = this.getPos();
            for (Box box : voxelShape.getBoundingBoxes()) {
                if (!box.offset(blockPos).contains(vec3d2)) continue;
                this.kill();
                return;
            }
        }
		
		Vec3d vec3d3 = this.getPos();
        Vec3d vec3d2 = vec3d3.add(vec3d);
        HitResult hitResult = this.getWorld().raycast(new RaycastContext(vec3d3, vec3d2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
        if (hitResult.getType() != HitResult.Type.MISS) {
            vec3d2 = hitResult.getPos();
        }
        EntityHitResult entityHitResult = this.getEntityCollision(vec3d3, vec3d2);
        //System.out.println("Checkpoint");
            
        if (entityHitResult != null) {
            hitResult = entityHitResult;
        }
        if (hitResult != null && hitResult.getType() == HitResult.Type.ENTITY) {
            Entity entity = ((EntityHitResult)hitResult).getEntity();
            Entity entity2 = this.getOwner();
            if (entity instanceof PlayerEntity && entity2 instanceof PlayerEntity && !((PlayerEntity)entity2).shouldDamagePlayer((PlayerEntity)entity)) {
                hitResult = null;
                entityHitResult = null;
            }
            if(entity instanceof Bullet || entity == entity2) {
                	
             	hitResult = null;
               	entityHitResult = null;
               	
            }
        }
        if (hitResult != null && !bl) {
            this.onCollision(hitResult);
            this.velocityDirty = true;
        }
		//System.out.println("Checkpoint3");
		
        vec3d = this.getVelocity();
        double e = vec3d.x;
        double f = vec3d.y;
        double g = vec3d.z;
        double h = this.getX() + e;
        double j = this.getY() + f;
        double k = this.getZ() + g;
        this.setPosition(h, j, k);
        this.checkBlockCollision();
        
		if(--lifeLeft <= 0) {
			
			this.kill();
			
		}
		
	}

    protected EntityHitResult getEntityCollision(Vec3d currentPosition, Vec3d nextPosition) {
        return ProjectileUtil.getEntityCollision(this.getWorld(), this, currentPosition, nextPosition, this.getBoundingBox().stretch(this.getVelocity()).expand(1.0), this::canHit);
    }

	@Override
	public void onEntityHit(EntityHitResult event) {
		
		DamageSource damageSource;
        super.onEntityHit(event);
        Entity entity = event.getEntity();
        
        damageSource = this.getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner());
        if(entity instanceof Tameable && this.getOwner() instanceof Tameable) {
        	
        	if(((Tameable)entity).getOwner() == ((Tameable)this.getOwner()).getOwner()) {
        		return;
        	}
        	
        }
    	entity.damage(damageSource, 0.0625f*lifeLeft);
        if(entity instanceof LivingEntity) {
        	
        	((LivingEntity) entity).timeUntilRegen-=4;
        	
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