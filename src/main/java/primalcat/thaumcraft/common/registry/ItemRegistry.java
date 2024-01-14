package primalcat.thaumcraft.common.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import primalcat.thaumcraft.Thaumcraft;
import primalcat.thaumcraft.common.items.thaumometer.Thaumometer;

public class ItemRegistry {

    public static Item THAUMONOMICON = registerItem("thaumonomcon", new Item(new FabricItemSettings()));
    public static Item THAUMOMETER = registerItem("thaumometer", new Thaumometer(new FabricItemSettings()));
    public static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, new Identifier(Thaumcraft.MODID, name), item);
    }
    public static void registry(){

    }
}
