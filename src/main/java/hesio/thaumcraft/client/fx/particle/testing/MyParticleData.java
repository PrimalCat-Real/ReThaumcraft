package hesio.thaumcraft.client.fx.particle.testing;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Locale;

public class MyParticleData implements ParticleOptions {
    public static final Codec<MyParticleData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.FLOAT.fieldOf("myParameter").forGetter(d -> d.myParameter)
            ).apply(instance, MyParticleData::new)
    );

    public static final Deserializer<MyParticleData> DESERIALIZER = new Deserializer<MyParticleData>() {
        @Override
        public MyParticleData fromCommand(ParticleType<MyParticleData> particleType, StringReader reader) throws CommandSyntaxException {
            if (reader.canRead() && reader.peek() == ' ') {
                reader.expect(' ');
                float myParameter = (float)reader.readDouble();
                return new MyParticleData(myParameter);
            } else {
                // Значение по умолчанию
                return new MyParticleData(1.0f);
            }
        }

        @Override
        public MyParticleData fromNetwork(ParticleType<MyParticleData> particleType, FriendlyByteBuf buffer) {
            float myParameter = buffer.readFloat();
            return new MyParticleData(myParameter);
        }
    };


    private final float myParameter;

    public MyParticleData(float myParameter) {
        this.myParameter = myParameter;
    }

    @Override
    public ParticleType<?> getType() {
        return MyParticleType.MY_PARTICLE_TYPE;
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buf) {
        buf.writeFloat(this.myParameter);
    }

    public static MyParticleData fromNetwork(ParticleType<MyParticleData> particleType, FriendlyByteBuf buf) {
        return new MyParticleData(buf.readFloat());
    }
    public static MyParticleData fromCommand(ParticleType<MyParticleData> particleType, StringReader reader) throws CommandSyntaxException {
        reader.expect(' ');
        return new MyParticleData((float) reader.readDouble());
    }

    @Override
    public String writeToString() {
        return String.format(Locale.ROOT, "%s %.2f", ForgeRegistries.PARTICLE_TYPES.getKey(this.getType()), this.myParameter);
    }

    public float getMyParameter() {
        return myParameter;
    }
}
