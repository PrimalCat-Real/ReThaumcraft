package primalcat.thaumcraft.common.item.misc

import net.minecraft.world.item.Rarity
import primalcat.thaumcraft.common.item.ItemBase

class Brain : ItemBase(
    props = Properties()
        .stacksTo(1)
        .rarity(Rarity.UNCOMMON)
) {
}