package hesio.thaumcraft.client.fx.particle.testing;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;

public class MyParticle extends TextureSheetParticle {
    private int myParameter;

    protected MyParticle(ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet spriteSet) {
        super(world, x, y, z, xSpeed, ySpeed, zSpeed);
        System.out.println("Inside MyParticle constructor");
        this.setSpriteFromAge(spriteSet);
    }

    public void setMyParameter(int myParameter) {
        System.out.println("Setting myParameter to " + myParameter);
        this.myParameter = myParameter;
    }

    @Override
    public ParticleRenderType getRenderType() {
        System.out.println("Inside getRenderType");
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }
    // Override other necessary methods
}
