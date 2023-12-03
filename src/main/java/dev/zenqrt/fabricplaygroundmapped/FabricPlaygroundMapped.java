package dev.zenqrt.fabricplaygroundmapped;

import dev.zenqrt.fabricplaygroundmapped.commands.AddMarkerCommand;
import dev.zenqrt.fabricplaygroundmapped.commands.HuckIsCoolCommand;
import dev.zenqrt.fabricplaygroundmapped.commands.MobnadoCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationConnectionEvents;

public final class FabricPlaygroundMapped implements ModInitializer {

    public static final String MOD_ID = "fabricplaygroundmapped";

    /**
     * Runs the mod initializer.
     */
    @Override
    public void onInitialize() {
        ServerConfigurationConnectionEvents.CONFIGURE.register((handler, server) -> {
            server.setMotd("Fabric Playground");
        });
        CommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess, environment) -> {
            AddMarkerCommand.register(dispatcher);
            HuckIsCoolCommand.register(dispatcher);
            MobnadoCommand.register(dispatcher, registryAccess);
        }));
    }
}
