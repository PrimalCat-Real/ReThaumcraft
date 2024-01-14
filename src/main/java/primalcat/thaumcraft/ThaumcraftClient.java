package primalcat.thaumcraft;

import mod.azure.azurelib.common.api.client.renderer.layer.BlockAndItemGeoLayer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import primalcat.thaumcraft.client.particles.TestParticle;
import primalcat.thaumcraft.common.registry.ParticleRegistry;
import net.minecraft.client.render.RenderLayer;

public class ThaumcraftClient implements ClientModInitializer {


	@Override
	public void onInitializeClient() {
		ParticleFactoryRegistry particleRegistry = ParticleFactoryRegistry.getInstance();
		particleRegistry.register(ParticleRegistry.TEST_PARTICLE, TestParticle.Factory::new);

//		BlockRenderLayerMap.INSTANCE.putBlock(Blocks.HEALTH5_BLOCK, RenderLayer.getTranslucent());
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
	}

}