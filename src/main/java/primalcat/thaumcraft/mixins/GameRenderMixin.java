package primalcat.thaumcraft.mixins;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import primalcat.thaumcraft.init.ItemInit;
import primalcat.thaumcraft.utilites.Variables;

@Mixin(GameRenderer.class)
public class GameRenderMixin {
    @Inject(method = "bobView", at = @At("HEAD"), cancellable = true)
    private void cancelBobView(PoseStack poseStack, float tick, CallbackInfo ci) {
        // Cancel the original bobView method by not executing its content
        if (Minecraft.getInstance().getCameraEntity() instanceof Player) {
            // Add your custom code here if needed
            if (Minecraft.getInstance().player.getMainHandItem().getItem().asItem().equals(ItemInit.THAUMOMETER.get().asItem()) ){
                Variables.partialTick = tick;
//                ci.cancel();
            }

        }
        // You can add custom code here if needed
    }
}
