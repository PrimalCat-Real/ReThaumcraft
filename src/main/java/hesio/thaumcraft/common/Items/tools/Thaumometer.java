package hesio.thaumcraft.common.Items.tools;

//import hesio.thaumcraft.client.fx.particle.BlockRunesData;
import hesio.thaumcraft.client.fx.particle.BlockRuneType;
import hesio.thaumcraft.client.fx.particle.TestParticleType;
import hesio.thaumcraft.client.fx.particle.testing.MyParticleType;
import hesio.thaumcraft.common.Items.ItemBase;
import hesio.thaumcraft.inits.ParticlesInit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.joml.Vector3f;

public class Thaumometer extends ItemBase {
    public Thaumometer() {
        super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
    }


    private static final int RIGHT_CLICK_HOLD_DURATION = 40;

    private boolean isHoldingRightClick = false;

    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player != null) {
            player.startUsingItem(context.getHand());
            spawnRunesParticles(context.getLevel(), context.getClickedPos());
        }

        return InteractionResult.CONSUME;
    }

//    @Override
//    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
//        if (hand == InteractionHand.MAIN_HAND) {
//            System.out.println("remain tiks "+ player.getUseItemRemainingTicks());
//            if(player.getUseItemRemainingTicks() == 0){
//                player.startUsingItem(hand);
//            }
//
//            System.out.println(this.getUseDuration(player.getItemInHand(hand)));
//            // Reset the hold ticks counter when the player starts using the item
//        }
//        return super.use(level, player, hand);
//    }




    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int count) {
        if (count == 1) { // Every second (20 ticks = 1 second)
            // Do something

            System.out.println("Test from use tick " + count);
        }
        System.out.println("tick " + count);
//        System.out.println("Test from use tick");
    }

    private void spawnRunesParticles(Level world, BlockPos positionClicked) {
        double x = positionClicked.getX() + 0.5d;
        double y = positionClicked.getY() + 1;
        double z = positionClicked.getZ() + 0.5d;

        double xSpeed = 0; // Speed along X
        double ySpeed = 0; // Speed along Y
        double zSpeed = 0; // Speed along Z
        if (world.isClientSide) {  // Ensure we're on the client side before spawning particles
            // Creating a new BlockRuneType instance with your custom value for each direction and adding the particle
//            if (world.isClientSide) {  // Ensure we're on the client side before spawning particles
//                world.addParticle(ParticlesInit.BLOCKRUNE_PARTICLE_NORTH.get(), x, y, z, xSpeed, ySpeed, zSpeed);
//                world.addParticle(ParticlesInit.BLOCKRUNE_PARTICLE_SOUTH.get(), x, y, z, xSpeed, ySpeed, zSpeed);
//                world.addParticle(ParticlesInit.BLOCKRUNE_PARTICLE_EAST.get(), x, y, z, xSpeed, ySpeed, zSpeed);
//                world.addParticle(ParticlesInit.BLOCKRUNE_PARTICLE_WEST.get(), x, y, z, xSpeed, ySpeed, zSpeed);
//            }
            MyParticleType data = new MyParticleType(true, 1);
            world.addParticle(data.getType(), x, y, z, xSpeed, ySpeed, zSpeed);
        }

        System.out.println("spawn particle");

    }
//    private void spawnRunesParticles(Level world, BlockPos positionClicked) {
//        double motionX = 0;
//        double motionY = -0.2; // Set negative value to make particles fall
//        double motionZ = 0;
//
//        world.addParticle(ParticlesInit.BLOCKRUNE_PARTICLE.get(),
//                positionClicked.getX() + 0.5d, positionClicked.getY() + 1, positionClicked.getZ() + 0.5d,
//                motionX, motionY, motionZ);
//    }
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack resultStack = super.finishUsingItem(stack, level, entity);
        if (this.isEdible()) {
            System.out.println("Item finished using: " + stack.getItem());
            // Add your desired logging or code here
        }
        return resultStack;
    }
    @Override
    public int getUseDuration(ItemStack itemStack) {
        return this.RIGHT_CLICK_HOLD_DURATION;
    }
    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int duration) {
        super.releaseUsing(stack, level, entity, duration);
        System.out.println("dur: "+duration);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, net.minecraft.world.entity.Entity entity, int slot, boolean isSelected) {
        if (level.isClientSide && entity instanceof Player player) {
            if (player.getItemInHand(player.getUsedItemHand()).getItem() == this.asItem()){

            }
        }
    }

}