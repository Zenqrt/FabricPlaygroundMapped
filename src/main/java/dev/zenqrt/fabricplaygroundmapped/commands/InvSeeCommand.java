package dev.zenqrt.fabricplaygroundmapped.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.server.level.ServerPlayer;

public final class InvSeeCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("invsee").requires(CommandSourceStack::isPlayer)
                .then(Commands.argument("target", EntityArgument.player())
                        .executes(context -> {
                            ServerPlayer player = context.getSource().getPlayerOrException();
                            ServerPlayer target = EntityArgument.getPlayer(context, "target");

                            player.openMenu(target.getInventory());
                            return 1;
                        })));
    }

}
