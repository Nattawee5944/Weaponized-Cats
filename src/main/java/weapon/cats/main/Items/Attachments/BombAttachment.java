package weapon.cats.main.Items.Attachments;

import org.joml.Math;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Tameable;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import weapon.cats.main.SoundManager;
import weapon.cats.main.Entities.Dynamite;
import weapon.cats.main.Entities.EntityManager;

public class BombAttachment extends Attachment{

	public BombAttachment(Settings settings) {
		super(settings);
		// TODO Auto-generated constructor stub
	}
	
	public static void YeetBomb(LivingEntity shooter, LivingEntity target) {
		
		World world = shooter.getWorld();
		
		Dynamite bomb = new Dynamite(EntityManager.DYNAMITE, world);
		bomb.setOwner(((Tameable)shooter).getOwner());
		bomb.setPosition(shooter.getEyePos().add(0, 0.25, 0));
		Vec3d direction = target.getEyePos().subtract(bomb.getPos());
		
		direction = calculateShot(direction,1.0);
		System.out.println(direction.length());
		//direction = new Vec3d(direction.x, Math.sin(theta+Math.toRadians(5)),direction.z).multiply(1.5);
		bomb.setVelocity(direction);
		world.spawnEntity(bomb);
		
		shooter.playSoundIfNotSilent(SoundManager.Bomb_Plop_Sound_Event);
		
	}
	
	public static Vec3d calculateShot(Vec3d dist, double u) {
		
		double sSquared = dist.horizontalLengthSquared();
		double h = dist.y;
		double g = 0.05;
		double u2 = u*u;
		
		double delta = u2*u2-2*g*h*u2-g*g*sSquared;
		if(delta < 0)//unreachable
			return dist.normalize().multiply(u);
		
		double alpha = (u2-g*h - Math.sqrt(delta))/(2*dist.lengthSquared())*sSquared;
		if(alpha < 0)//unreachable
			return dist.normalize().multiply(u);
		
		Vec3d horizontallyProjected = dist.multiply(1, 0, 1).normalize();
		
		double y2 = u2-alpha;
		if(y2 < 0)
			return dist.normalize().multiply(u);
		
		return horizontallyProjected.multiply(Math.sqrt(alpha)).add(0, Math.sqrt(y2), 0);
		
	}
	
}