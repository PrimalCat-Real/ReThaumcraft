package hesio.thaumcraft.client.fx.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.Locale;
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
        this.quadSize *= 1f;
        this.gravity = 0.06F;
        this.lifetime = 60; // Вы можете изменить это на желаемую продолжительность жизни
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
        float f = (float)(Mth.lerp((double)partialTicks, this.xo, this.x) - vec3.x() + forward.x());
        float f1 = (float)(Mth.lerp((double)partialTicks, this.yo, this.y) - vec3.y() + forward.y());
        float f2 = (float)(Mth.lerp((double)partialTicks, this.zo, this.z) - vec3.z() + forward.z());

        // Particle size
        float f3 = this.getQuadSize(partialTicks);

        // Texture coordinates
        int uTexture = this.randomNumberX;
        int vTexture = this.randomNumberY;
        float f6 = uTexture / 16.0f;
        float f4 = vTexture / 16.0f;
        float f7 = f6 + (1.0f / 16.0f);
        float f5 = f4 + (1.0f / 16.0f);

        int j = this.getLightColor(partialTicks);

        vertexConsumer.vertex((double)(f - f3), (double)(f1 - f3), (double)f2).uv(f7, f5).color(1.0F, 0.5F, 0.8F, 0.5F).uv2(j).endVertex();
        vertexConsumer.vertex((double)(f - f3), (double)(f1 + f3), (double)f2).uv(f7, f4).color(1.0F, 0.5F, 0.8F, 0.5F).uv2(j).endVertex();
        vertexConsumer.vertex((double)(f + f3), (double)(f1 + f3), (double)f2).uv(f6, f4).color(1.0F, 0.5F, 0.8F, 0.5F).uv2(j).endVertex();
        vertexConsumer.vertex((double)(f + f3), (double)(f1 - f3), (double)f2).uv(f6, f5).color(1.0F, 0.5F, 0.8F, 0.5F).uv2(j).endVertex();
    }

    @Override
    public int getLightColor(float partialTick) {
        return 15728880; // Это максимальное значение для освещения
    }



}
