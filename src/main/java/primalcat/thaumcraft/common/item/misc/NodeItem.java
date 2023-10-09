package primalcat.thaumcraft.common.item.misc;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import team.lodestar.lodestone.handlers.screenparticle.ParticleEmitterHandler;
import team.lodestar.lodestone.registry.common.particle.LodestoneScreenParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.ScreenParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.SpinParticleData;
import team.lodestar.lodestone.systems.particle.screen.LodestoneScreenParticleRenderType;
import team.lodestar.lodestone.systems.particle.screen.base.ScreenParticle;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

import static net.minecraft.world.InteractionHand.MAIN_HAND;
import static net.minecraft.world.InteractionHand.OFF_HAND;

public class NodeItem extends BlockItem implements ParticleEmitterHandler.ItemParticleSupplier {
    public NodeItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    protected boolean canPlace(BlockPlaceContext context, BlockState state) {
        ItemStack otherStack = context.getPlayer().getItemInHand(context.getHand().equals(MAIN_HAND) ? OFF_HAND : MAIN_HAND);
        return super.canPlace(context, state);
    }

    @Override
    public void spawnParticles(HashMap<LodestoneScreenParticleRenderType, ArrayList<ScreenParticle>> target, Level level, float v, ItemStack itemStack, float v1, float v2) {
        System.out.println("Tick node");
        float gameTime = level.getGameTime();
//        AbstractEtherItem etherItem = (AbstractEtherItem) stack.getItem();
//        Color firstColor = new Color(etherItem.getFirstColor(stack));
//        Color secondColor = new Color(etherItem.getSecondColor(stack));
//        float alphaMultiplier = etherItem.iridescent ? 0.75f : 0.5f;

        Color firstColor = new Color(124, 151, 96);
        Color secondColor = new Color(123,43,97);
        float alphaMultiplier = 0.5f;
        final SpinParticleData.SpinParticleDataBuilder spinDataBuilder = SpinParticleData.create(0, 1).setSpinOffset(0.025f * gameTime % 6.28f).setEasing(Easing.EXPO_IN_OUT);
        ScreenParticleBuilder.create(LodestoneScreenParticleRegistry.STAR, target)
                .setTransparencyData(GenericParticleData.create(0.11f * alphaMultiplier, 0f).setEasing(Easing.QUINTIC_IN).build())
                .setScaleData(GenericParticleData.create((float) (0.75f + Math.sin(gameTime * 0.05f) * 0.125f), 0).build())
                .setColorData(ColorParticleData.create(firstColor, secondColor).setCoefficient(1.25f).build())
                .setSpinData(spinDataBuilder.build())
                .setLifetime(7)
                .setRandomOffset(0.05f)
                .spawnOnStack(0, -1)
                .setScaleData(GenericParticleData.create((float) (0.75f - Math.sin(gameTime * 0.075f) * 0.125f), 0).build())
                .setColorData(ColorParticleData.create(secondColor, firstColor).build())
                .setSpinData(spinDataBuilder.setSpinOffset(0.785f - 0.01f * gameTime % 6.28f).build())
                .spawnOnStack(0, -1);
    }
}
