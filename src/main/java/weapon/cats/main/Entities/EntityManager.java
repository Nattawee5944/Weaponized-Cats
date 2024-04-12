package weapon.cats.main.Entities;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import weapon.cats.main.WeaponizedCats;

public class EntityManager {
	
	public static EntityType<LaserCursor> LASER_CURSOR = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(WeaponizedCats.MOD_ID,"laser_cursor"),
			FabricEntityTypeBuilder.create(SpawnGroup.MISC, LaserCursor::new).dimensions(EntityDimensions.fixed(0.125f, 0.125f)).build()
		);
	
}
