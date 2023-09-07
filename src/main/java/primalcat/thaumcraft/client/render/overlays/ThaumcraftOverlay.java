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
import java.util.*;
import java.util.List;

public class ThaumcraftOverlay implements IGuiOverlay {

    private static int index = 0;

    private static List<TextElement> textForRender = new ArrayList<>();


    private static Iterator<Map.Entry<Aspect, Integer>> iterator;

    private static Aspect currentAspect = null;
    private static int animationIndex = 0;

    private static float fixTick = 0;

    private static AspectList aspectsForRender = new AspectList();

    private static List<CustomElement> activeRenderAspects = new ArrayList<>();

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
        poseStack.pushPose();
        if (aspectsForRender != null && !aspectsForRender.isEmpty()) {
            RenderSystem.disableBlend();
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            GuiComponent.blit(poseStack, width - 16 - 8,  8, 0, 0, 16, 16, 16, 16);
            if(fixTick > 10){
                fixTick = 0;
                System.out.println("Tick clear");
                for (Aspect renderAspect: aspectsForRender.aspects.keySet()) {
                    if( aspectsForRender.aspects.get(renderAspect) != null && aspectsForRender.aspects.get(renderAspect) == 0){
                        aspectsForRender.aspects.remove(renderAspect);
                    }
                    if(aspectsForRender.aspects.get(renderAspect) != null){
                        aspectsForRender.aspects.put(renderAspect, aspectsForRender.aspects.get(renderAspect) - 1);
                        activeRenderAspects.add(new CustomElement(renderAspect.getColor(),poseStack, width, height, renderAspect.getAspectImage()));
                    }
                }
            }else {
                fixTick += partialTicks;
            }
        }
        if(activeRenderAspects != null && !activeRenderAspects.isEmpty()){
            RenderSystem.setShaderTexture(0, new ResourceLocation(Thaumcraft.MOD_ID, "textures/items/thaumonomicon.png"));
            for (CustomElement renderElement: activeRenderAspects) {
                renderElement.render();
            }
        }

        poseStack.popPose();
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