package hesio.thaumcraft.client.fx.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class TestParticleType extends SimpleParticleType {
    public TestParticleType() {
        // Этот параметр определяет, будут ли частицы данного типа всегда отображаться независимо от настроек видимости частиц в настройках клиента.
        super(true);
    }
    

    public static class TestFactory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public TestFactory(SpriteSet s) {
            this.spriteSet = s;
        }

        @Nullable
        public Particle createParticle(SimpleParticleType basicParticleType, ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            TestParticle ret = new TestParticle(world, x, y, z, 0.5F, this.spriteSet); // предполагается, что вы добавите SpriteSet в конструктор вашего TestParticle
            return ret;
        }
    }
}
