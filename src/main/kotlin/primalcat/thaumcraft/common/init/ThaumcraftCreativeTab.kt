package primalcat.thaumcraft.common.init

import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent
import net.neoforged.neoforge.registries.DeferredRegister
import primalcat.thaumcraft.Thaumcraft
import thedarkcolour.kotlinforforge.neoforge.forge.getValue
import java.util.function.Supplier

object ThaumcraftCreativeTab {
    val REGISTRY: DeferredRegister<CreativeModeTab> = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB,Thaumcraft.MODID)

//    val INSTANCE by REGISTRY.register("thaumcraft") { key ->
//        object : CreativeModeTab(
//            CreativeModeTab.builder()
//                .title(
//                    Component.translatable("itemGroup.thaumcraft.tab")
//                )
//                .icon { ItemStack(ThaumcraftItems.AMULET_FANCY) }
//
//        ) {
//
//        }
//    }

    // Создаем объект Supplier для регистрации внутри DeferredRegister
    private val ThaumcraftCreativeTabSupplier: Supplier<CreativeModeTab> = Supplier {
        CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.thaumcraft.tab"))
            .icon { ItemStack(ThaumcraftItems.BOTTLE_TAINT) }
            .build()
    }

    private val INSTANCE: CreativeModeTab by REGISTRY.register("thaumcraft", ThaumcraftCreativeTabSupplier)

    open fun addToCreativeTab(event: BuildCreativeModeTabContentsEvent) {
        if (event.tab == ThaumcraftCreativeTab.INSTANCE) {

            // clusters
            event.accept(ThaumcraftItems.CLUSTER_GOLD)
            event.accept(ThaumcraftItems.CLUSTER_IRON)
            event.accept(ThaumcraftItems.CLUSTER_COPPER)
            event.accept(ThaumcraftItems.CLUSTER_LEAD)
            event.accept(ThaumcraftItems.CLUSTER_QUARTZ)
            event.accept(ThaumcraftItems.CLUSTER_SILVER)
            event.accept(ThaumcraftItems.CLUSTER_TIN)

            event.accept(ThaumcraftItems.BOTTLE_TAINT)
        }
    }
}