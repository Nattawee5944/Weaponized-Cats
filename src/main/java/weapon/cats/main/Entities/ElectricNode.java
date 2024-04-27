package weapon.cats.main.Entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Ownable;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.mob.Monster;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import weapon.cats.main.SoundManager;
import weapon.cats.main.Items.Attachments.TeslaCoilAttachment;

public class ElectricNode extends Entity implements Ownable{
	
	LivingEntity attacker;
	public Set<LivingEntity> chain = new HashSet<>();
	private UUID ownerUuid;
	public Map<Entity,List<Entity>> pairs = new HashMap<>();
	
	public ElectricNode(EntityType<?> type, World world) {
		super(type, world);
	}
	
	public boolean shouldDamage(LivingEntity entity) {
		
		if(entity == attacker) return false;
		if((entity instanceof Tameable) && (attacker instanceof Tameable) && ((Tameable)entity).getOwner() == ((Tameable)attacker).getOwner())return false;
		if((attacker instanceof Tameable) && entity == ((Tameable)attacker).getOwner())return false;
		return true;
		
	}
	
	public void setOwner(LivingEntity owner) {
		
		if (owner != null) {
            this.ownerUuid = owner.getUuid();
            this.attacker = owner;
        }
		
	}
	
	@Override
	public void tick() {
		
		if(attacker != null) {
			
			setPosition(attacker.getPos());
			
		}else {
			try {
				setOwner((LivingEntity) getOwner());
			}catch(Exception e) {
				this.discard();
			}
		}
		if(this.age%5 == 0) {
			//scans for nearby entities to attack
			chain.clear();
			pairs.clear();
			
			List<Entity> list = this.getWorld().getOtherEntities(this, getBoundingBox().expand(7.0), (entity)->{return entity instanceof Monster && shouldDamage((LivingEntity)entity);});
			
			Queue<LivingEntity> queue = new LinkedList<>();
			for(Entity e: list) {
				
				LivingEntity le = (LivingEntity) e;
				if(le.squaredDistanceTo(this) < 2500) {
					
					chain.add(le);
					queue.add(le);
					
					pairs.putIfAbsent(this, new ArrayList<>());
					List<Entity> tmp = pairs.get(this);
					tmp.add(le);
					pairs.put(this, tmp);
					
				}
			}
			
			while(!queue.isEmpty()) {
				
				LivingEntity target = queue.poll();
				List<Entity> tmpList = this.getWorld().getOtherEntities(this, target.getBoundingBox().expand(7.0), (entity)->{return entity instanceof Monster && shouldDamage((LivingEntity)entity) && !chain.contains(entity) && entity.squaredDistanceTo(this) < 2500;});
				
				for(Entity e: tmpList) {
					
					LivingEntity le = (LivingEntity) e;
					//if(le.squaredDistanceTo(this) < 2500) {
						
						chain.add(le);
						queue.add(le);
						
						//stores the connection between entities, where the electric will arcs
						pairs.putIfAbsent(target, new ArrayList<>());
						List<Entity> tmp = pairs.get(target);
						tmp.add(le);
						pairs.put(target, tmp);
						
					//}
					
				}
				
			}
			this.playSoundIfNotSilent(SoundManager.Electric_Shock_Sound_Event);
		}
		
		for(LivingEntity e : chain) {
			e.damage(this.getDamageSources().lightningBolt(), 5);
			e.timeUntilRegen--;
		}
		
		
		if(this.age%100 == 0 && !this.getEntityWorld().isClient() && attacker != null) {
			
			TeslaCoilAttachment.generateAndSendPacket(attacker, this);
			
		}
		
	}
	
	@Override
	protected void initDataTracker() {
	}

	@Override
	protected void readCustomDataFromNbt(NbtCompound nbt) {
		if (nbt.containsUuid("Owner")) {
            this.ownerUuid = nbt.getUuid("Owner");
            this.attacker = null;
        }
	}

	@Override
	protected void writeCustomDataToNbt(NbtCompound nbt) {
		if (this.ownerUuid != null) {
            nbt.putUuid("Owner", this.ownerUuid);
        }
	}

	@Override
	public Entity getOwner() {
		if (this.attacker != null && !this.attacker.isRemoved()) {
            return this.attacker;
        }
        if (this.ownerUuid != null && this.getWorld() instanceof ServerWorld) {
            this.attacker = (LivingEntity) ((ServerWorld)this.getWorld()).getEntity(this.ownerUuid);
            return this.attacker;
        }
        return null;
	}

}
