package thaumcraft.thaumcraft.common;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

import java.util.logging.Level;

public class CommonProxy {

    public void onWorldLoad()
    {
    }

    public void resetManual()
    {
    }

    public void handleTileSound(SoundEvent soundEvent, BlockEntity tile, boolean tileActive, float volume, float pitch)
    {
    }

    public void stopTileSound(String soundName, BlockEntity tile)
    {
    }

    public void spawnFractalFX(Level world, double x, double y, double z, Vec3 direction, double scale, int prefixColour, float[][] colour)
    {

    }
    public Level getClientWorld()
    {
        return null;
    }

    public Player getClientPlayer()
    {
        return null;
    }

    public void reInitGui()
    {
    }

    public void clearRenderCaches()
    {
    }


    public void openManual()
    {

    }

    public void openTileScreen(String guiId, BlockEntity tileEntity)
    {
    }
}
