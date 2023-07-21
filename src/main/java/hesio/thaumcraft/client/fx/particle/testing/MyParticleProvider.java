package hesio.thaumcraft.client.fx.particle.testing;

import hesio.thaumcraft.client.fx.particle.BlockRunesData;
import hesio.thaumcraft.client.fx.particle.BlockRunesParticle;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class MyParticleProvider implements ParticleProvider<MyParticleType> {
    private final SpriteSet spriteSet;

    public MyParticleProvider(SpriteSet spriteSet) {
        System.out.println("Inside MyParticleProvider constructor");
        this.spriteSet = spriteSet;
    }

//    @Nullable
//    @Override
//    public Particle createParticle(MyParticleType data, ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
//        try {
//            System.out.println("Inside createParticle");
//            MyParticle particle = new MyParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
//            // Обратите внимание, что мы не устанавливаем параметр myParameter, так как у SimpleParticleType нет дополнительных параметров
//            return particle;
//        } catch (Exception e) {
//            System.out.println("oshibka blyat");
//            e.printStackTrace();
//            return null;
//        }
//    }
    @Nullable
    @Override
    public Particle createParticle(MyParticleType data, ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        try {
            System.out.println("Inside createParticle");
            MyParticle particle = new MyParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
            // Обратите внимание, что мы не устанавливаем параметр myParameter, так как у SimpleParticleType нет дополнительных параметров

            int additionalParameter = data.getAdditionalParameter(); // Получаем значение дополнительного параметра
            System.out.println("Additional Parameter: " + additionalParameter); // Выводим значение дополнительного параметра в консоль

            particle.setMyParameter(additionalParameter);
            return particle;
        } catch (Exception e) {
            System.out.println("oshibka blyat");
            e.printStackTrace();
            return null;
        }
    }

}
//public class MyParticleProvider implements ParticleProvider<MyParticleType> {
//    private final SpriteSet spriteSet;
//
//    public MyParticleProvider(SpriteSet spriteSet) {
//        this.spriteSet = spriteSet;
//    }

//    @Nullable
//    @Override
//    public Particle createParticle(SimpleParticleType blockRuneType, ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
//        MyParticle ret = new MyParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet); // предполагается, что вы добавите SpriteSet в конструктор вашего TestParticle
//        return ret;
//    }

//    @Nullable
//    @Override
//    public Particle createParticle(MyParticleData data, ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
//        MyParticle particle = new MyParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
//        particle.setMyParameter(data.getMyParameter());
//        return particle;
//    }

//    @Nullable
//    @Override
//    public Particle createParticle(MyParticleType data, ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
//        MyParticle particle = new MyParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
//        particle.setMyParameter(data.getMyParameter());
//        return particle;
//    }
//}
