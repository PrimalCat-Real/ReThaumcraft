package hesio.thaumcraft.client.fx.particle;

import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;

public class MyParticleType extends ParticleType<SimpleParticleType> {
    public MyParticleType(boolean p_123740_, ParticleOptions.Deserializer<SimpleParticleType> p_123741_) {
        super(p_123740_, p_123741_);
    }

    @Override
    public Codec<SimpleParticleType> codec() {
        return null;
    }
}
