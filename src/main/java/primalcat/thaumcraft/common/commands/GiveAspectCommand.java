package primalcat.thaumcraft.common.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.player.Player;

public class GiveAspectCommand {
    public GiveAspectCommand(CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(Commands.literal("thaumcraft").then(Commands.literal("aspect").then(Commands.argument("player", EntityArgument.player())
                .then(Commands.argument("aspect", StringArgumentType.string())
                        .then(Commands.argument("amount", IntegerArgumentType.integer())
                                .executes((command) -> {
                                    Player player = command.getSource().getPlayerOrException();
                                    String aspect = command.getArgument("aspect", String.class);
                                    int amount = command.getArgument("amount", int.class);

                                    // Do something with the player, aspect, and amount

                                    return 0;
                                }))))));
    }
}
