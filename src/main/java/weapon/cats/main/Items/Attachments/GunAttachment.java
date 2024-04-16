package weapon.cats.main.Items.Attachments;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import weapon.cats.main.SoundManager;
import weapon.cats.main.Entities.Bullet;
import weapon.cats.main.Entities.EntityManager;

public class GunAttachment extends Attachment{

	public GunAttachment(Settings settings) {
		super(settings);
	}
	
	public static void blastAt(LivingEntity shooter, LivingEntity target) {
		
		World world = shooter.getWorld();
		
		Bullet bullet = new Bullet(EntityManager.BULLET, world);
		bullet.setOwner(shooter);
		bullet.setPosition(shooter.getEyePos().add(0, 0.25, 0).addRandom(Random.create(), 0.0625f));
		Vec3d direction = target.getEyePos().subtract(bullet.getPos());
		bullet.setVelocity(direction.x, direction.y, direction.z, 5, 2);
		world.spawnEntity(bullet);
		
		shooter.playSoundIfNotSilent(SoundManager.Gunshot_Sound_Event);
		
	}
	
}
