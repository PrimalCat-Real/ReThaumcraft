package thaumcraft.thaumcraft.events;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import thaumcraft.thaumcraft.Thaumcraft;
import thaumcraft.thaumcraft.init.ItemInit;
import thaumcraft.thaumcraft.sound.ModSounds;
import thaumcraft.thaumcraft.utilites.DoScan;
import thaumcraft.thaumcraft.utilites.DrawText;

import java.awt.*;


@Mod.EventBusSubscriber(modid = Thaumcraft.MOD_ID, value = Dist.CLIENT)
public class ThaumometerEvent {
    private static Slot tempSlot = null;
    private static ScreenEvent.Render.Post drawScreenEvent = null;
    private static final int SCAN_TICK = 40;
    private static final int SOUND_TICKS = 3;
    private static int ticksHovered;
    private static DoScan currentScan;
    private static Slot lastScannedSlot;
    private static boolean canScan;
    private static DrawText dText = new DrawText();
    /** Ticking event
     * for do scan **/
    @SubscribeEvent
    public static void onClientTick(final TickEvent.ClientTickEvent event) {

        Player player = Minecraft.getInstance().player;
        if(isHoldingThaumometer(player)){
            /** Get working slot,
             * empty slot,
             * null slot**/
            tempSlot = ((AbstractContainerScreen<?>) drawScreenEvent.getScreen()).getSlotUnderMouse();
            if(tempSlot != null && !tempSlot.getItem().getItem().equals(Items.AIR)){
                if(lastScannedSlot != tempSlot){
                    dText.setStartDraw(true);
                    // custom tick for tick logic
                    ticksHovered ++;
                    // set progress for dot animation in Scaning text
                    dText.setProgress(ticksHovered / (float) SCAN_TICK);
                    // play sound event while scan
                    if(ticksHovered > SOUND_TICKS && ticksHovered % 4 == 0){
                        player.level.playSound(player,player.getX(), player.getY(), player.getZ(), ModSounds.cameraticks.get(), SoundSource.NEUTRAL, 0.2f,0.45f + player.level.random.nextFloat() * 0.1f);
                    }
                    // scan 40 tick or 2 second
                    if(ticksHovered > SCAN_TICK){
                        Item scanItem = tempSlot.getItem().getItem();
                        lastScannedSlot = tempSlot;
                        // stop render scan process text
                        dText.setStartDraw(false);
                    }
                }else{
                    // reset scan progress
                    ticksHovered = 0;
                    dText.setProgress(0);
                    dText.setStartDraw(false);
                }
            }else if(tempSlot == null || tempSlot.getItem().getItem().equals(Items.AIR)){
                lastScannedSlot = null;
                dText.setProgress(0);
                dText.setStartDraw(false);
                ticksHovered = 0;
                // reset scan progress and draw process text
            }
        }else if(!isHoldingThaumometer(player)){
            // reset scan progress and draw process text when not hold thaumometer
            ticksHovered = 0;
            currentScan = null;
            canScan = true;
            dText.setStartDraw(false);
            dText.setProgress(0);
        }

    }





    /** Check if player hold theumometer
     * by cursor in inventory **/
    public static boolean isHoldingThaumometer(Player player) {
        if (player == null) {
            return false;
        }
        // get player item in mouse
        ItemStack mouseItem = player.containerMenu.getCarried();
        return !mouseItem.isEmpty() && mouseItem.getItem().equals(ItemInit.THAUMOMETER.get());
    }

    /** Get ScreenEvent for
     * get item wich hovered by mouse
     * and set this to temp slot
     **/
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onInventorySlotInteract(final ScreenEvent.Render.Post event) {
        if(!event.isCanceled()) {
            Slot slot;
            Player player;
            // many checks for different screens types
            if(event.getScreen() instanceof ContainerScreen){
                ContainerScreen containerScreen = (ContainerScreen)event.getScreen();
                slot = containerScreen.getSlotUnderMouse();
                player = Minecraft.getInstance().player;
            }else if(event.getScreen() instanceof InventoryScreen){
                InventoryScreen inventoryScreen = (InventoryScreen)event.getScreen();
                slot = inventoryScreen.getSlotUnderMouse();
                player = Minecraft.getInstance().player;
            }else if(event.getScreen() instanceof CreativeModeInventoryScreen){
                CreativeModeInventoryScreen creativeModeInventoryScreen = (CreativeModeInventoryScreen)event.getScreen();
                slot = creativeModeInventoryScreen.getSlotUnderMouse();
                player = Minecraft.getInstance().player;
            }else {
                slot = null;
                player = null;
            }
            // start draw process text
            if(slot != null && slot.isActive() && player != null){
                drawScreenEvent = event;
//                Minecraft.getInstance().font.drawShadow(drawScreenEvent.getPoseStack(),"test", 140-10 , 150-20, new Color(250,0,0).getRGB());
                DrawText.setPoseStack(drawScreenEvent.getPoseStack());
                dText.renderScanningProgress(drawScreenEvent.getPoseStack(), drawScreenEvent.getMouseX(), drawScreenEvent.getMouseY());
//                drawScreenEvent.getScreen().renderTooltip(drawScreenEvent.getPoseStack(), drawScreenEvent.getMouseX(), drawScreenEvent.getMouseY());
//                renderScanningProgress(drawScreenEvent.getPoseStack(), drawScreenEvent.getMouseX(), drawScreenEvent.getMouseY(), 0);

            }

        }
    }

    // Event for render player hand as crossbow in third person camera
    @SubscribeEvent
    public static void onPlayerRenderPre(RenderPlayerEvent.Pre event) {
        Player player = (Player) event.getEntity();
        PlayerModel<?> model = event.getRenderer().getModel();

        // Check if the player is holding THAUMOMETER item in either hand
        boolean holdingYourItem = player.isHolding(ItemInit.THAUMOMETER.get());

        // Change the left arm pose based on whether the player is holding THAUMOMETER
        model.leftArmPose = holdingYourItem ? HumanoidModel.ArmPose.BOW_AND_ARROW : HumanoidModel.ArmPose.EMPTY;
    }
    /** ивент посути для проверки на ховер предметов,
     * Должно показывать елементы предмета на шифте.
     **/
//    @SubscribeEvent
//    public static void itemToolTip(ItemTooltipEvent event) {
//        System.out.println("hover item");
//        System.out.println(counter);
//
//    }
//
//
//    public static boolean isHoveringPlayer(Screen gui, int mouseX, int mouseY){
//        return true;
//        //return gui instanceof InventoryScreen && mouseX >= ((InventoryScreen) gui).getGuiLeft() + ((InventoryScreen) gui).getYSize() && mouseX < ((InventoryScreen) gui).getGuiLeft() + ((InventoryScreen) gui).getXSize() + gui.get && mouseY >= ((InventoryScreen) gui).getGuiTop() + INVENTORY_PLAYER_Y && mouseY < ((InventoryScreen) gui).getGuiTop() + INVENTORY_PLAYER_Y + INVENTORY_PLAYER_HEIGHT;
//    } /
}
