package primalcat.thaumcraft.common.items.test;

import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import primalcat.thaumcraft.Thaumcraft;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AnimatedItemModel extends AnimatedGeoModel<AnimatedItem> {
    @Override
    public ResourceLocation getModelResource(AnimatedItem object) {
        return new ResourceLocation(Thaumcraft.MOD_ID, "geo/animated_item.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AnimatedItem object) {
        return new ResourceLocation(Thaumcraft.MOD_ID, "textures/items/animated_item.png");
    }
    @Override
    public ResourceLocation getAnimationResource(AnimatedItem animatable) {
        return new ResourceLocation(Thaumcraft.MOD_ID, "animations/animated_item.animation.json");
    }
}
