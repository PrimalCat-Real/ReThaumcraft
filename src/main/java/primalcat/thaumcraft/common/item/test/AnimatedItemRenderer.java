package primalcat.thaumcraft.common.item.test;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import primalcat.thaumcraft.Thaumcraft;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.util.Color;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;
import software.bernie.geckolib3.util.EModelRenderCycle;

import java.util.Collections;

public class AnimatedItemRenderer extends GeoItemRenderer<AnimatedItem> {

    public AnimatedItemRenderer() {
        super(new AnimatedItemModel());
    }

    @Override
    public void render(AnimatedItem  animatable, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, ItemStack stack) {
        this.currentItemStack = stack;
        GeoModel model = this.modelProvider.getModel(this.modelProvider.getModelResource(animatable));
        AnimationEvent animationEvent = new AnimationEvent((IAnimatable)animatable, 0.0F, 0.0F, Minecraft.getInstance().getFrameTime(), false, Collections.singletonList(stack));
        this.dispatchedMat = poseStack.last().pose().copy();
        this.setCurrentModelRenderCycle(EModelRenderCycle.INITIAL);
        this.modelProvider.setLivingAnimations(animatable, this.getInstanceId(animatable), animationEvent);
        poseStack.pushPose();
        poseStack.translate(0.5, 0.5099999904632568, 0.5);
        RenderSystem.setShaderTexture(0, this.getTextureLocation(animatable));
        Color renderColor = this.getRenderColor(animatable, 0.0F, poseStack, bufferSource, null, packedLight);
        RenderType renderType = this.getRenderType(animatable, 0.0F, poseStack, bufferSource, null, packedLight, this.getTextureLocation(animatable));
        this.render(model, animatable, 0.0F, renderType, poseStack, bufferSource, null, packedLight, OverlayTexture.NO_OVERLAY, (float)renderColor.getRed() / 255.0F, (float)renderColor.getGreen() / 255.0F, (float)renderColor.getBlue() / 255.0F, (float)renderColor.getAlpha() / 255.0F);

        long timeOfDay = Minecraft.getInstance().level.getDayTime() % 24000;
        boolean isDaytime = timeOfDay >= 0 && timeOfDay < 12000;
        String textToDisplay = isDaytime ? "Daytime Text" : "Nighttime Text";
        Font font = Minecraft.getInstance().font;


        RenderSystem.setShaderTexture(0, new ResourceLocation(Thaumcraft.MODID, "geo/animated_item.geo.json"));
        bufferSource.getBuffer(RenderType.entityTranslucent(new ResourceLocation(Thaumcraft.MODID, "geo/animated_item.geo.json"))); // Set the buffer source for the custom texture

        // Load the PNG texture using the TextureAtlasSprite class
        ResourceLocation textureLocation = new ResourceLocation(Thaumcraft.MODID, "textures/items/amber.png");

        // Save the current matrix and scale for smaller text
        int yOffset = -20;
        int xOffset = -10;
        poseStack.pushPose();
        poseStack.scale(0.05F, 0.05F, 0.05F); // Scale down by 80%
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(180));
//        poseStack.mulPose(Vector3f.YP.rotationDegrees(90));
        poseStack.mulPose(Vector3f.XN.rotationDegrees(-90));
        font.drawInBatch(textToDisplay, xOffset, yOffset, 0x79ff92, false, poseStack.last().pose(), bufferSource, false, 0, packedLight);
        poseStack.popPose();

        poseStack.popPose();
        poseStack.last().pose();

    }
}
