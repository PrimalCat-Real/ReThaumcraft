package primalcat.thaumcraft.common.block;

import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import team.lodestar.lodestone.systems.block.LodestoneBlockProperties;

public class ThaumcraftBlockProperties {
    public static LodestoneBlockProperties NITOR() {
        return new LodestoneBlockProperties(Material.GLASS, MaterialColor.COLOR_BLUE)
                .strength(0.75f, 64f)
                .noOcclusion()
                .noCollission();
    }

}
