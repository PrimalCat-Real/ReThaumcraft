package hesio.thaumcraft.client.fx.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.Locale;
import java.util.Random;

public class BlockRunesParticle extends TextureSheetParticle {
    private Vector3f forward;
    private int randomNumberX;
    private int randomNumberY;
    protected BlockRunesParticle(ClientLevel world, double x, double y, double z, float scale, SpriteSet sprite) {
        super(world, x, y, z);
        this.xd = 0;
        this.yd = 0;
        this.zd = 0;
        this.quadSize *= scale;
        this.gravity = 0.02F;
        this.lifetime = 80; // Вы можете изменить это на желаемую продолжительность жизни
        this.setSpriteFromAge(sprite);

//        this.forward = new Vector3f(0, 0, 1);

        Random random = new Random();
        this.randomNumberX = random.nextInt(8);
        this.randomNumberY = random.nextInt(2);
    }
    protected BlockRunesParticle(ClientLevel world, double x, double y, double z, float scale, SpriteSet sprite, Vector3f forward) {
        super(world, x, y, z);
        this.xd = 0;
        this.yd = 0;
        this.zd = 0;
        this.quadSize *= scale;
        this.gravity = 0.02F;
        this.lifetime = 80; // Вы можете изменить это на желаемую продолжительность жизни
        this.setSpriteFromAge(sprite);

//        this.forward = new Vector3f(0, 0, 1);
        this.forward = forward;
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
    public void render(VertexConsumer vertexConsumer, Camera camera, float partialTicks) {
        // Get the current camera position
        Vec3 vec3 = camera.getPosition();

        // Calculate particle's current position
        float f = (float)(Mth.lerp((double)partialTicks, this.xo, this.x) - vec3.x());
        float f1 = (float)(Mth.lerp((double)partialTicks, this.yo, this.y) - vec3.y());
        float f2 = (float)(Mth.lerp((double)partialTicks, this.zo, this.z) - vec3.z());

        // Calculate direction from particle to camera
        Vector3f particleToCamera = new Vector3f(f, f1, f2);

        // Assuming that the particle's forward vector is stored in a Vector3f named particleForward

        if(this.getForward() != null){
            Vector3f particleForward = this.getForward();
            if (particleToCamera.dot(particleForward) <= 0) {
                // If particle is not facing the camera, do not render it.
                return;
            }
        }
        // Dot product to check if particle is facing the camera


        Quaternionf quaternionf;
        if (this.roll == 0.0F) {
            quaternionf = camera.rotation();
        } else {
            quaternionf = new Quaternionf(camera.rotation());
            quaternionf.rotateZ(Mth.lerp(partialTicks, this.oRoll, this.roll));
        }

        Vector3f[] avector3f = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};

        float f3 = this.getQuadSize(partialTicks);

        for(int i = 0; i < 4; ++i) {
            Vector3f vector3f = avector3f[i];
            vector3f.rotate(quaternionf);
            vector3f.mul(f3);
            vector3f.add(f, f1, f2);
        }

        // Устанавливаем координаты текстуры
        int uTexture = this.randomNumberX; // Вторая текстура (начинается с 0)
        int vTexture = this.randomNumberY; // Первый ряд текстур

        // Вычисляем начальные координаты UV с учетом отступа
        float f6 = uTexture / 16.0f; // Начало U с учетом отступа
        float f4 = vTexture / 16.0f; // Начало V

        // Вычисляем конечные координаты UV с учетом отступа
        float f7 = f6 + (1.0f / 16.0f); // Конец U с учетом отступа
        float f5 = f4 + (1.0f / 16.0f); // Конец V

        // Добавляем отступ 32 пикселя к U координатам
//        float pixelSize = 1.0f / 128.0f; // Размер одного пикселя
//        float offset = 1 * pixelSize; // Размер отступа
//        f6 += offset;
//        f7 += offset;
        int j = this.getLightColor(partialTicks);

        vertexConsumer.vertex((double)avector3f[0].x(), (double)avector3f[0].y(), (double)avector3f[0].z()).uv(f7, f5).color(1.0F, 0.5F, 0.8F, 0.5F).uv2(j).endVertex();
        vertexConsumer.vertex((double)avector3f[1].x(), (double)avector3f[1].y(), (double)avector3f[1].z()).uv(f7, f4).color(1.0F, 0.5F, 0.8F, 0.5F).uv2(j).endVertex();
        vertexConsumer.vertex((double)avector3f[2].x(), (double)avector3f[2].y(), (double)avector3f[2].z()).uv(f6, f4).color(1.0F, 0.5F, 0.8F, 0.5F).uv2(j).endVertex();
        vertexConsumer.vertex((double)avector3f[3].x(), (double)avector3f[3].y(), (double)avector3f[3].z()).uv(f6, f5).color(1.0F, 0.5F, 0.8F, 0.5F).uv2(j).endVertex();
    }

    @Override
    public int getLightColor(float partialTick) {
        return 15728880; // Это максимальное значение для освещения
    }


    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Factory(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new BlockRunesParticle(world, x, y, z, 1.0F, this.spriteSet);
        }
    }

//    public static class Options implements ParticleOptions {
//        private final ParticleType<Options> type;
//        private final Vector3f direction;
//
//        public Options(ParticleType<Options> type, Vector3f direction) {
//            this.type = type;
//            this.direction = direction;
//        }
//
//        public Vector3f getDirection() {
//            return this.direction;
//        }
//
//        @Override
//        public ParticleType<?> getType() {
//            return this.type;
//        }
//
//        @Override
//        public void writeToNetwork(FriendlyByteBuf buf) {
//            buf.writeFloat(direction.x());
//            buf.writeFloat(direction.y());
//            buf.writeFloat(direction.z());
//        }
//    }

}
