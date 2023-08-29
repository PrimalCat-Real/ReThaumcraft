package primalcat.thaumcraft.utilites;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;

import java.awt.*;

public class DrawScanProgress {
    private boolean canDraw = false;

    public boolean isCanDraw() {
        return canDraw;
    }

    public void setCanDraw(boolean canDraw) {
        this.canDraw = canDraw;
    }

    public static PoseStack poseStack;

    public void renderScanningProgress(PoseStack poseStack, int mouseX, int mouseY, float progress) {
        if(this.canDraw){
            StringBuilder sb = new StringBuilder("Scanning");
            if(progress == 1.0f){
                this.setCanDraw(false);
            }
            if (progress >= 0.75f) {
                sb.append("...");
                System.out.println(sb);
            } else if (progress >= 0.5f) {
                sb.append("..");
                System.out.println(sb);
            } else if (progress >= 0.25f) {
                sb.append(".");
                System.out.println(sb);
            }
            drawText(poseStack, mouseX-10 , mouseY-20, sb.toString(), new Color(234,186,43));
        }
    }
    public static void drawText(PoseStack poseStack, int mouseX, int mouseY, String text, Color color){
        poseStack.pushPose();
        poseStack.last();
        poseStack.translate(0,0,300f);
        Minecraft.getInstance().font.drawShadow(poseStack,text, mouseX , mouseY, color.getRGB());
        poseStack.popPose();
    }
    public static void drawText(int mouseX, int mouseY, String text, Color color){
        poseStack.pushPose();
        poseStack.last();
        poseStack.translate(0,0,300f);
        Minecraft.getInstance().font.drawShadow(poseStack,text, mouseX , mouseY, color.getRGB());
        poseStack.popPose();
    }
}
