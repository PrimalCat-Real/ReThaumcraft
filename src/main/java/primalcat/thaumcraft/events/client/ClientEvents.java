package primalcat.thaumcraft.events.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.AirItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import primalcat.thaumcraft.Thaumcraft;
import primalcat.thaumcraft.aspects.Aspect;
import primalcat.thaumcraft.aspects.AspectList;
import primalcat.thaumcraft.client.ScanManager;
import primalcat.thaumcraft.config.ThaumcraftClientConfig;
import primalcat.thaumcraft.init.ItemInit;

@Mod.EventBusSubscriber(modid = Thaumcraft.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {
    private static Slot tempSlot = null;

    private static Slot lastScannedSlot;
    private static ScreenEvent.Render.Post drawScreenEvent = null;

    @SubscribeEvent
    public void onTooltipPostText(RenderTooltipEvent event) {
        System.out.println("Render tooltip");
    }
//    @SubscribeEvent
//    public void onTooltipPostText(RenderTooltipEvent.PostText event) {
//        if (isHoldingThaumometer() && !GuiScreen.isShiftKeyDown()) {
//            Minecraft mc = Minecraft.getMinecraft();
//            if (mc.currentScreen instanceof GuiContainer && !ScanningManager.isThingStillScannable(mc.player, event.getStack())) {
//                renderAspectsInGui((GuiContainer) mc.currentScreen, mc.player, event.getStack(), 0, event.getX(), event.getY());
//            }
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
    @SubscribeEvent
    public static void onClientTick(final TickEvent.ClientTickEvent event) {
        if (!event.isCanceled() && event.phase == TickEvent.Phase.END ) {
            Player player = Minecraft.getInstance().player;
            if (player != null && ScanManager.isMouseHoldingThaumometer(player) && drawScreenEvent != null) {
                tempSlot = ((AbstractContainerScreen<?>) drawScreenEvent.getScreen()).getSlotUnderMouse();
                if (tempSlot != null && !tempSlot.getItem().getItem().equals(Items.AIR)) {
                    lastScannedSlot = tempSlot;
//                    System.out.println(tempSlot.getItem().getItem().toString());
                    ScanManager.doScan(player,tempSlot.getItem().getItem().toString(), ScanManager.getAspectFromObject(tempSlot));
//                    drawScanProgress.setCanDraw(true);
//                    ticksHovered++;
                    lastScannedSlot = tempSlot;

                    //ad eligal check
                } else if (ScanManager.isHoveringPlayer(drawScreenEvent.getScreen(), drawScreenEvent.getMouseX(), drawScreenEvent.getMouseY())) {
                    ScanManager.doScan(player,player.getUUID().toString(), ScanManager.getAspectFromObject(player));
                    lastScannedSlot = tempSlot;
//                    scanHelper.doInventoryScan(ticksHovered, player, player.getStringUUID());
//                    drawScanProgress.setCanDraw(true);
//                    ticksHovered++;
                } else if (lastScannedSlot != tempSlot) {
//                    scanHelper.setCanDoScan(true);
//                    ticksHovered = 0;
                    ScanManager.setIsScanning(false);
                    ScanManager.setHoverTick(0);
                    ScanManager.drawInvScanProgress.setCanDraw(false);
//                    drawScanProgress.setCanDraw(false);
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onInventorySlotInteract(final ScreenEvent.Render.Post event) {
        if(event.getScreen() instanceof InventoryScreen || event.getScreen() instanceof AbstractContainerScreen<?> || event.getScreen() instanceof ContainerScreen || event.getScreen() instanceof CreativeModeInventoryScreen){
            drawScreenEvent = event;
            if(ScanManager.isScanning()){
                ScanManager.drawInvScanProgress.renderScanningProgress(drawScreenEvent.getPoseStack(), drawScreenEvent.getMouseX(), drawScreenEvent.getMouseY(), ScanManager.getHoverTick() / (float) ThaumcraftClientConfig.THAUMOMETER_SCAN_DURATION.get());
            } else if (ScanManager.isHoveringPlayer(event.getScreen(),event.getMouseX(),event.getMouseY())) {
                int color = 0xFF00FF00;  // ARGB color (green)
                RenderSystem.setShaderColor((float)((color >> 16) & 0xFF) / 255f, (float)((color >> 8) & 0xFF) / 255f, (float)(color & 0xFF) / 255f, (float)((color >> 24) & 0xFF) / 255f);
                drawScreenEvent.getPoseStack().pushPose();
                drawScreenEvent.getPoseStack().last();
                drawScreenEvent.getPoseStack().translate(0,0,301f);
                RenderSystem.setShaderTexture(0, new ResourceLocation(Thaumcraft.MOD_ID, "textures/aspects/aer.png"));
                GuiComponent.blit(drawScreenEvent.getPoseStack(), drawScreenEvent.getMouseX() + 10, drawScreenEvent.getMouseY() - 20, 0, 0, 0, 16, 16, 16, 16);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                drawScreenEvent.getPoseStack().popPose();
            } else if(tempSlot != null && ScanManager.isScannedObject(tempSlot.getItem().getItem().toString())){
                drawScreenEvent.getPoseStack().pushPose();
                drawScreenEvent.getPoseStack().last();
                drawScreenEvent.getPoseStack().translate(0,0,301f);
                Font font = Minecraft.getInstance().font;
                AspectList renderAspects = ScanManager.getAspectFromObject(tempSlot);
                int i = 1;
                int b = 1;
                for (Aspect aspect: renderAspects.aspects.keySet() ) {
                    int color = 0xFF00FF00;  // ARGB color (green)
                    RenderSystem.setShaderColor((float)((color >> 16) & 0xFF) / 255f, (float)((color >> 8) & 0xFF) / 255f, (float)(color & 0xFF) / 255f, (float)((color >> 24) & 0xFF) / 255f);
                    RenderSystem.setShaderTexture(0, new ResourceLocation(Thaumcraft.MOD_ID, "textures/aspects/back.png"));
                    GuiComponent.blit(drawScreenEvent.getPoseStack(), drawScreenEvent.getMouseX() + 16 * i, drawScreenEvent.getMouseY() - 20, 0, 0, 0, 16, 16, 16, 16);
                    int colorValue = aspect.getColor();  // Color value (decimal)
                    int alpha = (colorValue >> 24) & 0xFF;
                    int red = (colorValue >> 16) & 0xFF;
                    int green = (colorValue >> 8) & 0xFF;
                    int blue = colorValue & 0xFF;
                    float normalizedAlpha = (float) alpha / 255f;
                    float normalizedRed = (float) red / 255f;
                    float normalizedGreen = (float) green / 255f;
                    float normalizedBlue = (float) blue / 255f;
                    RenderSystem.setShaderColor(normalizedRed, normalizedGreen, normalizedBlue, normalizedAlpha);
                    RenderSystem.setShaderTexture(0, aspect.getAspectImage());
                    GuiComponent.blit(drawScreenEvent.getPoseStack(), drawScreenEvent.getMouseX() + 16 * i, drawScreenEvent.getMouseY() - 20, 0, 0, 0, 16, 16, 16, 16);
                    i += 1;
                }
                drawScreenEvent.getPoseStack().scale(0.5f,0.5f, 1f);
                for (Integer aspectCount: renderAspects.aspects.values()) {
                    GuiComponent.drawCenteredString(drawScreenEvent.getPoseStack(), font, aspectCount.toString(), (drawScreenEvent.getMouseX()) * 2 + (32 * b), (drawScreenEvent.getMouseY() - 20 + 16 - font.lineHeight / 2)*2,0xFFFFFF);
                    b +=1;
                }
                drawScreenEvent.getPoseStack().popPose();

//                int color = 0xFF00FF00;  // ARGB color (green)
//                RenderSystem.setShaderColor((float)((color >> 16) & 0xFF) / 255f, (float)((color >> 8) & 0xFF) / 255f, (float)(color & 0xFF) / 255f, (float)((color >> 24) & 0xFF) / 255f);
//                drawScreenEvent.getPoseStack().pushPose();
//                drawScreenEvent.getPoseStack().last();
//                drawScreenEvent.getPoseStack().translate(0,0,301f);
//                RenderSystem.setShaderTexture(0, new ResourceLocation(Thaumcraft.MOD_ID, "textures/aspects/aer.png"));
//                GuiComponent.blit(drawScreenEvent.getPoseStack(), drawScreenEvent.getMouseX() + 10, drawScreenEvent.getMouseY() - 20, 0, 0, 0, 16, 16, 16, 16);
//                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//                drawScreenEvent.getPoseStack().popPose();
            };
//            boolean isScanningItemSlot = ScanManager.isHoldingThaumometer(Minecraft.getInstance().player) && tempSlot != null && !tempSlot.getItem().getItem().equals(Items.AIR);
//            if(isScanningItemSlot){
//                ScanManager.drawInvScanProgress.renderScanningProgress(drawScreenEvent.getPoseStack(), drawScreenEvent.getMouseX(), drawScreenEvent.getMouseY(), ScanManager.getHoverTick() / (float) ThaumcraftClientConfig.THAUMOMETER_SCAN_DURATION.get());
//            }
//            if(ScanManager.isHoldingThaumometer(Minecraft.getInstance().player) && tempSlot != null && ScanManager.isScannedObject(tempSlot.getItem().getItem().toString())){
//                System.out.println("is done");
//                int color = 0xFF00FF00;  // ARGB color (green)
//                RenderSystem.setShaderColor((float)((color >> 16) & 0xFF) / 255f, (float)((color >> 8) & 0xFF) / 255f, (float)(color & 0xFF) / 255f, (float)((color >> 24) & 0xFF) / 255f);
//                drawScreenEvent.getPoseStack().pushPose();
//                drawScreenEvent.getPoseStack().last();
//                drawScreenEvent.getPoseStack().translate(0,0,301f);
//                RenderSystem.setShaderTexture(0, new ResourceLocation(Thaumcraft.MOD_ID, "textures/aspects/aer.png"));
//                GuiComponent.blit(drawScreenEvent.getPoseStack(), drawScreenEvent.getMouseX() + 10, drawScreenEvent.getMouseY() - 20, 0, 0, 0, 16, 16, 16, 16);
//                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//                drawScreenEvent.getPoseStack().popPose();
//            }
//            // add check !ScanManager.isScannedObject(tempSlot.getItem().getItem().toString())
//            if(Minecraft.getInstance().player != null && (ScanManager.isHoldingThaumometer(Minecraft.getInstance().player) || tempSlot != null)){
////                drawScanProgress.renderScanningProgress(drawScreenEvent.getPoseStack(), drawScreenEvent.getMouseX(), drawScreenEvent.getMouseY(), ticksHovered / (float) SCAN_TICK);
////                Minecraft.getInstance().font.drawShadow(drawScreenEvent.getPoseStack(),"test", drawScreenEvent.getMouseX() , drawScreenEvent.getMouseY(), new Color(250,0,0).getRGB());
//            }

        }
//                ContainerScreen containerScreen = (ContainerScreen)event.getScreen();
    }

}