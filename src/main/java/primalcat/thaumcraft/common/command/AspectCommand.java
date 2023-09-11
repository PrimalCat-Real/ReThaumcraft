package primalcat.thaumcraft.common.command;

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
import primalcat.thaumcraft.core.registry.AspectRegistry;
import primalcat.thaumcraft.common.networking.PacketManager;
import primalcat.thaumcraft.common.networking.packets.PlayerAspectsActionPacket;

public class AspectCommand {
    public AspectCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("thaumcraft")
                        .then(Commands.literal("aspect")
                            .then(Commands.literal("give")
                                    .then(Commands.argument("player", EntityArgument.player())
                                        .then(Commands.argument("aspect", StringArgumentType.string())
                                                .suggests(suggestAspects())
                                                .then(Commands.argument("amount", IntegerArgumentType.integer())
                                                        .executes(this::giveAspect)
                                                )
                                        )
                                    )
                            )
                            .then(Commands.literal("clear")
                                    .then(Commands.argument("player", EntityArgument.player())
                                        .then(Commands.argument("aspect", StringArgumentType.string())
                                                .suggests(suggestAspects())
                                                .then(Commands.argument("amount", IntegerArgumentType.integer())
                                                        .executes(this::clearAspects)
                                                )
                                        )
                                )
                            )
                            .then(Commands.literal("info")
                                    .then(Commands.argument("player", EntityArgument.player())
                                            .then(Commands.argument("aspect", StringArgumentType.string())
                                                    .suggests(suggestAspects())
                                                    .executes(this::infoAspect)
                                            )
                                    )
                            )
                        )
        );
    }

    private int infoAspect(CommandContext<CommandSourceStack> context) throws CommandSyntaxException{
        Player player = EntityArgument.getPlayer(context, "player");
        String aspect = StringArgumentType.getString(context, "aspect");
        if((AspectRegistry.checkAspectExist(aspect) || aspect.equals("all")) && player != null){
            PacketManager.sendToServer(new PlayerAspectsActionPacket(aspect,  0, player.getUUID(), "info"));
        }
        // TODO: Implement logic to give Thaumcraft aspect to the player
        // Example: ThaumcraftAspectUtil.giveAspect(player, aspect, amount);

        return 1; // Return 1 to indicate success
    }
    private int giveAspect(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = EntityArgument.getPlayer(context, "player");
        String aspect = StringArgumentType.getString(context, "aspect");
        int amount = IntegerArgumentType.getInteger(context, "amount");
        if((AspectRegistry.checkAspectExist(aspect) || aspect.equals("all")) && player != null){
//            ScanManager.addPlayerAspects(new AspectList().add(AspectInit.getAspect(aspect), Math.abs(amount)));
            PacketManager.sendToServer(new PlayerAspectsActionPacket(aspect,  Math.abs(amount), player.getUUID(), "give"));

        }
        // TODO: Implement logic to give Thaumcraft aspect to the player
        // Example: ThaumcraftAspectUtil.giveAspect(player, aspect, amount);

        return 1; // Return 1 to indicate success
    }

    private int clearAspects(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = EntityArgument.getPlayer(context, "player");
        String aspect = StringArgumentType.getString(context, "aspect");
        int amount = IntegerArgumentType.getInteger(context, "amount");
        if((AspectRegistry.checkAspectExist(aspect) || aspect.equals("all")) && player != null){

            System.out.println(player.getUUID());
            PacketManager.sendToServer(new PlayerAspectsActionPacket(aspect,  Math.abs(amount), player.getUUID(), "clear"));
        }
        // TODO: Implement logic to clear Thaumcraft aspects from the player
        // Example: ThaumcraftAspectUtil.clearAspects(player);

        return 1; // Return 1 to indicate success
    }
    // SuggestionProvider to generate aspect suggestions
    private static SuggestionProvider<CommandSourceStack> suggestAspects() {
        return (context, builder) -> {
            String remaining = builder.getRemaining().toLowerCase();
            for (String aspect : AspectRegistry.getAspectsInitStringsNames()) {
                if (aspect.toLowerCase().startsWith(remaining)) {
                    builder.suggest(aspect);
                }
            }
            builder.suggest("all");
            return builder.buildFuture();
        };
    }
}
