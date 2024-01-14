package primalcat.thaumcraft.common.items.thaumometer;

import mod.azure.azurelib.common.api.common.animatable.GeoItem;
import mod.azure.azurelib.common.internal.client.RenderProvider;
import mod.azure.azurelib.common.internal.common.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.common.internal.common.core.animatable.instance.InstancedAnimatableInstanceCache;
import mod.azure.azurelib.common.internal.common.core.animation.AnimatableManager;
import mod.azure.azurelib.common.internal.common.core.animation.AnimationController;
import mod.azure.azurelib.common.internal.common.core.object.PlayState;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.item.Item;
import primalcat.thaumcraft.client.render.items.ThaumometerRenderer;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Thaumometer extends Item implements GeoItem {
    public Thaumometer(Settings settings)
    {
        super(settings);
    }
    AnimatableInstanceCache cache = new InstancedAnimatableInstanceCache(this);
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);


    
    @Override
    public void createRenderer(Consumer<Object> consumer)
    {
        consumer.accept(new RenderProvider() {
            private ThaumometerRenderer renderer;

            @Override
            public BuiltinModelItemRenderer getCustomRenderer() {
                if (this.renderer == null)
                    this.renderer = new ThaumometerRenderer();

                return renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider()
    {
        return renderProvider;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar)
    {
        controllerRegistrar.add(new AnimationController<>(this, "thaumometer", 0, ignored -> PlayState.STOP));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache()
    {
        return cache;
    }
}
