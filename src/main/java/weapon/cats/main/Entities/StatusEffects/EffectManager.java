package weapon.cats.main.Entities.StatusEffects;

import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import weapon.cats.main.WeaponizedCats;

public class EffectManager {
	
	public static StatusEffect SLIMED = new Slimed().addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "ff755948-5c44-48ca-9ad2-da5f90320e61", -0.02, Operation.MULTIPLY_TOTAL);
	
	public static void InitEffects() {
		
		Registry.register(Registries.STATUS_EFFECT, new Identifier(WeaponizedCats.MOD_ID,"slimed"), SLIMED);
	}
}
