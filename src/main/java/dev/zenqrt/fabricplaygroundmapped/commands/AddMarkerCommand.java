package dev.zenqrt.fabricplaygroundmapped.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.GameTestAddMarkerDebugPayload;
import net.minecraft.server.level.ServerLevel;

import java.awt.*;

public final class AddMarkerCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("addmarker")
                .then(Commands.argument("color", StringArgumentType.word()).suggests((context, builder) -> {
                    builder.suggest("rainbow");

                    for (ChatFormatting chatFormatting: ChatFormatting.values()) {
                        if (chatFormatting.isColor()) {
                            builder.suggest(chatFormatting.getName());
                        }
                    }
                    return builder.buildFuture();
                })
                        .then(Commands.argument("durationMs", IntegerArgumentType.integer(0))
                            .then(Commands.argument("pos", BlockPosArgument.blockPos())
                                    .then(Commands.argument("text", StringArgumentType.string())
                                            .executes(AddMarkerCommand::spawnMarker))))));
    }

    private static int spawnMarker(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        BlockPos blockPos = BlockPosArgument.getBlockPos(context, "pos");
        int durationMs = IntegerArgumentType.getInteger(context, "durationMs");
        String text = StringArgumentType.getString(context, "text");
        String color = StringArgumentType.getString(context, "color").toLowerCase();

        if (color.equals("rainbow")) {
            ServerTickEvents.START_WORLD_TICK.register(new ServerTickEvents.StartWorldTick() {

                float hue = 0;

                @Override
                public void onStartTick(ServerLevel world) {
                    Color rainbowColor = Color.getHSBColor(hue, 1, 1);
                    ClientboundCustomPayloadPacket packet = new ClientboundCustomPayloadPacket(
                            new GameTestAddMarkerDebugPayload(blockPos, rainbowColor.getRGB(), text, 50)
                    );

                    context.getSource().getLevel().players().forEach(serverPlayer -> serverPlayer.connection.send(packet));
                    hue += 0.005F;
                }
            });
        } else {
            ChatFormatting formatting = ChatFormatting.getByName(color);

            if (formatting == null || formatting.getColor() == null)
                throw new SimpleCommandExceptionType(Component.literal("Invalid color")).create();

            ClientboundCustomPayloadPacket packet = new ClientboundCustomPayloadPacket(
                    new GameTestAddMarkerDebugPayload(blockPos, new Color(formatting.getColor()).getRGB(), text, durationMs)
            );

            context.getSource().getLevel().players().forEach(serverPlayer -> serverPlayer.connection.send(packet));
        }

        return 1;
    }

}
