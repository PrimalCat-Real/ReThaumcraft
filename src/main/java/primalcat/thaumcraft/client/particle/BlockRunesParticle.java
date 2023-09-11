package primalcat.thaumcraft.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class BlockRunesParticle extends TextureSheetParticle {
    private Vector3f forward;
    private int randomNumberX;
    private int randomNumberY;
    protected BlockRunesParticle(ClientLevel world, double x, double y, double z, float scale, SpriteSet sprite, Vector3f direction) {
        super(world, x, y, z);
        this.xd = 0;
        this.yd = 0;
        this.zd = 0;
        this.quadSize *= 0.8f;
        this.gravity = 0.06F;
        this.lifetime = 68; // Вы можете изменить это на желаемую продолжительность жизни
        this.setSpriteFromAge(sprite);
        this.forward = direction;

//        this.forward = new Vector3f(0, 0, 1);

        Random random = new Random();
        this.randomNumberX = random.nextInt(8);
        this.randomNumberY = random.nextInt(2);

    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public Vector3f getForward() {
        return this.forward;
    }

    public void setForward(Vector3f forward) {
        this.forward = forward;
    }

    @Override
    public void tick(){
//        System.out.println("test");
        super.tick();
        // Изменение цвета в радужном порядке

        // Движение частицы, не взирая на блоки
        this.x += this.xd;
        this.y += this.yd;
        this.z += this.zd;

        // При необходимости уменьшите скорость
        this.xd *= 0.9100000262260437D;
        this.yd *= 0.9100000262260437D;
        this.zd *= 0.9100000262260437D;
        this.fadeOut(0.5F);
//        this.setAlpha(0.5F);
    }


    private void fadeOut(float alpha) {
        this.alpha = (-alpha/(float)lifetime) * age + alpha;
    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float partialTicks) {
        // Get the current camera position
        Vec3 vec3 = camera.getPosition();

        // Get the forward vector
        Vector3f forward = this.getForward();

        // Calculate particle's current position with the forward offset
        float px = (float)(Mth.lerp((double)partialTicks, this.xo, this.x) - vec3.x() + forward.x());
        float py = (float)(Mth.lerp((double)partialTicks, this.yo, this.y) - vec3.y() + forward.y());
        float pz = (float)(Mth.lerp((double)partialTicks, this.zo, this.z) - vec3.z() + forward.z());

        // Particle size
        float size = this.getQuadSize(partialTicks);

        // Rotation around Y axis
        float rotation;
        if (forward.z() > 0) {
            rotation = (float)Math.PI; // South
        } else if (forward.z() < 0) {
            rotation = 0; // North
        } else if (forward.x() > 0) {
            rotation = (float)Math.PI / 2F; // East
        } else if (forward.x() < 0) {
            rotation = (float)Math.PI * 1.5F; // West
        } else {
            rotation = 0; // Default to North if something goes wrong
        }
        float sinRot = Mth.sin(rotation);
        float cosRot = Mth.cos(rotation);

        // Texture coordinates
        int uTexture = this.randomNumberX;
        int vTexture = this.randomNumberY;
        float u0 = uTexture / 16.0f;
        float v0 = vTexture / 16.0f;
        float u1 = u0 + (1.0f / 16.0f);
        float v1 = v0 + (1.0f / 16.0f);

        int j = this.getLightColor(partialTicks);

        // Rotated vertices
        vertexConsumer.vertex((double)(px - size * cosRot), (double)(py - size), (double)(pz + size * sinRot)).uv(u1, v1).color(1.0F, 0.5F, 0.8F, this.alpha).uv2(j).endVertex();
        vertexConsumer.vertex((double)(px - size * cosRot), (double)(py + size), (double)(pz + size * sinRot)).uv(u1, v0).color(1.0F, 0.5F, 0.8F, this.alpha).uv2(j).endVertex();
        vertexConsumer.vertex((double)(px + size * cosRot), (double)(py + size), (double)(pz - size * sinRot)).uv(u0, v0).color(1.0F, 0.5F, 0.8F, this.alpha).uv2(j).endVertex();
        vertexConsumer.vertex((double)(px + size * cosRot), (double)(py - size), (double)(pz - size * sinRot)).uv(u0, v1).color(1.0F, 0.5F, 0.8F, this.alpha).uv2(j).endVertex();
    }

    @Override
    public int getLightColor(float partialTick) {
        return 15728880; // Это максимальное значение для освещения
    }
}
