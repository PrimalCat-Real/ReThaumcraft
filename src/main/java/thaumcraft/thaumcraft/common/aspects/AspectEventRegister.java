package thaumcraft.thaumcraft.common.aspects;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import thaumcraft.thaumcraft.common.aspects.Aspect;
import thaumcraft.thaumcraft.common.aspects.AspectList;

import javax.annotation.Nullable;
import java.util.HashMap;


public class AspectEventRegister {
    public static HashMap<Item, AspectList> aspectsItems = new HashMap<Item, AspectList>();

    public AspectEventRegister() {
        aspectsItems.put(Items.COBBLESTONE,new AspectList(Aspect.EARTH, 8));
    }

    public static HashMap<Item, AspectList> getAspectsItems() {
        return aspectsItems;
    }
    public static AspectList getAspectsItem(@Nullable Item item) {
        return aspectsItems.get(item);
    }
}
