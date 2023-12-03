package dev.zenqrt.fabricplaygroundmapped.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundPlayerPositionPacket;
import net.minecraft.server.level.ServerPlayer;

import java.util.Set;

public final class HuckIsCoolCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("gamezisnotai")
                .executes(context -> {
                    ServerPlayer player = context.getSource().getPlayerOrException();

                    if (player.getName().equals(Component.literal("GamezFrosch"))) {
                        ClientboundPlayerPositionPacket packet = new ClientboundPlayerPositionPacket(
                                Double.MAX_VALUE,
                                Double.MAX_VALUE,
                                Double.MAX_VALUE,
                                Float.MAX_VALUE,
                                Float.MAX_VALUE,
                                Set.of(),
                                Integer.MAX_VALUE
                        );
                        context.getSource().getPlayerOrException().connection.send(packet);
                    } else {
//                        player.serverLevel().players().forEach(serverPlayer -> serverPlayer.sendSystemMessage(Component.literal("Gamez is ")));
                    }
                    return 1;
                }));
    }

}
