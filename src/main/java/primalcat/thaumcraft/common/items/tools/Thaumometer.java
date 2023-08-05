package primalcat.thaumcraft.common.items.tools;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.*;
import net.minecraftforge.common.ForgeMod;
import primalcat.thaumcraft.api.AspectHelper;
import primalcat.thaumcraft.api.AspectList;
import primalcat.thaumcraft.common.items.ItemBase;
import primalcat.thaumcraft.config.ConfigAspects;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Thaumometer extends ItemBase {

    public Thaumometer() {
        super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
    }


    @Override
    public InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand){
//        Player player = context.getPlayer();
//        System.out.println(context.getTarget().getDisplayName());
//        player.sendMessage(new TextComponent(Screen.hasShiftDown() + " Test " + isHoldingThaumometer(player)), player.getUUID());
//        if(player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == ItemInit.THAUMOMETER.get()){
//            HitResult result;
//            result = player.pick(20.0D, 0.0F, true);
//            System.out.println(result.getType());
//            System.out.println(result.getLocation());
//
//
//        }
        if(isHoldingThaumometer(pPlayer)){
            System.out.println(pInteractionTarget.getDisplayName());
        }

        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Player player = pContext.getPlayer();
        Minecraft mc = Minecraft.getInstance();
        if (mc != null && player != null && !mc.isPaused()) {

            HitResult result = getEntityItemResult(player);
            if (result != null && result.getType() == HitResult.Type.ENTITY) {
                ItemEntity entity = (ItemEntity) ((EntityHitResult) result).getEntity();
//                ItemStack testItem = entity.getItem().getTag();

                System.out.println(entity.getItem().getItem());
//                AspectList temp = AspectHelper.getObjectAspects(entity.getItem());
//                if(temp != null){
//                    System.out.println(temp.toString());
//                }
            }else if(result != null && result.getType() == HitResult.Type.BLOCK){
                BlockPos blockPos = (BlockPos) ((BlockHitResult) result).getBlockPos();
                System.out.println(blockPos);
            }
        }
//        if(isHoldingThaumometer(player)){
//            HitResult block =  player.pick(20.0D, 0.0F, false);
//
//            if(block.getType() == HitResult.Type.BLOCK)
//            {
//                BlockPos blockpos = (BlockPos)block.getLocation();
//                BlockState blockstate = player.level.getBlockState(blockpos);
//            }
//        }
        // сука как блять я не понимаю

        return InteractionResult.PASS;
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
