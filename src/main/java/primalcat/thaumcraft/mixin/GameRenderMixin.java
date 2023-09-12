package primalcat.thaumcraft.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import primalcat.thaumcraft.client.ScanManager;
import primalcat.thaumcraft.core.registry.ItemRegistry;

@Mixin(GameRenderer.class)
public class GameRenderMixin {
    @Inject(method = "bobView", at = @At("HEAD"), cancellable = true)
    private void cancelBobView(PoseStack poseStack, float tick, CallbackInfo ci) {
        // Cancel the original bobView method by not executing its content
        if (Minecraft.getInstance().getCameraEntity() instanceof Player) {
            if (Minecraft.getInstance().player.getMainHandItem().getItem().asItem().equals(ItemRegistry.THAUMOMETER.get().asItem()) ){
                ScanManager.partialTick = tick;
//                ci.cancel();
            }

        }
    }
}
