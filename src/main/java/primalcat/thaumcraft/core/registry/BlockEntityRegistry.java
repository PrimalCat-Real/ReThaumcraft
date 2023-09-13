package primalcat.thaumcraft.core.registry;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import primalcat.thaumcraft.Thaumcraft;
import primalcat.thaumcraft.common.block.light.NitorEntity;

public class BlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES= DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Thaumcraft.MODID);

    public static final RegistryObject<BlockEntityType<NitorEntity>> NITOR = BLOCK_ENTITY_TYPES.register("nitor", () -> BlockEntityType.Builder.of(NitorEntity::new, BlockRegistry.NITOR.get()).build(null));
//    public static final RegistryObject<BlockEntityType<NitorEntity>> NITOR = BLOCK_ENTITIES.register("nitor", () -> BlockEntityType.Builder.of(NitorEntity).build(null));

    public static void register(IEventBus bus)
    {
        BLOCK_ENTITY_TYPES.register(bus);
    }
}
