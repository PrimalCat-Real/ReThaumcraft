package primalcat.thaumcraft.mixins;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;


@Mixin(ItemStack.class)
public class MixinItemStack {

    public MixinItemStack(){
    }

    @Inject(method = "<init>(Lnet/minecraft/world/level/ItemLike;)V", at = @At("RETURN"))
    private void modifyNBT(ItemLike itemLike, CallbackInfo ci) throws IOException {
        // Check if aspects exist for the specific item
//        if (itemLike != null && AspectInit.getItemAspects() != null) {
////            System.out.println(ConfigAspects.getReadConfig().toString());
//            LinkedHashMap<String, AspectList> aspectsItems = AspectInit.getItemAspects();
//            Item actualItem = itemLike.asItem();
//            if(aspectsItems != null && aspectsItems.containsKey(actualItem.toString())){
//                System.out.println("Containe aspects");
//                System.out.println(aspectsItems.get(actualItem.toString()));
//            }
//
//        }
//            Item actualItem = itemLike.asItem();
//            System.out.println(actualItem.toString());
//            Map<String, AspectList> aspectItems = ConfigAspects.getReadConfig().get("items");
//            if(aspectItems != null && aspectItems.containsKey(actualItem.toString())){
//                ListTag list = new ListTag();
//                LinkedHashMap<Aspect, Integer> aspects = aspectItems.get(actualItem.toString()).aspects;
//                aspects.forEach((aspect, amount) -> {
//                    CompoundTag aspectTag = new CompoundTag();
//                    aspectTag.putInt(aspect.getName(), amount);
//                    list.add(aspectTag);
//                });
//                CompoundTag nbt = new CompoundTag();
//                nbt.put("aspects", list);
//                ((ItemStack)(Object)this).setTag(nbt);
//            }
//        }
    }
}
