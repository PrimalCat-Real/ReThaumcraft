package primalcat.thaumcraft.client.renderer;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ExperienceOrbRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ExperienceOrb;
import primalcat.thaumcraft.common.entities.AspectOrb;

public class AspectOrbRenderer extends EntityRenderer<AspectOrb> {

    protected AspectOrbRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(AspectOrb aspectOrb) {
        return null;
    }
}
