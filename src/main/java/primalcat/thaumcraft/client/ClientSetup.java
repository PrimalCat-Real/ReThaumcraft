package primalcat.thaumcraft.client;

import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import primalcat.thaumcraft.client.render.overlays.ThaumcraftOverlay;

public class ClientSetup {
    public static void onModConstruction() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::registerGuiOverlays);

    }
    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAbove(VanillaGuiOverlay.CROSSHAIR.id(), "thaumcraftoverlay", new ThaumcraftOverlay());
    }
}
