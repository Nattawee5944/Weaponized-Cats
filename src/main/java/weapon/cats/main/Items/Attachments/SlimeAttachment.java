package weapon.cats.main.Items.Attachments;

import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import weapon.cats.main.Entities.EntityManager;
import weapon.cats.main.Entities.ThrownSlimeBall;

public class SlimeAttachment extends Attachment{

	public SlimeAttachment(Settings settings) {
		super(settings);
		// TODO Auto-generated constructor stub
	}
	
	public static void ThrowSlime(LivingEntity shooter, LivingEntity target) {
		
		World world = shooter.getWorld();
		
		ThrownSlimeBall ball = new ThrownSlimeBall(EntityManager.THROWN_SLIMEBALL, world);
		ball.setOwner(shooter);
		ball.setPosition(shooter.getEyePos().add(0, 0.25, 0));
		Vec3d direction = target.getEyePos().subtract(ball.getPos());
		ball.setVelocity(direction.x, direction.y+0.5, direction.z, 2, 0);
		world.spawnEntity(ball);
		
		shooter.playSoundIfNotSilent(SoundEvents.BLOCK_SLIME_BLOCK_PLACE);
		
	}
	
}