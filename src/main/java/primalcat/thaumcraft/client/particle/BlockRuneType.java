package primalcat.thaumcraft.client.particle;

import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;

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
