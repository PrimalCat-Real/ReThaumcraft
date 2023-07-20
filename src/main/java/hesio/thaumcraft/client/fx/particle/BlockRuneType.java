package hesio.thaumcraft.client.fx.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class BlockRuneType extends SimpleParticleType {
    public BlockRuneType() {
        super(true);
    }

    public static class BlockRuneFactory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public BlockRuneFactory(SpriteSet s) {
            this.spriteSet = s;
        }

        @Nullable
        public Particle createParticle(SimpleParticleType blockRuneType, ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            BlockRunesParticle ret = new BlockRunesParticle(world, x, y, z, 0.5F, this.spriteSet); // предполагается, что вы добавите SpriteSet в конструктор вашего TestParticle
            return ret;
        }
    }
}
