package primalcat.thaumcraft.common.item.misc;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import static net.minecraft.world.InteractionHand.MAIN_HAND;
import static net.minecraft.world.InteractionHand.OFF_HAND;

public class NitorItem extends BlockItem {
    public NitorItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    protected boolean canPlace(BlockPlaceContext context, BlockState state) {
        ItemStack otherStack = context.getPlayer().getItemInHand(context.getHand().equals(MAIN_HAND) ? OFF_HAND : MAIN_HAND);
        return super.canPlace(context, state);
    }
}
