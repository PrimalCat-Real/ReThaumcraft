package thaumcraft.thaumcraft.init;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import thaumcraft.thaumcraft.Thaumcraft;
import thaumcraft.thaumcraft.common.blocks.ObsidianOBJ;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Thaumcraft.MOD_ID);
    public static final RegistryObject<Block> obsidanObj = BLOCKS.register("obsidian_obj", ObsidianOBJ::new);

    public static void register()
    {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
