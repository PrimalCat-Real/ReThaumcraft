package hesio.thaumcraft.common.Items.tools;

//import hesio.thaumcraft.client.fx.particle.BlockRunesData;
import hesio.thaumcraft.client.fx.particle.BlockRuneData;
import hesio.thaumcraft.client.fx.particle.BlockRuneType;
import hesio.thaumcraft.client.fx.particle.BlockRunesParticle;
import hesio.thaumcraft.client.fx.particle.TestParticleType;
import hesio.thaumcraft.client.fx.particle.testing.MyParticleType;
import hesio.thaumcraft.common.Items.ItemBase;
import hesio.thaumcraft.inits.ParticlesInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.joml.Vector3f;

import static hesio.thaumcraft.inits.ParticlesInit.BLOCKRUNE_PARTICLE;

public class Thaumometer extends ItemBase {

    private UseOnContext tempContext;
    public Thaumometer() {
        super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
    }


    private static final int RIGHT_CLICK_HOLD_DURATION = 40;

    private boolean isHoldingRightClick = false;

    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player != null) {
            player.startUsingItem(context.getHand());
            this.tempContext = context;
//            spawnRunesParticles(context.getLevel(), context.getClickedPos());
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
        if(count % 5 == 0){
            spawnRunesParticles(level, tempContext.getClickedPos());
        }

        System.out.println("tick 1 " + count + " spawn: " + tempContext.getClickedPos());
//        System.out.println("Test from use tick");
    }

    private void spawnRunesParticles(Level world, BlockPos positionClicked) {

        double y = positionClicked.getY() + 1.1F;

        double xSpeed = 0; // Скорость вдоль оси X
        double ySpeed = 0; // Скорость вдоль оси Y
        double zSpeed = 0; // Скорость вдоль оси Z
        if (world.isClientSide) {  // Убедитесь, что мы находимся на стороне клиента перед созданием частиц
            double xRandomOffset = Math.random() * 0.9 - 0.4;
            double xSouth = positionClicked.getX() + 0.5d + xRandomOffset;
            double zSouth = positionClicked.getZ() -1.02f; // сторона блока - Z

            Vector3f directionNorth = new Vector3f(0f, 0f, 1.0f); // сторона втдимости частицы + Z
            ParticleOptions particleDataNorth = BlockRuneData.createData(directionNorth.x(), directionNorth.y(), directionNorth.z());
            world.addParticle(particleDataNorth, xSouth, y, zSouth, xSpeed, ySpeed, zSpeed);
        }
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