package primalcat.thaumcraft.events;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import primalcat.thaumcraft.Thaumcraft;
import primalcat.thaumcraft.aspects.ScanHelper;
import primalcat.thaumcraft.init.ItemInit;
import primalcat.thaumcraft.utilites.DoScan;
import primalcat.thaumcraft.utilites.DrawScanProgress;


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
    private static ScanHelper scanHelper = new ScanHelper();
    //70 w
    private static final int INVENTORY_PLAYER_X = 26;
    private static final int INVENTORY_PLAYER_Y = 8;
    private static final int INVENTORY_PLAYER_WIDTH = 52;
    private static final int INVENTORY_PLAYER_HEIGHT = 70;

    private static DrawScanProgress drawScanProgress = new DrawScanProgress();


    /** Ticking event
     * for do scan **/
    @SubscribeEvent
    public static void onClientTick(final TickEvent.ClientTickEvent event) {
        if (!event.isCanceled() && event.phase == TickEvent.Phase.END ) {
            Player player = Minecraft.getInstance().player;
            if (player != null && isHoldingThaumometer(player) && drawScreenEvent != null) {
                tempSlot = ((AbstractContainerScreen<?>) drawScreenEvent.getScreen()).getSlotUnderMouse();
                if (tempSlot != null && !tempSlot.getItem().getItem().equals(Items.AIR) && scanHelper.isCanDoScan()) {
                    scanHelper.doInventoryScan(ticksHovered, player, tempSlot.getItem().getItem().toString());
                    drawScanProgress.setCanDraw(true);
                    ticksHovered++;
                    lastScannedSlot = tempSlot;

                } else if (isHoveringPlayer(drawScreenEvent.getScreen(), drawScreenEvent.getMouseX(), drawScreenEvent.getMouseY()) && scanHelper.isCanDoScan()) {
                    scanHelper.doInventoryScan(ticksHovered, player, player.getStringUUID());
                    drawScanProgress.setCanDraw(true);
                    ticksHovered++;
                } else if (lastScannedSlot != tempSlot) {
                    scanHelper.setCanDoScan(true);
                    ticksHovered = 0;
                    drawScanProgress.setCanDraw(false);
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onInventorySlotInteract(final ScreenEvent.Render.Post event) {
        if(event.getScreen() instanceof InventoryScreen || event.getScreen() instanceof AbstractContainerScreen<?> || event.getScreen() instanceof ContainerScreen || event.getScreen() instanceof CreativeModeInventoryScreen){
            drawScreenEvent = event;
            if((isHoldingThaumometer(Minecraft.getInstance().player) || tempSlot != null) && !scanHelper.isCanDoScan()){
                System.out.println("is done");
                int color = 0xFF00FF00;  // ARGB color (green)
                RenderSystem.setShaderColor((float)((color >> 16) & 0xFF) / 255f, (float)((color >> 8) & 0xFF) / 255f, (float)(color & 0xFF) / 255f, (float)((color >> 24) & 0xFF) / 255f);
                drawScreenEvent.getPoseStack().pushPose();
                drawScreenEvent.getPoseStack().last();
                drawScreenEvent.getPoseStack().translate(0,0,301f);
                RenderSystem.setShaderTexture(0, new ResourceLocation(Thaumcraft.MOD_ID, "textures/aspects/aer.png"));
                GuiComponent.blit(drawScreenEvent.getPoseStack(), drawScreenEvent.getMouseX() + 10, drawScreenEvent.getMouseY() - 20, 0, 0, 0, 16, 16, 16, 16);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                drawScreenEvent.getPoseStack().popPose();
            }
            if(Minecraft.getInstance().player != null && (isHoldingThaumometer(Minecraft.getInstance().player) || tempSlot != null) && scanHelper.isCanDoScan()){
                drawScanProgress.renderScanningProgress(drawScreenEvent.getPoseStack(), drawScreenEvent.getMouseX(), drawScreenEvent.getMouseY(), ticksHovered / (float) SCAN_TICK);
//                Minecraft.getInstance().font.drawShadow(drawScreenEvent.getPoseStack(),"test", drawScreenEvent.getMouseX() , drawScreenEvent.getMouseY(), new Color(250,0,0).getRGB());
            }

        }
//                ContainerScreen containerScreen = (ContainerScreen)event.getScreen();
    }
    private static boolean isHoveringPlayer(Screen screen, int mouseX, int mouseY) {
        if (screen instanceof InventoryScreen) {
            // Check if the mouse is within the player's inventory area
//            return mouseX >= inventoryX && mouseX <= inventoryX + inventoryWidth &&
//                    mouseY >= inventoryY && mouseY <= inventoryY + inventoryHeight;
            return mouseY >= ((InventoryScreen) screen).getGuiTop() + INVENTORY_PLAYER_Y &&  mouseY <= ((InventoryScreen) screen).getGuiTop() + INVENTORY_PLAYER_Y + INVENTORY_PLAYER_HEIGHT &&
                    mouseX >= ((InventoryScreen) screen).getGuiLeft() + INVENTORY_PLAYER_X && mouseX <= ((InventoryScreen) screen).getGuiLeft() + INVENTORY_PLAYER_X + INVENTORY_PLAYER_WIDTH;
        }
        return false;
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
//    @SubscribeEvent(priority = EventPriority.HIGHEST)
//    public static void onInventorySlotInteract(final ScreenEvent.Render.Post event) {
//        if(!event.isCanceled()) {
//            Slot slot;
//            Player player;
//            // many checks for different screens types
//            if(event.getScreen() instanceof ContainerScreen){
//                ContainerScreen containerScreen = (ContainerScreen)event.getScreen();
//                slot = containerScreen.getSlotUnderMouse();
//                player = Minecraft.getInstance().player;
//            }else if(event.getScreen() instanceof InventoryScreen){
//                InventoryScreen inventoryScreen = (InventoryScreen)event.getScreen();
//                slot = inventoryScreen.getSlotUnderMouse();
//                player = Minecraft.getInstance().player;
//            }else if(event.getScreen() instanceof CreativeModeInventoryScreen){
//                CreativeModeInventoryScreen creativeModeInventoryScreen = (CreativeModeInventoryScreen)event.getScreen();
//                slot = creativeModeInventoryScreen.getSlotUnderMouse();
//                player = Minecraft.getInstance().player;
//            }else {
//                slot = null;
//                player = null;
//            }
//            // start draw process text
//            if(slot != null && slot.isActive() && player != null){
//                drawScreenEvent = event;
////                Minecraft.getInstance().font.drawShadow(drawScreenEvent.getPoseStack(),"test", 140-10 , 150-20, new Color(250,0,0).getRGB());
//                DrawText.setPoseStack(drawScreenEvent.getPoseStack());
//                dText.renderScanningProgress(drawScreenEvent.getPoseStack(), drawScreenEvent.getMouseX(), drawScreenEvent.getMouseY());
////                drawScreenEvent.getScreen().renderTooltip(drawScreenEvent.getPoseStack(), drawScreenEvent.getMouseX(), drawScreenEvent.getMouseY());
////                renderScanningProgress(drawScreenEvent.getPoseStack(), drawScreenEvent.getMouseX(), drawScreenEvent.getMouseY(), 0);
//
//            }
//
//        }
//    }



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
////        System.out.println(counter);
//
//    }
//
//
//    public static boolean isHoveringPlayer(Screen gui, int mouseX, int mouseY){
//        return true;
//        //return gui instanceof InventoryScreen && mouseX >= ((InventoryScreen) gui).getGuiLeft() + ((InventoryScreen) gui).getYSize() && mouseX < ((InventoryScreen) gui).getGuiLeft() + ((InventoryScreen) gui).getXSize() + gui.get && mouseY >= ((InventoryScreen) gui).getGuiTop() + INVENTORY_PLAYER_Y && mouseY < ((InventoryScreen) gui).getGuiTop() + INVENTORY_PLAYER_Y + INVENTORY_PLAYER_HEIGHT;
//    } /



}
