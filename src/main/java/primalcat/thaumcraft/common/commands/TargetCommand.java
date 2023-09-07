package primalcat.thaumcraft.common.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.player.Player;
import primalcat.thaumcraft.aspects.Aspect;
import primalcat.thaumcraft.client.ScanManager;
import primalcat.thaumcraft.config.ConfigAspects;
import primalcat.thaumcraft.init.AspectInit;
import primalcat.thaumcraft.networking.PacketManager;
import primalcat.thaumcraft.networking.packets.PlayerAspectsActionPacket;
import primalcat.thaumcraft.networking.packets.PlayerTargetsSyncC2SPacket;

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
                        )
        );
    }

    private int addTarget(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = EntityArgument.getPlayer(context, "player");
        String target = StringArgumentType.getString(context, "target");
        if((!ScanManager.isScannedObject(target) || target.equals("all")) && player != null){
//            ScanManager.addPlayerAspects(new AspectList().add(AspectInit.getAspect(aspect), Math.abs(amount)));
//            List<String> targetList = new ArrayList<>();
            if(target.equals("all")){
                PacketManager.sendToServer(new PlayerTargetsSyncC2SPacket(AspectInit.getItemsAspectsHolderName(), player.getUUID()));
                System.out.println(AspectInit.getItemsAspectsHolderName());
            }
//            PacketManager.sendToServer(new PlayerTargetsSyncC2SPacket());

        }
        // TODO: Implement logic to give Thaumcraft aspect to the player
        // Example: ThaumcraftAspectUtil.giveAspect(player, aspect, amount);

        return 1; // Return 1 to indicate success
    }

//    private static List<String> getAllAspectHolders(){
//        List<String> temp = new ArrayList<>();
//        return
//    }
    private static SuggestionProvider<CommandSourceStack> suggestTargets() {
        return (context, builder) -> {
            String remaining = builder.getRemaining().toLowerCase();
            for (String target : AspectInit.getItemsAspectsHolderName()) {
                if (target.toLowerCase().startsWith(remaining)) {
                    builder.suggest(target);
                }
            }
            builder.suggest("all");
            return builder.buildFuture();
        };
    }
}
