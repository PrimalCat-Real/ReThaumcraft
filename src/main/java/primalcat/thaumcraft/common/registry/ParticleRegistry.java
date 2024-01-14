package primalcat.thaumcraft.common.registry;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import primalcat.thaumcraft.Thaumcraft;

public class ParticleRegistry {
    public static final DefaultParticleType TEST_PARTICLE = Registry.register(Registries.PARTICLE_TYPE,
            new Identifier(Thaumcraft.MODID, "test_particle"), FabricParticleTypes.simple());


}
