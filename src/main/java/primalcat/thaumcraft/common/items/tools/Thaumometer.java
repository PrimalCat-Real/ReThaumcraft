package primalcat.thaumcraft.common.items.tools;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import primalcat.thaumcraft.api.AspectHelper;
import primalcat.thaumcraft.common.items.ItemBase;
import primalcat.thaumcraft.init.AspectInit;
import primalcat.thaumcraft.networking.ModMessages;
import primalcat.thaumcraft.networking.packets.SyncPlayerApsectsCapability;

import java.util.*;

public class Thaumometer extends ItemBase {

    public Thaumometer() {
        super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
    }


    @Override
    public InteractionResult interactLivingEntity(ItemStack pStack, Player player, LivingEntity pInteractionTarget, InteractionHand pUsedHand){
        if(isHoldingThaumometer(player)){

//            AspectHelper.getEntityAspects(pInteractionTarget);
            player.sendSystemMessage(Component.literal("Aspects: " +  AspectHelper.getAspectsFromEntity(pInteractionTarget)));
        }

        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Player player = pContext.getPlayer();
        Minecraft mc = Minecraft.getInstance();
        if (mc != null && player != null && !mc.isPaused()) {

            // drop items
            HitResult result = getEntityItemResult(player);

            if (result != null && result.getType() == HitResult.Type.ENTITY) {
                ItemEntity entity = (ItemEntity) ((EntityHitResult) result).getEntity();
//                ItemStack testItem = entity.getItem().getTag();

                System.out.println(entity.getItem().getItem());
//                AspectList temp = AspectHelper.getObjectAspects(entity.getItem());
//                if(temp != null){
//                    System.out.println(temp.toString());
//                }

                player.sendSystemMessage(Component.literal("Aspects: " +  AspectHelper.getAspectsFromObject(entity.getItem())));


            }else if(result != null && result.getType() == HitResult.Type.BLOCK){
                BlockPos blockPos = (BlockPos) ((BlockHitResult) result).getBlockPos();
                System.out.println(blockPos);
            }

            // get fluid
            Level world = Minecraft.getInstance().level;
            Vec3 position = player.getEyePosition(1.0F);
            double reachDistance = player.getAttribute(ForgeMod.REACH_DISTANCE.get()).getValue();

            Vec3 look = player.getViewVector(1.0F).scale(reachDistance).add(position);

            getAspectsFromBlock(position, look ,player, world);
            // get fluid end
        }
        HashMap<String, Integer> testting = new HashMap<String, Integer>();
        testting.put(AspectInit.FIRE.getName(), 10);
//        ModMessages.sendToServer(new SyncPlayerApsectsCapability(testting, testList));
        ModMessages.sendToServer(new SyncPlayerApsectsCapability(testting, "testblock"));

//        ModMessages.sendToServer(new DrinkWaterC2SPacket());




//        // Create a sample PlayerAspects instance
//        PlayerAspects playerAspects = new PlayerAspects();
//        playerAspects.getAspects().put(AspectInit.FIRE.getName(), 5);
//
//        CompoundTag nbt = new CompoundTag();
//        playerAspects.saveNBTData(nbt);
//
//        // Load NBT data
//        PlayerAspects loadedAspects = new PlayerAspects();
//        loadedAspects.loadNBTData(nbt);
//
//        // Retrieve the value using the originalAspect instance as the key
//        System.out.println("Value for originalAspect: " + loadedAspects.getAspects().get(AspectInit.FIRE.getName()));
//        System.out.println("Value for originalAspect: " + loadedAspects.getAspects().keySet());

        return InteractionResult.PASS;
    }

    public static void getAspectsFromBlock(Vec3 position, Vec3 look, Entity player, Level world){
        ClipContext clipContext = new ClipContext(position, look, ClipContext.Block.OUTLINE, ClipContext.Fluid.ANY, player);

        HitResult hitResult = world.clip(clipContext);
        BlockPos hitPosition = ((BlockHitResult) hitResult).getBlockPos();

        BlockState blockState = world.getBlockState(hitPosition);
        player.sendSystemMessage(Component.literal("Aspects: " +  AspectHelper.getAspectsFromBlock(blockState)));
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
