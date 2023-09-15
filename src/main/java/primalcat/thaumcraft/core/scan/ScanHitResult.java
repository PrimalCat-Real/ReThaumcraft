package primalcat.thaumcraft.core.scan;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import primalcat.thaumcraft.core.aspects.AspectList;
import team.lodestar.lodestone.helpers.VecHelper;

import java.util.ArrayList;


public class ScanHitResult {

    private ArrayList<Vec3> targetBlockOutline;
    private MutableComponent localizedComponent;

    public ArrayList<Vec3> getTargetBlockOutline() {
        return targetBlockOutline;
    }

    ItemEntity targetEntityItem;
    BlockState targetBlock;
    LivingEntity targetEntity;

    AspectList targetAspects = new AspectList();

    public AspectList getTargetAspects() {
        return targetAspects;
    }

    public ItemEntity getEntityItem() {
        return targetEntityItem;
    }

    public BlockState getBlock() {
        return targetBlock;
    }

    public LivingEntity getEntity() {
        return targetEntity;
    }

    private Player player;
    private Level level;

    private String targetName = "";


    public String getTargetName() {
        return targetName;
    }

    private Vec3 targetHitPosition;
    private Vec3[] topPlanePoints;

    public Vec3[] getTopPlanePoints() {
        return topPlanePoints;
    }

    public Vec3 getTargetHitPosition() {
        return targetHitPosition;
    }

    public ScanHitResult(Player player, Level level, BlockState blockState, BlockPos blockPos) {
        this.targetBlockOutline = VecHelper.blockOutlinePositions(level, blockPos);
        this.targetBlock = blockState;
        this.level = level;
        this.player = player;
        this.init();
    }

    public ScanHitResult(Player player, Level level, ItemEntity itemEntity) {
        this.targetHitPosition = itemEntity.position();
        this.targetEntityItem = itemEntity;
        this.level = level;
        this.player = player;
        this.init();
    }

    public ScanHitResult(Player player, Level level, LivingEntity livingEntity) {
        this.targetHitPosition = livingEntity.position();
        this.targetEntity = livingEntity;
        this.level = level;
        this.player = player;
        this.init();
    }

    public void init(){
//        this.getTargetBlock();
        if (this.targetEntity != null){
            AABB targetEntityBoundingBox = targetEntity.getBoundingBox();
            this.topPlanePoints = claclTopPlanePoints(targetEntityBoundingBox);
            this.targetAspects = ScanManager.getAspectFromObject(targetEntity);
            this.targetName = getTargetStringName(targetEntity);
        }
        if (this.targetEntityItem != null){
            AABB targetEntityBoundingBox = targetEntityItem.getBoundingBox();
            this.topPlanePoints = claclTopPlanePoints(targetEntityBoundingBox);
            this.targetAspects = ScanManager.getAspectFromObject(targetEntityItem);
            this.targetName = getTargetStringName(targetEntityItem);
        }
        if(this.targetBlock != null){
//            AABB targetEntityBoundingBox = targetBlock.getBoundingBox();
//            this.topPlanePoints = claclTopPlanePoints(targetEntityBoundingBox);
            this.targetAspects = ScanManager.getAspectFromObject(targetBlock);
            this.targetName = getTargetStringName(targetBlock);
        }
//        System.out.println(Component.translatable("pruce_planks.name"));
    }
    public Vec3[] claclTopPlanePoints(AABB boundingBox) {
        // Calculate the four points of the top plane of the bounding box
        double minX = boundingBox.minX;
        double maxX = boundingBox.maxX;
        double minZ = boundingBox.minZ;
        double maxZ = boundingBox.maxZ;
        double y = boundingBox.maxY;
        Vec3[] points = new Vec3[4];
        points[0] = new Vec3(minX, y, minZ);
        points[1] = new Vec3(minX, y, maxZ);
        points[2] = new Vec3(maxX, y, minZ);
        points[3] = new Vec3(maxX, y, maxZ);

        return points;

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

}
