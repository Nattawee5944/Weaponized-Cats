package weapon.cats.main.client.Packets;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import weapon.cats.main.WeaponizedCats;
import weapon.cats.main.Entities.ElectricNode;

public class PacketManager {
	
	public static Identifier ElectricNodeOwnerPacketID = new Identifier(WeaponizedCats.MOD_ID,"electric_node_owner");
	
	public static void RegisterPacketHandlers() {
		
		ClientPlayNetworking.registerGlobalReceiver(ElectricNodeOwnerPacketID, (client, handler, buf, responseSender) -> {
			
			int enId = buf.readInt();
			int mobId = buf.readInt();
			
			client.execute(()->{
				
				ElectricNodeOwnerPacketAction(client,mobId,enId);
				
			});
			
		});
		
	}
	
	public static void ElectricNodeOwnerPacketAction(MinecraftClient client, int mobId, int enId) {
		
		Entity mob = client.world.getEntityById(mobId);
		Entity en = client.world.getEntityById(enId);
		if(en instanceof ElectricNode && mob instanceof LivingEntity) {
			
			((ElectricNode)en).setOwner((LivingEntity)mob);
			
		}
		
	}
	
}