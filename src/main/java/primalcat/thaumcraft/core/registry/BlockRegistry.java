package primalcat.thaumcraft.core.registry;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import primalcat.thaumcraft.common.block.ObsidianOBJ;
import primalcat.thaumcraft.Thaumcraft;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Thaumcraft.MODID);

//    public static final RegistryObject<Block> SOUL_VIAL = BLOCKS.register("soul_vial", () -> new Nitor<>(MalumBlockProperties.SOUL_VIAL().setCutoutRenderType().noOcclusion()).setBlockEntity(BlockEntityRegistry.SOUL_VIAL));
    public static final RegistryObject<Block> obsidanObj = BLOCKS.register("obsidian_obj", ObsidianOBJ::new);

    public static void register()
    {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
