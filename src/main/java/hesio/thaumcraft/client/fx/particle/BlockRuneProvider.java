package hesio.thaumcraft.client.fx.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public class BlockRuneProvider implements ParticleProvider<BlockRuneType> {
    private final SpriteSet spriteSet;

    public BlockRuneProvider(SpriteSet spriteSet) {
        System.out.println("Inside BlockRuneProvider constructor");
        this.spriteSet = spriteSet;
    }

    @Nullable
    @Override
    public Particle createParticle(BlockRuneType data, ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        try {
            System.out.println("Inside createParticle");
            BlockRunesParticle particle = new BlockRunesParticle(world, x, y, z, 1f, this.spriteSet);

            Vector3f additionalParameter = data.getVector(); // Getting the value of the additional parameter
            System.out.println("Additional Parameter: " + additionalParameter); // Printing the value of the additional parameter

            particle.setForward(additionalParameter);
            return particle;
        } catch (Exception e) {
            System.out.println("An error occurred");
            e.printStackTrace();
            return null;
        }
    }
}