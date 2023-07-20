package hesio.thaumcraft.client.fx.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import hesio.thaumcraft.Thaumcraft;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class TestParticle extends TextureSheetParticle {

//    ModelManager modelManager = Minecraft.getInstance().getModelManager();
//    BakedModel dayModel = modelManager.getModel(new ResourceLocation("<yourmodid>:item/day_texture"));
//    BakedModel nightModel = modelManager.getModel(new ResourceLocation(Thaumcraft.MODID, "particle/citring.png"));

    protected TestParticle(ClientLevel world, double x, double y, double z, float scale, SpriteSet sprite) {
        super(world, x, y, z);

        this.xd = 0;
        this.yd = 0;
        this.zd = 0;
        this.quadSize *= scale;
        this.gravity = 0.01F;
        this.lifetime = 80; // Вы можете изменить это на желаемую продолжительность жизни
        this.setSpriteFromAge(sprite);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float partialTicks) {
        Vec3 vec3 = camera.getPosition();
        float f = (float)(Mth.lerp((double)partialTicks, this.xo, this.x) - vec3.x());
        float f1 = (float)(Mth.lerp((double)partialTicks, this.yo, this.y) - vec3.y());
        float f2 = (float)(Mth.lerp((double)partialTicks, this.zo, this.z) - vec3.z());
        Quaternionf quaternionf;
        if (this.roll == 0.0F) {
            quaternionf = camera.rotation();
        } else {
            quaternionf = new Quaternionf(camera.rotation());
            quaternionf.rotateZ(Mth.lerp(partialTicks, this.oRoll, this.roll));
        }

        float f3 = this.getQuadSize(partialTicks);
        Vector3f[] avector3f = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};

        float progress = (float) ((Math.sin(System.currentTimeMillis() / 1000.0) + 1) / 2); // Прогресс от 0 до 1
        float red = 1.0f - progress; // Red color component decreases from 1 to 0
        float green = 1.0f; // Green color component remains constant

        for(int i = 0; i < 4; ++i) {
            Vector3f vector3f = avector3f[i];
            vector3f.rotate(quaternionf);
            vector3f.mul(f3);
            vector3f.add(f, f1, f2);
        }

        float f6 = this.getU0();
        float f7 = this.getU1();
        float f4 = this.getV0();
        float f5 = this.getV1();
        int j = this.getLightColor(partialTicks);
        vertexConsumer.vertex((double)avector3f[0].x(), (double)avector3f[0].y(), (double)avector3f[0].z()).uv(f7, f5).color(red, green, this.bCol, this.alpha).uv2(j).endVertex();
        vertexConsumer.vertex((double)avector3f[1].x(), (double)avector3f[1].y(), (double)avector3f[1].z()).uv(f7, f4).color(red, green, this.bCol, this.alpha).uv2(j).endVertex();
        vertexConsumer.vertex((double)avector3f[2].x(), (double)avector3f[2].y(), (double)avector3f[2].z()).uv(f6, f4).color(red, green, this.bCol, this.alpha).uv2(j).endVertex();
        vertexConsumer.vertex((double)avector3f[3].x(), (double)avector3f[3].y(), (double)avector3f[3].z()).uv(f6, f5).color(red, green, this.bCol, this.alpha).uv2(j).endVertex();
    }

    // Всегда возвращает максимальное значение освещения
    @Override
    public int getLightColor(float partialTick) {
        return 15728880; // Это максимальное значение для освещения
    }


    @Override
    public void tick() {
        super.tick();
        // Изменение цвета в радужном порядке

        setAlpha(0.5F);
        System.out.println("Current sprite: " + this.sprite.atlasLocation());

        // Здесь вы можете добавить любой код, который должен выполняться при каждом обновлении частицы
    }

    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Factory(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new TestParticle(world, x, y, z, 1.0F, this.spriteSet);
        }
    }
}
