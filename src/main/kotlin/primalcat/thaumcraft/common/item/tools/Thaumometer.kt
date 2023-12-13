package primalcat.thaumcraft.common.item.tools

import net.minecraft.world.item.Rarity
import primalcat.thaumcraft.common.item.ItemBase

class Thaumometer : ItemBase(
    props = Properties()
        .stacksTo(1)
        .rarity(Rarity.UNCOMMON)
) {
}