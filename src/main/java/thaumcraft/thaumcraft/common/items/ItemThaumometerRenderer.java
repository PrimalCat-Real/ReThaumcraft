package thaumcraft.thaumcraft.common.items;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.client.model.obj.ObjLoader;
import net.minecraftforge.client.model.obj.ObjModel;
import net.minecraftforge.common.util.NonNullLazy;
import thaumcraft.thaumcraft.Thaumcraft;
import thaumcraft.thaumcraft.common.init.ItemInit;
import thaumcraft.thaumcraft.common.items.tools.Thaumometer;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class ItemThaumometerRenderer extends BlockEntityWithoutLevelRenderer{
    private BlockEntityRenderDispatcher dispatcher = Minecraft.getInstance().getBlockEntityRenderDispatcher();
    public ItemThaumometerRenderer() {

        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
        System.out.println("create render");
    }
    private static final MultiBufferSource.BufferSource GHOST_ENTITY_BUF = MultiBufferSource.immediate(new BufferBuilder(256));
    private static final ResourceLocation MODEL_LOC = new ResourceLocation(Thaumcraft.MODID, "item/scaner.obj");
    private static final Map<String, ResourceLocation> LOADED_ICONS = new HashMap<>();
//    private static final ResourceLocation DEFAULT_ICON_TEXTURE = new ResourceLocation("citadel:textures/gui/book/icon_default.png");
    ObjModel tModel = (ObjModel) ObjLoader.INSTANCE.loadModel(new ObjModel.ModelSettings(new ResourceLocation(Thaumcraft.MODID), true, false, true, false, "scaner.mtl"));
    @Override
    public void renderByItem(ItemStack itemStackIn, ItemTransforms.TransformType transformType, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {

        System.out.println("render");
        Minecraft mc = Minecraft.getInstance();
        BakedModel model;
        if (mc.player == null || itemStackIn.isEmpty()) return;
        float partialTicks = mc.getFrameTime();
        float ticksExisted = Util.getMillis() / 50F + partialTicks;
        int id = mc.player == null ? 0 : mc.player.getId();
        float tick;
        if(Minecraft.getInstance().player == null || Minecraft.getInstance().isPaused()){
            tick = ticksExisted;
        }else{
            tick = Minecraft.getInstance().player.tickCount;
        }
        boolean thirdPerson = transformType == ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND || transformType == ItemTransforms.TransformType.THIRD_PERSON_LEFT_HAND;
        if(thirdPerson){
            System.out.println("test");
            model = mc.getModelManager().getModel(new ResourceLocation(Thaumcraft.MODID, "item/thaumometer"));
            matrixStackIn.pushPose();
            matrixStackIn.translate(10,10,10);
            matrixStackIn.pushPose();
            mc.getItemRenderer().render(itemStackIn, transformType,mc.player.getMainArm() == HumanoidArm.LEFT, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, model);
        }else if(itemStackIn.getItem() == ItemInit.THAUMOMETER.get()){
            model = mc.getModelManager().getModel(new ResourceLocation(Thaumcraft.MODID, "item/thaumometer"));
            matrixStackIn.pushPose();
            matrixStackIn.translate(10,10,10);
            matrixStackIn.pushPose();
            mc.getItemRenderer().render(new ItemStack(Items.NETHER_BRICK), transformType,mc.player.getMainArm() == HumanoidArm.LEFT, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, model);
        }


    }

    public static class RenderProperties implements IClientItemExtensions {
        static final NonNullLazy<BlockEntityWithoutLevelRenderer> renderer = NonNullLazy.of(() ->
                new ItemThaumometerRenderer());

    }

}
