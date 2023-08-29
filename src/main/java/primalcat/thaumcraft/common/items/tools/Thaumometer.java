package primalcat.thaumcraft.common.items.tools;

import com.mojang.math.Vector3d;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BasePressurePlateBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.minecraftforge.common.ForgeMod;
import primalcat.thaumcraft.Thaumcraft;
import primalcat.thaumcraft.api.AspectHelper;
import primalcat.thaumcraft.api.AspectList;
import primalcat.thaumcraft.api.ScanHelper;
import primalcat.thaumcraft.common.items.ItemBase;
import primalcat.thaumcraft.networking.ModMessages;
import primalcat.thaumcraft.networking.packets.SyncPlayerApsectsCapability;
import primalcat.thaumcraft.sound.ModSounds;

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
            if (result != null && result.getType() == HitResult.Type.ENTITY) {
                System.out.println(result);
                ItemEntity entity = (ItemEntity) ((EntityHitResult) result).getEntity();
//                scanHelper.setItemEntityTarget(entity);
            }else{
                Vec3 position = player.getEyePosition(1.0F);
                double reachDistance = player.getAttribute(ForgeMod.REACH_DISTANCE.get()).getValue();

                Vec3 look = player.getViewVector(1.0F).scale(reachDistance).add(position);

                BlockState targetBlock = getTargetBlock(position, look, player, level);
//                System.out.println(targetBlock);
//                scanHelper.setBlockTarget(targetBlock);
//                scanHelper.setScanning(true);
            }
        }
        ItemStack held = player.getItemInHand(hand);
        if(scanHelper.isCanDoScan()){
            player.startUsingItem(hand);
        }

        return InteractionResultHolder.pass(held);
    }

    public static LivingEntity getEntityLookedAt(Player player) {
        Vec3 position = player.getEyePosition(1.0F);
        double reachDistance = player.getAttribute(ForgeMod.REACH_DISTANCE.get()).getValue();

        Vec3 look = player.getViewVector(1.0F).scale(reachDistance).add(position);
//        Vec3 endVec = position.add(look.x * reachDistance, look.y * reachDistance, look.z * reachDistance);
//        System.out.println(look);
//        System.out.println("player " + endVec);
//
        AABB bb2 = new AABB(position, look);
//        Level world = player.getLevel();
//
//        System.out.println(world.getEntities(player, bb, e -> e.canBeCollidedWith() && e != player));
        double radius = 3.0; // Adjust the radius as needed
        AABB bb = player.getBoundingBox().inflate(radius);
        Level world = player.getLevel();
        for (Entity entity : world.getEntities(player, bb2)) {
            System.out.println(entity.canCollideWith(player));
            return (LivingEntity)entity;
        }

        return null;
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
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int count) {
        if (level.isClientSide() && entity instanceof Player player && level != null) {
            if(count % 5 == 0){
//            spawnRunesParticles(level, tempContext.getClickedPos());
                Vec3 position = player.getEyePosition(1.0F);
                double reachDistance = player.getAttribute(ForgeMod.REACH_DISTANCE.get()).getValue();
                Vec3 look = player.getViewVector(1.0F).scale(reachDistance).add(position);
                BlockState targetBlock = getTargetBlock(position, look, player, level);
//                ItemEntity targetItem = (ItemEntity) ((EntityHitResult) getEntityItemResult(player)).getEntity();
//                scanHelper.setItemEntityTarget(targetItem);
//                scanHelper.setBlockTarget(targetBlock);
                LivingEntity entityTarget = getEntityLookedAt(player);
//                System.out.println(entityTarget);
//                scanHelper.setEntityTarget(entityTarget);

//                level.playSound(player,player.getX(), player.getY(), player.getZ(), ModSounds.cameraticks.get(), SoundSource.MASTER, 0.2f,0.45f + player.level.random.nextFloat() * 0.1f);
            }
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

//    @Override
//    public InteractionResult useOn(UseOnContext pContext) {
//        Player player = pContext.getPlayer();
//
//        Minecraft mc = Minecraft.getInstance();
//        if (mc != null && player != null && !mc.isPaused()) {
//            player.startUsingItem(pContext.getHand());
//            // drop items
//            HitResult result = getEntityItemResult(player);
//
//            if (result != null && result.getType() == HitResult.Type.ENTITY) {
//                ItemEntity entity = (ItemEntity) ((EntityHitResult) result).getEntity();
////                ItemStack testItem = entity.getItem().getTag();
//
//                System.out.println(entity.getItem().getItem());
////                AspectList temp = AspectHelper.getObjectAspects(entity.getItem());
////                if(temp != null){
////                    System.out.println(temp.toString());
////                }
//                scanHelper.setItemEntityTarget(entity);
//                String targetName = AspectHelper.getTarget(entity.getItem());
//                AspectList  aspectList = AspectHelper.getAspectsFromObject(entity.getItem());
//                player.sendSystemMessage(Component.literal("Aspects: " +  aspectList));
//                player.sendSystemMessage(Component.literal("Target " + aspectList));
//
//                if(!aspectList.isEmpty() && targetName != null){
//                    ModMessages.sendToServer(new SyncPlayerApsectsCapability(aspectList, targetName));
//                }
////                player.sendSystemMessage(Component.literal("Aspects: " +  AspectHelper.getAspectsFromObject(entity.getItem())));
//
//
//            }else if(result != null && result.getType() == HitResult.Type.BLOCK){
//                BlockPos blockPos = (BlockPos) ((BlockHitResult) result).getBlockPos();
//                System.out.println(blockPos);
//            }
//
//            // get fluid
//            Level world = Minecraft.getInstance().level;
//            Vec3 position = player.getEyePosition(1.0F);
//            double reachDistance = player.getAttribute(ForgeMod.REACH_DISTANCE.get()).getValue();
//
//            Vec3 look = player.getViewVector(1.0F).scale(reachDistance).add(position);
//
//
//            getAspectsFromBlock(position, look ,player, world);
//            // get fluid end
//        }
//
////        // Create a sample PlayerAspects instance
////        PlayerAspects playerAspects = new PlayerAspects();
////        playerAspects.getAspects().put(AspectInit.FIRE.getName(), 5);
////
////        CompoundTag nbt = new CompoundTag();
////        playerAspects.saveNBTData(nbt);
////
////        // Load NBT data
////        PlayerAspects loadedAspects = new PlayerAspects();
////        loadedAspects.loadNBTData(nbt);
////
////        // Retrieve the value using the originalAspect instance as the key
////        System.out.println("Value for originalAspect: " + loadedAspects.getAspects().get(AspectInit.FIRE.getName()));
////        System.out.println("Value for originalAspect: " + loadedAspects.getAspects().keySet());
//
//        return InteractionResult.PASS;
//    }

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


    private boolean isHoldingThaumometer(Player player) {
        if (player == null) {
            return false;
        }

        ItemStack mouseItem = player.getMainHandItem();

        return !mouseItem.isEmpty() && mouseItem.getItem() == this;

    }
}
