package primalcat.thaumcraft.client;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.event.ScreenEvent;
import primalcat.thaumcraft.aspects.Aspect;
import primalcat.thaumcraft.aspects.AspectList;
import primalcat.thaumcraft.config.ThaumcraftClientConfig;
import primalcat.thaumcraft.init.AspectInit;
import primalcat.thaumcraft.init.ItemInit;
import primalcat.thaumcraft.networking.PacketManager;
import primalcat.thaumcraft.networking.packets.SyncPlayerApsectsCapability;
import primalcat.thaumcraft.sound.ModSounds;
import primalcat.thaumcraft.utilites.DrawInvScanProgress;

import java.util.*;

public class ScanManager {
//    public static final int THAUMOMETER_SCAN_DURATION = ThaumcraftClientConfig.THAUMOMETER_SCAN_DURATION.get();
//
//    public static final int SCAN_SOUND_TICKS = 5;

    public static DrawInvScanProgress drawInvScanProgress = new DrawInvScanProgress();

    private static int hoverTick = 0;
    private static String targetNameForRender = "";

    private static AspectList targetAspectsForRender = null;

    private static boolean isScanning = false;

    // values used to define
    public static final int INVENTORY_PLAYER_X = 26;
    public static final int INVENTORY_PLAYER_Y = 8;
    public static final int INVENTORY_PLAYER_WIDTH = 52;
    public static final int INVENTORY_PLAYER_HEIGHT = 70;

    // client player data for do scan
    private static AspectList playerAspects;
    private static List<String> playerScannedObjects = new ArrayList<>();
    /**
     * Sets the hover tick count.
     *
     * @param hoverTick The hover tick count to set.
     */
    public static void setHoverTick(int hoverTick) {
        ScanManager.hoverTick = hoverTick;
    }

    /**
     * Sets whether the player is currently scanning.
     *
     * @param isScanning True if the player is scanning; false otherwise.
     */
    public static void setIsScanning(boolean isScanning) {
        ScanManager.isScanning = isScanning;
    }

    /**
     * Gets the current hover tick count.
     *
     * @return The current hover tick count.
     */
    public static int getHoverTick() {
        return hoverTick;
    }

    /**
     * Checks if the player is currently scanning.
     *
     * @return True if the player is scanning; false otherwise.
     */
    public static boolean isScanning() {
        return isScanning;
    }

    /**
     * Gets the target aspects for rendering.
     *
     * @return The target aspects for rendering.
     */
    public static AspectList getTargetAspectsForRender() {
        return targetAspectsForRender;
    }

    /**
     * Sets the target aspects for rendering.
     *
     * @param targetAspectsForRender The target aspects for rendering.
     */
    public static void setTargetAspectsForRender(AspectList targetAspectsForRender) {
        ScanManager.targetAspectsForRender = targetAspectsForRender;
    }

    /**
     * Gets the target name for rendering.
     *
     * @return The target name for rendering.
     */
    public static String getTargetNameForRender() {
        return targetNameForRender;
    }

    /**
     * Sets the target name for rendering.
     *
     * @param targetNameForRender The target name for rendering.
     */
    public static void setTargetNameForRender(String targetNameForRender) {
        ScanManager.targetNameForRender = targetNameForRender;
    }


    /**
     * Gets the player aspects.
     *
     * @return The player aspects.
     */
    public static AspectList getPlayerAspects() {
        return playerAspects;
    }

    /**
     * Sets the player aspects.
     *
     * @param playerAspects The player aspects to set.
     */
    public static void setPlayerAspects(AspectList playerAspects) {
        ScanManager.playerAspects = playerAspects;
    }
    /**
     * Adds player aspects to the existing player aspects.
     *
     * @param playerAspects The player aspects to add.
     */
    public static void addPlayerAspects(AspectList playerAspects) {
        ScanManager.playerAspects.merge(playerAspects);
    }

