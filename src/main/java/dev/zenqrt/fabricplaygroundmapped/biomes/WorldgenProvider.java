package dev.zenqrt.fabricplaygroundmapped.biomes;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biome;

import java.util.concurrent.CompletableFuture;

public final class WorldgenProvider extends FabricDynamicRegistryProvider {

    public WorldgenProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        final HolderLookup.RegistryLookup<Biome> biomeRegistry = registries.lookupOrThrow(Registries.BIOME);

        entries.addAll(biomeRegistry);
    }

    @Override
    public String getName() {
        return "Fabric Playground (Mapped)";
    }
}
