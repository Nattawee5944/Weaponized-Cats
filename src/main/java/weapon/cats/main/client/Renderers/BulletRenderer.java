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
import weapon.cats.main.Entities.Bullet;

public class BulletRenderer extends EntityRenderer<Bullet>{

	protected BulletRenderer(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Identifier getTexture(Bullet entity) {
		// TODO Auto-generated method stub
		return new Identifier(WeaponizedCats.MOD_ID,"textures/entity/bullet.png");
	}
	
	@Override
	public void render(Bullet bullet, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		
        Vec3d velocity = bullet.getVelocity();
        if(velocity.lengthSquared() == 0) {
        	
        	super.render(bullet, f, g, matrixStack, vertexConsumerProvider, i);
        	return;
        }
		matrixStack.push();

        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutout(this.getTexture(bullet)));
        MatrixStack.Entry entry = matrixStack.peek();
        Matrix4f matrix4f = entry.getPositionMatrix();
        Matrix3f matrix3f = entry.getNormalMatrix();
        Vec3d p1;
        Vec3d p2;
        
        if(velocity.x == 0) {
        	
        	p1 = new Vec3d(0,-velocity.z,-velocity.y).normalize();
        	p2 = new Vec3d(1,0          ,0          );
        	
        }else if(velocity.y == 0) {
        	
        	p1 = new Vec3d(-velocity.z,0,-velocity.x).normalize();
        	p2 = new Vec3d(0          ,1,0          );
        	
        }else if(velocity.z == 0) {
        	
        	p1 = new Vec3d(-velocity.y,-velocity.x,0).normalize();
        	p2 = new Vec3d(0          ,0          ,1);
        	
        }else {
        	
        	p1 = new Vec3d(0         ,-velocity.z,-velocity.y).normalize();
        	p2 = new Vec3d(
        			velocity.y*velocity.y-velocity.z*velocity.z,
        			velocity.y*velocity.y-velocity.x*velocity.y,
        			velocity.x*velocity.z
        			).normalize();
        	
        }
        
        p1 = p1.multiply(0.03125);
        p2 = p2.multiply(0.03125);
        velocity = velocity.multiply(1d/4d);
        
    	this.vertex(matrix4f, matrix3f, vertexConsumer, (float) (0f+p1.x+p2.x), (float) (0f+p1.y+p2.y), (float) (0f+p1.z+p2.z), 0.0f, 0.0f, (float) p2.x, (float) p2.y, (float) p2.z, i);
    	this.vertex(matrix4f, matrix3f, vertexConsumer, (float) (0f-p1.x+p2.x), (float) (0f-p1.y+p2.y), (float) (0f-p1.z+p2.z), 0.0f, 0.0f, (float) p2.x, (float) p2.y, (float) p2.z, i);
    	this.vertex(matrix4f, matrix3f, vertexConsumer, (float) (velocity.x+(0f-p1.x+p2.x)), (float) (velocity.y+(0f-p1.y+p2.y)), (float) (velocity.z+(0f-p1.z+p2.z)), 0.0f, 0.0f, (float) p2.x, (float) p2.y, (float) p2.z, i);
    	this.vertex(matrix4f, matrix3f, vertexConsumer, (float) (velocity.x+(0f+p1.x+p2.x)), (float) (velocity.y+(0f+p1.y+p2.y)), (float) (velocity.z+(0f+p1.z+p2.z)), 0.0f, 0.0f, (float) p2.x, (float) p2.y, (float) p2.z, i);
        
    	this.vertex(matrix4f, matrix3f, vertexConsumer, (float) (0f+p1.x+p2.x), (float) (0f+p1.y+p2.y), (float) (0f+p1.z+p2.z), 0.0f, 0.0f, (float) p1.x, (float) p1.y, (float) p1.z, i);
    	this.vertex(matrix4f, matrix3f, vertexConsumer, (float) (0f+p1.x-p2.x), (float) (0f+p1.y-p2.y), (float) (0f+p1.z-p2.z), 0.0f, 0.0f, (float) p1.x, (float) p1.y, (float) p1.z, i);
    	this.vertex(matrix4f, matrix3f, vertexConsumer, (float) (velocity.x+(0f+p1.x-p2.x)), (float) (velocity.y+(0f+p1.y-p2.y)), (float) (velocity.z+(0f+p1.z-p2.z)), 0.0f, 0.0f, (float) p1.x, (float) p1.y, (float) p1.z, i);
    	this.vertex(matrix4f, matrix3f, vertexConsumer, (float) (velocity.x+(0f+p1.x+p2.x)), (float) (velocity.y+(0f+p1.y+p2.y)), (float) (velocity.z+(0f+p1.z+p2.z)), 0.0f, 0.0f, (float) p1.x, (float) p1.y, (float) p1.z, i);
        
    	this.vertex(matrix4f, matrix3f, vertexConsumer, (float) (0f-p1.x-p2.x), (float) (0f-p1.y-p2.y), (float) (0f-p1.z-p2.z), 0.0f, 0.0f, (float) -p2.x, (float) -p2.y, (float) -p2.z, i);
    	this.vertex(matrix4f, matrix3f, vertexConsumer, (float) (0f+p1.x-p2.x), (float) (0f+p1.y-p2.y), (float) (0f+p1.z-p2.z), 0.0f, 0.0f, (float) -p2.x, (float) -p2.y, (float) -p2.z, i);
    	this.vertex(matrix4f, matrix3f, vertexConsumer, (float) (velocity.x+(0f+p1.x-p2.x)), (float) (velocity.y+(0f+p1.y-p2.y)), (float) (velocity.z+(0f+p1.z-p2.z)), 0.0f, 0.0f, (float) -p2.x, (float) -p2.y, (float) -p2.z, i);
    	this.vertex(matrix4f, matrix3f, vertexConsumer, (float) (velocity.x+(0f-p1.x-p2.x)), (float) (velocity.y+(0f-p1.y-p2.y)), (float) (velocity.z+(0f-p1.z-p2.z)), 0.0f, 0.0f, (float) -p2.x, (float) -p2.y, (float) -p2.z, i);
    	
    	this.vertex(matrix4f, matrix3f, vertexConsumer, (float) (0f-p1.x+p2.x), (float) (0f-p1.y+p2.y), (float) (0f-p1.z+p2.z), 0.0f, 0.0f, (float) -p1.x, (float) -p1.y, (float) -p1.z, i);
    	this.vertex(matrix4f, matrix3f, vertexConsumer, (float) (0f-p1.x-p2.x), (float) (0f-p1.y-p2.y), (float) (0f-p1.z-p2.z), 0.0f, 0.0f, (float) -p1.x, (float) -p1.y, (float) -p1.z, i);
    	this.vertex(matrix4f, matrix3f, vertexConsumer, (float) (velocity.x+(0f-p1.x-p2.x)), (float) (velocity.y+(0f-p1.y-p2.y)), (float) (velocity.z+(0f-p1.z-p2.z)), 0.0f, 0.0f, (float) -p1.x, (float) -p1.y, (float) -p1.z, i);
    	this.vertex(matrix4f, matrix3f, vertexConsumer, (float) (velocity.x+(0f-p1.x+p2.x)), (float) (velocity.y+(0f-p1.y+p2.y)), (float) (velocity.z+(0f-p1.z+p2.z)), 0.0f, 0.0f, (float) -p1.x, (float) -p1.y, (float) -p1.z, i);
    	
        matrixStack.pop();
        super.render(bullet, f, g, matrixStack, vertexConsumerProvider, i);
		
	}
	
	public void vertex(Matrix4f positionMatrix, Matrix3f normalMatrix, VertexConsumer vertexConsumer, float x, float y, float z, float u, float v, float x2, float y2, float z2, int light) {
        vertexConsumer.vertex(positionMatrix, x, y, z).color(255, 255, 255, 255).texture(u, v).overlay(OverlayTexture.DEFAULT_UV).light(255).normal(normalMatrix, x2, z2, y2).next();
    }
	
}
