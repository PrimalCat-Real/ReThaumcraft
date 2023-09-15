package primalcat.thaumcraft.common.networking.packets.scan;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import primalcat.thaumcraft.core.scan.ScanManager;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class PlayerTargetSyncS2CPacket {
    private List<String> targets = new ArrayList<>();

    public PlayerTargetSyncS2CPacket(String target) {
        List<String> targets = new ArrayList<>();
        targets.add(target);
        this.targets = targets;
    }
    public PlayerTargetSyncS2CPacket(List<String> targets) {
        this.targets = targets;
    }

    public PlayerTargetSyncS2CPacket(FriendlyByteBuf buf) {
        int targetCount = buf.readInt();
        for (int i = 0; i < targetCount; i++) {
            targets.add(buf.readUtf());
        }
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(targets.size());
        for (String target : targets) {
            buf.writeUtf(target);
        }
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            if(targets != null) {
                ScanManager.setPlayerScannedObjects(targets);
            }
        });
        return true;
    }
}
