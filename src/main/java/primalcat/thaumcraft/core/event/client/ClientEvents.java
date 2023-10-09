package primalcat.thaumcraft.core.event.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
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
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import primalcat.thaumcraft.Thaumcraft;
import primalcat.thaumcraft.core.aspects.Aspect;
import primalcat.thaumcraft.core.aspects.AspectList;
import primalcat.thaumcraft.core.registry.particle.ParticlesRegistry;
import primalcat.thaumcraft.core.scan.ScanManager;
import primalcat.thaumcraft.core.config.ClientConfig;
import primalcat.thaumcraft.core.registry.ItemRegistry;


@Mod.EventBusSubscriber(modid = Thaumcraft.MODID, value = Dist.CLIENT)
public class ClientEvents {
    private static Slot tempSlot = null;
    private static Slot lastScannedSlot;
    private static ScreenEvent.Render.Post drawScreenEvent = null;

    @SubscribeEvent
    public void onTooltipPostText(RenderTooltipEvent event) {
        System.out.println("Render tooltip");
    }

    @SubscribeEvent
    public static void onPlayerRenderPre(RenderPlayerEvent.Pre event) {
        Player player = (Player) event.getEntity();
        PlayerModel<?> model = event.getRenderer().getModel();
        boolean holdingYourItem = player.isHolding(ItemRegistry.THAUMOMETER.get());
        model.leftArmPose = holdingYourItem ? HumanoidModel.ArmPose.BOW_AND_ARROW : HumanoidModel.ArmPose.EMPTY;
    }

    @SubscribeEvent
    public static void registerParticleFactory(RegisterParticleProvidersEvent event) {
        ParticlesRegistry.registerParticleFactory(event);
//        LodestoneScreenParticleRegistry.registerParticleFactory(event);
    }

    @SubscribeEvent
    public static void onClientTick(final TickEvent.ClientTickEvent event) {
        if (!event.isCanceled() && event.phase == TickEvent.Phase.END) {
            Player player = Minecraft.getInstance().player;
            if (player != null && ScanManager.isMouseHoldingThaumometer(player) && drawScreenEvent != null) {
                tempSlot = ((AbstractContainerScreen<?>) drawScreenEvent.getScreen()).getSlotUnderMouse();
                if (lastScannedSlot != tempSlot) {
                    lastScannedSlot = tempSlot;
                    resetScanManagerState();
                }
                if (tempSlot != null && !tempSlot.getItem().getItem().equals(Items.AIR)) {
                    lastScannedSlot = tempSlot;
                    ScanManager.doScan(player, tempSlot.getItem().getItem().toString(), ScanManager.getAspectFromObject(tempSlot));
                } else if (ScanManager.isMouseHoldingThaumometer(player) && ScanManager.isHoveringPlayer(drawScreenEvent.getScreen(), drawScreenEvent.getMouseX(), drawScreenEvent.getMouseY())) {
                    lastScannedSlot = tempSlot;
                    ScanManager.doScan(player, player.getUUID().toString(), ScanManager.getAspectFromObject(player));
                } else {
                    resetScanManagerState();
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onInventorySlotInteract(final ScreenEvent.Render.Post event) {
        if (event.getScreen() instanceof InventoryScreen || event.getScreen() instanceof AbstractContainerScreen<?> || event.getScreen() instanceof ContainerScreen || event.getScreen() instanceof CreativeModeInventoryScreen) {
            drawScreenEvent = event;
            if (ScanManager.isScanning()) {
                ScanManager.drawInvScanProgress.renderScanningProgress(drawScreenEvent.getPoseStack(), drawScreenEvent.getMouseX(), drawScreenEvent.getMouseY(), ScanManager.getHoverTick() / (float) ClientConfig.THAUMOMETER_SCAN_DURATION.get());
            } else if (ScanManager.isMouseHoldingThaumometer(Minecraft.getInstance().player) && ScanManager.isHoveringPlayer(event.getScreen(), event.getMouseX(), event.getMouseY()) && ScanManager.getPlayerScannedObjects().contains(Minecraft.getInstance().player.getUUID().toString())) {
                renderPlayerAspects(drawScreenEvent.getPoseStack());
            } else if (tempSlot != null && ScanManager.isScannedObject(tempSlot.getItem().getItem().toString())) {
                renderScannedObjectAspects(drawScreenEvent.getPoseStack());
            }
        }
    }

    private static void resetScanManagerState() {
        lastScannedSlot = tempSlot;
        ScanManager.drawInvScanProgress.setCanDraw(false);
        ScanManager.setIsScanning(false);
        ScanManager.setScanDone(false);
        ScanManager.setHoverTick(0);
    }


    private static void renderAspects(PoseStack matrixStack, AspectList renderAspects) {
        int i = 1;
        int aspectIndex = 1;
        Font font = Minecraft.getInstance().font;
        renderAspects.sortByName();
        for (Aspect aspect : renderAspects.aspects.keySet()) {
            // Placeholder rendering logic for background
            RenderSystem.enableBlend();
            RenderSystem.depthMask(false);
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShaderTexture(0, new ResourceLocation(Thaumcraft.MODID, "textures/aspects/back.png"));
            RenderSystem.setShaderColor(1, 1, 1, 0.8f);
            GuiComponent.blit(matrixStack, drawScreenEvent.getMouseX() + 18 * i - 2, drawScreenEvent.getMouseY() - 20 - 2, 0, 0, 0, 20, 20, 20, 20);

            int colorValue = aspect.getColor();
            int alpha = (colorValue >> 24) & 0xFF;
            int red = (colorValue >> 16) & 0xFF;
            int green = (colorValue >> 8) & 0xFF;
            int blue = colorValue & 0xFF;
            float normalizedAlpha = (float) alpha / 255f;
            float normalizedRed = (float) red / 255f;
            float normalizedGreen = (float) green / 255f;
            float normalizedBlue = (float) blue / 255f;

            RenderSystem.depthMask(true);
            RenderSystem.setShaderColor(normalizedRed, normalizedGreen, normalizedBlue, 1F);
            RenderSystem.setShaderTexture(0, aspect.getAspectImage());
            GuiComponent.blit(matrixStack, drawScreenEvent.getMouseX() + 18 * i, drawScreenEvent.getMouseY() - 20, 0, 0, 0, 16, 16, 16, 16);

            i += 1;
        }

        matrixStack.scale(0.5f, 0.5f, 1f);
        matrixStack.translate(0f,0f,301f);
        for (Integer aspectCount : renderAspects.aspects.values()) {
            renderScannedAspectCount(matrixStack, font, aspectCount, aspectIndex);
            aspectIndex += 1;
        }
    }
    private static void renderPlayerAspects(PoseStack matrixStack) {
        Player player = Minecraft.getInstance().player;
        AspectList renderAspects = ScanManager.getAspectFromObject(player);
        renderAspects(matrixStack, renderAspects);
    }



    private static void renderScannedObjectAspects(PoseStack matrixStack) {
        AspectList renderAspects = ScanManager.getAspectFromObject(tempSlot);
        renderAspects(matrixStack, renderAspects);
    }

    private static void renderScannedAspectCount(PoseStack matrixStack, Font font, Integer aspectCount, int aspectIndex) {
        GuiComponent.drawCenteredString(matrixStack, font, aspectCount.toString(),
                (drawScreenEvent.getMouseX()) * 2 + (36 * aspectIndex) + 32 - font.width(aspectCount.toString()) / 2,
                (drawScreenEvent.getMouseY() - 20 + 16 - font.lineHeight / 2) * 2, 0xFFFFFF);
    }
}
