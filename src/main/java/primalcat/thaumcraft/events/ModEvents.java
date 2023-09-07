package primalcat.thaumcraft.events;


import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;
import primalcat.thaumcraft.Thaumcraft;
import primalcat.thaumcraft.common.capability.aspects.PlayerAspectsProvider;
import primalcat.thaumcraft.common.commands.AspectCommand;
import primalcat.thaumcraft.common.commands.TargetCommand;

@Mod.EventBusSubscriber(modid = Thaumcraft.MOD_ID)
public class ModEvents {
    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(PlayerAspectsProvider.PLAYER_ASPECTS).isPresent()) {
                System.out.println("Attaching PlayerAspects capability to player");
//                event.addCapability(new ResourceLocation(Thaumcraft.MOD_ID, "properties"), new PlayerThirstProvider());
                event.addCapability(new ResourceLocation(Thaumcraft.MOD_ID, "properties"), new PlayerAspectsProvider());
            }
        }
    }


    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().reviveCaps(); // Revive the old capabilities
            System.out.println("Original Capability Present: " + (event.getEntity().getCapability(PlayerAspectsProvider.PLAYER_ASPECTS).isPresent()));
            event.getOriginal().getCapability(PlayerAspectsProvider.PLAYER_ASPECTS).ifPresent(oldStore -> {
                event.getEntity().getCapability(PlayerAspectsProvider.PLAYER_ASPECTS).ifPresent(newStore -> {
                    System.out.println("Death from event");
                    newStore.copyFrom(oldStore);
                });
            });
            event.getOriginal().invalidateCaps();
        }

    }
    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event){
        new AspectCommand(event.getDispatcher());
        new TargetCommand(event.getDispatcher());
        ConfigCommand.register(event.getDispatcher());
    }


//    private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(Thaumcraft.MOD_ID, "textures/aspects/aer.png");
//
//    @SubscribeEvent
//    public static void renderSelectedBlocks(final RenderLevelStageEvent event) {
//        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES)
//            return;
//        if(Minecraft.getInstance().player != null) {
//            Minecraft mc = Minecraft.getInstance();
//            PoseStack matrix = event.getPoseStack();
//            Vec3 playerPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
//            MultiBufferSource.BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();
//            matrix.pushPose();
//            matrix.translate(-playerPos.x, -playerPos.y, -playerPos.z);
//            int combinedLightIn = mc.getEntityRenderDispatcher().getPackedLightCoords(mc.player, event.getPartialTick());
//            matrix.translate(0, -59, 0);
//            Quaternion q = mc.getEntityRenderDispatcher().cameraOrientation();
//            matrix.mulPose(q);
//            matrix.scale(-0.125F, -0.125F, 0.125F);
//            Matrix4f matrix4f = matrix.last().pose();
//            float opacity = Minecraft.getInstance().options.getBackgroundOpacity(0.5F);
//            Font fontRenderer = Minecraft.getInstance().font;
//            float width = (float) (-fontRenderer.width("test") / 2);
//            fontRenderer.drawInBatch("test", width, 0f, -1, false, matrix4f, buffer, true, 0, combinedLightIn);
//
//
//
////            VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityTranslucent(TEXTURE_LOCATION));
////            vertexConsumer.vertex(matrix.last().pose(), 0.0F, -59F, 0.0F)
////                    .color(255, 255, 255, 255) // Set your desired color and opacity
////                    .uv(0F, 0F)
////                    .overlayCoords(OverlayTexture.NO_OVERLAY)
////                    .uv2(combinedLightIn)
////                    .normal(matrix.last().normal(), 0.0F, 1.0F, 0.0F)
////                    .endVertex();
//            matrix.popPose();
//        }
//    }
//    @SubscribeEvent
//    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
//        if(event.side == LogicalSide.SERVER) {
//            event.player.getCapability(PlayerAspectsProvider.PLAYER_ASPECTS).ifPresent(thirst -> {
//                event.player.sendSystemMessage(Component.literal("Subtracted Thirst"));
//            });
//        }
//    }

//    @SubscribeEvent
//    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
//        event.register(PlayerAspects.class);
//        event.register(PlayerThirst.class);
//    }
}
