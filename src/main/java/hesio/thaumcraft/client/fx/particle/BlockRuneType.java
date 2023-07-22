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

public class BlockRuneType extends ParticleType<VectorParticleTypeData> {
//    public static final BlockRuneType BLOCK_RUNE_TYPE =  new BlockRuneType();

    public BlockRuneType() {
        super(true, VectorParticleTypeData.DESERIALIZER);
    }

    @Override
    public Codec<VectorParticleTypeData> codec() {
        return VectorParticleTypeData.CODEC;
    }

}