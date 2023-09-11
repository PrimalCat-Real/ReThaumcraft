package primalcat.thaumcraft.common.item.test;

import net.minecraft.resources.ResourceLocation;
import primalcat.thaumcraft.Thaumcraft;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AnimatedItemModel extends AnimatedGeoModel<AnimatedItem> {
    @Override
    public ResourceLocation getModelResource(AnimatedItem object) {
        return new ResourceLocation(Thaumcraft.MODID, "geo/animated_item.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AnimatedItem object) {
        return new ResourceLocation(Thaumcraft.MODID, "textures/items/animated_item.png");
    }
    @Override
    public ResourceLocation getAnimationResource(AnimatedItem animatable) {
        return new ResourceLocation(Thaumcraft.MODID, "animations/animated_item.animation.json");
    }
}
