package hesio.thaumcraft.events;


import hesio.thaumcraft.Thaumcraft;
import hesio.thaumcraft.client.fx.particle.BlockRunesParticle;
import hesio.thaumcraft.client.fx.particle.CitrineParticles;
import hesio.thaumcraft.client.fx.particle.TestParticle;
import hesio.thaumcraft.inits.ParticlesInit;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static hesio.thaumcraft.inits.ParticlesInit.BLOCKRUNE_PARTICLE;
import static hesio.thaumcraft.inits.ParticlesInit.TEST_PARTICLE;

@Mod.EventBusSubscriber(modid = Thaumcraft.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register(ParticlesInit.CITRINE_PARTICLES.get(),
                CitrineParticles.Provider::new);
        Minecraft.getInstance().particleEngine.register(TEST_PARTICLE.get(), TestParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(BLOCKRUNE_PARTICLE.get(), BlockRunesParticle.Factory::new);
    }
}


