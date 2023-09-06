package primalcat.thaumcraft.client.render.overlays;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.lwjgl.opengl.GL11;
import org.w3c.dom.Text;
import primalcat.thaumcraft.Thaumcraft;
import primalcat.thaumcraft.aspects.Aspect;
import primalcat.thaumcraft.aspects.AspectList;
import primalcat.thaumcraft.aspects.AspectListIterator;
import primalcat.thaumcraft.client.ScanManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ThaumcraftOverlay implements IGuiOverlay {

    private static int index = 0;

    private static List<TextElement> textForRender = new ArrayList<>();

    private static CustomElement test = new CustomElement();

    private static Iterator<Map.Entry<Aspect, Integer>> iterator;

    private static AspectList aspectsForRender = new AspectList();

    public static AspectList getAspectsForRender() {
        return aspectsForRender;
    }

    public static void setAspectsForRenderAnimation(AspectList aspectsForRender) {
        ThaumcraftOverlay.aspectsForRender = aspectsForRender;
    }

    public static void addTextForRender(String text) {
        textForRender.add(new TextElement(text, 0xFFFFFF));
    }

    public static void addTextForRender(String text, int color) {
        textForRender.add(new TextElement(text, color));
    }
    @Override
    public void render(ForgeGui gui, PoseStack poseStack, float partialTicks, int width, int height) {
        MultiBufferSource buffers = Minecraft.getInstance().renderBuffers().bufferSource();
        Font font = Minecraft.getInstance().font;
        poseStack.pushPose();
//        poseStack.scale(0.5F, 0.5F, 1.0F);
//        if (aspectsForRender.aspects.keySet().size() > index){
//            RenderSystem.enableBlend();
//            RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//            RenderSystem.setShaderTexture(0, aspectsForRender.aspects.keySet());
//            GuiComponent.blit(poseStack, positionX, positionY, 0, 0, 16, 16, 16, 16);
//        }
        if(aspectsForRender != null && !aspectsForRender.isEmpty()){
            iterator = new AspectListIterator(aspectsForRender);
//            while (iterator.hasNext()) {
//                Map.Entry<Aspect, Integer> entry = iterator.next();
//                Aspect aspect = entry.getKey();
//                int amount = entry.getValue();
//
//                // Process the Aspect and amount as needed
//                System.out.println("Aspect: " + aspect.getName() + ", Amount: " + amount);
//            }
            RenderSystem.disableBlend();
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            RenderSystem.setShaderTexture(0, new ResourceLocation(Thaumcraft.MOD_ID, "textures/items/thaumonomicon.png"));
            GuiComponent.blit(poseStack, width - 16 - 8,  8, 0, 0, 16, 16, 16, 16);

            // test animation
            if(iterator.hasNext()){
                Aspect tempAspect = iterator.next().getKey();
                RenderSystem.setShaderTexture(0, tempAspect.getAspectImage());


//            double randomValue = Math.random(); // Generates a random value between 0 (inclusive) and 1 (exclusive)
//            double scaledValue = randomValue * 40 - 20;

                int startPointX = width / 2 - 8;
                int startPointY = height / 2 - 8;

                int controlPointX = (int) (width / 1.4);
                int controlPointY = height / 3;

                int endPointX = width - 16 - 8;
                int endPointY = 8;

                int maxIndex = 100; // You can adjust this value based on your desired animation speed

// Calculate the position along the quadratic Bézier curve
                float t = (float) index / maxIndex;
                float u = 1 - t;
                int currentX = (int) (u * u * startPointX + 2 * u * t * controlPointX + t * t * endPointX);
                int currentY = (int) (u * u * startPointY + 2 * u * t * controlPointY + t * t * endPointY);

// Clamp currentX and currentY to stay within bounds
                currentX = Math.max(0, Math.min(currentX, 1920));
                currentY = Math.max(0, Math.min(currentY, 1080));

// Render the texture at the current position (x, y)
                int scale;
                float opacity;
                if (index < 50) {
                    opacity = 0.5f + ((float) index / 50.0f) * 0.5f; // Change opacity from 0.5 to 1
                    scale = (int) (16 + (float) index * 0.16f);
                } else {
                    opacity = 1.0f - ((float) (index - 50) / 50.0f) * 0.5f; // Change opacity from 1 to 0
                    scale = (int) (24 - (float) (index - 50) * 0.32f); // Change scale from 24 to 8
                }
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                int color = tempAspect.getColor();
                float red = (( color >> 16) & 0xFF) / 255.0f;
                float green = (( color >> 8) & 0xFF) / 255.0f;
                float blue = ( color & 0xFF) / 255.0f;
                RenderSystem.setShaderColor(red, green, blue, opacity);
                GuiComponent.blit(poseStack, currentX, currentY, 0, 0, scale, scale, scale, scale);
                index += 1;
            }

        }

        // Initialize the iterator and current index
//        Iterator<AspectList> textIterator = aspectsForRender.iterator();
//        int currentIndex = 0;
//        double v = (height / 2) / Math.pow(width / 2, 2);
//        int positionX  = test.positionX + width / 2;
//        int positionY  = (int) (v *  Math.pow((test.positionX - width / 2), 2));
//
//        if(positionX <= width-16) {
//            test.positionX += 1;
////        System.out.println(positionX);
//
//            RenderSystem.enableBlend();
//            RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
////        RenderSystem.setShaderColor(0.2f, 0.7f, 0.2f, 0.75f);
//            RenderSystem.setShaderTexture(0, new ResourceLocation(Thaumcraft.MOD_ID, "textures/items/thaumonomicon.png"));
//            GuiComponent.blit(poseStack, positionX, positionY, 0, 0, 16, 16, 16, 16);
//        }


//        while (textIterator.hasNext()) {
//
//        }
//
//        for (TextElement textItem : textForRender) {
//            float opacity = 0;
//            if(textItem.isFadingIn()){
//                // Calculate the opacity based on the time elapsed and partialTicks
//                opacity += partialTicks; // Adjust the divisor for the desired speed
//
//                // Ensure opacity stays within the 0.0 to 1.0 range
//                opacity = Math.min(opacity, 1.0f);
//            }
//            int textWidth = font.width(textItem.getText());
//            int x = ((width - 3) - textWidth / 4) * 2;
//            int y = ((height - 3) - font.lineHeight - i * font.lineHeight) * 2;
//
//            // Convert opacity from 0.0-1.0 to 0-255
//            int opacityInt = (int) (opacity * 255);
//            System.out.println(opacity);
//            if(opacity == 1){
//                textItem.setFadingIn(false);
//            }
//            GuiComponent.drawCenteredString(poseStack, font, textItem.getText(), x, y, new Color(234, 186, 43, opacityInt).getRGB());
//            i += 1;
//        }
        poseStack.popPose();
    }

}
