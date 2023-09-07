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
import org.lwjgl.openal.SOFTDeferredUpdates;
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

    private static int maxAnimation = 0;

    private static AspectList aspectsForRender = new AspectList();

    private static List<CustomElement> activeRenderAspects = new ArrayList<>();

    public static void removeActiveRenderAspect(CustomElement element){
        activeRenderAspects.remove(element);
    }

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
        RenderSystem.disableBlend();
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        if(activeRenderAspects != null && !activeRenderAspects.isEmpty()){
            RenderSystem.setShaderTexture(0, new ResourceLocation(Thaumcraft.MOD_ID, "textures/items/thaumonomicon.png"));
            gui.blit(poseStack, width - 16 - 8,  8, 0, 0, 16, 16, 16, 16);
        }

        if (aspectsForRender != null && !aspectsForRender.isEmpty()) {
            if(fixTick > 7){
                fixTick = 0;
                // @TODO need fix Error rendering overlay 'thaumcraft:thaumcraftoverlay' java.util.ConcurrentModificationException: null
                if(maxAnimation <= 20){
                    for (Aspect renderAspect: aspectsForRender.aspects.keySet()) {
                        maxAnimation += 1;
                        if( aspectsForRender.aspects.get(renderAspect) != null && aspectsForRender.aspects.get(renderAspect) == 0){
                            aspectsForRender.aspects.remove(renderAspect);
                        }
                        if(aspectsForRender.aspects.get(renderAspect) != null){
                            aspectsForRender.aspects.put(renderAspect, aspectsForRender.aspects.get(renderAspect) - 1);
                            activeRenderAspects.add(new CustomElement(renderAspect.getColor(),poseStack, width, height, renderAspect.getAspectImage()));
                        }
                    }
                }


            }else {
                fixTick += partialTicks;
            }
        }
        if(activeRenderAspects != null && !activeRenderAspects.isEmpty()){
            for (CustomElement renderElement: activeRenderAspects) {
                renderElement.render();

            }
        }

        if(activeRenderAspects.isEmpty()){
            maxAnimation = 0;
        }

        poseStack.popPose();
    }

}

