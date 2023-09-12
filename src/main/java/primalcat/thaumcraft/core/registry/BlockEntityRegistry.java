package primalcat.thaumcraft.core.registry;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import primalcat.thaumcraft.Thaumcraft;

public class BlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES= DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Thaumcraft.MODID);
//    public static final RegistryObject<BlockEntityType<NitorEntity>> nitor = BLOCK_ENTITIES.register("nitor", () -> BlockEntityType.Builder.of(NitorEntity).build(null));

    public static void register()
    {
        BLOCK_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
