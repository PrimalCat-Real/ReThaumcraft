package primalcat.thaumcraft.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import primalcat.thaumcraft.config.ConfigAspects;
import primalcat.thaumcraft.init.AspectInit;

import javax.swing.text.html.parser.Entity;
import java.util.LinkedHashMap;
import java.util.Map;

public class AspectHelper {

    public static AspectList getObjectAspects(ItemStack itemStack) {
//        itemStack.getTags().toList();
//        String tagName = "forge:ores/emerald";
//        TagKey<Item> testTag = ItemTags.create(new ResourceLocation(tagName));
        AspectList objectAspects = new AspectList();
        LinkedHashMap<String, AspectList> itemsAspects = AspectInit.getItemAspects();
        System.out.println(itemsAspects.containsValue(itemStack.getItem().toString()));

        if(itemsAspects.get(itemStack.getItem().toString()) != null){
//            LinkedHashMap<Aspect, Integer> tempAspects = AspectInit.getItemAspects().get(itemStack.toString()).aspects;
            LinkedHashMap<Aspect, Integer> tempAspects = AspectInit.getItemAspects().get(itemStack.getItem().toString()).aspects;
            for (Aspect aspect : tempAspects.keySet()) {
                int amount = tempAspects.get(aspect);
                objectAspects.add(aspect, amount);
            }
        }else{
            for (var tag: itemStack.getTags().toList()) {
//                System.out.println(tag.location().toString().equals("forge:ores/emerald"));
                if(AspectInit.getItemAspects().containsKey(tag.location().toString())){
                    LinkedHashMap<Aspect, Integer> tempAspects = AspectInit.getItemAspects().get(tag.location().toString()).aspects;
                    for (Aspect aspect : tempAspects.keySet()) {
                        int amount = tempAspects.get(aspect);
                        objectAspects.add(aspect, amount);
                    }
                    System.out.println(AspectInit.getItemAspects().get(tag.location().toString()).aspects);
                }

            }
        }

        return objectAspects;
    }
    public static AspectList getEntityAspects(Entity entity) {
        return null;
    }
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
