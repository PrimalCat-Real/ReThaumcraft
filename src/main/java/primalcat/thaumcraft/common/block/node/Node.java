package primalcat.thaumcraft.common.block.node;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import team.lodestar.lodestone.systems.block.WaterLoggedEntityBlock;

public class Node <T extends NodeEntity>  extends WaterLoggedEntityBlock {
    public static final VoxelShape SHAPE = makeShape();
    public Node(Properties properties){
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }

    public static VoxelShape makeShape() {
        VoxelShape shape = Shapes.empty();
        double scaleFactor = 0.5; // Scale factor to make it 2 times smaller

        // Calculate the dimensions of the smaller cube
        double smallerSizeX = (0.65625 - 0.34375) * scaleFactor;
        double smallerSizeY = (0.65625 - 0.03125) * scaleFactor;
        double smallerSizeZ = (0.65625 - 0.34375) * scaleFactor;

        // Calculate the center of the original bounding box
        double centerX = (0.34375 + 0.65625) / 2.0;
        double centerY = (0.03125 + 0.65625) / 2.0;
        double centerZ = (0.34375 + 0.65625) / 2.0;

        // Calculate the minimum and maximum coordinates of the smaller cube
        double minX = centerX - (smallerSizeX / 2.0);
        double minY = centerY - (smallerSizeY / 2.0);
        double minZ = centerZ - (smallerSizeZ / 2.0);
        double maxX = centerX + (smallerSizeX / 2.0);
        double maxY = centerY + (smallerSizeY / 2.0);
        double maxZ = centerZ + (smallerSizeZ / 2.0);

        shape = Shapes.join(shape, Shapes.box(minX, minY, minZ, maxX, maxY, maxZ), BooleanOp.OR);
        return shape;
    }
}
