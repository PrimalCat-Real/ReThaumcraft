package primalcat.thaumcraft.client.particle;

import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.registry.common.particle.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.SpinParticleData;

import java.awt.*;

import static net.minecraft.util.Mth.nextFloat;

public class ParticleEffects {
    public static void spawnSpiritGlimmerParticles(Level level, double x, double y, double z, Color color, Color endColor) {
        spawnSpiritGlimmerParticles(level, x, y, z, 1, Vec3.ZERO, color, endColor);
    }

    public static void spawnSpiritGlimmerParticles(Level level, double x, double y, double z, float alphaMultiplier, Vec3 extraVelocity, Color color, Color endColor) {
        RandomSource rand = level.getRandom();
        WorldParticleBuilder.create(LodestoneParticleRegistry.TWINKLE_PARTICLE)
                .setTransparencyData(GenericParticleData.create(0.4f * alphaMultiplier, 0f).build())
                .setScaleData(GenericParticleData.create(0.25f + rand.nextFloat() * 0.1f, 0).build())
                .setColorData(ColorParticleData.create(color, endColor).build())
                .setLifetime(5 + rand.nextInt(4))
                .setRandomOffset(0.05f)
                .enableNoClip()
                .addMotion(extraVelocity.x, extraVelocity.y, extraVelocity.z)
                .setRandomMotion(0.02f, 0.02f)
                .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.INVISIBLE)
                .spawn(level, x, y, z);

        spawnSpiritParticles(level, x, y, z, 1, extraVelocity, color, endColor);
    }

    public static void spawnSpiritParticles(Level level, double x, double y, double z, Color color, Color endColor) {
        spawnSpiritParticles(level, x, y, z, 1, Vec3.ZERO, color, endColor);
    }

    public static void spawnSpiritParticles(Level level, double x, double y, double z, float alphaMultiplier, Vec3 extraVelocity, Color color, Color endColor) {
        RandomSource rand = level.getRandom();
        WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                .setTransparencyData(GenericParticleData.create(0.275f * alphaMultiplier, 0f).build())
                .setLifetime(15 + rand.nextInt(4))
//                .setSpinData(SpinParticleData.create(nextFloat(rand, 0.05f, 0.1f)).build())
//                .setScaleData(GenericParticleData.create(0.05f + rand.nextFloat() * 0.025f, 0).build())
//                .setColorData(ColorParticleData.create(color, endColor).setCoefficient(1.25f).build())
//                .setRandomOffset(0.02f)
//                .enableNoClip()
//                .addMotion(extraVelocity.x, extraVelocity.y, extraVelocity.z)
//                .setRandomMotion(0.01f, 0.01f)
//                .setDiscardFunction(SimpleParticleOptions.ParticleDiscardFunctionType.INVISIBLE)
//                .repeat(level, x, y, z, 1)
//                .setTransparencyData(GenericParticleData.create(0.2f * alphaMultiplier, 0f).build())
//                .setLifetime(10 + rand.nextInt(2))
//                .setScaleData(GenericParticleData.create(0.15f + rand.nextFloat() * 0.05f, 0f).build())
//                .setRandomMotion(0.01f, 0.01f)
                .repeat(level, x, y, z, 1);

    }

    public static void spawnSoulParticles(Level level, double x, double y, double z, float alphaMultiplier, float scaleMultiplier, Vec3 extraVelocity, Color color, Color endColor) {
        RandomSource rand = level.getRandom();
        WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                .setTransparencyData(GenericParticleData.create(0.1f * alphaMultiplier, 0).build())
                .setScaleData(GenericParticleData.create((0.2f + rand.nextFloat() * 0.2f) * scaleMultiplier, 0).setEasing(Easing.QUINTIC_IN).build())
                .setColorData(ColorParticleData.create(color, endColor).build())
                .setLifetime(8 + rand.nextInt(5))
                .setRandomOffset(0.05f)
                .enableNoClip()
                .addMotion(extraVelocity.x, extraVelocity.y, extraVelocity.z)
                .setRandomMotion(0.01f * scaleMultiplier, 0.01f * scaleMultiplier)
                .repeat(level, x, y, z, 1);

        WorldParticleBuilder.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                .setTransparencyData(GenericParticleData.create(0, 0.05f * alphaMultiplier, 0f).setEasing(Easing.CUBIC_IN, Easing.CUBIC_OUT).build())
                .setSpinData(SpinParticleData.create(0.05f * level.getGameTime() % 6.28f, 0, nextFloat(rand, 0.05f, 0.4f)).setEasing(Easing.CUBIC_IN).build())
                .setScaleData(GenericParticleData.create((0.2f + rand.nextFloat() * 0.1f) * scaleMultiplier, 0.1f * scaleMultiplier).setEasing(Easing.QUINTIC_IN).build())
                .setColorData(ColorParticleData.create(color, endColor).build())
                .setLifetime(80 + rand.nextInt(10))
                .setRandomOffset(0.1f)
                .enableNoClip()
                .addMotion(extraVelocity.x, extraVelocity.y, extraVelocity.z)
                .setRandomMotion(0.01f * scaleMultiplier, 0.01f * scaleMultiplier)
                .repeat(level, x, y, z, 1)
                .setTransparencyData(GenericParticleData.create(0.12f * alphaMultiplier, 0f).build())
                .setSpinData(SpinParticleData.create(0, nextFloat(rand, 0.1f, 0.5f)).build())
                .setScaleData(GenericParticleData.create((0.15f + rand.nextFloat() * 0.1f) * scaleMultiplier, 0.1f * scaleMultiplier).build())
                .setLifetime(10 + rand.nextInt(5))
                .setRandomMotion(0.03f * scaleMultiplier, 0.03f * scaleMultiplier)
                .repeat(level, x, y, z, 2);

        WorldParticleBuilder.create(LodestoneParticleRegistry.STAR_PARTICLE)
                .setTransparencyData(GenericParticleData.create((rand.nextFloat() * 0.1f + 0.1f) * alphaMultiplier, 0f).build())
                .setSpinData(SpinParticleData.create(0.025f * level.getGameTime() % 6.28f, 0, nextFloat(rand, 0.05f, 0.4f)).build())
                .setScaleData(GenericParticleData.create((0.7f + rand.nextFloat() * 0.1f) * scaleMultiplier, 0.1f * scaleMultiplier).build())
                .setColorData(ColorParticleData.create(color, endColor).build())
                .setLifetime(20 + rand.nextInt(10))
                .setRandomOffset(0.01f)
                .enableNoClip()
                .addMotion(extraVelocity.x, extraVelocity.y, extraVelocity.z)
                .repeat(level, x, y, z, 1)
                .setTransparencyData(GenericParticleData.create((rand.nextFloat() * 0.05f + 0.05f) * alphaMultiplier, 0f).build())
                .setSpinData(SpinParticleData.create(0, nextFloat(rand, 0.1f, 0.5f)).build())
                .setScaleData(GenericParticleData.create((0.5f + rand.nextFloat() * 0.1f) * scaleMultiplier, 0.1f * scaleMultiplier).build())
                .setLifetime(10 + rand.nextInt(5))
                .repeat(level, x, y, z, 1);
    }
}
