package primalcat.thaumcraft.common.block.light;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;

public class NitorEntity extends LodestoneBlockEntity {
    public NitorEntity(BlockEntityType<? extends NitorEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

//    public NitorEntity(BlockPos pos, BlockState state) {
//        this(BlockEntityInit.SOUL_VIAL.get(), pos, state);
//    }

    @Override
    public void tick() {
        super.tick();

        // Your custom code here
        Level level = this.level;
        BlockPos blockPos = this.worldPosition;

        // Example: Spawn particles at the Nitor's position
        level.addParticle(ParticleTypes.FLAME, blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, 0.0, 0.0, 0.0);

        // Output a message to the console
        System.out.println("Nitor is ticking!");
    }
}
