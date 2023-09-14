package primalcat.thaumcraft.common.item.tools;

import com.mojang.math.Vector3f;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraftforge.common.ForgeMod;
import primalcat.thaumcraft.client.particle.ParticleEffects;
import primalcat.thaumcraft.core.aspects.AspectList;
import primalcat.thaumcraft.core.scan.ScanHitResult;
import primalcat.thaumcraft.core.scan.ScanManager;
import primalcat.thaumcraft.client.particle.BlockRuneData;
import primalcat.thaumcraft.common.item.ItemBase;
import primalcat.thaumcraft.core.config.ClientConfig;
import team.lodestar.lodestone.helpers.VecHelper;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Thaumometer extends ItemBase {
    private boolean isHoldingRightClick = false;

    private String scanTargetName;

    private AspectList scanTargetAspects;

    private ScanHitResult scanHitResult;

    private UseOnContext tempContext;



    public Thaumometer() {
        super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (player != null && ScanManager.isHoldingThaumometer(player)) {
//            ScanHitResult scanHitBlockResult = getTargetBlock(player, level);
            ScanHitResult scanHitEntityResult = getTargetEntity(player, level);
            ScanHitResult scanHitBlockResult = getTargetBlock(player, level);
            ScanHitResult scanHitItemResult = getTargetEntityItem(player, level);

            // add check if entity not behind
            if(scanHitEntityResult != null){
                this.scanHitResult = scanHitEntityResult;
            }
            if(scanHitBlockResult != null){
                this.scanHitResult = scanHitBlockResult;
            }
            if(scanHitItemResult != null){
                this.scanHitResult = scanHitItemResult;
            }
            if (scanHitResult != null && !ScanManager.isScannedObject(scanHitResult.getTargetName())) {
                this.scanTargetName = scanHitResult.getTargetName();
                player.startUsingItem(hand);
            }


//            if(scanHitEntityResult != null){
//                if(scanHitBlockResult != null && scanHitBlockResult.getTargetHitPosition() > scanHitEntityResult.getTargetHitPosition())
//                float red = 252 / 255.0F;
//                float green = 173 / 255.0F;
//                float blue = 3 / 255.0F;
//                ParticleEffects.spawnSoulParticles(level, scanHitEntityResult.getTopPlanePoints()[0].x + 0.5f, scanHitEntityResult.getTopPlanePoints()[0].y, scanHitEntityResult.getTopPlanePoints()[0].z, 1, 0.75f, Vec3.ZERO, new Color(red, green, blue), Color.GREEN);
//                ParticleEffects.spawnSoulParticles(level, scanHitEntityResult.getTopPlanePoints()[1].x + 0.5f, scanHitEntityResult.getTopPlanePoints()[1].y, scanHitEntityResult.getTopPlanePoints()[1].z, 1, 0.75f, Vec3.ZERO, new Color(red, green, blue), Color.GREEN);
//                ParticleEffects.spawnSoulParticles(level, scanHitEntityResult.getTopPlanePoints()[2].x + 0.5f, scanHitEntityResult.getTopPlanePoints()[2].y, scanHitEntityResult.getTopPlanePoints()[2].z, 1, 0.75f, Vec3.ZERO, new Color(red, green, blue), Color.GREEN);
//                ParticleEffects.spawnSoulParticles(level, scanHitEntityResult.getTopPlanePoints()[3].x + 0.5f, scanHitEntityResult.getTopPlanePoints()[3].y, scanHitEntityResult.getTopPlanePoints()[3].z, 1, 0.75f, Vec3.ZERO, new Color(red, green, blue), Color.GREEN);
//
//                System.out.println(scanHitEntityResult.getTopPlanePoints());
//            }

//            scanHitResult = getTargetEntityItem(player, level);
//            if(scanHitResult != null){
//                System.out.println(scanHitResult.getTopPlanePoints());
//            }

//            if(scanHitBlockResult != null){
//
//            }
//            scanHitResult = getTargetEntityItem(player, level);
//            scanHitResult = getTargetEntity(player, level);
//            ItemEntity targetEntityItem = getTargetEntityItem(player);
//            BlockState targetBlock = getTargetBlock(player, level);
//            LivingEntity targetEntity = getTargetEntity(player);
//
//            if (targetEntityItem != null) {
//                this.scanTargetName = ScanManager.getTargetStringName(targetEntityItem);
//            } else if (targetBlock != null) {
//                this.scanTargetName = ScanManager.getTargetStringName(targetBlock);
//            } else if (targetEntity != null) {
//                this.scanTargetName = ScanManager.getTargetStringName(targetEntity);
//            }
//
//            if (!ScanManager.isScannedObject(this.scanTargetName)) {
//                player.startUsingItem(hand);
//            }
        }

        return InteractionResultHolder.pass(player.getItemInHand(hand));
    }


    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int tick, boolean p_41408_) {
        if (level != null && entity instanceof Player player && level.isClientSide() && ScanManager.isHoldingThaumometer(player)) {

            ScanHitResult scanHitEntityResult = getTargetEntity(player, level);
            ScanHitResult scanHitBlockResult = getTargetBlock(player, level);
            ScanHitResult scanHitItemResult = getTargetEntityItem(player, level);

            // add check if entity not behind
            if(scanHitEntityResult != null){
                this.scanHitResult = scanHitEntityResult;
            }
            if(scanHitBlockResult != null){
                this.scanHitResult = scanHitBlockResult;
            }
            if(scanHitItemResult != null){
                this.scanHitResult = scanHitItemResult;
            }
            if (scanHitResult != null) {
                this.scanTargetName = scanHitResult.getTargetName();
                ScanManager.setTargetNameForRender(scanHitResult.getTargetName());
            }
            if (scanHitResult != null && ScanManager.isScannedObject(scanHitResult.getTargetName())) {
                ScanManager.setTargetAspectsForRender(scanHitResult.getTargetAspects());
            }else{
                ScanManager.setTargetAspectsForRender(new AspectList());
            }
//            scanHitResult = getTargetEntityItem(player, level);
            // @TODO add localized name for display
            if(scanHitResult != null){
                ScanManager.setTargetNameForRender(scanHitResult.getTargetName());
            }

//            System.out.println(scanHitResult.getTargetBlockOutline());
//            ItemEntity targetEntityItem = getTargetEntityItem(player, level).getTargetEntityItem();
//            BlockState targetBlock = getTargetBlock(player, level).getTargetBlock();
//            LivingEntity targetEntity = getTargetEntity(player, level).getTargetEntity();
//
//            String tempTarget = "";
//            AspectList tempAspects = new AspectList();
//
//            if (targetEntityItem != null) {
//                tempTarget = ScanManager.getTargetStringName(targetEntityItem);
//                tempAspects = ScanManager.getAspectFromObject(targetEntityItem);
//            } else if (targetBlock != null) {
//                tempTarget = ScanManager.getTargetStringName(targetBlock);
//                tempAspects = ScanManager.getAspectFromObject(targetBlock);
//            } else if (targetEntity != null) {
//                tempTarget = ScanManager.getTargetStringName(targetEntity);
//                tempAspects = ScanManager.getAspectFromObject(targetEntity);
//            }
//
//            if (ScanManager.isScannedObject(tempTarget)) {
//                ScanManager.setTargetAspectsForRender(tempAspects);
//            } else {
//                ScanManager.setTargetAspectsForRender(new AspectList());
//            }
//
//            ScanManager.setTargetNameForRender(tempTarget);
        }
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int tick) {
        if (level != null && entity instanceof Player player && level.isClientSide()) {


            ScanHitResult scanHitEntityResult = getTargetEntity(player, level);
            ScanHitResult scanHitBlockResult = getTargetBlock(player, level);
            ScanHitResult scanHitItemResult = getTargetEntityItem(player, level);

            // add check if entity not behind
            if(scanHitEntityResult != null){
                this.scanHitResult = scanHitEntityResult;
            }
            if(scanHitBlockResult != null){
                this.scanHitResult = scanHitBlockResult;
            }
            if(scanHitItemResult != null){
                this.scanHitResult = scanHitItemResult;
            }
//            System.out.println(tempTargetName.equals(this.scanHitResult.getTargetName()));
            if(this.scanHitResult.getTargetName() != null && this.scanHitResult.getTargetAspects() != null && scanTargetName.equals(this.scanHitResult.getTargetName())){
//                BlockPos position = new BlockPos(0, -58, 0);
                System.out.println(getTargetBlock(player, level).getTargetBlockOutline().get(0));
                ScanManager.doScan(player, tick, this.scanHitResult.getTargetName(), this.scanHitResult.getTargetAspects());
//                spawnRunesParticles(level,position);
//                System.out.println(tick + " " + this.scanTargetName);
            }


//            ScanHitResult scanHitResult = new ScanHitResult(player, level);
//            if(scanHitResult.getTargetHitPosition() != null && tick % 5 == 1){
//                spawnRunesParticles(level,new BlockPos(scanHitResult.getTargetHitPosition()));
//            }
//            System.out.println(scanHitResult.getTargetHitPosition());
//            ItemEntity targetEntityItem = getTargetEntityItem(player, level).getTargetEntityItem();
//            BlockState targetBlock = getTargetBlock(player, level).getTargetBlock();
//            LivingEntity targetEntity = getTargetEntity(player, level).getTargetEntity();
//
//            String tempTarget = "";
//            if(targetEntityItem != null){
//                tempTarget= ScanManager.getTargetStringName(targetEntityItem);
//                scanTargetAspects = ScanManager.getAspectFromObject(targetEntityItem);
//            }
//            if(targetBlock != null){
//                tempTarget = ScanManager.getTargetStringName(targetBlock);
//                scanTargetAspects = ScanManager.getAspectFromObject(targetBlock);
//            }
//            if(targetEntity != null) {
//                tempTarget = ScanManager.getTargetStringName(targetEntity);
//                scanTargetAspects = ScanManager.getAspectFromObject(targetEntity);
//            }
//

        }
    }

    private void spawnRunesParticles(Level world, BlockPos positionClicked) {
        if (!world.isClientSide) {
            return;  // Early exit if we are not on the client side
        }

        double y = positionClicked.getY() + 1.0F;

        // Define particle speed
        double xSpeed = 0;
        double ySpeed = 0;
        double zSpeed = 0;

        // Define positions and directions for each particle
        double[][] positions = {
                {positionClicked.getX() + 0.5d + Math.random() * 0.8 - 0.3, positionClicked.getZ() + 0.99f}, // North
                {positionClicked.getX() + 0.5d + Math.random() * 0.8 - 0.3, positionClicked.getZ() + 0.01f}, // South
                {positionClicked.getX() - 1.01f, positionClicked.getZ() + 0.5d + Math.random() * 0.8 - 0.3}, // East
                {positionClicked.getX() + 2.01f, positionClicked.getZ() + 0.5d + Math.random() * 0.8 - 0.3}  // West
        };

        Vector3f[] directions = {
                new Vector3f(0f, 0f, -1.0f), // North
                new Vector3f(0f, 0f, 1.0f),  // South
                new Vector3f(1.0f, 0f, 0f),  // East
                new Vector3f(-1.0f, 0f, 0f)  // West
        };

        // Spawn particles
        for (int i = 0; i < positions.length; i++) {
            double x = positions[i][0];
            double z = positions[i][1];
            Vector3f direction = directions[i];
            ParticleOptions particleData = BlockRuneData.createData(direction.x(), direction.y(), direction.z());
            world.addParticle(particleData, x, y, z, xSpeed, ySpeed, zSpeed);
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {

        if (entity instanceof Player player) {
            if (!level.isClientSide()) {
//                if(ScanManager.isObjectAspectsKnown(this.scanTargetAspects)){
//                    player.displayClientMessage(Component.translatable(("actionText.subtitle.aspects_not_valid" + new Random().nextInt(3))),true);
//                }
//                System.out.println("Item finished using: " + stack.getItem());

            }
        }
        return stack;
    }

    @Override
    public int getUseDuration(ItemStack itemStack) {
        return ClientConfig.THAUMOMETER_SCAN_DURATION.get();
    }
    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int duration) {
        if (level.isClientSide) {
            return;
        }
        System.out.println("dur: "+duration);
    }


    public static ScanHitResult getTargetBlock(Player player, Level level){
        if(player != null){
            Vec3 position = player.getEyePosition(1.0F);
            double reachDistance = player.getAttribute(ForgeMod.REACH_DISTANCE.get()).getValue();
            Vec3 look = player.getViewVector(1.0F).scale(reachDistance).add(position);

            ClipContext clipContext = new ClipContext(position, look, ClipContext.Block.OUTLINE, ClipContext.Fluid.ANY, player);

            HitResult hitResult = level.clip(clipContext);
//            System.out.println(((BlockHitResult) hitResult).getBlockPos());
            BlockPos hitPosition = ((BlockHitResult) hitResult).getBlockPos();

            BlockState blockState = level.getBlockState(hitPosition);

            return blockState.getBlock() instanceof AirBlock ? null : new ScanHitResult(player, level, blockState, ((BlockHitResult) hitResult).getBlockPos());
        } else {
            return null;
        }
    }

    // maybe one day
//    public static ScanHitResult getTargetEntityItem(Player player, Level level) {
//        // Get the player's eye position and reach distance
//        Vec3 position = player.getEyePosition(1.0F);
//        double reachDistance = player.getAttribute(ForgeMod.REACH_DISTANCE.get()).getValue();
//
//        // Calculate the look direction
//        Vec3 look = player.getViewVector(1.0F).scale(reachDistance).add(position);
//
//        // Create an axis-aligned bounding box (AABB) for the player's reach
//        AABB reachAABB = player.getBoundingBox().expandTowards(look).inflate(3.0F);
//
//        // Get the entity hit result using ProjectileUtil.getEntityHitResult
//        EntityHitResult entityHitResult = ProjectileUtil.getEntityHitResult(player, position, look, reachAABB, (entity) -> entity instanceof ItemEntity, reachDistance);
//
//        if (entityHitResult != null) {
//            Entity entity = entityHitResult.getEntity();
//            if (entity instanceof ItemEntity itemEntity) {
//                return new ScanHitResult(player, level, itemEntity);
//            }
//        }
//
//        return null; // No locked item entity found
//    }



    public static ScanHitResult getTargetEntityItem(Player player, Level level) {
        // Get the player's eye position and reach distance
        Vec3 position = player.getEyePosition(1.0F);
        double reachDistance = player.getAttribute(ForgeMod.REACH_DISTANCE.get()).getValue();

        // Calculate the look direction
        Vec3 look = player.getViewVector(1.0F).scale(reachDistance).add(position);

        // Create an axis-aligned bounding box (AABB) for the player's reach
        AABB reachAABB = player.getBoundingBox().expandTowards(look).inflate(3F);

        // Get all entities within the reach AABB
        List<Entity> entitiesInReach = player.level.getEntities(player, reachAABB);

        // Iterate through the entities to find the targeted ItemEntity
        for (Entity entity : entitiesInReach) {
            if (entity instanceof ItemEntity) {
                ItemEntity itemEntity = (ItemEntity) entity;

                // Check if the player's view is intersecting with the item's bounding box
                AABB itemBB = itemEntity.getBoundingBox();

                if (itemBB.contains(position)) {
                    return new ScanHitResult(player, level, itemEntity); // Player's view intersects with the item's bounding box
                }

                // Check if the line of sight intersects with the item's bounding box
                Optional<Vec3> hitVec = itemBB.inflate(0.33f).clip(position, look);
                if (hitVec.isPresent()) {
                    return new ScanHitResult(player, level, itemEntity); // Line of sight intersects with the item's bounding box
                }
            }
        }
        return null; // No locked item entity found
    }
    public static ScanHitResult getTargetEntity(Player player, Level level) {
        Vec3 position = player.getEyePosition(1.0F);
        double reachDistance = player.getAttribute(ForgeMod.REACH_DISTANCE.get()).getValue();

        Vec3 look = player.getViewVector(1.0F).scale(reachDistance).add(position);
        AABB bb2 = new AABB(position, look).inflate(0.1F);
//
//        System.out.println(world.getEntities(player, bb, e -> e.canBeCollidedWith() && e != player));
        Level world = player.getLevel();

        for (Entity entity : world.getEntities(player, bb2)) {
            if(entity instanceof ItemEntity){
                return null;
            } else if (entity instanceof Player playerLookedAt) {
                return new ScanHitResult(player, level, playerLookedAt) ;
            }
            return new ScanHitResult(player, level, (LivingEntity)entity);
        }

        return null;
    }
    // Helper method to check if an entity is behind a block
//    private static boolean isEntityBehindBlock(Player player, Entity entity) {
//        Vec3 playerPos = player.getEyePosition(1.0F);
//        Vec3 entityPos = entity.position();
//        Vec3 lookVector = entityPos.subtract(playerPos).normalize();
//        BlockHitResult result = player.level.clip(new ClipContext(playerPos, entityPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
//
//        if (result.getType() == BlockHitResult.Type.BLOCK) {
//            // The ray hit a block, so there's something obstructing the view
//            BlockPos blockPos = ((BlockHitResult) result).getBlockPos();
//            Vec3 blockHitPos = result.getLocation();
//            double distanceToBlock = blockHitPos.distanceTo(playerPos);
//            double distanceToEntity = playerPos.distanceTo(entityPos);
//
//            // Check if the entity is closer to the player than the obstructing block
//            return distanceToEntity < distanceToBlock;
//        }
//
//        // No block obstruction found
//        return false;
//    }
}
