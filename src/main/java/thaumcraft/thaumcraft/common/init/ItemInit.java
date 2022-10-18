package thaumcraft.thaumcraft.common.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import thaumcraft.thaumcraft.Thaumcraft;
import thaumcraft.thaumcraft.common.items.tools.Thaumometer;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Thaumcraft.MODID);

    public static final RegistryObject<Item> THAUMOMETER = ITEMS.register("thaumometer", Thaumometer::new);

    public static void register()
    {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static class Tags {
        public static final TagKey<Block> NEEDS_EXAMPLE_TOOL = create("aspects/air");

        private static TagKey<Block> create(String location) {
            return BlockTags.create(new ResourceLocation(Thaumcraft.MODID, location));
        }
    }


}
