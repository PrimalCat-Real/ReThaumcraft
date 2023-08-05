package primalcat.thaumcraft.api;

import net.minecraft.resources.ResourceLocation;

public interface IAspect {
    String getName();
    int getColor();
    ResourceLocation getAspectImage();
    int getBlend();
    Aspect[] getComponents();
    boolean isPrimal();
}
