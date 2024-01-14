package primalcat.thaumcraft.client.particles.type;

import com.google.gson.JsonDeserializer;
import com.mojang.serialization.Codec;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.network.PacketByteBuf;


import java.io.StringReader;

public class ThaumcraftParticleType extends ParticleType<ThaumcraftParticleType> implements ParticleEffect {
    public boolean noClip = false;

    protected ThaumcraftParticleType(boolean alwaysShow, Factory<ThaumcraftParticleType> parametersFactory) {
        super(alwaysShow, parametersFactory);
    }

    @Override
    public ParticleType<?> getType() {
        return null;
    }

    @Override
    public void write(PacketByteBuf buf) {

    }

    @Override
    public String asString() {
        return null;
    }

    @Override
    public Codec<ThaumcraftParticleType> getCodec() {
        return null;
    }

//    public static final Deserializer<ThaumcraftParticleType> DESERIALIZER = new JsonDeserializer<>() {
//        @Override
//        public WorldParticleOptions fromCommand(ParticleType<ThaumcraftParticleType> type, StringReader reader) {
//            return new WorldParticleOptions(type);
//        }
//
//        @Override
//        public WorldParticleOptions fromNetwork(ParticleType<WorldParticleOptions> type, FriendlyByteBuf buf) {
//            return new WorldParticleOptions(type);
//        }
//    };
}
