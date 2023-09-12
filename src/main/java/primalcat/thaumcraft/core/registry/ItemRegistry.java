package primalcat.thaumcraft.core.registry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import primalcat.thaumcraft.common.item.test.AnimatedItem;
import primalcat.thaumcraft.common.item.tools.Thaumometer;
import primalcat.thaumcraft.Thaumcraft;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Thaumcraft.MODID);

    // items

    public static final RegistryObject<Item> THAUMOMETER = ITEMS.register("thaumometer", Thaumometer::new);

    // test item

    public static final RegistryObject<Item> ANIMATED_ITEM = ITEMS.register("animated_item",
            () -> new AnimatedItem(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));

    public static final RegistryObject<Item> obsidianObj = ITEMS.register("obsidian_obj", () -> new BlockItem(BlockRegistry.obsidanObj.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));


    public static void register()
    {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
