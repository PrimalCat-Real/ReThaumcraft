package primalcat.thaumcraft.client.render.overlays;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import primalcat.thaumcraft.client.ScanManager;

import java.util.ArrayList;
import java.util.List;

public class ThaumcraftOverlay implements IGuiOverlay {

    private static List<String> textForRender = new ArrayList<>();

    private static int color = 0xFFFFFF;
    public static void addTextForRender(String text){
        textForRender.add(text);
    }

    @Override
    public void render(ForgeGui gui, PoseStack poseStack, float partialTicks, int width, int height) {

        MultiBufferSource buffers = Minecraft.getInstance().renderBuffers().bufferSource();

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F); // Set opacity
        Font font = Minecraft.getInstance().font;
        // Custom font rendering code with yOffset
        poseStack.pushPose();

        poseStack.scale(0.5F, 0.5F, 1.0F);
        int i = 0;
        for (String textItem: textForRender) {
            int textWidth = font.width(textItem);

            // Calculate the X-coordinate for centered text
//        int x = 0 - textWidth;
            int x = ((width - 3) - textWidth / 4) * 2;

            int y = ((height - 3) - font.lineHeight - i * font.lineHeight) * 2;


            // Use GuiComponent.drawCenteredString to draw centered text
            GuiComponent.drawCenteredString(poseStack, font, textItem, x, y, 0xFFFFFF);
            i += 1;
        }



        poseStack.popPose();

    }

}
