package hesio.thaumcraft.mixins;


import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public class MixinItemStack {
    @Inject(method = "<init>(Lnet/minecraft/world/level/ItemLike;)V", at = @At("RETURN"))
    private void modifyNBT(ItemLike p_41599_, CallbackInfo ci) {
        if (p_41599_ != null) {
            Item actualItem = p_41599_.asItem();
            String itemId = Registries.ITEM.toString();

            System.out.println(itemId);
        }

        ListTag list = new ListTag();

        // Create the first CompoundTag object
        CompoundTag compound1 = new CompoundTag();
        compound1.putInt("ordo", 20);
        list.add(compound1);

        // Create the second CompoundTag object
        CompoundTag compound2 = new CompoundTag();
        compound2.putInt("terra", 2);
        list.add(compound2);

        CompoundTag nbt = new CompoundTag();
        nbt.put("aspects", list);
        ((ItemStack) (Object) this).setTag(nbt);
    }
}
