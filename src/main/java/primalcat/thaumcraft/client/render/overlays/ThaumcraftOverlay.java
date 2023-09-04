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
import org.w3c.dom.Text;
import primalcat.thaumcraft.client.ScanManager;

import java.util.ArrayList;
import java.util.List;

public class ThaumcraftOverlay implements IGuiOverlay {

    private static List<TextElement> textForRender = new ArrayList<>();


    public static void addTextForRender(String text) {
        textForRender.add(new TextElement(text, 0xFFFFFF));
    }

    public static void addTextForRender(String text, int color) {
        textForRender.add(new TextElement(text, color));
    }
    @Override
    public void render(ForgeGui gui, PoseStack poseStack, float partialTicks, int width, int height) {

        MultiBufferSource buffers = Minecraft.getInstance().renderBuffers().bufferSource();


//        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F); // Set opacity
        Font font = Minecraft.getInstance().font;
        // Custom font rendering code with yOffset
        poseStack.pushPose();
//        System.out.println(partialTicks);
        poseStack.scale(0.5F, 0.5F, 1.0F);
        int i = 0;
        for (TextElement textItem: textForRender) {
            int textWidth = font.width(textItem.getText());
            float opacity = textItem.getOpacity();
//            if (textItem.isFadingIn()) {
//                opacity = Math.min(opacity + partialTicks / 20.0F, 1.0F);
//                textItem.setOpacity(opacity);
//                if (opacity >= 1.0F) {
//                    textItem.setFadingIn(false);
//                }
//            }
            // Calculate the X-coordinate for centered text
//        int x = 0 - textWidth;
            int x = ((width - 3) - textWidth / 4) * 2;
            int y = ((height - 3) - font.lineHeight - i * font.lineHeight) * 2;
            // Use GuiComponent.drawCenteredString to draw centered text
//            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, opacity);
//
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(.7f, .7f, .8f, 0.25f);
            GuiComponent.drawCenteredString(poseStack, font, textItem.getText(), x, y, textItem.getColor());
            i += 1;
        }
        poseStack.popPose();

    }

}
