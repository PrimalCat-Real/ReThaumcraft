package hesio.thaumcraft.client.events;

import hesio.thaumcraft.Thaumcraft;
import hesio.thaumcraft.inits.ItemInit;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Thaumcraft.MODID, value = Dist.CLIENT)
public class PlayerRenderHandler {

    @SubscribeEvent
    public static void onPlayerRenderPre(RenderPlayerEvent.Pre event) {
        Player player = event.getEntity();
        PlayerModel<?> model = event.getRenderer().getModel();

        // Check if the player is holding THAUMOMETER item in either hand
        boolean holdingYourItem = player.isHolding(ItemInit.THAUMOMETER.get());

        // Change the left arm pose based on whether the player is holding THAUMOMETER
        model.leftArmPose = holdingYourItem ? HumanoidModel.ArmPose.BOW_AND_ARROW : HumanoidModel.ArmPose.EMPTY;
    }


}
