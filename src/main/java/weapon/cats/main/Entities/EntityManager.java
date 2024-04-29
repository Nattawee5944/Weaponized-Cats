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
	
	public static EntityType<LaserCursor> LASER_CURSOR;
	
	public static EntityType<Bullet> BULLET;
	public static EntityType<ThrownSlimeBall> THROWN_SLIMEBALL;
	
	public static EntityType<Dynamite> DYNAMITE;
	
	public static EntityType<LaserExplosion> LASER_EXPLOSION;
	
	public static EntityType<ElectricNode> ELECTRIC_NODE;
	
	public static void InitEntities() {
		
		LASER_CURSOR = Registry.register(
				Registries.ENTITY_TYPE,
				new Identifier(WeaponizedCats.MOD_ID,"laser_cursor"),
				FabricEntityTypeBuilder.create(SpawnGroup.MISC, LaserCursor::new).dimensions(EntityDimensions.fixed(0.125f, 0.125f)).build()
			);
		
		BULLET = Registry.register(
				Registries.ENTITY_TYPE, 
				new Identifier(WeaponizedCats.MOD_ID,"bullet"),
				FabricEntityTypeBuilder.create(SpawnGroup.MISC, Bullet::new).dimensions(EntityDimensions.fixed(0.0625f, 0.0625f)).build()
			);
		
		THROWN_SLIMEBALL = Registry.register(
				Registries.ENTITY_TYPE,
				new Identifier(WeaponizedCats.MOD_ID,"thrown_slimeball"),
				FabricEntityTypeBuilder.create(SpawnGroup.MISC, ThrownSlimeBall::new).dimensions(EntityDimensions.fixed(0.125f, 0.125f)).build()
			);
		
		DYNAMITE = Registry.register(
				Registries.ENTITY_TYPE,
				new Identifier(WeaponizedCats.MOD_ID,"dynamite"),
				FabricEntityTypeBuilder.create(SpawnGroup.MISC, Dynamite::new).dimensions(EntityDimensions.fixed(0.25f,0.25f)).build()
			);
		
		LASER_EXPLOSION = Registry.register(
				Registries.ENTITY_TYPE,
				new Identifier(WeaponizedCats.MOD_ID,"laser_explosion"),
				FabricEntityTypeBuilder.create(SpawnGroup.MISC, LaserExplosion::new).dimensions(EntityDimensions.changing(10f, 10f)).build()
			);
		
		ELECTRIC_NODE = Registry.register(
				Registries.ENTITY_TYPE,
				new Identifier(WeaponizedCats.MOD_ID,"electric_node"),
				FabricEntityTypeBuilder.create(SpawnGroup.MISC, ElectricNode::new).dimensions(EntityDimensions.changing(1f, 1f)).build()
			);
		
	}
}
