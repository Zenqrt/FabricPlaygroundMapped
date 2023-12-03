package dev.zenqrt.fabricplaygroundmapped.biomes;

import dev.zenqrt.fabricplaygroundmapped.FabricPlaygroundMapped;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public final class DataGeneratorEntrypoint implements net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(WorldgenProvider::new);
        pack.addProvider(BiomeTagProvider::new);
    }

    @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        registryBuilder.add(Registries.BIOME, bootstapContext -> {
            bootstapContext.register(ResourceKey.create(Registries.BIOME, new ResourceLocation(FabricPlaygroundMapped.MOD_ID, "custom")), CustomBiomes.CUSTOM);
        });
    }
}
