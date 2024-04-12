package weapon.cats.main.mixin.Entities;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.mob.MobEntity;

@Mixin(MobEntity.class)
public interface MobAccessorMixin {
	@Accessor
	GoalSelector getGoalSelector();
}
