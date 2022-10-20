package thaumcraft.thaumcraft.common.items.tools;


import com.google.common.eventbus.Subscribe;
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
import thaumcraft.thaumcraft.common.aspects.Aspect;
import thaumcraft.thaumcraft.common.aspects.AspectEventRegister;
import thaumcraft.thaumcraft.common.aspects.AspectList;
import thaumcraft.thaumcraft.common.items.ItemBase;


import java.util.List;
import java.util.Optional;

public class Thaumometer extends ItemBase{
    public Thaumometer() {
        super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
    }


    @Override
    public InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand){
        if(isHoldingThaumometer(pPlayer)){
            System.out.println(pInteractionTarget.getDisplayName());
        }

        return InteractionResult.PASS;
    }



    @Subscribe
    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Player player = pContext.getPlayer();
        Minecraft mc = Minecraft.getInstance();
        if (mc != null && player != null && !mc.isPaused()) {
            HitResult result = getEntityItemResult(player, mc);
            System.out.println(result);

            // check item
            if(result.getType() == HitResult.Type.ENTITY){
                System.out.println("Item");
                ItemEntity entity = (ItemEntity) ((EntityHitResult) result).getEntity();
                getAspectsFromItem(entity.getItem().getItem());
            // check block
            }else if(result.getType() == HitResult.Type.BLOCK){
                BlockPos blockpos = ((BlockHitResult)result).getBlockPos();
                // check is block liquid
                if(mc.level.getBlockState(blockpos).getMaterial().isLiquid()){
                    System.out.println(mc.level.getBlockState(blockpos).getBlock());
                    // TODO liquid scan
                }else{
                    getAspectsFromItem(mc.level.getBlockState(blockpos).getBlock().asItem());
                }
            }

            }
        return InteractionResult.PASS;
    }

    // check if player use Thaumometer
    // TODO make solo class
    private boolean isHoldingThaumometer(Player player) {
        if (player == null) {
            return false;
        }
        ItemStack mouseItem = player.getMainHandItem();
        return !mouseItem.isEmpty() && mouseItem.getItem() == this;
    }



    // get keys from AspectList
    public static void getAspectsFromItem(Item item){
        AspectList aspects = AspectEventRegister.getAspectsItem(item);
        if(aspects != null){
            for(Object asp: aspects.getKeys()){
                Aspect aspect = (Aspect) asp;
                System.out.println(aspect.getName());
            }
        }else {
            System.out.println("Dont have aspects");
        }

    }

    // Will be in future
    public static void getAspectsFromEntity(Entity entity){
        System.out.println(entity.getDisplayName());
    }
    // get hit result
    public static HitResult getEntityItemResult(Player player, Minecraft mc){
        double distance = 5f;
        float partialTicks = mc.getDeltaFrameTime();
        Vec3 position = player.getEyePosition(partialTicks);
        Vec3 vec3d1 = player.getViewVector(partialTicks);
        Vec3 look = position.add(vec3d1.x * distance, vec3d1.y * distance, vec3d1.z * distance);

        // for test log player look
        System.out.println(look);

        // get block by vector
        HitResult blockResult = mc.level.clip(new ClipContext(position, look, ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY, player));
        if (blockResult != null){
            distance = Math.min(distance, blockResult.getLocation().distanceToSqr(position));
            AABB axisalignedbb = player.getBoundingBox().expandTowards(vec3d1.scale(distance)).inflate(1.0D, 1.0D, 1.0D);
            EntityHitResult entityraytraceresult = ProjectileUtil.getEntityHitResult(player, position, look, axisalignedbb, (p_215312_0_) -> {
                return !p_215312_0_.isSpectator() && p_215312_0_.canBeCollidedWith();
            }, distance);
            if (entityraytraceresult != null) {
                Vec3 vec3d3 = entityraytraceresult.getLocation();
                double d2 = position.distanceToSqr(vec3d3);
                if (d2 < distance || blockResult == null) {
                    blockResult = entityraytraceresult;
                    distance = d2;
                }
            }
            HitResult itemResult = getEntityItemResult(player, position, position.add(vec3d1.x * distance, vec3d1.y * distance, vec3d1.z * distance), distance);

            if(itemResult == null){
                // if item null it is block, because return block
                return blockResult;
            }else{
                // return item
                return  itemResult;
            }
        }else{
            /*
            * @TODO Make mob scan trigger!
            * */
            EntityHitResult entityResult = new EntityHitResult(player);
            getAspectsFromEntity(entityResult.getEntity());
        }
        return null;
    }

    // get drop items by vector
    private static HitResult getEntityItemResult(Player player, Vec3 position, Vec3 look, double distance) {
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
}
