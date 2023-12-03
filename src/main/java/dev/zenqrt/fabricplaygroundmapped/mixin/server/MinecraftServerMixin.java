package dev.zenqrt.fabricplaygroundmapped.mixin.server;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(MinecraftServer.class)
public interface MinecraftServerMixin {

    @Accessor("tickables")
    List<Runnable> tickables();
}