    /**
     * Sets the player aspects from a map.
     *
     * @param setPlayerAspects The map of player aspects to set.
     */
    public static void setPlayerAspects(Map<String, Integer> setPlayerAspects) {
        ScanManager.playerAspects = fromMap(setPlayerAspects);
    }

    /**
     * Gets the list of player-scanned objects.
     *
     * @return The list of player-scanned objects.
     */
    public static List<String> getPlayerScannedObjects() {
        return ScanManager.playerScannedObjects;
    }

    /**
     * Adds a player-scanned object to the list.
     *
     * @param scannedObject The object to add.
     */
    public static void addPlayerScannedObjects(String scannedObject) {
        ScanManager.playerScannedObjects.add(scannedObject);
    }
    /**
     * Sets the list of player-scanned objects.
     *
     * @param playerScannedObjects The list of player-scanned objects to set.
     */
    public static void setPlayerScannedObjects(List<String> playerScannedObjects) {
        ScanManager.playerScannedObjects = playerScannedObjects;
    }

    /**
     * Checks if aspects of an object are known to the player.
     *
     * @param aspects The object to check.
     * @return True if the aspects of the object are known; false otherwise.
     */
    public static boolean isObjectAspectsKnown(AspectList aspects){
        if(aspects.isEmpty()){
            return false;
        }
        for (Aspect aspect: aspects.aspects.keySet()) {
            if(!aspect.isPrimal() && ScanManager.playerAspects.aspects.get(aspect) == null){
//                ThaumcraftOverlay.addTextForRender("You don't have aspects for this block");
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the object has been scanned by the player.
     *
     * @param object The object to check.
     * @return True if the object has been scanned; false otherwise.
     */
    public static boolean isScannedObject(String object){
        return ScanManager.playerScannedObjects.contains(object);
    }

    /**
     * Performs scanning during a tick of player action.
     *
     * @param player           The player performing the scan.
     * @param tick             The current tick.
     * @param scanTargetName   The name of the scanned target.
     * @param scanTargetAspects The aspects of the scanned target.
     */
    public static void doScan(Player player, int tick, String scanTargetName, AspectList scanTargetAspects){
        if(tick == 1 && scanTargetAspects != null && ScanManager.isObjectAspectsKnown(scanTargetAspects)){
            player.getLevel().playSound(player,player.getX(), player.getY(), player.getZ(), ModSounds.learn.get(), SoundSource.MASTER, 0.4f,0.45f + player.level.random.nextFloat() * 0.1f);
            ScanManager.addPlayerAspects(scanTargetAspects);
            ScanManager.addPlayerScannedObjects(scanTargetName);
            PacketManager.sendToServer(new SyncPlayerApsectsCapability(scanTargetAspects.toMap(), scanTargetName));

        }else if (tick % 5 == 0){
            player.getLevel().playSound(player,player.getX(), player.getY(), player.getZ(), ModSounds.cameraticks.get(), SoundSource.MASTER, 0.2f,0.45f + player.level.random.nextFloat() * 0.1f);
        }
    }

    /**
     * Performs scanning when rendering a screen.
     *
     * @param player           The player performing the scan.
     * @param scanTargetName   The name of the scanned target.
     * @param scanTargetAspects The aspects of the scanned target.
     * @param drawScreenEvent   The render event for the screen.
     */
    public static void doScan(Player player, String scanTargetName, AspectList scanTargetAspects, ScreenEvent.Render.Post drawScreenEvent){
        if(ScanManager.isObjectAspectsKnown(scanTargetAspects) && !ScanManager.isScannedObject(scanTargetName)){
            drawInvScanProgress.setCanDraw(true);
//            if(drawScreenEvent.getScreen() instanceof InventoryScreen || drawScreenEvent.getScreen() instanceof AbstractContainerScreen<?> || drawScreenEvent.getScreen() instanceof ContainerScreen || drawScreenEvent.getScreen() instanceof CreativeModeInventoryScreen){
//                drawInvScanProgress.renderScanningProgress(drawScreenEvent.getPoseStack(), drawScreenEvent.getMouseX(), drawScreenEvent.getMouseY(), hoverTick);
//            }
            hoverTick+=1;
            isScanning = true;
        } else{
            hoverTick = 0;
            drawInvScanProgress.setCanDraw(false);
            isScanning = false;
        }
        if(hoverTick == ThaumcraftClientConfig.THAUMOMETER_SCAN_DURATION.get() && ScanManager.isObjectAspectsKnown(scanTargetAspects)){
            player.getLevel().playSound(player,player.getX(), player.getY(), player.getZ(), ModSounds.learn.get(), SoundSource.MASTER, 0.4f,0.45f + player.level.random.nextFloat() * 0.1f);
            hoverTick = 0;
            drawInvScanProgress.setCanDraw(false);
            isScanning = false;
            ScanManager.addPlayerAspects(scanTargetAspects);
            ScanManager.addPlayerScannedObjects(scanTargetName);
            PacketManager.sendToServer(new SyncPlayerApsectsCapability(scanTargetAspects.toMap(), scanTargetName));
        }else if (hoverTick % 5 == 1){
            player.getLevel().playSound(player,player.getX(), player.getY(), player.getZ(), ModSounds.cameraticks.get(), SoundSource.MASTER, 0.2f,0.45f + player.level.random.nextFloat() * 0.1f);
        }

    }

    /**
     * Converts a map of aspects to an AspectList.
     *
     * @param map The map of aspects to convert.
     * @return An AspectList containing the converted aspects.
     */
    public static AspectList fromMap(Map<String, Integer> map) {
        AspectList aspectList = new AspectList();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String aspectName = entry.getKey();
            int amount = entry.getValue();
            Aspect aspect = AspectInit.getAspect(aspectName);
            if (aspect != null) {
                aspectList.add(aspect, amount);
            }
        }
        return aspectList;
    }

    // get values from target

    public static AspectList getAspectFromObject(Slot object){
        return getAspectFromObject(object.getItem());
    }
    public static AspectList getAspectFromObject(ItemEntity object){
        ItemStack itemStack = new ItemStack(object.getItem().getItem());
        return getAspectFromObject(itemStack);
    }
    public static AspectList getAspectFromObject(ItemStack object){
        AspectList objectAspects = new AspectList();
        LinkedHashMap<String, AspectList> itemsAspects = AspectInit.getItemAspects();

        if(itemsAspects.get(object.getItem().toString()) != null){
            LinkedHashMap<Aspect, Integer> tempAspects = AspectInit.getItemAspects().get(object.getItem().toString()).aspects;
            addAspectsFromTarget(objectAspects, tempAspects);
        }else{
            for (var tag: object.getTags().toList()) {
                if(AspectInit.getItemAspects().containsKey(tag.location().toString())){
                    LinkedHashMap<Aspect, Integer> tempAspects = AspectInit.getItemAspects().get(tag.location().toString()).aspects;
                    addAspectsFromTarget(objectAspects, tempAspects);
//                    System.out.println(AspectInit.getItemAspects().get(tag.location().toString()).aspects);
                }
            }
        }

        return objectAspects;
    }
    public static AspectList getAspectsFromEntity(LivingEntity entity) {
        if(entity instanceof Player player){
            return getAspectFromPlayer(player);
        }
        AspectList objectAspects = new AspectList();

        String localizedName = entity.getDisplayName().getString();
        localizedName = I18n.get(localizedName); // To remove formatting
        LinkedHashMap<String, AspectList> entityAspects = AspectInit.getEntityAspects();
        if(entityAspects.get(localizedName) != null){
            LinkedHashMap<Aspect, Integer> tempAspects = AspectInit.getEntityAspects().get(localizedName).aspects;
            addAspectsFromTarget(objectAspects, tempAspects);
        }
//        System.out.println(localizedName);
        return objectAspects;
    }
    public static AspectList getAspectFromObject(LivingEntity object){
        return getAspectsFromEntity(object);
    }
    public static AspectList getAspectFromPlayer(Player player){
        AspectList objectAspects = new AspectList();
        LinkedHashMap<String, AspectList> entityAspects = AspectInit.getEntityAspects();
        objectAspects.add(AspectInit.MAN, 4);
        return objectAspects;
    }
    public static AspectList getAspectFromObject(BlockState object){
        AspectList objectAspects = new AspectList();
        FluidState fluidState = object.getFluidState();
        String localizedName;
        if (fluidState.getType() != Fluids.EMPTY) {
            localizedName =  fluidState.getFluidType().toString();
        }else{
            Item item = Item.byBlock(object.getBlock());
            localizedName = item.toString();
        }

//        System.out.println(blockState.getBlock());
        LinkedHashMap<String, AspectList> itemsAspects = AspectInit.getItemAspects();
        if(itemsAspects.get(localizedName) != null){
//            LinkedHashMap<Aspect, Integer> tempAspects = AspectInit.getItemAspects().get(itemStack.toString()).aspects;
            LinkedHashMap<Aspect, Integer> tempAspects = AspectInit.getItemAspects().get(localizedName).aspects;
//            for (Aspect aspect : tempAspects.keySet()) {
//                int amount = tempAspects.get(aspect);
//                objectAspects.add(aspect, amount);
//            }
            addAspectsFromTarget(objectAspects, tempAspects);
        }else{
            for (var tag: object.getTags().toList()) {
                if(AspectInit.getItemAspects().containsKey(tag.location().toString())){
                    LinkedHashMap<Aspect, Integer> tempAspects = AspectInit.getItemAspects().get(tag.location().toString()).aspects;
                    addAspectsFromTarget(objectAspects, tempAspects);
                }

            }
        }
        return objectAspects;
    }

    private static void addAspectsFromTarget(AspectList objectAspects, LinkedHashMap<Aspect, Integer> aspects) {
        for (Aspect aspect : aspects.keySet()) {
            int amount = aspects.get(aspect);
            objectAspects.add(aspect, amount);
        }
    }
    public static String getTargetStringName(Entity entity){
        String localizedName = entity.getDisplayName().getString();
        localizedName = I18n.get(localizedName); // To remove formatting
        return localizedName;
    }

    public static String getTargetStringName(ItemEntity itemEntity){
        return itemEntity.getItem().getItem().toString();
    }
    public static String getTargetStringName(ItemStack itemStack){
        return itemStack.getItem().toString();
    }
    public static String getTargetStringName(BlockState blockState){
        FluidState fluidState = blockState.getFluidState();
        String localizedName;
        if (fluidState.getType() != Fluids.EMPTY) {
            localizedName =  fluidState.getFluidType().toString();
        }else{
            Item item = Item.byBlock(blockState.getBlock());
            localizedName = item.toString();
        }
        return localizedName;
    }

    public static boolean isHoldingThaumometer(Player player) {
        if (player == null) {
            return false;
        }

        ItemStack mouseItem = player.getMainHandItem();

        return !mouseItem.isEmpty() && mouseItem.getItem() == ItemInit.THAUMOMETER.get();

    }

    public static boolean isHoveringPlayer(Screen screen, int mouseX, int mouseY) {
        if (screen instanceof InventoryScreen) {
            return mouseY >= ((InventoryScreen) screen).getGuiTop() + INVENTORY_PLAYER_Y &&  mouseY <= ((InventoryScreen) screen).getGuiTop() + INVENTORY_PLAYER_Y + INVENTORY_PLAYER_HEIGHT &&
                    mouseX >= ((InventoryScreen) screen).getGuiLeft() + INVENTORY_PLAYER_X && mouseX <= ((InventoryScreen) screen).getGuiLeft() + INVENTORY_PLAYER_X + INVENTORY_PLAYER_WIDTH;
        }
        return false;
    }

    public static boolean isMouseHoldingThaumometer(Player player) {
        if (player == null) {
            return false;
        }
        // get player item in mouse
        ItemStack mouseItem = player.containerMenu.getCarried();
        return !mouseItem.isEmpty() && mouseItem.getItem().equals(ItemInit.THAUMOMETER.get());
    }
}