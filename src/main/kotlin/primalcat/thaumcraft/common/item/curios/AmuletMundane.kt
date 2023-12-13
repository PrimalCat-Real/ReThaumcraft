package primalcat.thaumcraft.common.item.curios

import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Rarity
import primalcat.thaumcraft.common.item.ItemBase
import top.theillusivec4.curios.api.SlotContext
import top.theillusivec4.curios.api.type.capability.ICurioItem

class AmuletMundane : ItemBase(
    props = Properties()
        .rarity(Rarity.UNCOMMON)
), ICurioItem {
    override fun canEquipFromUse(slotContext: SlotContext, stack: ItemStack): Boolean {
        return true
    }
}