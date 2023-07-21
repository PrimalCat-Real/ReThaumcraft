package hesio.thaumcraft.common.Items.tools;

//import hesio.thaumcraft.client.fx.particle.BlockRunesData;
import hesio.thaumcraft.client.fx.particle.BlockRunesData;
import hesio.thaumcraft.client.fx.particle.BlockRunesParticle;
import hesio.thaumcraft.client.fx.particle.testing.MyParticleData;
import hesio.thaumcraft.client.fx.particle.testing.MyParticleType;
import hesio.thaumcraft.common.Items.ItemBase;
import hesio.thaumcraft.inits.ParticlesInit;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
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
        Vector3f direction = new Vector3f(0f, -0.2f, 0f);  // North direction

        double x = positionClicked.getX() + 0.5d;
        double y = positionClicked.getY() + 1;
        double z = positionClicked.getZ() + 0.5d;

//        BlockRunesData data = new BlockRunesData(direction, 1f, 1f, 1f, 0);
//        world.addParticle(data, x, y, z, 0, -0.1f, 0);

        double xSpeed = 0; // Скорость по X
        double ySpeed = 0; // Скорость по Y
        double zSpeed = 0; // Скорость по Z



// Создание нового экземпляра MyParticleData с вашим уникальным значением
        MyParticleType data = new MyParticleType(true,1);

// Получение экземпляра клиентского мира

// Создание частицы
        world.addParticle(data, x, y, z, xSpeed, ySpeed, zSpeed);
        System.out.println(direction);

//
//        world.addParticle();
//        world.addParticle(ParticlesInit.BLOCKRUNE_PARTICLE.get(), new BlockRunesData(direction),
//                positionClicked.getX() + 0.5d, positionClicked.getY() + 1, positionClicked.getZ() + 0.5d,
//                x, y, z)
    }
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