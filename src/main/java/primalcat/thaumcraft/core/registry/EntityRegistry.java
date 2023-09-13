package primalcat.thaumcraft.core.registry;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import primalcat.thaumcraft.Thaumcraft;
import primalcat.thaumcraft.common.entities.AspectOrb;

public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> THAUMCRAFT_ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Thaumcraft.MODID);

//    public static final RegistryObject<EntityType<AspectOrb>> ASPECT_ORB = THAUMCRAFT_ENTITIES.register("aspect_orb",
//            () -> EntityType.Builder.of(AspectOrb::new, MobCategory.MISC).sized(1.0f, 1.0f).build(Thaumcraft.MODID + ":aspect_orb"));
}
