package weapon.cats.main.Items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import weapon.cats.main.WeaponizedCats;
import weapon.cats.main.Items.Attachments.BombAttachment;
import weapon.cats.main.Items.Attachments.GunAttachment;
import weapon.cats.main.Items.Attachments.LaserAttachment;
import weapon.cats.main.Items.Attachments.SlimeAttachment;

public class ItemManager {
	
	public static final LaserPointer LASER_POINTER = Registry.register(Registries.ITEM, new Identifier(WeaponizedCats.MOD_ID,"laser_pointer") ,new LaserPointer(new FabricItemSettings().maxCount(1)));
	public static final Attacher ATTACHER = Registry.register(Registries.ITEM, new Identifier(WeaponizedCats.MOD_ID,"attacher") ,new Attacher(new FabricItemSettings().maxCount(1)));
	public static final Detacher DETACHER = Registry.register(Registries.ITEM, new Identifier(WeaponizedCats.MOD_ID,"detacher") ,new Detacher(new FabricItemSettings().maxCount(1)));
	public static final GunAttachment GUN_ATTACHMENT = Registry.register(Registries.ITEM, new Identifier(WeaponizedCats.MOD_ID,"gun_attachment") ,new GunAttachment(new FabricItemSettings().maxCount(1)));
	public static final SlimeAttachment SLIME_ATTACHMENT = Registry.register(Registries.ITEM, new Identifier(WeaponizedCats.MOD_ID,"slime_attachment") ,new SlimeAttachment(new FabricItemSettings().maxCount(1)));
	public static final BombAttachment BOMB_ATTACHMENT = Registry.register(Registries.ITEM, new Identifier(WeaponizedCats.MOD_ID,"bomb_attachment"), new BombAttachment(new FabricItemSettings().maxCount(1)));
	public static final LaserAttachment LASER_ATTACHMENT = Registry.register(Registries.ITEM, new Identifier(WeaponizedCats.MOD_ID,"laser_attachment"), new LaserAttachment(new FabricItemSettings().maxCount(1)));
	
	public static void RegisterItems() {
		
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content->{
			
			content.add(LASER_POINTER);
			content.add(ATTACHER);
			content.add(DETACHER);
			content.add(GUN_ATTACHMENT);
			content.add(SLIME_ATTACHMENT);
			content.add(BOMB_ATTACHMENT);
			content.add(LASER_ATTACHMENT);
			
		});
		
	}
	
	public static void RegisterModelPredicates() {
		
		ModelPredicateProviderRegistry.register(LASER_POINTER, new Identifier("pointing"), (itemStack, clientWorld, livingEntity, num)->{
			
			if(livingEntity == null) {
				return 0.0f;
			}
			return livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F;
			
		});
		
	}
	
}
