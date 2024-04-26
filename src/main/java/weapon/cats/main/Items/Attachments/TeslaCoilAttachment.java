package weapon.cats.main.Items.Attachments;

import java.util.Collection;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import weapon.cats.main.Entities.ElectricNode;
import weapon.cats.main.Entities.EntityManager;
import weapon.cats.main.client.Packets.PacketManager;

public class TeslaCoilAttachment extends Attachment{

	public TeslaCoilAttachment(Settings settings) {
		super(settings);
		// TODO Auto-generated constructor stub
	}
	
	public static ElectricNode Shock(LivingEntity mob) {
		
		if(!mob.getEntityWorld().isClient()) {
			
			ElectricNode en = new ElectricNode(EntityManager.ELECTRIC_NODE,mob.getEntityWorld());
			en.setOwner(mob);
			en.setPosition(mob.getEyePos());
			
			mob.getEntityWorld().spawnEntity(en);
			EntitySpawnS2CPacket packet = new EntitySpawnS2CPacket(en);
			mob.getEntityWorld().getServer().getPlayerManager().sendToAll(packet);
			
			generateAndSendPacket(mob,en);
			
			return en;
			
		}
		return null;
		
	}
	
	public static void generateAndSendPacket(LivingEntity mob, ElectricNode en) {
		
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeInt(en.getId());
		buf.writeInt(mob.getId());
		
		Collection<ServerPlayerEntity> players = PlayerLookup.world((ServerWorld)mob.getWorld());
		
		for(ServerPlayerEntity player : players) {
			
			ServerPlayNetworking.send(player, PacketManager.ElectricNodeOwnerPacketID, buf);
			
		}
	}
	
}