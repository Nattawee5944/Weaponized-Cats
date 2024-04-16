package weapon.cats.main.Entities.StatusEffects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class Slimed extends StatusEffect{

	protected Slimed() {
		super(StatusEffectCategory.HARMFUL, 0x26C000);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		int i = 100 >> amplifier;
        if (i > 0) {
            return duration % i == 0;
        }
        return true;
		
	}
	
	@Override
	public void applyUpdateEffect(LivingEntity entity, int amplifier) {
		entity.timeUntilRegen = 0;
		entity.damage(entity.getDamageSources().magic(), amplifier*0.25f);
	}
	
}
