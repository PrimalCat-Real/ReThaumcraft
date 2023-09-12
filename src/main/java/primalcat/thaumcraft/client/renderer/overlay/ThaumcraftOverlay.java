package primalcat.thaumcraft.client.renderer.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import primalcat.thaumcraft.Thaumcraft;
import primalcat.thaumcraft.core.aspects.Aspect;
import primalcat.thaumcraft.core.aspects.AspectIterator;
import primalcat.thaumcraft.core.aspects.AspectList;

import java.util.*;
import java.util.List;

public class ThaumcraftOverlay implements IGuiOverlay {

    private static int MAX_ASPECT_ANIMATION_COUNT = 20;

    private static Iterator<Map.Entry<Aspect, Integer>> iterator;

    private static float fixTick = 0;

    private static int maxAnimation = 0;

    private static AspectList aspectsForRender = new AspectList();

    private static List<AspectAnimationItem> activeRenderAspects = new ArrayList<>();

    public static AspectList getAspectsForRender() {
        return aspectsForRender;
    }

    public static void setAspectsForRenderAnimation(AspectList aspectsForRender) {
        ThaumcraftOverlay.aspectsForRender = aspectsForRender;
        iterator = aspectsForRender.aspects.entrySet().iterator();
    }

    @Override
    public void render(ForgeGui gui, PoseStack poseStack, float partialTicks, int width, int height) {
        poseStack.pushPose();
        RenderSystem.disableBlend();
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);

        if (!activeRenderAspects.isEmpty()) {
            RenderSystem.setShaderTexture(0, new ResourceLocation(Thaumcraft.MODID, "textures/items/thaumonomicon.png"));
            gui.blit(poseStack, width - 16 - 8, 8, 0, 0, 16, 16, 16, 16);
        }


        /**
         * Add aspect for activeAspectRender
         */
        if (!aspectsForRender.isEmpty() && fixTick > 7) {
            fixTick = 0;
            for (Aspect renderAspect : aspectsForRender.aspects.keySet()) {

                Integer aspectCount = aspectsForRender.aspects.get(renderAspect);
                if(maxAnimation > MAX_ASPECT_ANIMATION_COUNT){
                    aspectsForRender.clear();
                    break;
                }
                if (aspectCount != null && aspectCount != 0 && maxAnimation <= MAX_ASPECT_ANIMATION_COUNT) {
                    aspectsForRender.aspects.put(renderAspect, aspectCount - 1);
                    activeRenderAspects.add(new AspectAnimationItem(renderAspect.getColor(), poseStack, width, height, renderAspect.getAspectImage()));
                    maxAnimation += 1;
                }
            }
        } else {
            fixTick += partialTicks;
        }

        if(activeRenderAspects != null && !activeRenderAspects.isEmpty()){
            List<AspectAnimationItem> aspectAnimationItemsForRemove = new ArrayList<>();
            for (AspectAnimationItem renderElement: activeRenderAspects) {
                renderElement.render();
                if(renderElement.shouldRemove){
                    aspectAnimationItemsForRemove.add(renderElement);
                }
            }
            for (AspectAnimationItem elementForRemove: aspectAnimationItemsForRemove) {
                activeRenderAspects.remove(elementForRemove);
            }
        }


        if (activeRenderAspects.isEmpty()) {
            maxAnimation = 0;
        }

        poseStack.popPose();
    }

}

