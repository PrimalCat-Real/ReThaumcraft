package primalcat.thaumcraft.core.registry.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import primalcat.thaumcraft.Thaumcraft;
import primalcat.thaumcraft.client.particle.BlockRuneType;
import primalcat.thaumcraft.client.particle.CitrineParticles;
import primalcat.thaumcraft.client.particle.ScytheAttackParticle;
import team.lodestar.lodestone.systems.particle.type.LodestoneParticleType;

public class ParticlesRegistry{
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Thaumcraft.MODID);

    // particles
//    public static RegistryObject<SimpleParticleType> SCYTHE_SWEEP_ATTACK_PARTICLE = PARTICLE_TYPES.register("scythe_sweep_attack", () -> new SimpleParticleType(true));
    public static final RegistryObject<BlockRuneType> BLOCKRUNE_PARTICLE =
            PARTICLE_TYPES.register("blockrunes_particle", BlockRuneType::new);

//    public static final RegistryObject<SimpleParticleType> CITRINE_PARTICLES =
//            PARTICLE_TYPES.register("citrine_particles", () -> new SimpleParticleType(true));

//    public static RegistryObject<LodestoneParticleType> AURANODES_PARTICLE = PARTICLE_TYPES.register("auranodes", LodestoneParticleType::new);
//

    public static void registerParticleFactory(RegisterParticleProvidersEvent event) {
//        Minecraft.getInstance().particleEngine.register(CITRINE_PARTICLES.get(),
//            CitrineParticles.Provider::new);
//        Minecraft.getInstance().particleEngine.register(AURANODES_PARTICLE.get(), LodestoneParticleType.Factory::new);
//        event.register(SCYTHE_SWEEP_ATTACK_PARTICLE.get(), ScytheAttackParticle.Factory::new);
    }
    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
