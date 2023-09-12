package primalcat.thaumcraft.common.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import primalcat.thaumcraft.Thaumcraft;
import primalcat.thaumcraft.common.networking.packets.*;

public class PacketManager {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(Thaumcraft.MODID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;


        net.messageBuilder(PlayerTargetsSyncC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PlayerTargetsSyncC2SPacket::new)
                .encoder(PlayerTargetsSyncC2SPacket::toBytes)
                .consumerMainThread(PlayerTargetsSyncC2SPacket::handle)
                .add();

        net.messageBuilder(ExampleC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ExampleC2SPacket::new)
                .encoder(ExampleC2SPacket::toBytes)
                .consumerMainThread(ExampleC2SPacket::handle)
                .add();


        net.messageBuilder(SyncPlayerApsectsCapability.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SyncPlayerApsectsCapability::new)
                .encoder(SyncPlayerApsectsCapability::toBytes)
                .consumerMainThread(SyncPlayerApsectsCapability::handle)
                .add();

        net.messageBuilder(PlayerAspectsActionPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PlayerAspectsActionPacket::new)
                .encoder(PlayerAspectsActionPacket::toBytes)
                .consumerMainThread(PlayerAspectsActionPacket::handle)
                .add();

        // S2C
        net.messageBuilder(PlayerTargetsSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PlayerTargetsSyncS2CPacket::new)
                .encoder(PlayerTargetsSyncS2CPacket::toBytes)
                .consumerMainThread(PlayerTargetsSyncS2CPacket::handle)
                .add();
        net.messageBuilder(PlayerAspectsSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PlayerAspectsSyncS2CPacket::new)
                .encoder(PlayerAspectsSyncS2CPacket::toBytes)
                .consumerMainThread(PlayerAspectsSyncS2CPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
