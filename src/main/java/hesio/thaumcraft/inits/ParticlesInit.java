package hesio.thaumcraft.inits;

import hesio.thaumcraft.Thaumcraft;
import hesio.thaumcraft.client.fx.particle.BlockRuneType;
import hesio.thaumcraft.client.fx.particle.TestParticle;
import hesio.thaumcraft.client.fx.particle.TestParticleType;
import hesio.thaumcraft.client.fx.particle.testing.MyParticleType;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.joml.Vector3f;

public class ParticlesInit {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Thaumcraft.MODID);

    // Добавляем вашу частицу
    public static final RegistryObject<TestParticleType> TEST_PARTICLE =
            PARTICLE_TYPES.register("test_particle", TestParticleType::new);
    public static final RegistryObject<SimpleParticleType> CITRINE_PARTICLES =
            PARTICLE_TYPES.register("citrine_particles", () -> new SimpleParticleType(true));

    public static final RegistryObject<BlockRuneType> BLOCKRUNE_PARTICLE =
            PARTICLE_TYPES.register("blockrunes_particle", BlockRuneType::new);


    public static final RegistryObject<MyParticleType> MY_PARTICLE =
            PARTICLE_TYPES.register("my_particle", () -> new MyParticleType(true, 1));


    // Добавьте функцию для регистрации фабрик частиц
//    @OnlyIn(Dist.CLIENT)
//    public static void registerFactories(ParticleEngine particleManager) {
//        // Регистрируем фабрику для вашей частицы
//        particleManager.register(TEST_PARTICLE.get(), TestParticle.Factory::new);
//    }

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }



}
