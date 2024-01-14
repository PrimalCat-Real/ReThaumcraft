package primalcat.thaumcraft.api.aspects;

import net.minecraft.util.Identifier;
import primalcat.thaumcraft.common.aspects.Aspect;

public interface IAspect {
    String getName();
    int getColor();
    Identifier getAspectImage();
    Aspect[] getComponents();
    boolean isPrimal();
}
