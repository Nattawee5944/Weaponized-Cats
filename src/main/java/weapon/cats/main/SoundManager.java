package weapon.cats.main;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class SoundManager {
	public static final Identifier GUNSHOT_SOUND_ID = new Identifier(WeaponizedCats.MOD_ID,"gunshot");
	public static SoundEvent Gunshot_Sound_Event = SoundEvent.of(GUNSHOT_SOUND_ID);
	
	public static final Identifier BOMB_PLOP_SOUND_ID = new Identifier(WeaponizedCats.MOD_ID,"bomb_plop");
	public static SoundEvent Bomb_Plop_Sound_Event = SoundEvent.of(GUNSHOT_SOUND_ID);
	
	public static void InitializeSounds() {
		
		Registry.register(Registries.SOUND_EVENT, GUNSHOT_SOUND_ID,Gunshot_Sound_Event);
		Registry.register(Registries.SOUND_EVENT, BOMB_PLOP_SOUND_ID, Bomb_Plop_Sound_Event);
		
	}
	
}
