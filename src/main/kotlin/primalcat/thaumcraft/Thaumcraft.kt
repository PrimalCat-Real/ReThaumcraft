package primalcat.thaumcraft

import primalcat.thaumcraft.common.init.ThaumcraftBlocks
import net.minecraft.client.Minecraft
import net.neoforged.fml.common.Mod
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
import net.neoforged.fml.event.lifecycle.FMLDedicatedServerSetupEvent
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import primalcat.thaumcraft.common.init.ThaumcraftCreativeTab
import primalcat.thaumcraft.common.init.ThaumcraftItems
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS
import thedarkcolour.kotlinforforge.neoforge.forge.runForDist

/**
 * Main mod class. Should be an `object` declaration annotated with `@Mod`.
 * The modid should be declared in this object and should match the modId entry
 * in mods.toml.
 *
 * An example for blocks is in the `blocks` package of this mod.
 */
@Mod(Thaumcraft.MODID)
object Thaumcraft {
    const val MODID = "thaumcraft"

    // the logger for our mod
    val LOGGER: Logger = LogManager.getLogger(MODID)

    init {
        LOGGER.log(Level.INFO, "Hello world!")

        // Register the KDeferredRegister to the mod-specific event bus
        ThaumcraftBlocks.REGISTRY.register(MOD_BUS)
        ThaumcraftItems.REGISTRY.register(MOD_BUS)
        ThaumcraftCreativeTab.REGISTRY.register(MOD_BUS)


        MOD_BUS.addListener(ThaumcraftCreativeTab::addToCreativeTab)
        val obj = runForDist(
            clientTarget = {
                MOD_BUS.addListener(Thaumcraft::onClientSetup)
                Minecraft.getInstance()
            },
            serverTarget = {
                MOD_BUS.addListener(Thaumcraft::onServerSetup)
                "test"
            })

        println(obj)
    }

    /**
     * This is used for initializing client specific
     * things such as renderers and keymaps
     * Fired on the mod specific event bus.
     */
    private fun onClientSetup(event: FMLClientSetupEvent) {
        LOGGER.log(Level.INFO, "Initializing client...")
    }

    /**
     * Fired on the global Forge bus.
     */
    private fun onServerSetup(event: FMLDedicatedServerSetupEvent) {
        LOGGER.log(Level.INFO, "Server starting...")
    }
}