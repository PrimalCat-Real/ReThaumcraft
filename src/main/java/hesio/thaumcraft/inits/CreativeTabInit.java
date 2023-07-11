package hesio.thaumcraft.inits;

import hesio.thaumcraft.Thaumcraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;


//@Mod.EventBusSubscriber(modid = Thaumcraft.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class CreativeTabInit {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Thaumcraft.MODID);

//    public static final List<Supplier<? extends ItemLike>> EXAMPLE_TAB_ITEMS = new ArrayList<>();

    public static final RegistryObject<CreativeModeTab> THAUMCRAFT_TAB = CREATIVE_MODE_TABS.register("thaumcraft_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ItemInit.THAUMOMETER.get()))
                    .title(Component.translatable("itemGroup.thaumcraft_tab"))
                    .displayItems((displayParams, output) ->{
                        output.accept(ItemInit.THAUMOMETER.get());
                    })
                    .build()
    );


    public static void register(IEventBus event) {
        CREATIVE_MODE_TABS.register(event);
    }
}

//
//@Mod.EventBusSubscriber(modid = Thaumcraft.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
//public class CreativeTabInit {
//
//    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Thaumcraft.MODID);
//
//    public static final List<Supplier<? extends ItemLike>> EXAMPLE_TAB_ITEMS = new ArrayList<>();
//
//    public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB = TABS.register("example_tab",
//            () -> CreativeModeTab.builder()
//                    .title(Component.translatable("itemGroup.thaumcraft"))
//                    .icon(ItemInit.THAUMOMETER.get()::getDefaultInstance)
//                    .displayItems((displayParams, output) ->
//                            EXAMPLE_TAB_ITEMS.forEach(itemLike -> output.accept(itemLike.get())))
//                    .withSearchBar()
//                    .build()
//    );
//
//    public static <T extends Item> RegistryObject<T> addToTab(RegistryObject<T> itemLike) {
//        EXAMPLE_TAB_ITEMS.add(itemLike);
//        return itemLike;
//    }
//    @SubscribeEvent
//    public static void buildContents(BuildCreativeModeTabContentsEvent event) {
//        if(event.getTab() == EXAMPLE_TAB.get()) {
//            event.accept(Items.CROSSBOW);
//        }
//    }

//    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Thaumcraft.MODID);

//    public static final List<Supplier<? extends ItemLike>> THAUMCRAFT_TAB_ITEMS = new ArrayList<>();
//    public static RegistryObject<CreativeModeTab> THAUMCRAFT_TAB = TABS.register("thaumcraft_tab", () -> CreativeModeTab.builder()
//                    .title(Component.translatable("itemGroup.thaumcraft"))
//                    .icon(ItemInit.THAUMOMETER.get()::getDefaultInstance)
//                    .displayItems((displayParams, output) ->
//                            THAUMCRAFT_TAB_ITEMS.forEach(itemLike -> output.accept(itemLike.get())))
//                    .withSearchBar()
//                    .build()
//            );
//
//    public static <T extends Item> RegistryObject<T> addToTab(RegistryObject<T> itemLike) {
//        THAUMCRAFT_TAB_ITEMS.add(itemLike);
//        return itemLike;
//    }
//
//    @SubscribeEvent
//    public static void buildContents(BuildCreativeModeTabContentsEvent event) {
//        if(event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
//            event.getEntries().putAfter(Items.ACACIA_LOG.getDefaultInstance(), ItemInit.obsidianObj.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
//        }
//
//        if(event.getTab() == THAUMCRAFT_TAB.get()) {
//            event.accept(Items.CROSSBOW);
//        }
//    }
//}
