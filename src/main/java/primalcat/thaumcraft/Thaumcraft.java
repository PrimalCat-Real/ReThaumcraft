package primalcat.thaumcraft;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import primalcat.thaumcraft.common.registry.ItemRegistry;
import primalcat.thaumcraft.utils.Config;
import primalcat.thaumcraft.utils.ConfigManager;

import java.io.IOException;

public class Thaumcraft implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MODID = "thaumcraft";
    public static final Logger LOGGER = LoggerFactory.getLogger("thaumcraft");

	@Override
	public void onInitialize() {
		ItemRegistry.registry();

		Config.initConfig();
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
	}
}