package weapon.cats.main;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class SoundManager {
	public static final Identifier GUNSHOT_SOUND_ID = new Identifier(WeaponizedCats.MOD_ID,"gunshot");
	public static SoundEvent Gunshot_Sound_Event = SoundEvent.of(GUNSHOT_SOUND_ID);
	
	public static final Identifier BOMB_PLOP_SOUND_ID = new Identifier(WeaponizedCats.MOD_ID,"bomb_plop");
	public static SoundEvent Bomb_Plop_Sound_Event = SoundEvent.of(BOMB_PLOP_SOUND_ID);
	
	public static final Identifier LASER_CHARGE_SOUND_ID = new Identifier(WeaponizedCats.MOD_ID,"laser_charge");
	public static SoundEvent Laser_Charge_Sound_Event = SoundEvent.of(LASER_CHARGE_SOUND_ID);
	
	public static final Identifier ELECTRIC_SHOCK_SOUND_ID = new Identifier(WeaponizedCats.MOD_ID,"electric_shock");
	public static SoundEvent Electric_Shock_Sound_Event = SoundEvent.of(ELECTRIC_SHOCK_SOUND_ID);
	
	public static void InitializeSounds() {
		
		Registry.register(Registries.SOUND_EVENT, GUNSHOT_SOUND_ID,Gunshot_Sound_Event);
		Registry.register(Registries.SOUND_EVENT, BOMB_PLOP_SOUND_ID, Bomb_Plop_Sound_Event);
		Registry.register(Registries.SOUND_EVENT, LASER_CHARGE_SOUND_ID, Laser_Charge_Sound_Event);
		Registry.register(Registries.SOUND_EVENT, ELECTRIC_SHOCK_SOUND_ID, Electric_Shock_Sound_Event);
		
	}
	
}
