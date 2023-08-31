package primalcat.thaumcraft.common.items.tools;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.minecraftforge.common.ForgeMod;
import primalcat.thaumcraft.aspects.Aspect;
import primalcat.thaumcraft.aspects.AspectHelper;
import primalcat.thaumcraft.aspects.AspectList;
import primalcat.thaumcraft.aspects.ScanHelper;
import primalcat.thaumcraft.client.ClientAspectManager;
import primalcat.thaumcraft.common.items.ItemBase;
import primalcat.thaumcraft.init.AspectInit;
import primalcat.thaumcraft.sound.ModSounds;
import primalcat.thaumcraft.utilites.Variables;

import java.util.*;

public class Thaumometer extends ItemBase {
    private static final int RIGHT_CLICK_HOLD_DURATION = 34;

    private static ScanHelper scanHelper = new ScanHelper();
    private boolean isHoldingRightClick = false;

    private String scanTargetName;

    private UseOnContext tempContext;
    private double reachDistance;

    public Thaumometer() {
        super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
    }



    @Override
    public InteractionResult interactLivingEntity(ItemStack pStack, Player player, LivingEntity pInteractionTarget, InteractionHand pUsedHand){
        if(player != null && isHoldingThaumometer(player)){

//            AspectHelper.getAspectsFromEntity(pInteractionTarget);
//            String targetName = AspectHelper.getTarget(pInteractionTarget);
//            AspectList  aspectList = AspectHelper.getAspectsFromEntity(pInteractionTarget);
//            player.sendSystemMessage(Component.literal("Aspects: " +  aspectList));
//            player.sendSystemMessage(Component.literal("Target " + targetName));

//            if(!aspectList.isEmpty() && targetName != null){
//                ModMessages.sendToServer(new SyncPlayerApsectsCapability(aspectList, targetName));
//            }
//            System.out.println(pInteractionTarget);
            scanHelper.setEntityTarget(pInteractionTarget);
//            player.startUsingItem(pInteractionTarget.getUsedItemHand());
//            player.sendSystemMessage(Component.literal("Aspects: " +  AspectHelper.getAspectsFromEntity(pInteractionTarget)));
        }

        return InteractionResult.PASS;
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (player != null && isHoldingThaumometer(player)) {
            // Your code here
            HitResult result = getEntityItemResult(player);
            if(getEntityLookedAt(player) != null){

                AspectList aspectList = AspectHelper.getAspectFromObject(getEntityLookedAt(player));
                System.out.println(getEntityLookedAt(player));
                if(scanHelper.IsValidAspect(aspectList)) {
                    scanHelper.setCanDoScan(!ClientAspectManager.checkTarget(scanTargetName));
                    Variables.tempAspects = AspectHelper.getAspectFromObject(getEntityLookedAt(player) );
                }else{
                    scanHelper.setCanDoScan(false);
                }
            }else  if (result != null && result.getType() == HitResult.Type.ENTITY) {
                ItemEntity entityItem = (ItemEntity) ((EntityHitResult) result).getEntity();
//                Variables.tempAspects = AspectHelper.getAspectsFromObject(entity.getItem());
//                scanHelper.setItemEntityTarget(entity);
                AspectList aspectList = AspectHelper.getAspectFromObject(entityItem.getItem());
                System.out.println(aspectList);
                if(scanHelper.IsValidAspect(aspectList)) {
                    scanHelper.setCanDoScan(!ClientAspectManager.checkTarget(scanTargetName));
                    Variables.tempAspects = AspectHelper.getAspectFromObject(entityItem.getItem());
                }else{
                    scanHelper.setCanDoScan(false);
                }
            }else{
                Vec3 position = player.getEyePosition(1.0F);
                double reachDistance = player.getAttribute(ForgeMod.REACH_DISTANCE.get()).getValue();

                Vec3 look = player.getViewVector(1.0F).scale(reachDistance).add(position);

                BlockState targetBlock = getTargetBlock(position, look, player, level);


//                scanTargetName = AspectHelper.getTarget(targetBlock);
                AspectList aspectList = AspectHelper.getAspectFromObject(targetBlock);

                if(scanHelper.IsValidAspect(aspectList)) {
                    scanHelper.setCanDoScan(!ClientAspectManager.checkTarget(scanTargetName));
                    Variables.tempAspects = AspectHelper.getAspectFromObject(targetBlock);
                }else{
                    scanHelper.setCanDoScan(false);
                }

            }
        }
        ItemStack held = player.getItemInHand(hand);
        if(scanHelper.isCanDoScan()){
            player.startUsingItem(hand);
        }

        return InteractionResultHolder.pass(held);
    }


//    @Override
//    public void (ItemStack stack, LivingEntity entity, int count) {
//        if (entity instanceof Player && !entity.level().isClientSide) {
//            ((Player) entity).getCooldowns().addCooldown(this, this.cooldown / 2);
//            this.cooldown = 0;
//        }
//        this.isBlocking = false;
//    }

    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int tick, boolean p_41408_) {
//        System.out.println("tick " + tick);
//        scanHelper.doScan();
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int tick) {
        if (level.isClientSide() && entity instanceof Player player && level != null) {
            System.out.println(RIGHT_CLICK_HOLD_DURATION - tick);
            scanHelper.doScan(RIGHT_CLICK_HOLD_DURATION - tick, (Player) entity, scanTargetName);

//            if(count % 5 == 0){
////            spawnRunesParticles(level, tempContext.getClickedPos());
//                Vec3 position = player.getEyePosition(1.0F);
//                double reachDistance = player.getAttribute(ForgeMod.REACH_DISTANCE.get()).getValue();
//                Vec3 look = player.getViewVector(1.0F).scale(reachDistance).add(position);
//                BlockState targetBlock = getTargetBlock(position, look, player, level);
////                ItemEntity targetItem = (ItemEntity) ((EntityHitResult) getEntityItemResult(player)).getEntity();
////                scanHelper.setItemEntityTarget(targetItem);
////                scanHelper.setBlockTarget(targetBlock);
//                LivingEntity entityTarget = getEntityLookedAt(player);
//                Variables.tempAspects = AspectHelper.getAspectsFromEntity(entityTarget);
////                System.out.println(entityTarget);
////                scanHelper.setEntityTarget(entityTarget);
//
////                level.playSound(player,player.getX(), player.getY(), player.getZ(), ModSounds.cameraticks.get(), SoundSource.MASTER, 0.2f,0.45f + player.level.random.nextFloat() * 0.1f);
//            }
//            scanHelper.doScan();

        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {

        if (entity instanceof Player) {
            if (!level.isClientSide()) {

            System.out.println("Item finished using: " + stack.getItem());
            scanHelper.syncPlayerAspectsFromScan();
                Player player = Minecraft.getInstance().player;
                level.playSound(player,player.getX(), player.getY(), player.getZ(), ModSounds.learn.get(), SoundSource.MASTER, 0.2f,0.45f + player.level.random.nextFloat() * 0.1f);
            }
        }
        return stack;
    }

    @Override
    public int getUseDuration(ItemStack itemStack) {
        return this.RIGHT_CLICK_HOLD_DURATION;
    }
    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int duration) {
        if (level.isClientSide) {
            return;
        }
//        super.releaseUsing(stack, level, entity, duration);
        System.out.println("dur: "+duration);
    }


    public static BlockState getTargetBlock(Vec3 position, Vec3 look, Entity player, Level world){
        ClipContext clipContext = new ClipContext(position, look, ClipContext.Block.OUTLINE, ClipContext.Fluid.ANY, player);

        HitResult hitResult = world.clip(clipContext);
        BlockPos hitPosition = ((BlockHitResult) hitResult).getBlockPos();

        BlockState blockState = world.getBlockState(hitPosition);
        return blockState;
//        String targetName = AspectHelper.getTarget(blockState);
//        AspectList  aspectList = AspectHelper.getAspectsFromBlock(blockState);
//        player.sendSystemMessage(Component.literal("Aspects: " +  aspectList));
//        player.sendSystemMessage(Component.literal("Target " + AspectHelper.getTarget(blockState)));
//
//        if(!aspectList.isEmpty() && targetName != null){
//            ModMessages.sendToServer(new SyncPlayerApsectsCapability(aspectList, targetName));
//        }
    }


    public static HitResult getEntityItemResult(Player player){
        double distance = player.getAttribute(ForgeMod.REACH_DISTANCE.get()).getValue();
        Minecraft mc = Minecraft.getInstance();
        float partialTicks = mc.getDeltaFrameTime();
        Vec3 position = player.getEyePosition(partialTicks);
        Vec3 vec3d1 = player.getViewVector(partialTicks);
        Vec3 look = position.add(vec3d1.x * distance, vec3d1.y * distance, vec3d1.z * distance);

        HitResult result = mc.level.clip(new ClipContext(position, look, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
        if (result != null)
            distance = Math.min(distance, result.getLocation().distanceToSqr(position));

        AABB axisalignedbb = player.getBoundingBox().expandTowards(vec3d1.scale(distance)).inflate(1.0D, 1.0D, 1.0D);
        EntityHitResult entityraytraceresult = ProjectileUtil.getEntityHitResult(player, position, look, axisalignedbb, (p_215312_0_) -> {
            return !p_215312_0_.isSpectator() && p_215312_0_.canBeCollidedWith();
        }, distance);
        if (entityraytraceresult != null) {
            Vec3 vec3d3 = entityraytraceresult.getLocation();
            double d2 = position.distanceToSqr(vec3d3);
            if (d2 < distance || result == null) {
                result = entityraytraceresult;
                distance = d2;
            }
        }
        HitResult res = getEntityItem(player,position, position.add(vec3d1.x * distance, vec3d1.y * distance, vec3d1.z * distance), distance);
        return  res;
    }

    public static HitResult getEntityItem(Player player, Vec3 position, Vec3 look, double distance) {
        float f1 = 3.0F;
        Vec3 include = look.subtract(position);
        List list = player.level.getEntities(player, player.getBoundingBox().expandTowards(include.x, include.y, include.z).expandTowards(f1, f1, f1));
        for (int i = 0; i < list.size(); ++i) {
            Entity entity = (Entity) list.get(i);
            if (entity instanceof ItemEntity) {
                AABB axisalignedbb = entity.getBoundingBox().inflate(0.2);
                Optional<Vec3> vec = axisalignedbb.clip(position, look);
                if (vec.isPresent()){
                    return new EntityHitResult(entity, vec.get());
                }else if (axisalignedbb.contains(position)){
                    return new EntityHitResult(entity);
                }
                System.out.println(entity.getDisplayName());

            }
        }
        return null;
    }
    public static LivingEntity getEntityLookedAt(Player player) {
        Vec3 position = player.getEyePosition(1.0F);
        double reachDistance = player.getAttribute(ForgeMod.REACH_DISTANCE.get()).getValue();

        Vec3 look = player.getViewVector(1.0F).scale(reachDistance).add(position);
        AABB bb2 = new AABB(position, look);
//
//        System.out.println(world.getEntities(player, bb, e -> e.canBeCollidedWith() && e != player));
        double radius = 3.0; // Adjust the radius as needed
        AABB bb = player.getBoundingBox().inflate(radius);
        Level world = player.getLevel();
        for (Entity entity : world.getEntities(player, bb2)) {
            System.out.println(entity.canCollideWith(player));
            if(entity instanceof ItemEntity){
                return null;
            }
            return (LivingEntity)entity;
        }

        return null;
    }


    private boolean isHoldingThaumometer(Player player) {
        if (player == null) {
            return false;
        }

        ItemStack mouseItem = player.getMainHandItem();

        return !mouseItem.isEmpty() && mouseItem.getItem() == this;

    }
}
