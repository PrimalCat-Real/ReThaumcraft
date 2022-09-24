package thaumcraft.thaumcraft.utilites;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;

import java.awt.*;

public class DrawText {
    public float progress = 0;
    public boolean startDraw = false;
    public static PoseStack poseStack;

    public static PoseStack getPoseStack() {
        return poseStack;
    }

    public static void setPoseStack(PoseStack poseStack) {
        DrawText.poseStack = poseStack;
    }

    public void setProgress(float progress){
        this.progress= progress;
    }

    public void setStartDraw(boolean startDraw){
        this.startDraw= startDraw;
    }


    // method special for scanning process
    public void renderScanningProgress(PoseStack poseStack, int mouseX, int mouseY) {
        if(this.startDraw){
            StringBuilder sb = new StringBuilder("Scanning");
            if (this.progress >= 0.75f) {
                sb.append("...");
                System.out.println(sb);
            } else if (this.progress >= 0.5f) {
                sb.append("..");
                System.out.println(sb);
            } else if (this.progress >= 0.25f) {
                sb.append(".");
                System.out.println(sb);
            }
            drawText(poseStack, mouseX-10 , mouseY-20, sb.toString(), new Color(234,186,43));
        }


    }

    // simple draw text method realisation
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
