package hesio.thaumcraft.client.fx.particle;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;

public class MyParticleData implements ParticleOptions {

    @Override
    public ParticleType<?> getType() {
        return null;
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf p_123732_) {

    }

    @Override
    public String writeToString() {
        return null;
    }
}
