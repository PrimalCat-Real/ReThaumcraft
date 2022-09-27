package thaumcraft.thaumcraft.common.blocks;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.property.Properties;

public class ObsidianOBJ extends Block {
    public ObsidianOBJ() {
        super(BlockBehaviour.Properties.of(Material.STONE).strength(5f));
    }
}
