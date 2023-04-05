package hesio.thaumcraft.mixins;


import hesio.thaumcraft.Thaumcraft;
import net.minecraft.client.gui.screens.TitleScreen;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class MixinTest {
    @Shadow @Final private static Logger LOGGER;

    @Inject(method = "init", at = @At("TAIL"))
    public void exampleMod$onInit(CallbackInfo ci) {
        LOGGER.info("mixin start");
    }
}
