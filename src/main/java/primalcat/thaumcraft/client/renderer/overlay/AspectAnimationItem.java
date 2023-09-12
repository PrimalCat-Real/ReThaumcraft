package primalcat.thaumcraft.client.renderer.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;

public class AspectAnimationItem {

    private int currentX = 0;
    private int currentY = 0;

    public boolean shouldRemove = false;

    private int color;
    private PoseStack poseStack;
    private int width;
    private int height;

    private int spreadX = (int) (Math.random() * 50);
    private int spreadY = (int) (Math.random() * 50 - 50);

    public int index = 0;
    public boolean isRender = true;
    public ResourceLocation texture;

//    public ResourceLocation texture = new ResourceLocation(Thaumcraft.MOD_ID, "textures/items/thaumonomicon.png");

    public AspectAnimationItem(int color, PoseStack poseStack, int width, int height, ResourceLocation texture) {
        this.color = color;
        this.poseStack = poseStack;
        this.width = width;
        this.height = height;
        this.texture = texture;
    }

    public void render(){
        int startPointX = width / 2 - 8  - spreadX;
        int startPointY = height / 2 - 8 - spreadY;

        int controlPointX = (int) (width / 1.4) - spreadX;
        int controlPointY = height / 3 - spreadY;

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
            scale = (int) (20 - (float) (index - 50) * 0.32f); // Change scale from 20 to 8
        }
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        float red = (( color >> 16) & 0xFF) / 255.0f;
        float green = (( color >> 8) & 0xFF) / 255.0f;
        float blue = ( color & 0xFF) / 255.0f;
        RenderSystem.setShaderColor(red, green, blue, opacity);
        RenderSystem.setShaderTexture(0,texture);
        GuiComponent.blit(poseStack, currentX, currentY, 0, 0, scale, scale, scale, scale);
        if(currentX > width - 16 - 8 || currentY < 8){
//            ThaumcraftOverlay.removeActiveRenderAspect(this);
            this.shouldRemove = true;
        }
        index +=1;
    }
}
