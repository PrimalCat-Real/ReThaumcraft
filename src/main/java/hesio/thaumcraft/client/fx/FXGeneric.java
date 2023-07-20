package hesio.thaumcraft.client.fx;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;

public class FXGeneric extends Particle  {

    public FXGeneric(ClientLevel world, double x, double y, double z, double vx, double vy, double vz) {
        super(world, x, y, z, vx, vy, vz);
    }


    @Override
    public void tick() {
        // Here you can define behavior of the particle.
        // For example, you might want to move the particle, check for collision, or handle its "death".
        // This method is called 20 times per second.
        // For now, the particle will do nothing.
    }
    
    @Override
    public void render(VertexConsumer p_107261_, Camera p_107262_, float p_107263_) {

    }

    @Override
    public ParticleRenderType getRenderType() {
        return null;
    }

}