package primalcat.thaumcraft.mixins;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import primalcat.thaumcraft.Thaumcraft;
import primalcat.thaumcraft.aspects.Aspect;
import primalcat.thaumcraft.client.ScanManager;
import primalcat.thaumcraft.init.ItemInit;

import java.util.Map;

@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {

    private static final float SCALE_FACTOR = 0.005F;
    private static final float INVERSE_SCALE_FACTOR = 1.0F / 0.00579F;
    private static final int ASPECT_SIZE = 32;
    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(Thaumcraft.MOD_ID, "textures/aspects/aer.png");

    @Inject(method = "renderItem", at = @At("HEAD"), cancellable = true)
    private void renderFirstPersonItem(LivingEntity livingEntity, ItemStack itemStack, ItemTransforms.TransformType transformType, boolean leftHanded, PoseStack poseStack, MultiBufferSource buffers, int light, CallbackInfo ci) {
        if (!(livingEntity instanceof Player player) || player == null || !player.getMainHandItem().getItem().asItem().equals(ItemInit.THAUMOMETER.get().asItem()) || !Minecraft.getInstance().options.getCameraType().isFirstPerson()) {
            return;
        }
        Font font = Minecraft.getInstance().font;
        // Custom font rendering code
        String textToDisplay = ScanManager.getTargetNameForRender();
        int textWidth = font.width(textToDisplay);
        int xOffset = -110 - textWidth / 2; // Set  X offset
        int yOffset = -138; // Set Y offset

        float f = player.walkDist - player.walkDistO;
        float f1 = -(player.walkDist + f * ScanManager.partialTick);
        float f2 = Mth.lerp(ScanManager.partialTick, player.oBob, player.bob);
        poseStack.pushPose();

        // Apply scaling
        poseStack.scale(SCALE_FACTOR, SCALE_FACTOR, SCALE_FACTOR);

        // Calculate the inverse scaling factor
        float inverseScaleFactor = 1.0F / 0.00579F;

        // Apply inverse transformation for translation
        poseStack.translate(-Math.sin(f1 * Math.PI) * f2 * 0.5F * INVERSE_SCALE_FACTOR, Math.abs(Math.cos(f1 * Math.PI) * f2 * INVERSE_SCALE_FACTOR), 0.0D);
//        poseStack.translate((double)(-Mth.sin(f1 * (float)Math.PI) * f2 * 0.5F * inverseScaleFactor), (double)(Math.abs(Mth.cos(f1 * (float)Math.PI) * f2 * inverseScaleFactor)), 0.0D);
        // Apply inverse rotation for Vector3f.ZP.rotationDegrees
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(-Mth.sin(f1 * (float)Math.PI) * f2 * 3.0F));

        // Apply inverse rotation for Vector3f.XP.rotationDegrees
        poseStack.mulPose(Vector3f.XP.rotationDegrees(-Math.abs(Mth.cos(f1 * (float)Math.PI - 0.2F) * f2) * 5.0F));

        // rotate text for firth person view
        poseStack.mulPose(Vector3f.XN.rotationDegrees(180));

        // Render the text
        font.drawInBatch(textToDisplay, xOffset, yOffset, 0xFFFFFF, false, poseStack.last().pose(), buffers, false, 0, light);


        poseStack.scale(0.5F, 0.5F, 0.5F);
        int aspectXOffset = -257 - (ASPECT_SIZE / 2) * ScanManager.getTargetAspectsForRender().aspects.size();
        int aspectYOffset = -220;
        int countYOffset = (int) (-220 + 32 - font.lineHeight * 0.7);
        int aspectIndex = 0;
        for (Map.Entry<Aspect, Integer> entry : ScanManager.getTargetAspectsForRender().aspects.entrySet()) {
            Aspect aspect = entry.getKey();
            Integer aspectCount = entry.getValue();

            int colorValue = aspect.getColor();
            int alpha = (colorValue >> 24) & 0xFF;
            int red = (colorValue >> 16) & 0xFF;
            int green = (colorValue >> 8) & 0xFF;
            int blue = colorValue & 0xFF;

            float normalizedAlpha = 1F;
            float normalizedRed = (float) red / 255f;
            float normalizedGreen = (float) green / 255f;
            float normalizedBlue = (float) blue / 255f;

//            RenderSystem.setShaderColor(normalizedRed, normalizedGreen, normalizedBlue, normalizedAlpha);
            RenderSystem.setShaderTexture(0, aspect.getAspectImage());

            int xPosition = aspectXOffset + aspectIndex * ASPECT_SIZE + 32;
            int countXPosition = xPosition + ASPECT_SIZE - font.width(aspectCount.toString());

            /**
             * Enabling blending allows you to combine the colors of the rendered object with the colors already
             * in the frame buffer (the current image on the screen).
             * This is commonly used for rendering transparent or semi-transparent objects.
             * When blending is enabled, you can control how the colors are combined using the next method.
             */
            RenderSystem.enableBlend();

            /**
             * This method controls whether writes to the depth buffer are enabled or disabled.
             * When depthMask is set to false, it means that the depth buffer won't be updated with new depth values.
             * This can be useful when rendering objects that shouldn't affect the depth buffer,
             * like transparent objects or certain UI elements.
             */
            RenderSystem.depthMask(false);

            /**
             * Этот метод устанавливает функцию смешивания по умолчанию.
             * Функции наложения определяют, как комбинируются цвета из рендеримого объекта и кадрового буфера.
             * Функция смешивания "по умолчанию" обычно означает использование наиболее распространенного режима смешивания,
             * которым часто является альфа-смешивание (GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA).
             * В этом режиме цвета комбинируются на основе значений альфа (прозрачности) пикселей.
             */
            RenderSystem.defaultBlendFunc();

            RenderSystem.setShaderColor(normalizedRed, normalizedGreen, normalizedBlue, normalizedAlpha);
            GuiComponent.blit(poseStack, xPosition, aspectYOffset, 0, 0, 0, ASPECT_SIZE, ASPECT_SIZE, ASPECT_SIZE, ASPECT_SIZE);
            font.drawInBatch(aspectCount.toString(), countXPosition, countYOffset, 0xFCFCFC, false, poseStack.last().pose(), buffers, false, 0, light);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.disableBlend();
            RenderSystem.depthMask(true);
            aspectIndex++;
        }
        // Pop the original transformations
        poseStack.popPose();
    }
}


