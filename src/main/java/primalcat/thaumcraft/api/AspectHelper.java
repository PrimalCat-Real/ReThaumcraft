package primalcat.thaumcraft.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemStack;
import primalcat.thaumcraft.config.ConfigAspects;

import java.util.LinkedHashMap;
import java.util.Map;

public class AspectHelper {

//    private static Map<String, AspectList> aspectItems = ConfigAspects.getReadConfig().get("items");
//    public static AspectList getObjectAspects(ItemStack itemStack) {
//        LinkedHashMap<Aspect, Integer> aspects = aspectItems.get(itemStack.getItem().toString()).aspects;
//        System.out.println(aspectItems);
////        CompoundTag nbt = is.getTag();
////        if (nbt != null && nbt.contains("aspects")) {
////            ListTag list = nbt.getList("aspects", 10); // 10 is the tag type for CompoundTag
////
////            AspectList aspectList = new AspectList();
////            for (int i = 0; i < list.size(); i++) {
////                CompoundTag compound = list.getCompound(i);
////                for (String key : compound.getAllKeys()) {
////                    int value = compound.getInt(key);
//////                    System.out.println(key);
//////                    System.out.println(value);
//////                    System.out.println(Aspect.getAspect(key).getName());
//////                    System.out.println(Aspect.getAspect(key).getColor());
////                    aspectList.add(Aspect.getAspect(key), value);
////                }
////            }
////
////            return aspectList;
////        }
//
//        return null; // Return null if no aspects are found
//    }
}
