package primalcat.thaumcraft.init;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import primalcat.thaumcraft.common.items.test.AnimatedItem;
import primalcat.thaumcraft.common.items.tools.Thaumometer;
import primalcat.thaumcraft.Thaumcraft;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Thaumcraft.MOD_ID);

    public static final RegistryObject<Item> THAUMOMETER = ITEMS.register("thaumometer", Thaumometer::new);

//    public static final RegistryObject<Item> TESTITEM = ITEMS.register("rendertest", AnimatedItem::new);

    public static final RegistryObject<Item> ANIMATED_ITEM = ITEMS.register("animated_item",
            () -> new AnimatedItem(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> obsidianObj = ITEMS.register("obsidian_obj", () -> new BlockItem(BlockInit.obsidanObj.get(), new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS)));


    public static void register()
    {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
