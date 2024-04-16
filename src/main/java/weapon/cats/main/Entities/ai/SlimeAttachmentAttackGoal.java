package weapon.cats.main.Entities.ai;

import java.util.EnumSet;

import org.jetbrains.annotations.Nullable;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import weapon.cats.main.Items.ItemManager;
import weapon.cats.main.Items.Attachments.Attachment;
import weapon.cats.main.Items.Attachments.SlimeAttachment;

public class SlimeAttachmentAttackGoal extends Goal{
	
	private final MobEntity mob;
    @Nullable
    private LivingEntity target;
    private int updateCountdownTicks = -1;
    private final double mobSpeed;
    private int seenTargetTicks;
    private final float squaredMaxShootRange;

    public SlimeAttachmentAttackGoal(LivingEntity mob, double mobSpeed, int intervalTicks, float maxShootRange) {
        this(mob, mobSpeed, intervalTicks, intervalTicks, maxShootRange);
    }

    public SlimeAttachmentAttackGoal(LivingEntity mob, double mobSpeed, int minIntervalTicks, int maxIntervalTicks, float maxShootRange) {
        
        this.mob = (MobEntity)((Object)mob);
        this.mobSpeed = mobSpeed;
        this.squaredMaxShootRange = maxShootRange * maxShootRange;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    @Override
    public boolean canStart() {
    	if(mob.getEquippedStack(EquipmentSlot.CHEST).isOf(ItemManager.ATTACHER)) {
    		
    		ItemStack attachmentStack = Attachment.getAttachment(mob.getEquippedStack(EquipmentSlot.CHEST));
    		if(attachmentStack != null && attachmentStack.isOf(ItemManager.SLIME_ATTACHMENT)) {
    			
    	        LivingEntity livingEntity = this.mob.getTarget();
    	        if (livingEntity == null || !livingEntity.isAlive()) {
    	            return false;
    	        }
    	        this.target = livingEntity;
    	        return true;
    			
    		}
    		
    	}
    	return false;
    }

    @Override
    public boolean shouldContinue() {
        return this.canStart() || this.target.isAlive() && !this.mob.getNavigation().isIdle();
    }

    @Override
    public void stop() {
        this.target = null;
        this.seenTargetTicks = 0;
        this.updateCountdownTicks = -1;
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        double d = this.mob.squaredDistanceTo(this.target.getX(), this.target.getY(), this.target.getZ());
        boolean bl = this.mob.getVisibilityCache().canSee(this.target);
        this.seenTargetTicks = bl ? ++this.seenTargetTicks : 0;
        if (d > (double)this.squaredMaxShootRange || this.seenTargetTicks < 5) {
            this.mob.getNavigation().startMovingTo(this.target, this.mobSpeed);
        } else {
            this.mob.getNavigation().stop();
        }
        this.mob.getLookControl().lookAt(this.target, 30.0f, 30.0f);
        if (--this.updateCountdownTicks <= 0) {
            if (!bl) {
                return;
            }
            SlimeAttachment.ThrowSlime(mob, target);
            this.updateCountdownTicks = 20;
        }
    }
	
}