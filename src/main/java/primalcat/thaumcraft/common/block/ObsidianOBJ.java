package primalcat.thaumcraft.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class ObsidianOBJ extends Block {
    public ObsidianOBJ() {
        super(BlockBehaviour.Properties.of(Material.STONE).strength(5f));
    }
}
