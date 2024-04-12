package weapon.cats.main.Entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class LaserCursor extends Entity{
	
	public int ticksToDie = 1;
	public LaserCursor(EntityType<?> type, World world) {
		super(type, world);
	}
	
	public Vec3d getFacingVector() {
		float f = -MathHelper.sin(this.getYaw() * ((float)Math.PI / 180)) * MathHelper.cos(this.getPitch() * ((float)Math.PI / 180));
        float g = -MathHelper.sin((this.getPitch()) * ((float)Math.PI / 180));
        float h = MathHelper.cos(this.getYaw() * ((float)Math.PI / 180)) * MathHelper.cos(this.getPitch() * ((float)Math.PI / 180));
        return new Vec3d(f,g,h);
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
		if(ticksToDie > 0) {
			ticksToDie--;
			return;
		}
		if(!this.getWorld().isClient()) {
			this.kill();
		}
	}
	
}
