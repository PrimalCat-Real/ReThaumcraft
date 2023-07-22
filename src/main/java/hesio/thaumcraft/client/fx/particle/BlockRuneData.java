package hesio.thaumcraft.client.fx.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.Locale;

public class BlockRuneData implements ParticleProvider<VectorParticleTypeData> {

    private final SpriteSet spriteSet;
    public static final String NAME = "blockrunes_particle";

    public BlockRuneData(SpriteSet s) {
        this.spriteSet = s;
    }
    @Nullable
    @Override
    public Particle createParticle(VectorParticleTypeData data, ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        return new BlockRunesParticle(world, x, y, z, 1f, this.spriteSet, data.getVector());
    }

    public static ParticleOptions createData(float xv, float yv, float zv) {
        return new VectorParticleTypeData(ModParticles.BLOCKRUNE_TYPE.get(), xv,yv,zv);
    }

}
