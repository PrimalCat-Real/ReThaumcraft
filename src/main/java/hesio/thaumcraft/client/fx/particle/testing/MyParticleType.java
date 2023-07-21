package hesio.thaumcraft.client.fx.particle.testing;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.serialization.Codec;
import hesio.thaumcraft.client.fx.particle.BlockRunesData;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;

import java.util.Locale;
public class MyParticleType extends ParticleType<MyParticleType> implements ParticleOptions {
    public static MyParticleType MY_PARTICLE_TYPE = null;
    private static final ParticleOptions.Deserializer<MyParticleType> DESERIALIZER = new ParticleOptions.Deserializer<MyParticleType>() {
        public MyParticleType fromCommand(ParticleType<MyParticleType> particleType, StringReader reader) {
            return (MyParticleType) particleType;
        }

        public MyParticleType fromNetwork(ParticleType<MyParticleType> particleType, FriendlyByteBuf buffer) {
            return (MyParticleType) particleType;
        }
    };
    private final Codec<MyParticleType> codec = Codec.unit(this::getType);

    public int getAdditionalParameter() {
        return additionalParameter;
    }

    private final int additionalParameter; // Новый дополнительный параметр типа int

    public MyParticleType(boolean alwaysShow, int additionalParameter) {
        super(alwaysShow, DESERIALIZER);
        this.additionalParameter = additionalParameter;
    }

    public MyParticleType getType() {
        return this;
    }

    public Codec<MyParticleType> codec() {
        return this.codec;
    }

    public void writeToNetwork(FriendlyByteBuf buffer) {
        buffer.writeInt(additionalParameter); // Записываем значение дополнительного параметра в сеть
    }

    public String writeToString() {
        return String.format(Locale.ROOT, "%s %d", BuiltInRegistries.PARTICLE_TYPE.getKey(this).toString(), additionalParameter);
    }
}
//
//public class MyParticleType extends ParticleType<MyParticleData> {
//    public static MyParticleType MY_PARTICLE_TYPE = null;
//
////    public MyParticleType() {
////        super(true);
////    }
////
////    @Override
////    public Codec<SimpleParticleType> codec() {
////        return null;
////    }
//
//    public MyParticleType() {
//        super(false, MyParticleData.DESERIALIZER);
//    }
//
//    @Override
//    public Codec<MyParticleData> codec() {
//        return MyParticleData.CODEC;
//    }
//}
