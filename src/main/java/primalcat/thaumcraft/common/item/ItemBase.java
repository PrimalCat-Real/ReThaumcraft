package primalcat.thaumcraft.common.item;


import net.minecraft.world.item.Item;
import primalcat.thaumcraft.Thaumcraft;

public abstract class ItemBase extends Item {
    public ItemBase(Properties props) {
        super(props.tab(Thaumcraft.THAUMCRAFT_TAB));
//        setCreativeTab(CreativeModeTab.TAB_BREWING);
    }
}
