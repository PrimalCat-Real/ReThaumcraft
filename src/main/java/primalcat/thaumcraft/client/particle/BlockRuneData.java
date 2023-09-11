package primalcat.thaumcraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import org.jetbrains.annotations.Nullable;

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
