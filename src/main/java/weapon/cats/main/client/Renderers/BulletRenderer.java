package weapon.cats.main.client.Renderers;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import weapon.cats.main.WeaponizedCats;
import weapon.cats.main.Entities.Bullet;
import weapon.cats.main.Entities.LaserCursor;

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
	public void render(LaserCursor laserCursor, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
		matrixStack.push();

        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutout(this.getTexture(laserCursor)));
        MatrixStack.Entry entry = matrixStack.peek();
        Matrix4f matrix4f = entry.getPositionMatrix();
        Matrix3f matrix3f = entry.getNormalMatrix();
        
        Vec3d tmp = laserCursor.getFacingVector();
        Vec3i directions = new Vec3i((int)Math.round(tmp.x),(int)Math.round(tmp.y),(int)Math.round(tmp.z));
        
        if(directions.getY() == -1) {
        	
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180.0f));
        	
        }else if(directions.getY() != 1) {
        	
        	Vec3i axis = (new Vec3i(0,1,0)).crossProduct(directions);
        	matrixStack.multiply(RotationAxis.of(new Vector3f(axis.getX(),axis.getY(),axis.getZ())).rotationDegrees(-90.0f));
        	
        }
        
        
    	this.vertex(matrix4f, matrix3f, vertexConsumer, -0.0625f, 0.001f,-0.0625f, 0.0f, 0.0f, 0, 1, 0, i);
        this.vertex(matrix4f, matrix3f, vertexConsumer, -0.0625f, 0.001f, 0.0625f, 0.0f, 0.0f, 0, 1, 0, i);
        this.vertex(matrix4f, matrix3f, vertexConsumer,  0.0625f, 0.001f, 0.0625f, 0.0f, 0.0f, 0, 1, 0, i);
        this.vertex(matrix4f, matrix3f, vertexConsumer,  0.0625f, 0.001f,-0.0625f, 0.0f, 0.0f, 0, 1, 0, i);
        this.vertex(matrix4f, matrix3f, vertexConsumer,  0.0625f,-0.001f,-0.0625f, 0.0f, 0.0f, 0, -1, 0, i);
        this.vertex(matrix4f, matrix3f, vertexConsumer,  0.0625f,-0.001f, 0.0625f, 0.0f, 0.0f, 0, -1, 0, i);
        this.vertex(matrix4f, matrix3f, vertexConsumer, -0.0625f,-0.001f, 0.0625f, 0.0f, 0.0f, 0, -1, 0, i);
        this.vertex(matrix4f, matrix3f, vertexConsumer, -0.0625f,-0.001f,-0.0625f, 0.0f, 0.0f, 0, -1, 0, i);
        
        
        matrixStack.pop();
        super.render(laserCursor, f, g, matrixStack, vertexConsumerProvider, i);
		
	}
	
	public void vertex(Matrix4f positionMatrix, Matrix3f normalMatrix, VertexConsumer vertexConsumer, float x, float y, float z, float u, float v, int normalX, int normalZ, int normalY, int light) {
        vertexConsumer.vertex(positionMatrix, x, y, z).color(255, 255, 255, 255).texture(u, v).overlay(OverlayTexture.DEFAULT_UV).light(255).normal(normalMatrix, normalX, normalY, normalZ).next();
    }
	
}
