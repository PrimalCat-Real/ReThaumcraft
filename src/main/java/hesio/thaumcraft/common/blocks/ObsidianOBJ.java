package hesio.thaumcraft.common.blocks;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ObsidianOBJ extends Block {
    public ObsidianOBJ() {
        super(BlockBehaviour.Properties.copy(Blocks.OBSIDIAN).noOcclusion());
    }
}
