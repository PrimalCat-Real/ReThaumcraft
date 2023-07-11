package hesio.thaumcraft.inits;

import hesio.thaumcraft.Thaumcraft;
import hesio.thaumcraft.common.Items.tools.Thaumometer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Thaumcraft.MODID);

    public static final RegistryObject<Item> THAUMOMETER = ITEMS.register("thaumometer", Thaumometer::new);
//    public static final RegistryObject<Item> obsidianObj = ITEMS.register("obsidian_obj", () -> new BlockItem(BlockInit.obsidanObj.get(), new Item.Properties()));


    public static void register()
    {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
