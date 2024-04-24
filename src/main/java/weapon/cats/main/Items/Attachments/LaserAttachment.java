package weapon.cats.main.Items.Attachments;

import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.SoundEvents;
import weapon.cats.main.Entities.EntityManager;
import weapon.cats.main.Entities.LaserExplosion;

public class LaserAttachment extends Attachment{

	public LaserAttachment(Settings settings) {
		super(settings);
		// TODO Auto-generated constructor stub
	}
	
	public static void CastBeam(LivingEntity shooter, LivingEntity target) {
		
		target.damage(shooter.getDamageSources().genericKill(), 20);
		
		boom(shooter,target);
		
		target.playSoundIfNotSilent(SoundEvents.ENTITY_GENERIC_EXPLODE);
		
	}

	private static void boom(LivingEntity shooter,LivingEntity target) {
		
		LaserExplosion explosion = new LaserExplosion(EntityManager.LASER_EXPLOSION,target.getEntityWorld());
		explosion.setOwner(shooter);
		explosion.setPosition(target.getEyePos());
		target.getEntityWorld().spawnEntity(explosion);
		
	}
	
}