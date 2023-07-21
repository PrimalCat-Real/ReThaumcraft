package hesio.thaumcraft.client.fx.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public interface ParticleProviderWithDirection<T extends ParticleOptions> extends ParticleProvider<T> {
    @Nullable
    Particle createParticle(T options, ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, Vector3f direction);
}
