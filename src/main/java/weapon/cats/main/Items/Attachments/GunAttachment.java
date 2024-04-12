package weapon.cats.main.Items.Attachments;

import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class GunAttachment extends Attachment{

	public GunAttachment(Settings settings) {
		super(settings);
	}
	
	public static void blastAt(LivingEntity shooter, LivingEntity target) {
		
		World world = shooter.getWorld();
		world.spawnEntity(target);
		
	}
	
}
