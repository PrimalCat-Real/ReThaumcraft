package primalcat.thaumcraft.aspects;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import primalcat.thaumcraft.init.AspectInit;

import java.util.LinkedHashMap;

public class AspectHelper {

    public static AspectList getAspectFromObject(ItemStack object){
        return getAspectsFromItem(object);
    }
    public static AspectList getAspectFromObject(Player object){
        return getAspectsFromPlayer(object);
    }
    public static AspectList getAspectFromObject(BlockState object){
        return getAspectsFromBlock(object);
    }
    public static AspectList getAspectFromObject(LivingEntity object){
        return getAspectsFromEntity(object);
    }
    public static AspectList getAspectsFromItem(ItemStack itemStack) {
//        itemStack.getTags().toList();
//        String tagName = "forge:ores/emerald";
//        TagKey<Item> testTag = ItemTags.create(new ResourceLocation(tagName));
        AspectList objectAspects = new AspectList();
        LinkedHashMap<String, AspectList> itemsAspects = AspectInit.getItemAspects();
        System.out.println(itemsAspects.containsValue(itemStack.getItem().toString()));

        if(itemsAspects.get(itemStack.getItem().toString()) != null){
            LinkedHashMap<Aspect, Integer> tempAspects = AspectInit.getItemAspects().get(itemStack.getItem().toString()).aspects;
            addAspectsFromTarget(objectAspects, tempAspects);
        }else{
            for (var tag: itemStack.getTags().toList()) {
                if(AspectInit.getItemAspects().containsKey(tag.location().toString())){
                    LinkedHashMap<Aspect, Integer> tempAspects = AspectInit.getItemAspects().get(tag.location().toString()).aspects;
                    addAspectsFromTarget(objectAspects, tempAspects);
                    System.out.println(AspectInit.getItemAspects().get(tag.location().toString()).aspects);
                }

            }
        }

        return objectAspects;
    }

    public static AspectList getAspectsFromPlayer(Player player){
        AspectList objectAspects = new AspectList();
        LinkedHashMap<String, AspectList> entityAspects = AspectInit.getEntityAspects();
        objectAspects.add(AspectInit.MAN, 4);
        return objectAspects;
    }
    public static AspectList getAspectsFromEntity(LivingEntity entity) {
        AspectList objectAspects = new AspectList();
        String localizedName = entity.getDisplayName().getString();
        localizedName = I18n.get(localizedName); // To remove formatting
        LinkedHashMap<String, AspectList> entityAspects = AspectInit.getEntityAspects();
        if(entityAspects.get(localizedName) != null){
            LinkedHashMap<Aspect, Integer> tempAspects = AspectInit.getEntityAspects().get(localizedName).aspects;
            addAspectsFromTarget(objectAspects, tempAspects);
        }
        System.out.println(localizedName);
        return objectAspects;
    }


    public static AspectList getAspectsFromBlock(BlockState blockState) {
        AspectList objectAspects = new AspectList();
        FluidState fluidState = blockState.getFluidState();
        String localizedName;
        if (fluidState.getType() != Fluids.EMPTY) {
            System.out.println();
            localizedName =  fluidState.getFluidType().toString();
        }else{
            Item item = Item.byBlock(blockState.getBlock());
            localizedName = item.toString();
        }

//        System.out.println(blockState.getBlock());
        LinkedHashMap<String, AspectList> itemsAspects = AspectInit.getItemAspects();
        if(itemsAspects.get(localizedName) != null){
//            LinkedHashMap<Aspect, Integer> tempAspects = AspectInit.getItemAspects().get(itemStack.toString()).aspects;
            LinkedHashMap<Aspect, Integer> tempAspects = AspectInit.getItemAspects().get(localizedName).aspects;
//            for (Aspect aspect : tempAspects.keySet()) {
//                int amount = tempAspects.get(aspect);
//                objectAspects.add(aspect, amount);
//            }
            addAspectsFromTarget(objectAspects, tempAspects);
        }else{
            for (var tag: blockState.getTags().toList()) {
                if(AspectInit.getItemAspects().containsKey(tag.location().toString())){
                    LinkedHashMap<Aspect, Integer> tempAspects = AspectInit.getItemAspects().get(tag.location().toString()).aspects;
                    addAspectsFromTarget(objectAspects, tempAspects);
                    System.out.println(AspectInit.getItemAspects().get(tag.location().toString()).aspects);
                }

            }
        }
        return objectAspects;
    }


    private static void addAspectsFromTarget(AspectList objectAspects, LinkedHashMap<Aspect, Integer> aspects) {
        for (Aspect aspect : aspects.keySet()) {
            int amount = aspects.get(aspect);
            objectAspects.add(aspect, amount);
        }
    }
    public static String getTarget(Entity entity){
        String localizedName = entity.getDisplayName().getString();
        localizedName = I18n.get(localizedName); // To remove formatting
        return localizedName;
    }

    public static String getTarget(ItemStack itemStack){
        return itemStack.getItem().toString();
    }
    public static String getTarget(BlockState blockState){
        FluidState fluidState = blockState.getFluidState();
        String localizedName;
        if (fluidState.getType() != Fluids.EMPTY) {
            System.out.println();
            localizedName =  fluidState.getFluidType().toString();
        }else{
            Item item = Item.byBlock(blockState.getBlock());
            localizedName = item.toString();
        }
        return localizedName;
    }
}
