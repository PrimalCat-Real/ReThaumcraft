package thaumcraft.thaumcraft.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.Tags;
import thaumcraft.thaumcraft.Thaumcraft;

public class ModTags {
    public static TagKey<Item> Test = regTag("aspects/air");
    public static TagKey<Item> regTag(String location){
        return ItemTags.create(new ResourceLocation("minecraft", location));
    }
}
