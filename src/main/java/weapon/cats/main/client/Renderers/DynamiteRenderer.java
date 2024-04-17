package weapon.cats.main.client.Renderers;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import weapon.cats.main.WeaponizedCats;
import weapon.cats.main.Entities.Dynamite;

@Environment(value = EnvType.CLIENT)
public class DynamiteRenderer extends EntityRenderer<Dynamite>{
	
	private static BlockState bs = Blocks.TNT.getDefaultState();
	private BlockRenderManager brm;
	
	protected DynamiteRenderer(Context ctx) {
		super(ctx);
		brm = ctx.getBlockRenderManager();
	}
	
	@Override
	public Identifier getTexture(Dynamite entity) {
		// TODO Auto-generated method stub
		return new Identifier(WeaponizedCats.MOD_ID,"textures/entity/dynamite.png");
	}
	
	@Override
	public void render(Dynamite dynamite, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		
		matrixStack.push();
		matrixStack.scale(0.25f, 0.25f, 0.25f);
		World world = dynamite.getWorld();
		BlockPos blockPos = BlockPos.ofFloored(dynamite.getX(), dynamite.getBoundingBox().maxY, dynamite.getZ());
		matrixStack.translate(-0.5, 0.0, -0.5);
		brm.getModelRenderer().render(world, this.brm.getModel(bs),bs , blockPos, matrixStack, vertexConsumerProvider.getBuffer(RenderLayers.getMovingBlockLayer(bs)), false, Random.create(), bs.getRenderingSeed(dynamite.getBlockPos()), OverlayTexture.DEFAULT_UV);
		
		matrixStack.pop();
		
	}
	
}
