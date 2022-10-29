package thaumcraft.thaumcraft.common.player;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ScannedAspectsProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<ScannedAspects> Player_Aspects = CapabilityManager.get(new CapabilityToken<ScannedAspects>() { });

    private ScannedAspects aspects = null;
    private final LazyOptional<ScannedAspects> optional = LazyOptional.of(this::createPlayerScannedAspects);

    private ScannedAspects createPlayerScannedAspects() {
        if(this.aspects == null) {
            this.aspects = new ScannedAspects();
        }

        return this.aspects;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == Player_Aspects) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }



    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerScannedAspects().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerScannedAspects().loadNBTData(nbt);
    }
}
