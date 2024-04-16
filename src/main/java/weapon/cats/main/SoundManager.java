package weapon.cats.main;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class SoundManager {
	public static final Identifier GUNSHOT_SOUND_ID = new Identifier(WeaponizedCats.MOD_ID,"gunshot");
	public static SoundEvent Gunshot_Sound_Event = SoundEvent.of(GUNSHOT_SOUND_ID);
	
	public static void InitializeSounds() {
		
		Registry.register(Registries.SOUND_EVENT, GUNSHOT_SOUND_ID,Gunshot_Sound_Event);
		
	}
	
}
