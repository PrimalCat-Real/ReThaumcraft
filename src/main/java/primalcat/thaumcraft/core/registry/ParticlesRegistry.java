package primalcat.thaumcraft.core.registry;

import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import primalcat.thaumcraft.Thaumcraft;
import primalcat.thaumcraft.client.particle.BlockRuneType;

public class ParticlesRegistry {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Thaumcraft.MODID);

    // particles
    public static final RegistryObject<BlockRuneType> BLOCKRUNE_PARTICLE =
            PARTICLE_TYPES.register("blockrunes_particle", BlockRuneType::new);

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
