package thaumcraft.thaumcraft.client.event;



import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import thaumcraft.thaumcraft.Thaumcraft;
import thaumcraft.thaumcraft.common.init.ItemInit;
import thaumcraft.thaumcraft.sound.ModSounds;
import thaumcraft.thaumcraft.util.DoScan;
import thaumcraft.thaumcraft.util.DrawText;

@Mod.EventBusSubscriber(modid = Thaumcraft.MODID, value = Dist.CLIENT)
public class InventoryScanning {
    private static Slot tempSlot = null;
    private static ScreenEvent.Render.Post drawScreenEvent = null;
    private static final int SCAN_TICK = 40;
    private static final int SOUND_TICKS = 3;
    private static int ticksHovered;
    private static DoScan currentScan;
    private static Slot lastScannedSlot;
    private static boolean canScan;
    private static DrawText dText = new DrawText();
    /** Ticking event
     * for do scan **/
    @SubscribeEvent
    public static void onClientTick(final TickEvent.ClientTickEvent event) {

        Player player = Minecraft.getInstance().player;
        if(isHoldingThaumometer(player)){
            /** Get working slot,
             * empty slot,
             * null slot**/
            tempSlot = ((AbstractContainerScreen<?>) drawScreenEvent.getScreen()).getSlotUnderMouse();
            if(tempSlot != null && !tempSlot.getItem().getItem().equals(Items.AIR)){
                if(lastScannedSlot != tempSlot){
                    dText.setStartDraw(true);
                    // custom tick for tick logic
                    ticksHovered ++;
                    // set progress for dot animation in Scaning text
                    dText.setProgress(ticksHovered / (float) SCAN_TICK);
                    // play sound event while scan
                    if(ticksHovered > SOUND_TICKS && ticksHovered % 4 == 0){
                        player.level.playSound(player,player.getX(), player.getY(), player.getZ(), ModSounds.cameraticks.get(), SoundSource.NEUTRAL, 0.2f,0.45f + player.level.random.nextFloat() * 0.1f);
                    }
                    // scan 40 tick or 2 second
                    if(ticksHovered > SCAN_TICK){
                        Item scanItem = tempSlot.getItem().getItem();
                        lastScannedSlot = tempSlot;
                        // stop render scan process text
                        dText.setStartDraw(false);
                    }
                }else{
                    // reset scan progress
                    ticksHovered = 0;
                    dText.setProgress(0);
                    dText.setStartDraw(false);
                }
            }else if(tempSlot == null || tempSlot.getItem().getItem().equals(Items.AIR)){
                lastScannedSlot = null;
                dText.setProgress(0);
                dText.setStartDraw(false);
                ticksHovered = 0;
                // reset scan progress and draw process text
            }
        }else if(!isHoldingThaumometer(player)){
            // reset scan progress and draw process text when not hold thaumometer
            ticksHovered = 0;
            currentScan = null;
            canScan = true;
            dText.setStartDraw(false);
            dText.setProgress(0);
        }

    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onInventorySlotInteract(final ScreenEvent.Render.Post event) {
        if(!event.isCanceled()) {
            Slot slot;
            Player player;
            // many checks for different screens types
            if(event.getScreen() instanceof ContainerScreen){
                ContainerScreen containerScreen = (ContainerScreen)event.getScreen();
                slot = containerScreen.getSlotUnderMouse();
                player = Minecraft.getInstance().player;
            }else if(event.getScreen() instanceof InventoryScreen){
                InventoryScreen inventoryScreen = (InventoryScreen)event.getScreen();
                slot = inventoryScreen.getSlotUnderMouse();
                player = Minecraft.getInstance().player;
            }else if(event.getScreen() instanceof CreativeModeInventoryScreen){
                CreativeModeInventoryScreen creativeModeInventoryScreen = (CreativeModeInventoryScreen)event.getScreen();
                slot = creativeModeInventoryScreen.getSlotUnderMouse();
                player = Minecraft.getInstance().player;
            }else {
                slot = null;
                player = null;
            }
            // start draw process text
            if(slot != null && slot.isActive() && player != null){
                drawScreenEvent = event;
//                Minecraft.getInstance().font.drawShadow(drawScreenEvent.getPoseStack(),"test", 140-10 , 150-20, new Color(250,0,0).getRGB());
                DrawText.setPoseStack(drawScreenEvent.getPoseStack());
                dText.renderScanningProgress(drawScreenEvent.getPoseStack(), drawScreenEvent.getMouseX(), drawScreenEvent.getMouseY());
//                drawScreenEvent.getScreen().renderTooltip(drawScreenEvent.getPoseStack(), drawScreenEvent.getMouseX(), drawScreenEvent.getMouseY());
//                renderScanningProgress(drawScreenEvent.getPoseStack(), drawScreenEvent.getMouseX(), drawScreenEvent.getMouseY(), 0);

            }

        }
    }

    /** Check if player hold theumometer
     * by cursor in inventory **/
    public static boolean isHoldingThaumometer(Player player) {
        if (player == null) {
            return false;
        }
        // get player item in mouse
        ItemStack mouseItem = player.containerMenu.getCarried();
        return !mouseItem.isEmpty() && mouseItem.getItem().equals(ItemInit.THAUMOMETER.get());
    }
}

