package hesio.thaumcraft.events;


import hesio.thaumcraft.Thaumcraft;
import hesio.thaumcraft.client.fx.particle.*;
import hesio.thaumcraft.client.fx.particle.testing.MyParticleProvider;
import hesio.thaumcraft.inits.ParticlesInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static hesio.thaumcraft.inits.ParticlesInit.BLOCKRUNE_PARTICLE;
import static hesio.thaumcraft.inits.ParticlesInit.TEST_PARTICLE;

@Mod.EventBusSubscriber(modid = Thaumcraft.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        ParticleEngine particleEngine = Minecraft.getInstance().particleEngine;

        particleEngine.register(ParticlesInit.CITRINE_PARTICLES.get(),
                CitrineParticles.Provider::new);
        particleEngine.register(TEST_PARTICLE.get(), TestParticle.Factory::new);
        particleEngine.register(BLOCKRUNE_PARTICLE.get(), BlockRuneType.Factory::new);
        particleEngine.register(ParticlesInit.MY_PARTICLE.get(), MyParticleProvider::new);
    }
}


