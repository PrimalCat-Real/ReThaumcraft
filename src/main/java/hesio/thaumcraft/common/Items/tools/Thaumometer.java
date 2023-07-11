package hesio.thaumcraft.common.Items.tools;

import hesio.thaumcraft.common.Items.ItemBase;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class Thaumometer extends ItemBase {
    public Thaumometer() {
        super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
    }
}
