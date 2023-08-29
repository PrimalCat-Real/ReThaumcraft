package primalcat.thaumcraft.api;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.block.state.BlockState;
import primalcat.thaumcraft.Thaumcraft;
import primalcat.thaumcraft.sound.ModSounds;
import primalcat.thaumcraft.networking.ModMessages;
import primalcat.thaumcraft.networking.packets.SyncPlayerApsectsCapability;

public class ScanHelper {

    private static final int SCAN_DURATION = 34;
    private boolean canDoScan = true;

    private boolean scanCompleted = false;

    public boolean isScanCompleted() {
        return scanCompleted;
    }

    public void setScanCompleted(boolean scanCompleted) {
        this.scanCompleted = scanCompleted;
    }

    public boolean isCanDoScan() {
        return canDoScan;
    }

    public void setCanDoScan(boolean canDoScan) {
        this.canDoScan = canDoScan;
    }

    private LivingEntity entityTarget = null;
    private ItemEntity itemEntityTarget = null;

    private BlockState blockTarget = null;



    public void setBlockTarget(BlockState blockTarget) {
        if(blockTarget == this.blockTarget){
            this.canDoScan = false;
        }
        this.blockTarget = blockTarget;
    }


    public void setItemEntityTarget(ItemEntity itemEntityTarget) {
        this.itemEntityTarget = itemEntityTarget;
    }


    public void setEntityTarget(LivingEntity entityTarget) {
        this.entityTarget = entityTarget;
    }

    private boolean isScanning = false;

    private String targetName;

    private String dynamicTargetName;


    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public void setScanning(boolean scanning) {
        isScanning = scanning;
    }

    private int ticksHovered;
    private Slot lastScannedSlot;


    public boolean isValidScanTarget(String targetName){

        return false;
    }

    public void doInventoryScan(int tick, Player player){
        System.out.println(tick);
//        if(this.isValidScanTarget()){
//
//        }
        if(tick == SCAN_DURATION){
            player.getLevel().playSound(player,player.getX(), player.getY(), player.getZ(), ModSounds.learn.get(), SoundSource.MASTER, 0.4f,0.45f + player.level.random.nextFloat() * 0.1f);
            System.out.println("Scan done");
            this.setScanCompleted(true);
        }else if (tick % 5 == 0){
            player.getLevel().playSound(player,player.getX(), player.getY(), player.getZ(), ModSounds.cameraticks.get(), SoundSource.MASTER, 0.2f,0.45f + player.level.random.nextFloat() * 0.1f);
        }
//        dText.setStartDraw(true);
    }
    public void doScan(){

//        if(targetName == dynamicTargetName){
//            this.canDoScan = false;
//        }
    }

    public void syncPlayerAspectsFromScan(){

        if(entityTarget != null && !(entityTarget instanceof Player)){
            String targetName = AspectHelper.getTarget(entityTarget);
            AspectList  aspectList = AspectHelper.getAspectsFromEntity(entityTarget);

            Thaumcraft.LOGGER.info(targetName);
            if(!aspectList.isEmpty() && targetName != null){
                ModMessages.sendToServer(new SyncPlayerApsectsCapability(aspectList, targetName));
            }
        } else if (entityTarget != null && entityTarget instanceof Player) {
            System.out.println("Player name");
        } else if (itemEntityTarget != null) {
            String targetName = AspectHelper.getTarget(itemEntityTarget.getItem());
            this.targetName = targetName;
            AspectList aspectList = AspectHelper.getAspectsFromObject(itemEntityTarget.getItem());
            Thaumcraft.LOGGER.info(targetName);
            if(!aspectList.isEmpty() && targetName != null){
                ModMessages.sendToServer(new SyncPlayerApsectsCapability(aspectList, targetName));
            }
        } else if (blockTarget != null) {
            String targetName = AspectHelper.getTarget(blockTarget);
            AspectList  aspectList = AspectHelper.getAspectsFromBlock(blockTarget);
            Thaumcraft.LOGGER.info(targetName);
            if(!aspectList.isEmpty() && targetName != null){
                ModMessages.sendToServer(new SyncPlayerApsectsCapability(aspectList, targetName));
            }
        }
    }
}
