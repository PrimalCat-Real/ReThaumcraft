package hesio.thaumcraft.client.fx.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.serialization.Codec;
import hesio.thaumcraft.client.fx.particle.testing.MyParticle;
import hesio.thaumcraft.client.fx.particle.testing.MyParticleData;
import hesio.thaumcraft.client.fx.particle.testing.MyParticleType;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.Locale;

public class BlockRuneType extends ParticleType<BlockRuneType> implements ParticleOptions {
    public static BlockRuneType BLOCK_RUNE_TYPE = null;

    private static final ParticleOptions.Deserializer<BlockRuneType> DESERIALIZER = new ParticleOptions.Deserializer<BlockRuneType>() {
        public BlockRuneType fromCommand(ParticleType<BlockRuneType> particleType, StringReader reader) {
            return (BlockRuneType) particleType;
        }

        public BlockRuneType fromNetwork(ParticleType<BlockRuneType> particleType, FriendlyByteBuf buffer) {
            return (BlockRuneType) particleType;
        }
    };

    private final Codec<BlockRuneType> codec = Codec.unit(this::getType);

    // replaced additionalParameter with vector
    private final Vector3f vector;

    public BlockRuneType(boolean alwaysShow, Vector3f vector) {
        super(alwaysShow, DESERIALIZER);
        this.vector = vector;
    }

    public BlockRuneType getType() {
        return this;
    }

    public Codec<BlockRuneType> codec() {
        return this.codec;
    }

    // replaced getAdditionalParameter with getVector
    public Vector3f getVector() {
        return vector;
    }

    public void writeToNetwork(FriendlyByteBuf buffer) {
        buffer.writeFloat(vector.x());
        buffer.writeFloat(vector.y());
        buffer.writeFloat(vector.z());
    }

    public String writeToString() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f", BuiltInRegistries.PARTICLE_TYPE.getKey(this).toString(), vector.x(), vector.y(), vector.z());
    }
}