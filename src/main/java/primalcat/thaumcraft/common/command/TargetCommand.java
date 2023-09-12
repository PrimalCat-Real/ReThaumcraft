package primalcat.thaumcraft.common.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import primalcat.thaumcraft.core.registry.AspectRegistry;
import primalcat.thaumcraft.common.networking.PacketManager;
import primalcat.thaumcraft.common.networking.packets.scan.PlayerTargetSyncC2SPacket;

import java.util.ArrayList;
import java.util.List;

public class TargetCommand {
    public TargetCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("thaumcraft")
                        .then(Commands.literal("targets")
                                .then(Commands.literal("add")
                                        .then(Commands.argument("player", EntityArgument.player())
                                                .then(Commands.argument("target", StringArgumentType.string())
                                                        .suggests(suggestTargets())
                                                        .executes(this::addTarget)
                                                )
                                        )
                                )
                                .then(Commands.literal("remove")
                                        .then(Commands.argument("player", EntityArgument.player())
                                                .then(Commands.argument("target", StringArgumentType.string())
                                                        .suggests(suggestTargets())
                                                        .executes(this::removeTarget)
                                                )
                                        )
                                )
                        )
        );
    }


    private int removeTarget(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = EntityArgument.getPlayer(context, "player");
        String target = StringArgumentType.getString(context, "target");
        if((AspectRegistry.getItemsAspectsHolderName().contains(target) || target.equals("all")) && player != null){
//            ScanManager.addPlayerAspects(new AspectList().add(AspectInit.getAspect(aspect), Math.abs(amount)));

            List<String> targetList = new ArrayList<>();
            if(target.equals("all")){

                PacketManager.sendToServer(new PlayerTargetSyncC2SPacket(targetList, player.getUUID()));
            }
            player.sendSystemMessage(Component.literal(target + " target cleared"));
            // @TODO remove for specific target
//            PacketManager.sendToServer(new PlayerTargetsSyncC2SPacket());

        }

        return 1; // Return 1 to indicate success
    }

    @SuppressWarnings("Don't work correctly")
    private int addTarget(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = EntityArgument.getPlayer(context, "player");
        String target = StringArgumentType.getString(context, "target");
        if((AspectRegistry.getItemsAspectsHolderName().contains(target) || target.equals("all")) && player != null){
//            ScanManager.addPlayerAspects(new AspectList().add(AspectInit.getAspect(aspect), Math.abs(amount)));
//            List<String> targetList = new ArrayList<>();
            // @TODO fix add target
//            if(target.equals("all")){
//                PacketManager.sendToServer(new PlayerTargetsSyncC2SPacket(AspectInit.getItemsAspectsHolderName(), player.getUUID()));
//
//            }
            // @TODO add for specific target
//            PacketManager.sendToServer(new PlayerTargetsSyncC2SPacket());

        }

        return 1; // Return 1 to indicate success
    }

//    private static List<String> getAllAspectHolders(){
//        List<String> temp = new ArrayList<>();
//        return
//    }
    private static SuggestionProvider<CommandSourceStack> suggestTargets() {
        return (context, builder) -> {
            String remaining = builder.getRemaining().toLowerCase();
            for (String target : AspectRegistry.getItemsAspectsHolderName()) {
                if (target.toLowerCase().startsWith(remaining)) {
                    builder.suggest(target);
                }
            }
            builder.suggest("all");
            return builder.buildFuture();
        };
    }
}
