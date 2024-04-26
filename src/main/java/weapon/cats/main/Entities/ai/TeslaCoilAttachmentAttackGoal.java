package weapon.cats.main.Entities.ai;

import java.util.EnumSet;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.item.ItemStack;
import weapon.cats.main.Entities.ElectricNode;
import weapon.cats.main.Items.ItemManager;
import weapon.cats.main.Items.Attachments.Attachment;
import weapon.cats.main.Items.Attachments.TeslaCoilAttachment;

public class TeslaCoilAttachmentAttackGoal extends Goal{
	
	private final MobEntity mob;
	private ElectricNode en;
    public TeslaCoilAttachmentAttackGoal(LivingEntity mob) {
        
        this.mob = (MobEntity)((Object)mob);
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    @Override
    public boolean canStart() {
    	if(mob.getEquippedStack(EquipmentSlot.CHEST).isOf(ItemManager.ATTACHER)) {
    		
    		ItemStack attachmentStack = Attachment.getAttachment(mob.getEquippedStack(EquipmentSlot.CHEST));
    		if(attachmentStack != null && attachmentStack.isOf(ItemManager.TESLA_COIL_ATTACHMENT)) {
    			List<Entity> tmp = mob.getEntityWorld().getOtherEntities(mob, mob.getBoundingBox().expand(5.0));
    			boolean flag = false;
    			for(Entity e : tmp) {
    				if(e instanceof Monster) {
    					flag = true;
    					break;
    				}
    			}
    			if(flag)
	    	        return true;
    		}
    		
    	}
    	return false;
    }

    @Override
    public boolean shouldContinue() {
        return this.canStart();
    }

    @Override
    public void stop() {
    	en.remove(RemovalReason.DISCARDED);
    	en = null;
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }
    
    @Override
    public void tick() {
    	
    	if(en == null) {
    		
    		en = TeslaCoilAttachment.Shock(mob);
    		
    	}
    	
    }
	
}