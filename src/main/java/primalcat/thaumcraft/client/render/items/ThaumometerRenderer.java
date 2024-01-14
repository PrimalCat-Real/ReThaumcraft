package primalcat.thaumcraft.client.render.items;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.azure.azurelib.common.api.client.model.DefaultedItemGeoModel;
import mod.azure.azurelib.common.api.client.renderer.GeoItemRenderer;
import mod.azure.azurelib.common.internal.common.cache.object.BakedGeoModel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import primalcat.thaumcraft.Thaumcraft;
import primalcat.thaumcraft.common.items.thaumometer.Thaumometer;

import java.awt.*;

import static net.minecraft.client.render.entity.ArrowEntityRenderer.TEXTURE;

public class ThaumometerRenderer extends GeoItemRenderer<Thaumometer>
{
    public ThaumometerRenderer()
    {
        super(new DefaultedItemGeoModel<>(new Identifier(Thaumcraft.MODID, "thaumometer")));
    }

    @Override
    public void actuallyRender(MatrixStack poseStack, Thaumometer animatable, BakedGeoModel model, RenderLayer renderType, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        alpha = 0.105f;
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }



    @Override
    public void render(ItemStack stack, ModelTransformationMode transformType, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight, int packedOverlay) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        poseStack.push();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        // Get the matrix from the MatrixStack
        Matrix4f matrix = poseStack.peek().getPositionMatrix();

        // Render your item model


        // Render text in 3D space
        String textToRender = "Hello, Fabric!";
        int color = 0xFFFFFF; // Set your color (white in this example)

        // Adjust the translation according to your needs
        float x = 0.5F; // X-coordinate
        float y = 1.0F; // Y-coordinate
        float z = 0.5F; // Z-coordinate

        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        textRenderer.draw(textToRender, (float) 0, (float) 0, color, false, poseStack.peek().getPositionMatrix(), (VertexConsumerProvider) bufferSource, TextRenderer.TextLayerType.NORMAL, 0, packedLight);

        // Pop the matrix stack to revert transformations
        if (!poseStack.isEmpty()) {
            poseStack.pop();
        }

        super.render(stack, transformType, poseStack, bufferSource, packedLight, packedOverlay);
    }

    @Override
    public Identifier getTextureLocation(Thaumometer animatable)
    {

        return new Identifier(Thaumcraft.MODID, "textures/item/thaumometer.png");
    }
}
