package primalcat.thaumcraft.common.block.light;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import team.lodestar.lodestone.systems.block.WaterLoggedEntityBlock;

public class Nitor <T extends NitorEntity>  extends WaterLoggedEntityBlock<T> {
    public static final VoxelShape SHAPE = makeShape();
    public Nitor(Properties properties){
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }

    public static VoxelShape makeShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.21875, 0.03125, 0.21875, 0.78125, 0.78125, 0.78125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.28125, 0.84375, 0.28125, 0.71875, 0.96875, 0.71875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.34375, 0.78125, 0.34375, 0.65625, 0.84375, 0.65625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.34375, -0.03125, 0.34375, 0.65625, 0.03125, 0.65625), BooleanOp.OR);

        return shape;
    }
}
