package primalcat.thaumcraft.common.aspects;

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

public class PlayerAspectsProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<PlayerAspects> PLAYER_ASPECTS = CapabilityManager.get(new CapabilityToken<PlayerAspects>() { });

    private PlayerAspects playerAspects = null;

    private final LazyOptional<PlayerAspects> optional = LazyOptional.of(this::createPlayerAspects);

    private PlayerAspects createPlayerAspects() {
        if(this.playerAspects == null) {
            this.playerAspects = new PlayerAspects();
        }

        return this.playerAspects;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == PLAYER_ASPECTS) {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerAspects().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerAspects().loadNBTData(nbt);
    }
}
