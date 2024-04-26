package weapon.cats.main.client.Renderers;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import org.joml.Matrix3f;
import org.joml.Matrix4f;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import weapon.cats.main.WeaponizedCats;
import weapon.cats.main.Entities.ElectricNode;

@Environment(EnvType.CLIENT)
public class ElectricNodeRenderer extends EntityRenderer<ElectricNode>{

	protected ElectricNodeRenderer(Context ctx) {
		super(ctx);
	}

	@Override
	public Identifier getTexture(ElectricNode entity) {
		// TODO Auto-generated method stub
		return new Identifier(WeaponizedCats.MOD_ID,"textures/entity/bullet.png");
	}
	
	@Override
	public void render(ElectricNode entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		matrixStack.push();

        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getLineStrip());
        MatrixStack.Entry entry = matrixStack.peek();
        Matrix4f matrix4f = entry.getPositionMatrix();
        Matrix3f matrix3f = entry.getNormalMatrix();
        
        Queue<Entity> connection = new LinkedList<>();
        connection.add(entity);
        
        while(!connection.isEmpty()) {
        	
        	Entity start = connection.poll();
        	List<Entity> upcomings = entity.pairs.get(start);
        	
        	if(upcomings == null)continue;
        	//System.out.println("Entity " + start.getDisplayName().getString() + " is connected to " + upcomings.size() +" entities");
        	for(Entity next : upcomings) {
        		
        		Vec3d direction = next.getEyePos().subtract(start.getEyePos());
        		renderLine(matrix4f, matrix3f, vertexConsumer, start.getEyePos().subtract(entity.getEyePos()),direction, i);
        		connection.add(next);
        		
        	}
        }
        matrixStack.pop();
        super.render(entity, f, g, matrixStack, vertexConsumerProvider, i);
		
	}
	
	public void renderLine(Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer vertexConsumer, Vec3d origin, Vec3d direction, int i) {
		
		vertex(matrix4f, matrix3f, vertexConsumer, (float)origin.x, (float)origin.y, (float)origin.z, 0f, 0f, 0f, 0f, 0f, i);
		double jags = 5.0;
		Vec3d vector = direction.multiply(1/jags);
		Random rng = new Random();
		for(int I = 1; I < jags;I ++) {
			Vec3d rand = new Vec3d(rng.nextDouble()-0.5,rng.nextDouble()-0.5,rng.nextDouble()-0.5);
			vertex(matrix4f, matrix3f, vertexConsumer, (float)(origin.x+vector.x*I+rand.x), (float)(origin.y+vector.y*I+rand.y), (float)(origin.z+vector.z*I+rand.z), 0f, 0f, 0f, 1f, 0f, i);
			
		}
		vertex(matrix4f, matrix3f, vertexConsumer, (float)(origin.x+direction.x), (float)(origin.y+direction.y), (float)(origin.z+direction.z), 0f, 0f, 0f, 1f, 0f, i);
		
	}
	
	public void vertex(Matrix4f positionMatrix, Matrix3f normalMatrix, VertexConsumer vertexConsumer, float x, float y, float z, float u, float v, float normalX, float normalZ, float normalY, int light) {
        vertexConsumer.vertex(positionMatrix, x, y, z).color(255, 255, 255, 255).texture(u, v).overlay(OverlayTexture.DEFAULT_UV).light(255).normal(normalMatrix, normalX, normalY, normalZ).next();
    }
	
	public void vertex(Matrix4f positionMatrix, Matrix3f normalMatrix, VertexConsumer vertexConsumer, float x, float y, float z, float u, float v, int normalX, int normalZ, int normalY, int light) {
        vertexConsumer.vertex(positionMatrix, x, y, z).color(255, 255, 255, 255).texture(u, v).overlay(OverlayTexture.DEFAULT_UV).light(255).normal(normalMatrix, normalX, normalY, normalZ).next();
    }
	
}
