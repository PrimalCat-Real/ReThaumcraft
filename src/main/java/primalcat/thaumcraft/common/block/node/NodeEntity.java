package primalcat.thaumcraft.common.block.node;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import primalcat.thaumcraft.client.particle.ParticleEffects;
import primalcat.thaumcraft.common.block.light.NitorEntity;
import primalcat.thaumcraft.core.registry.BlockEntityRegistry;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;

import java.awt.*;

public class NodeEntity extends LodestoneBlockEntity {
    public NodeEntity(BlockEntityType<? extends NodeEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }


    public NodeEntity(BlockPos pos, BlockState state) {
        this(BlockEntityRegistry.NODE.get(), pos, state);
    }


    @Override
    public void tick() {
        Level level = this.level;
        BlockPos blockPos = this.worldPosition;
        if (level.isClientSide) {
            double y = 0.5f;
            // @TODO read colors from nbt data
            float red = 252 / 255.0F;
            float green = 173 / 255.0F;
            float blue = 3 / 255.0F;
//            ParticleEffects.spawnSoulParticles(level, worldPosition.getX() + 0.5f, worldPosition.getY() + y, worldPosition.getZ() + 0.5f, 1, 0.75f, Vec3.ZERO, new Color(red, green, blue), Color.GREEN);
            ParticleEffects.spawnSpiritParticles(level, worldPosition.getX() + 0.5f, worldPosition.getY() + y, worldPosition.getZ() + 0.5f, new Color(red, green, blue), Color.GREEN);
//            level.addParticle(ParticleTypes.FLAME, blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, 0.0, 0.0, 0.0);
        }

    }
}
