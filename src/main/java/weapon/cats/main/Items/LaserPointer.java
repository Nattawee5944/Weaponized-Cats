package weapon.cats.main.Items;

import net.minecraft.command.argument.EntityAnchorArgumentType.EntityAnchor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import weapon.cats.main.Entities.EntityManager;
import weapon.cats.main.Entities.LaserCursor;

public class LaserPointer extends Item{

	public LaserPointer(Settings settings) {
		super(settings);
		// TODO Auto-generated constructor stub
	}
	
	private LaserCursor cursor;
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
		
		playerEntity.setCurrentHand(hand);
		if(!world.isClient) {
			HitResult raycast = playerEntity.raycast(100, 0, false);
			Vec3d pos = raycast.getPos();
			cursor = new LaserCursor(EntityManager.LASER_CURSOR, world);
			cursor.setPosition(pos);
			if(raycast.getType() == HitResult.Type.BLOCK) {
				
				BlockHitResult blockraycast = (BlockHitResult)raycast;
				cursor.lookAt(EntityAnchor.EYES, pos.add(blockraycast.getSide().getVector().getX(), blockraycast.getSide().getVector().getY(), blockraycast.getSide().getVector().getZ()));
				
			}
			world.spawnEntity(cursor);
		}
		return TypedActionResult.consume(playerEntity.getStackInHand(hand));
    }
	
	@Override
	public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
		
		super.finishUsing(stack, world, user);
		if(!world.isClient)cursor.kill();
		
	}
	
	@Override
	public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
		if(!world.isClient) {
			HitResult raycast = user.raycast(100, 0, false);
			Vec3d pos = raycast.getPos();
			if(cursor != null) {
				cursor.setPosition(pos);
				cursor.ticksToDie++;
			}
			if(raycast.getType() == HitResult.Type.BLOCK) {
				
				BlockHitResult blockraycast = (BlockHitResult)raycast;
				if(cursor != null) {
					cursor.lookAt(EntityAnchor.EYES, pos.add(blockraycast.getSide().getVector().getX(), blockraycast.getSide().getVector().getY(), blockraycast.getSide().getVector().getZ()));
				}
			}
		}
	}

}
