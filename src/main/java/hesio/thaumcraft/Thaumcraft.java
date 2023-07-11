package hesio.thaumcraft;

import com.mojang.logging.LogUtils;
import hesio.thaumcraft.inits.CreativeTabInit;
import hesio.thaumcraft.inits.BlockInit;
import hesio.thaumcraft.inits.ItemInit;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Thaumcraft.MODID)
public class Thaumcraft {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "thaumcraft";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "thaumcraft" namespace
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "thaumcraft" namespace
//

    // Creates a new Block with the id "thaumcraft:example_block", combining the namespace and path

    // Creates a new BlockItem with the id "thaumcraft:example_block", combining the namespace and path


    public Thaumcraft() {

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onInterModEnqueue);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so blocks get registered
//        BlockInit.register();
        // Register the Deferred Register to the mod event bus so items get registered
        ItemInit.register();
        CreativeTabInit.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }

    private void onInterModEnqueue(InterModEnqueueEvent event) {
        try {
            // Initialize the config file
            System.out.println("Test before init items");
//            ConfigAspects.initAspects();
        } catch (Exception e) {
            // Handle any exceptions that occur during initialization
            LOGGER.error("Error initializing config file:", e);
        }

        // Here you can modify the ItemStack or perform any necessary setup before Minecraft starts
        // For example:
        // ItemStack itemStack = new ItemStack(Items.DIAMOND);
        // itemStack.addEnchantment(Enchantments.SHARPNESS, 5);
        // ...
    }
}
