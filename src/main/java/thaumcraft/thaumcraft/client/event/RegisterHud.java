package thaumcraft.thaumcraft.client.event;


import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.*;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import thaumcraft.thaumcraft.Thaumcraft;
import thaumcraft.thaumcraft.client.RenderTypes;
import thaumcraft.thaumcraft.common.init.ItemInit;

@Mod.EventBusSubscriber(modid = Thaumcraft.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)

public class RegisterHud {
    @SubscribeEvent
    public static void renderSelectedBlocks(final RenderLevelStageEvent event) {
        if(event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES)
            return;
        if(Minecraft.getInstance().player != null) {
            Minecraft mc = Minecraft.getInstance();
            PoseStack matrix = event.getPoseStack();
            Vec3 playerPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
            MultiBufferSource.BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();
            matrix.pushPose();
            matrix.translate(-playerPos.x, -playerPos.y, -playerPos.z);
            renderNameTag(mc,matrix,buffer, event.getPartialTick());
        }
    }

    private static void renderNameTag(Minecraft mc, PoseStack matrix, MultiBufferSource.BufferSource buffer, float partialTicks) {
        int combinedLightIn = mc.getEntityRenderDispatcher().getPackedLightCoords(mc.player, partialTicks);
        matrix.translate(0, -60, 0);
        Quaternion q = mc.getEntityRenderDispatcher().cameraOrientation();
        matrix.mulPose(q);
        matrix.scale(-0.025F, -0.025F, 0.025F);
        Matrix4f matrix4f = matrix.last().pose();
        Font fontRenderer = Minecraft.getInstance().font;
        float width = (float)(-fontRenderer.width("test") / 2);
        fontRenderer.drawInBatch("test", width, 0f, -1, false, matrix4f, buffer, true, 0, combinedLightIn);
        matrix.popPose();
    }
//    @SubscribeEvent
//    public static void renderSelectedBlocks(final RenderLevelStageEvent event) {
//        // prevent double render
//        if(event.getStage() == RenderLevelStageEvent.Stage.AFTER_PARTICLES){
//            Minecraft mc = Minecraft.getInstance();
//            int combinedLightIn = mc.getEntityRenderDispatcher().getPackedLightCoords(mc.player, event.getPartialTick());
//            PoseStack matrix = event.getPoseStack();
//            MultiBufferSource.BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();
////            matrix.pushPose();
////            // mirror text
////            matrix.translate(-mc.player.position().x(), -mc.player.position().y(), -mc.player.position().z());
////            matrix.scale(1.1F,1F,1F);
////            // put cords to render text or position
////            matrix.translate(0, -61, 0);
////            Quaternion q = mc.getEntityRenderDispatcher().cameraOrientation();
////
////            // text scale
////            matrix.scale(-0.025F, -0.025F, 0.025F);
////            Matrix4f matrix4f = matrix.last().pose();
////            matrix.mulPose(q);
////            Font fontrenderer = Minecraft.getInstance().font;
////
////            // found width of your text
////            float width = (float)(-fontrenderer.width("testText") / 2);
////            // read about drawInBatch in forgeAPI
////            fontrenderer.drawInBatch("testText", width, 0f, -1, false, matrix4f, buffer, true, 0, combinedLightIn);
////            matrix.popPose();
//
//        }
//
//    }
    // TODO use for node render
//    @SubscribeEvent
//    public static void renderSelectedBlocks(final RenderLevelStageEvent event) {
//        if(event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES)
//            return;
//        if(Minecraft.getInstance().player != null && Minecraft.getInstance().player.getMainHandItem().getItem() == ItemInit.THAUMOMETER.get()) {
//            int combinedLightIn = Minecraft.getInstance().getEntityRenderDispatcher().getPackedLightCoords(Minecraft.getInstance().player, event.getPartialTick());
//            PoseStack matrix = event.getPoseStack();
//            MultiBufferSource.BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();
//            VertexConsumer builder = buffer.getBuffer(RenderTypes.THICK_LINES);
//            Vec3 playerPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
//            BlockPos blocks = new BlockPos(0,-61, 0);
//            AABB box = new AABB(blocks);
//            Matrix4f matrix4f = matrix.last().pose();
//            matrix.pushPose();
//            matrix.translate(-playerPos.x(), -playerPos.y(), -playerPos.z());
//            matrix.scale(1.1F,1F,1F);
//            LevelRenderer.renderLineBox(matrix, builder, box, 1.0F, 0.0F, 0.0F, 1.0F);
////            new ItemRenderer().;
//
////            IClientItemExtensions.FontContext fontrenderer;
//            float width = (float)(-Minecraft.getInstance().font.width("test") / 2);
//            TranslatableContents text = new TranslatableContents("Test");
////            Minecraft.getInstance().font.drawInBatch("test", width, 1F,0,false, matrix4f, buffer, true, 0, combinedLightIn);
////            FontRenderer fontrenderer = Minecraft.getInstance().font;
////            fontrenderer.drawInBatch(text, width, 0f, -1, false, matrix4f, buffer, true, 0, combinedLightIn);
////            LevelRenderer.renderLineBox(matrix,builder, 1D,0D,1D,1D,0D,0D, 1.0F, 0.0F, 0.0F, 1.0F);
//            renderNameTag(matrix, buffer, event.getPartialTick(), 0.0F);
//            matrix.popPose();
//            buffer.endBatch(RenderTypes.THICK_LINES);
//        }
//    }
//
//    private static void renderNameTag(PoseStack ms, MultiBufferSource.BufferSource buffer, float partialTicks, float yOffset) {
//        Minecraft mc = Minecraft.getInstance();
//        var playerLookX = mc.player.getLookAngle().x;
//        var playerLookY = mc.player.getLookAngle().y;
//        var playerLookZ = mc.player.getLookAngle().z;
//        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
//        int combinedLightIn = mc.getEntityRenderDispatcher().getPackedLightCoords(mc.player, partialTicks);
//        ms.pushPose();
// /*       System.out.println(mc.player.getX());*/
//        ms.translate(0, -61, 0);
//        Quaternion q = mc.getEntityRenderDispatcher().cameraOrientation();
//        ms.mulPose(q);
//        ms.scale(-0.025F, -0.025F, 0.025F);
//        Matrix4f matrix4f = ms.last().pose();
//
//        float opacity = Minecraft.getInstance().options.getBackgroundOpacity(0.5F);
//        int alpha = (int)(opacity * 255.0F) << 24;
//        Font fontrenderer = Minecraft.getInstance().font;
//        float width = (float)(-fontrenderer.width("test") / 2);
//        //fontrenderer.drawInBatch(text, f2, i, 553648127, false, matrix4f, buffer, false, j, p_225629_5_);
//
//        //fontrenderer.drawInBatch(text, width, 0f, 553648127, false, matrix4f, buffer, false, alpha, combinedLightIn);
//        fontrenderer.drawInBatch("test", width, 0f, -1, false, matrix4f, buffer, true, 0, combinedLightIn);
//        ms.popPose();
//    }

//    @SubscribeEvent
//    public static void onPostDrawOverlay(ScreenEvent.Render event){
//     // do some checks to see if the inventory screen is open
//        if(!event.isCanceled()) {
//            var tesselator = Tesselator.getInstance();
//            RenderSystem.disableDepthTest();
//            RenderSystem.depthMask(false);
//            RenderSystem.enableBlend();
//            RenderSystem.defaultBlendFunc();
//            RenderSystem.setShader(GameRenderer::getPositionTexShader);
//            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 0.7f); // I set the opacity of the draw here
//
//            RenderSystem.setShaderTexture(0, new ResourceLocation(Thaumcraft.MODID, "textures/aspects/terra.png"));
//
//            // Do scaling
//            double scaledWidth = 32;
//            double scaledHeight = 32;
//
//            // Do a blit
//            BufferBuilder builder = tesselator.getBuilder();
//            builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
//            builder.vertex(10.0D, scaledHeight, -90.0D).uv(0.0F, 1.0F).endVertex();
//            builder.vertex(scaledWidth, scaledHeight, -90.0D).uv(1.0F, 1.0F).endVertex();
//            builder.vertex(scaledWidth, 0.0D, -90.0D).uv(1.0F, 0.0F).endVertex();
//            builder.vertex(20.0D, 0.0D, -90.0D).uv(0.0F, 0.0F).endVertex();
//            tesselator.end();
//
//            RenderSystem.depthMask(true);
//            RenderSystem.enableDepthTest();
//            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
//            RenderSystem.disableBlend();
//        }
//    }
//    @SubscribeEvent
//    public static void onStart(ServerStartedEvent e){
//
//        Minecraft.getInstance().level.;
//        e.getServer().getCommandManager().executeCommand(MinecraftServer.getServer(), "help");
//    }
}