package weapon.cats.main.client.Renderers;

import org.joml.Matrix3f;
import org.joml.Matrix4f;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import weapon.cats.main.WeaponizedCats;
import weapon.cats.main.Entities.LaserExplosion;

public class LaserExplosionRenderer extends EntityRenderer<LaserExplosion>{
	
	protected LaserExplosionRenderer(Context ctx) {
		super(ctx);
	}

	@Override
	public Identifier getTexture(LaserExplosion entity) {
		return new Identifier(WeaponizedCats.MOD_ID,"textures/entity/laser_explosion.png");
	}
	
	@Override
	public void render(LaserExplosion entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		
		matrixStack.push();
		
		float scale = (float) (2*Math.sqrt(2*entity.explosionTick));
		matrixStack.scale(scale,scale,scale);
		
		int alpha = (int) (255.0f*(1-entity.explosionTick/14.0f));
		
		VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent(this.getTexture(entity)));
        MatrixStack.Entry entry = matrixStack.peek();
        Matrix4f matrix4f = entry.getPositionMatrix();
        Matrix3f matrix3f = entry.getNormalMatrix();

        float rings = 6;
        Vec3d vector = new Vec3d(0,1,0).rotateX(0.01f);
        Vec3d vector2 = rotateAroundX((float) (Math.PI/rings),vector).normalize();
        
        for(float I = 0;I < rings;I++) {
        	
        	for(float J = 0;J < 2*rings;J++) {

            	Vec3d tmpVector = vector.rotateY((float) (Math.PI/rings)*J).normalize();
            	Vec3d tmpVector2 = vector2.rotateY((float) (Math.PI/rings)*J).normalize();
            	
            	vertex(matrix4f, matrix3f, vertexConsumer, (tmpVector), I*0.5f, J*0.5f, i,(int)alpha);
            	vertex(matrix4f, matrix3f, vertexConsumer, (tmpVector2), I*0.5f, (J+1)*0.5f, i,(int)alpha);
            	
            	tmpVector = vector.rotateY((float) (Math.PI/rings)*(J+1)).normalize();
            	tmpVector2 = vector2.rotateY((float) (Math.PI/rings)*(J+1)).normalize();
            	
            	vertex(matrix4f, matrix3f, vertexConsumer, (tmpVector2), (I+1)*0.5f, (J+1)*0.5f, i,(int)alpha);
            	vertex(matrix4f, matrix3f, vertexConsumer, (tmpVector), (I+1)*0.5f, J*0.5f, i,(int)alpha);
            	
            }
        	vector = rotateAroundX((float) (Math.PI/rings),vector).normalize();
        	vector2 = rotateAroundX((float) (Math.PI/rings),vector2).normalize();
        }
		matrixStack.pop();
		super.render(entity, f, g, matrixStack, vertexConsumerProvider, i);
	}

	public void vertex(Matrix4f positionMatrix, Matrix3f normalMatrix, VertexConsumer vertexConsumer, Vec3d vector, Vec3d normal, float u, float v, int light, int alpha) {
		
		vertex(positionMatrix,normalMatrix,vertexConsumer,(float)vector.x,(float)vector.y,(float)vector.z,u,v,(float)normal.x,(float)normal.y,(float)normal.z,light, alpha);
		
	}
	
	public void vertex(Matrix4f positionMatrix, Matrix3f normalMatrix, VertexConsumer vertexConsumer, Vec3d vector, float u, float v, int light, int alpha) {
		
		vertex(positionMatrix,normalMatrix,vertexConsumer,(float)vector.x,(float)vector.y,(float)vector.z,u,v,(float)vector.x,(float)vector.y,(float)vector.z,255, alpha);
		
	}
	
	public void vertex(Matrix4f positionMatrix, Matrix3f normalMatrix, VertexConsumer vertexConsumer, float x, float y, float z, float u, float v, float x2, float y2, float z2, int light, int alpha) {
        vertexConsumer.vertex(positionMatrix, x, y, z).color(255, 255, 255, alpha).texture(u, v).overlay(OverlayTexture.DEFAULT_UV).light(255).normal(normalMatrix, x2, z2, y2).next();
    }
	
	public Vec3d rotateAroundAxis(double angle, Vec3d vec, Vec3d axis) {
		
		axis = axis.normalize();
		
		double c = Math.cos(angle);
		double s = Math.sin(angle);
		
		double x =(vec.x)*(c+axis.x*axis.x*(1-c))
				+ (vec.y)*(axis.x*axis.y*(1-c) - axis.z*s)
				+ (vec.z)*(axis.x*axis.z*(1-c) + axis.y*s);
		
		double y =(vec.x)*(axis.x*axis.y*(1-c) + axis.z*s)
				+ (vec.y)*(c+axis.y*axis.y*(1-c))
				+ (vec.z)*(axis.y*axis.z*(1-c) - axis.x*s);
		
		double z =(vec.x)*(axis.x*axis.z*(1-c) - axis.y*s)
				+ (vec.y)*(axis.y*axis.z*(1-c) + axis.x*s)
				+ (vec.z)*(c-axis.z*axis.z*(1-c));
		
		return new Vec3d(x,y,z);
		
	}
	
	public Vec3d rotateAroundX(double angle, Vec3d vec) {
		
		return new Vec3d(vec.x, (vec.y)*Math.cos(angle) - (vec.z)*Math.sin(angle), (vec.y)*Math.sin(angle) + (vec.z)*Math.cos(angle)).normalize();
		
	}
	
}
