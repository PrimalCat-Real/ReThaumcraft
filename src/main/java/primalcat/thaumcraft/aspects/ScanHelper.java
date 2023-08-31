package primalcat.thaumcraft.aspects;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.block.state.BlockState;
import primalcat.thaumcraft.Thaumcraft;
import primalcat.thaumcraft.client.ClientAspectManager;
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


    public void setEntityTarget(LivingEntity entityTarget) {
        this.entityTarget = entityTarget;
    }

    private boolean isScanning = false;

    private String targetName;

    private String dynamicTargetName;


    public void doInventoryScan(int tick, Player player, String targetName){
        if(ClientAspectManager.checkTarget(targetName)){
            this.setCanDoScan(false);
        }else {
            this.setCanDoScan(true);
        }
        if(tick == SCAN_DURATION && this.canDoScan){
            player.getLevel().playSound(player,player.getX(), player.getY(), player.getZ(), ModSounds.learn.get(), SoundSource.MASTER, 0.4f,0.45f + player.level.random.nextFloat() * 0.1f);
            System.out.println("Scan done");
            ClientAspectManager.addTarget(targetName);

            this.setScanCompleted(true);

        }else if (tick % 5 == 0 && this.canDoScan){
            player.getLevel().playSound(player,player.getX(), player.getY(), player.getZ(), ModSounds.cameraticks.get(), SoundSource.MASTER, 0.2f,0.45f + player.level.random.nextFloat() * 0.1f);
        }
//        dText.setStartDraw(true);
    }
    public void doScan(int tick, Player player, String targetName){
        if(ClientAspectManager.checkTarget(targetName)){
            this.setCanDoScan(false);
        }else {
            this.setCanDoScan(true);
        }
        if(tick == SCAN_DURATION && this.canDoScan){
            player.getLevel().playSound(player,player.getX(), player.getY(), player.getZ(), ModSounds.learn.get(), SoundSource.MASTER, 0.4f,0.45f + player.level.random.nextFloat() * 0.1f);
            System.out.println("Scan done");
            ClientAspectManager.addTarget(targetName);
            this.setScanCompleted(true);

        }else if (tick % 5 == 0 && this.canDoScan){
            player.getLevel().playSound(player,player.getX(), player.getY(), player.getZ(), ModSounds.cameraticks.get(), SoundSource.MASTER, 0.2f,0.45f + player.level.random.nextFloat() * 0.1f);
        }
    }

    public boolean IsValidAspect(AspectList aspectList){
        boolean isValid = false;
        for (Aspect aspect: aspectList.aspects.keySet()) {
            if( aspect.isPrimal()){
                isValid = true;
            }else{
                for (Aspect componentAspect: aspect.getComponents()) {
                    isValid = ClientAspectManager.checkTarget(componentAspect.getName());
                }
            }
        }
        return isValid;
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
            AspectList aspectList = AspectHelper.getAspectsFromItem(itemEntityTarget.getItem());
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
