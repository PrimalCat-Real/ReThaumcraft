package thaumcraft.thaumcraft.common.items.tools;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import thaumcraft.thaumcraft.common.items.ItemBase;
import thaumcraft.thaumcraft.init.ItemInit;
import net.minecraftforge.client.event.DrawSelectionEvent;

public class Thaumometer extends ItemBase {
    public Thaumometer() {

        super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON));

    }


    @Override
    public InteractionResult useOn(UseOnContext context){
        Player player = context.getPlayer();

        player.sendMessage(new TextComponent(Screen.hasShiftDown() + " Test " + isHoldingThaumometer(player)), player.getUUID());
        System.out.println("work2");
        return super.useOn(context);
    }
    private boolean isHoldingThaumometer(Player player) {
        if (player == null) {
            return false;
        }

        ItemStack mouseItem = player.getMainHandItem();

        return !mouseItem.isEmpty() && mouseItem.getItem() == this;

    }



}
