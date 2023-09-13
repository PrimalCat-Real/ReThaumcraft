package primalcat.thaumcraft.client.font;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.FontSet;
//import net.minecraft.client.gui.font.providers.;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;

public class NotoSansFontRenderer extends Font implements ResourceManagerReloadListener {


    public NotoSansFontRenderer(Function<ResourceLocation, FontSet> p_243253_, boolean p_243245_) {
        super(p_243253_, p_243245_);

    }

    @Override
    public CompletableFuture<Void> reload(PreparationBarrier p_10752_, ResourceManager p_10753_, ProfilerFiller p_10754_, ProfilerFiller p_10755_, Executor p_10756_, Executor p_10757_) {
        return ResourceManagerReloadListener.super.reload(p_10752_, p_10753_, p_10754_, p_10755_, p_10756_, p_10757_);
    }

    @Override
    public String getName() {
        return ResourceManagerReloadListener.super.getName();
    }

    @Override
    public void onResourceManagerReload(ResourceManager p_10758_) {

    }
}
