package primalcat.thaumcraft.core.helper;

import net.minecraft.resources.ResourceLocation;
import primalcat.thaumcraft.Thaumcraft;

public class ThaumcraftHelper {
    public static ResourceLocation thaumcraftPath(String path) {
        return new ResourceLocation(Thaumcraft.MODID, path);
    }
}
