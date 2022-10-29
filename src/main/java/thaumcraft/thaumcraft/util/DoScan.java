package thaumcraft.thaumcraft.util;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import thaumcraft.thaumcraft.common.aspects.Aspect;
import thaumcraft.thaumcraft.common.aspects.AspectEventRegister;
import thaumcraft.thaumcraft.common.aspects.AspectList;
import thaumcraft.thaumcraft.common.init.ItemInit;
import thaumcraft.thaumcraft.sound.ModSounds;

public class DoScan {
    private Item scanItem;
    private static AspectList aspectList;

    public boolean canScan(){
        return false;
    }

    public DoScan() {
    }

    public void playScanningSound(Player player){
        player.level.playSound(player,player.getX(), player.getY(), player.getZ(), ModSounds.cameraticks.get(), SoundSource.NEUTRAL, 0.2f,0.45f + player.level.random.nextFloat() * 0.1f);
    }

    public void playScanCompleted(Player player){
        player.level.playSound(player,player.getX(), player.getY(), player.getZ(), ModSounds.learn.get(), SoundSource.NEUTRAL, 0.9f,0.45f + player.level.random.nextFloat() * 0.1f);
    }

    public boolean isHoldingThaumometer(Player player) {
        if (player == null) {
            return false;
        }
        // get player item in mouse
        ItemStack mouseItem = player.containerMenu.getCarried();
        return !mouseItem.isEmpty() && mouseItem.getItem().equals(ItemInit.THAUMOMETER.get());
    }

}
