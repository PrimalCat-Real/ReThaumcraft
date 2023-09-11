package primalcat.thaumcraft.client.particle;


import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.math.Vector3f;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

import static net.minecraft.core.Registry.PARTICLE_TYPE;

public class VectorParticleTypeData implements ParticleOptions {

    private ParticleType<VectorParticleTypeData> type;
    private final Vector3f vector;
    public static final Codec<VectorParticleTypeData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.FLOAT.fieldOf("x").forGetter(d -> d.vector.x()),
            Codec.FLOAT.fieldOf("y").forGetter(d -> d.vector.y()),
            Codec.FLOAT.fieldOf("z").forGetter(d -> d.vector.z())
    ).apply(instance, VectorParticleTypeData::new));

    static final ParticleOptions.Deserializer<VectorParticleTypeData> DESERIALIZER = new ParticleOptions.Deserializer<>() {
        @Override
        public VectorParticleTypeData fromCommand(ParticleType<VectorParticleTypeData> type, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            float x = reader.readFloat();
            reader.expect(' ');
            float y = reader.readFloat();
            reader.expect(' ');
            float z = reader.readFloat();
            return new VectorParticleTypeData(type, x,y,z);
        }

        @Override
        public VectorParticleTypeData fromNetwork(ParticleType<VectorParticleTypeData> type, FriendlyByteBuf buffer) {
            float x = (float) buffer.readDouble();
            float y = (float) buffer.readDouble();
            float z = (float) buffer.readDouble();
            return new VectorParticleTypeData(type, x, y, z);
        }
    };


    public Vector3f getVector() {
        return vector;
    }

    public VectorParticleTypeData(ParticleType<VectorParticleTypeData> particleTypeData, float x, float y, float z) {
        this.type = particleTypeData;
        this.vector = new Vector3f(x, y, z);
    }
    public VectorParticleTypeData(float x, float y, float z) {
        this(ModParticles.BLOCKRUNE_TYPE.get(), x, y, z);
    }


    @Override
    public void writeToNetwork(FriendlyByteBuf buf) {
        buf.writeFloat(vector.x());
        buf.writeFloat(vector.y());
        buf.writeFloat(vector.z());
    }

    @NotNull
    @Override
    public String writeToString() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f", PARTICLE_TYPE.getKey(getType()), this.vector.x(), this.vector.y(), this.vector.z());
    }

    @Override
    public ParticleType<VectorParticleTypeData> getType() {
        return type;
    }
}

