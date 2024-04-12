package weapon.cats.main.Items.Attachments;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import weapon.cats.main.Items.ItemManager;

public class Attachment extends Item{
	
	public static final String NBT_KEY = "ATTACHMENT";
	
	public Attachment(Settings settings) {
		super(settings);
	}
	
	public static ItemStack getAttachment(ItemStack attacherStack) {
		
		NbtList nbtList;
        NbtCompound nbtCompound = attacherStack.getNbt();
        if (nbtCompound != null && nbtCompound.contains(NBT_KEY, NbtElement.LIST_TYPE) && (nbtList = nbtCompound.getList(NBT_KEY, NbtElement.COMPOUND_TYPE)) != null) {
            if(!nbtList.isEmpty()) {
            	NbtCompound nbtCompound2 = nbtList.getCompound(0);
        		return ItemStack.fromNbt(nbtCompound2);
            }
        }
		return ItemStack.EMPTY.copy();
	}
	
	public static void addAttachment(ItemStack attacherStack, ItemStack attachmentStack) {
		
		if(attacherStack.isOf(ItemManager.ATTACHER)) {
			
			NbtCompound nbt = attacherStack.getOrCreateNbt();
			NbtList nbtList = nbt.contains(NBT_KEY, NbtElement.LIST_TYPE) ? nbt.getList(NBT_KEY, NbtElement.COMPOUND_TYPE): new NbtList();
			NbtCompound nbtCompound = new NbtCompound();
			attachmentStack.writeNbt(nbtCompound);
			nbtList.add(nbtCompound);
			nbt.put(NBT_KEY, nbtList);
			
		}
		
	}
	
	public static void clearAttachment(ItemStack attacherStack) {
		
		if(attacherStack.isOf(ItemManager.ATTACHER)) {
			
			NbtCompound nbt = attacherStack.getOrCreateNbt();
			NbtList nbtList = nbt.contains(NBT_KEY, NbtElement.LIST_TYPE) ? nbt.getList(NBT_KEY, NbtElement.COMPOUND_TYPE): new NbtList();
			nbtList.clear();
			nbt.put(NBT_KEY, nbtList);
			
		}
		
	}
	
}
