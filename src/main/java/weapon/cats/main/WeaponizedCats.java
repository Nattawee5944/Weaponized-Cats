package weapon.cats.main;

import net.fabricmc.api.ModInitializer;
import weapon.cats.main.Entities.StatusEffects.EffectManager;
import weapon.cats.main.Items.ItemManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeaponizedCats implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("weaponized-cat");
    public static final String MOD_ID = "weaponized-cat";
    
	@Override
	public void onInitialize() {
		
		ItemManager.RegisterItems();
		SoundManager.InitializeSounds();
		EffectManager.InitEffects();
	}
	
}