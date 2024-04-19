package weapon.cats.main.Entities;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.Explosion.DestructionType;

public class Dynamite extends ProjectileEntity{

	private int fuse;
	public Dynamite(EntityType<? extends ProjectileEntity> entityType, World world) {
		super(entityType, world);
		fuse = 600;
	}
	@Override
	public void tick() {
		
		fuse--;
		super.tick();
		this.tickWithoutDrag();
		
		if(fuse <= 0) {
			
			boom(this.getPos());
			
		}
		
		List<Entity> nearbyEntity = this.getWorld().getOtherEntities(null, getBoundingBox());
		boolean flag = false;
		for(Entity e:nearbyEntity) {
			
			if(e instanceof LivingEntity) {
				flag = true;
				break;
			}
			
		}
		
		if(flag && fuse < 590){
			
			boom(this.getPos());
			
		}
		
	}
	
	private void tickWithoutDrag() {
		Vec3d vec3d2;
        VoxelShape voxelShape;
        Vec3d vec3d = this.getVelocity();
        if (this.prevPitch == 0.0f && this.prevYaw == 0.0f) {
            double d = vec3d.horizontalLength();
            this.setYaw((float)(MathHelper.atan2(vec3d.x, vec3d.z) * 57.2957763671875));
            this.setPitch((float)(MathHelper.atan2(vec3d.y, d) * 57.2957763671875));
            this.prevYaw = this.getYaw();
            this.prevPitch = this.getPitch();
        }
        BlockPos blockPos = this.getBlockPos();
        BlockState blockState = this.getWorld().getBlockState(blockPos);
        if (!(blockState.isAir() || (voxelShape = blockState.getCollisionShape(this.getWorld(), blockPos)).isEmpty())) {
            vec3d2 = this.getPos();
            for (Box box : voxelShape.getBoundingBoxes()) {
                if (!box.offset(blockPos).contains(vec3d2)) continue;
                break;
            }
        }
        if (this.isTouchingWaterOrRain() || blockState.isOf(Blocks.POWDER_SNOW)) {
            this.extinguish();
        }
        Vec3d vec3d3 = this.getPos();
        vec3d2 = vec3d3.add(vec3d);
        HitResult hitResult = this.getWorld().raycast(new RaycastContext(vec3d3, vec3d2, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
        if (hitResult.getType() != HitResult.Type.MISS) {
            vec3d2 = hitResult.getPos();
        }
        EntityHitResult entityHitResult = this.getEntityCollision(vec3d3, vec3d2);
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
        }
        if (hitResult != null) {
            this.onCollision(hitResult);
            this.velocityDirty = true;
        }
        vec3d = this.getVelocity();
        double e = vec3d.x;
        double f = vec3d.y;
        double g = vec3d.z;
        double h = this.getX() + e;
        double j = this.getY() + f;
        double k = this.getZ() + g;
        double l = vec3d.horizontalLength();
        
        this.setPitch((float)(MathHelper.atan2(f, l) * 57.2957763671875));
        this.setPitch(PersistentProjectileEntity.updateRotation(this.prevPitch, this.getPitch()));
        this.setYaw(PersistentProjectileEntity.updateRotation(this.prevYaw, this.getYaw()));
        if (!this.hasNoGravity()) {
            Vec3d vec3d4 = this.getVelocity();
            this.setVelocity(vec3d4.x, vec3d4.y - (double)0.05f, vec3d4.z);
        }
        this.setPosition(h, j, k);
        this.checkBlockCollision();
	}
	
	@Override
	public void onBlockHit(BlockHitResult blockHitResult) {
		
		BlockState blockState = this.getWorld().getBlockState(blockHitResult.getBlockPos());
        blockState.onProjectileHit(this.getWorld(), blockState, blockHitResult, this);
		
        Vec3i tmp = blockHitResult.getSide().getVector();
        Vec3d vec3d = new Vec3d(tmp.getX(),tmp.getY(),tmp.getZ());
        Vec3d velocity = this.getVelocity();
        double amp = vec3d.dotProduct(velocity);
        if(Math.abs(amp) >= 0.2) {
        	this.addVelocity(vec3d.multiply(-1.64*amp));
        }else {
        	this.addVelocity(vec3d.multiply(-1*amp));
        }
        //I chose 64% cause my friend picked a random number
	}
	
	@Override
	public void onEntityHit(EntityHitResult entityHitResult) {
		
		Vec3d pos = entityHitResult.getPos();
		boom(pos);
		
	}
	
	public void boom(Vec3d pos) {
		
		Explosion explosion = new Explosion(this.getWorld(), this, this.getDamageSources().explosion(this, this.getOwner()), null, this.getPos().x, this.getPos().y, this.getPos().z, 5.0f, false, DestructionType.KEEP);
		explosionAction(explosion,3.0f);
		explosion.affectWorld(true);
		this.discard();
		
	}
	
	private void explosionAction(Explosion explosion, float power) {
		
		this.getWorld().emitGameEvent(this, GameEvent.EXPLODE, this.getPos());
		float q = power * 2.0f;
        int k = MathHelper.floor(this.getX() - (double)q - 1.0);
        int l = MathHelper.floor(this.getX() + (double)q + 1.0);
        int r = MathHelper.floor(this.getY() - (double)q - 1.0);
        int s = MathHelper.floor(this.getY() + (double)q + 1.0);
        int t = MathHelper.floor(this.getZ() - (double)q - 1.0);
        int u = MathHelper.floor(this.getZ() + (double)q + 1.0);
        List<Entity> list = this.getWorld().getOtherEntities(this, new Box(k, r, t, l, s, u));
        Vec3d vec3d = this.getPos();
        for (int v = 0; v < list.size(); ++v) {
            PlayerEntity playerEntity;
            double ad;
            double z;
            double y;
            double x;
            double aa;
            double w;
            Entity entity = list.get(v);
            if(!(entity instanceof LivingEntity)) continue;
            if (entity.isImmuneToExplosion() || !((w = Math.sqrt(entity.squaredDistanceTo(vec3d)) / (double)q) <= 1.0) || (aa = Math.sqrt((x = entity.getX() - this.getX()) * x + (y = (entity instanceof TntEntity ? entity.getY() : entity.getEyeY()) - this.getY()) * y + (z = entity.getZ() - this.getZ()) * z)) == 0.0) continue;
            if((entity instanceof Tameable && ((Tameable)entity).getOwner()==this.getOwner()) || this.isOwner(entity))continue;
            
            x /= aa;
            y /= aa;
            z /= aa;
            double ab = Explosion.getExposure(vec3d, entity);
            double ac = (1.0 - w) * ab;
            entity.damage(explosion.getDamageSource(), (int)((ac * ac + ac) / 2.0 * 7.0 * (double)q + 1.0));
            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity)entity;
                ad = ProtectionEnchantment.transformExplosionKnockback(livingEntity, ac);
            } else {
                ad = ac;
            }
            Vec3d vec3d2 = new Vec3d(x *= ad, y *= ad, z *= ad);
            entity.setVelocity(entity.getVelocity().add(vec3d2));
            if (!(entity instanceof PlayerEntity) || (playerEntity = (PlayerEntity)entity).isSpectator() || playerEntity.isCreative() && playerEntity.getAbilities().flying) continue;
            explosion.getAffectedPlayers().put(playerEntity, vec3d2);
            
        }
	}
	@Override
	protected void initDataTracker() {
		// TODO Auto-generated method stub
		
	}
	@Nullable
    protected EntityHitResult getEntityCollision(Vec3d currentPosition, Vec3d nextPosition) {
        return ProjectileUtil.getEntityCollision(this.getWorld(), this, currentPosition, nextPosition, this.getBoundingBox().stretch(this.getVelocity()).expand(1.0), this::canHit);
    }
	
}
