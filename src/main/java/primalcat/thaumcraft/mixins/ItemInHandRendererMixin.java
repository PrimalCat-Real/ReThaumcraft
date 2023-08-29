package primalcat.thaumcraft.mixins;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.ForgeRenderTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import primalcat.thaumcraft.Thaumcraft;
import primalcat.thaumcraft.init.ItemInit;
import primalcat.thaumcraft.utilites.Variables;

@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {

    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(Thaumcraft.MOD_ID, "textures/aspects/aer.png");

    @Inject(method = "renderItem", at = @At("HEAD"), cancellable = true)
    private void renderFirstPersonItem(LivingEntity livingEntity, ItemStack itemStack, ItemTransforms.TransformType transformType, boolean leftHanded, PoseStack poseStack, MultiBufferSource buffers, int light, CallbackInfo ci) {
        if (Minecraft.getInstance().getCameraEntity() instanceof Player) {
            Player player = (Player)Minecraft.getInstance().getCameraEntity();
            if (player != null && Minecraft.getInstance().options.getCameraType().isFirstPerson() && player.getMainHandItem().getItem().asItem().equals(ItemInit.THAUMOMETER.get().asItem()) ){
                Font font = Minecraft.getInstance().font;
                // Custom font rendering code
                String textToDisplay = "Hello World!";
                int textWidth = font.width(textToDisplay);
                int xOffset = -110 - textWidth / 2; // Set  X offset
                int yOffset = -138; // Set Y offset

                float f = player.walkDist - player.walkDistO;
                float f1 = -(player.walkDist + f * Variables.partialTick);
                float f2 = Mth.lerp(Variables.partialTick, player.oBob, player.bob);
                poseStack.pushPose();

                // Apply scaling
                poseStack.scale(0.005F, 0.005F, 0.005F);

                // Calculate the inverse scaling factor
                float inverseScaleFactor = 1.0F / 0.00579F;

                // Apply inverse transformation for translation
                poseStack.translate((double)(-Mth.sin(f1 * (float)Math.PI) * f2 * 0.5F * inverseScaleFactor), (double)(Math.abs(Mth.cos(f1 * (float)Math.PI) * f2 * inverseScaleFactor)), 0.0D);

                // Apply inverse rotation for Vector3f.ZP.rotationDegrees
                poseStack.mulPose(Vector3f.ZP.rotationDegrees(-Mth.sin(f1 * (float)Math.PI) * f2 * 3.0F));

                // Apply inverse rotation for Vector3f.XP.rotationDegrees
                poseStack.mulPose(Vector3f.XP.rotationDegrees(-Math.abs(Mth.cos(f1 * (float)Math.PI - 0.2F) * f2) * 5.0F));

                // rotate text for firth person view
                poseStack.mulPose(Vector3f.XN.rotationDegrees(180));

                // Render the text
                font.drawInBatch(textToDisplay, xOffset,yOffset,0x79ff92,false, poseStack.last().pose(),buffers,false, 0, light);

                RenderType renderType = RenderType.text(TEXTURE_LOCATION);

                VertexConsumer vertexConsumer = buffers.getBuffer(renderType);

                // Push transformations onto the PoseStack if needed
//                poseStack.pushPose();
//                // Draw vertices
//                vertexConsumer.vertex(poseStack.last().pose(), xOffset, yOffset, 0)
//                        .color(1.0F, 1.0F, 1.0F, 1.0F)
//                        .uv(0F, 0F)
//                        .endVertex();



                // Pop the original transformations
                poseStack.popPose();
            }

        }
//        poseStack.pushPose();

    }
}


