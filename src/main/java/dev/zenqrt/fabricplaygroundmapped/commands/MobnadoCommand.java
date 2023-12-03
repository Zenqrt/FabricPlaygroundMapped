package dev.zenqrt.fabricplaygroundmapped.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.CompoundTagArgument;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Position;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.phys.Vec3;

public final class MobnadoCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandBuildContext) {
        dispatcher.register(
                Commands.literal("mobnado")
                        .then(Commands.argument("entity", ResourceArgument.resource(commandBuildContext, Registries.ENTITY_TYPE)).suggests(SuggestionProviders.SUMMONABLE_ENTITIES)
                                .executes(context -> {
                                    spawnMobnado(ResourceArgument.getSummonableEntityType(context, "entity"), new CompoundTag(), context.getSource().getLevel(), context.getSource().getPosition());
                                    context.getSource().sendSuccess(() -> Component.literal("Mobnado!"), false);
                                    return 1;
                                }).then(Commands.argument("nbt", CompoundTagArgument.compoundTag())
                                        .executes(context -> {
                                            spawnMobnado(ResourceArgument.getSummonableEntityType(context, "entity"), CompoundTagArgument.getCompoundTag(context, "nbt"), context.getSource().getLevel(), context.getSource().getPosition());
                                            context.getSource().sendSuccess(() -> Component.literal("Mobnado!"), false);
                                            return 1;
                                        })))
        );
    }

    private static void spawnMobnado(Holder.Reference<EntityType<?>> reference, CompoundTag compoundTag, ServerLevel level, Vec3 position) {
        CompoundTag compoundTagCopy = compoundTag.copy();
        compoundTagCopy.putString("id", reference.key().location().toString());
        ServerTickEvents.START_WORLD_TICK.register(new ServerTickEvents.StartWorldTick() {

            int currentTick = 0;

            @Override
            public void onStartTick(ServerLevel world) {
                if (currentTick++ % 5 == 0) {
                    Entity entity = EntityType.loadEntityRecursive(compoundTagCopy, level, (e) -> {
                        e.moveTo(position);
                        return e;
                    });

                    if (entity == null) {
                        return;
                    }

                    if (entity instanceof Mob mob) {
                        mob.finalizeSpawn(world, world.getCurrentDifficultyAt(new BlockPos((int) position.x, (int) position.y, (int) position.z)), MobSpawnType.COMMAND, null, null);
                    }

                    world.tryAddFreshEntityWithPassengers(entity);

                    Position origin = entity.position();

                    ServerTickEvents.START_WORLD_TICK.register(new ServerTickEvents.StartWorldTick() {
                        double theta = 0;
                        double radius = 0;
                        double currentHeight = 0;

                        @Override
                        public void onStartTick(ServerLevel world) {

                            double x = Math.cos(theta) * radius;
                            double z = Math.sin(theta) * radius;

//                            entity.setDeltaMovement(x, 0.2, z);
                            entity.teleportTo(origin.x() + x, origin.y() + currentHeight, origin.z() + z);

                            theta += Math.PI / 32;
                            radius += 0.05;
                            currentHeight += 0.2;
                        }
                    });
                }
            }
        });
    }
}
