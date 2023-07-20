package hesio.thaumcraft.utils;

import hesio.thaumcraft.Thaumcraft;
import net.minecraft.resources.ResourceLocation;

public class ResourceLocationHelper {
    public static ResourceLocation prefix(String path) {
        return new ResourceLocation(Thaumcraft.MODID, path);
    }
}
