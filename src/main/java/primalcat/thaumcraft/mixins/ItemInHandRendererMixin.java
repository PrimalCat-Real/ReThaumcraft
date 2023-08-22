package primalcat.thaumcraft.mixins;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
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
import primalcat.thaumcraft.utilites.Variables;

@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {


    @Inject(method = "renderItem", at = @At("HEAD"), cancellable = true)
    private void renderFirstPersonItem(LivingEntity livingEntity, ItemStack itemStack, ItemTransforms.TransformType transformType, boolean leftHanded, PoseStack poseStack, MultiBufferSource buffers, int light, CallbackInfo ci) {

//        poseStack.pushPose();
        Font font = Minecraft.getInstance().font;
        // Your custom font rendering code
        String textToDisplay = "Hello World!";
        int textWidth = font.width(textToDisplay);
        int xOffset = -110 - textWidth / 2; // Set your desired X offset
        int yOffset = -138; // Set your desired Y offset
//
        Player player = (Player)Minecraft.getInstance().getCameraEntity();
        float f = player.walkDist - player.walkDistO;
        float f1 = -(player.walkDist + f * Variables.partialTick);
        float f2 = Mth.lerp(Variables.partialTick, player.oBob, player.bob);
        poseStack.pushPose();

        poseStack.scale(0.005F, 0.005F, 0.005F); // Scale down by 8000%

        float inverseScaleFactor = 1.0F / 0.00579F;

// Apply inverse transformation for translation
        poseStack.translate((double)(-Mth.sin(f1 * (float)Math.PI) * f2 * 0.5F * inverseScaleFactor), (double)(Math.abs(Mth.cos(f1 * (float)Math.PI) * f2 * inverseScaleFactor)), 0.0D);
//
// Apply inverse rotation for Vector3f.ZP.rotationDegrees
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(-Mth.sin(f1 * (float)Math.PI) * f2 * 3.0F));

// Apply inverse rotation for Vector3f.XP.rotationDegrees
        poseStack.mulPose(Vector3f.XP.rotationDegrees(-Math.abs(Mth.cos(f1 * (float)Math.PI - 0.2F) * f2) * 5.0F));


        poseStack.mulPose(Vector3f.XN.rotationDegrees(180));
        font.drawInBatch(textToDisplay, xOffset,yOffset,0x79ff92,false, poseStack.last().pose(),buffers,false, 0, light);

        poseStack.popPose();
        poseStack.pushPose();
        poseStack.last().pose();
    }
}


