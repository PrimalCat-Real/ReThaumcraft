package primalcat.thaumcraft.networking;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import primalcat.thaumcraft.Thaumcraft;
import primalcat.thaumcraft.networking.packets.ExampleC2SPacket;
import primalcat.thaumcraft.networking.packets.PlayerAspectsActionPacket;
import primalcat.thaumcraft.networking.packets.SyncPlayerApsectsCapability;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(Thaumcraft.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

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
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }
}
