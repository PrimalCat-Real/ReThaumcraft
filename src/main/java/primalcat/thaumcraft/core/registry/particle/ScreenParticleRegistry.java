package primalcat.thaumcraft.core.registry.particle;

import net.minecraft.client.particle.ParticleEngine;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import primalcat.thaumcraft.core.helper.ThaumcraftHelper;
import team.lodestar.lodestone.LodestoneLib;
import team.lodestar.lodestone.systems.particle.screen.ScreenParticleOptions;
import team.lodestar.lodestone.systems.particle.screen.ScreenParticleType;
import team.lodestar.lodestone.systems.particle.type.LodestoneScreenParticleType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.resources.ResourceLocation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;

public class ScreenParticleRegistry {
    public static final ArrayList<ScreenParticleType<?>> PARTICLE_TYPES = new ArrayList<>();

    public static final ScreenParticleType<ScreenParticleOptions> AURANODES = registerType(new LodestoneScreenParticleType());
    public static void registerParticleFactory(RegisterParticleProvidersEvent event) {
        registerProvider(AURANODES, new LodestoneScreenParticleType.Factory(getSpriteSet(ThaumcraftHelper.thaumcraftPath("auranodes"))));
    }

    public static <T extends ScreenParticleOptions> ScreenParticleType<T> registerType(ScreenParticleType<T> type) {
        PARTICLE_TYPES.add(type);
        return type;
    }

    public static <T extends ScreenParticleOptions> void registerProvider(ScreenParticleType<T> type, ScreenParticleType.ParticleProvider<T> provider) {
        type.provider = provider;
    }

    public static SpriteSet getSpriteSet(ResourceLocation resourceLocation) {

        return Minecraft.getInstance().particleEngine.spriteSets.get(resourceLocation);
    }
}
