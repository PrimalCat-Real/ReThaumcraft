package primalcat.thaumcraft.client.particles;

import net.minecraft.client.particle.AnimatedParticle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class NodeParticle extends AnimatedParticle {
    protected NodeParticle(ClientWorld world, double x, double y, double z, SpriteProvider spriteProvider, float upwardsAcceleration) {
        super(world, x, y, z, spriteProvider, upwardsAcceleration);
    }
    @Environment(EnvType.CLIENT)
    public static class FireflyParticleFactory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider sprites;

        public FireflyParticleFactory(SpriteProvider sprites) {
            this.sprites = sprites;
        }

        @Nullable
        @Override
        public Particle createParticle(DefaultParticleType parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new NodeParticle(world, x, y, z, sprites, 1);
        }
    }
}
