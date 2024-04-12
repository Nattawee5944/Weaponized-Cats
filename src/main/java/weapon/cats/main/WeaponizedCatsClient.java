package weapon.cats.main;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import weapon.cats.main.Items.ItemManager;
import weapon.cats.main.client.Renderers.RendererManager;

@Environment(EnvType.CLIENT)
public class WeaponizedCatsClient implements ClientModInitializer{

	@Override
	public void onInitializeClient() {

		ItemManager.RegisterModelPredicates();
		RendererManager.InitializeRenderers();
		
	}
	
}
