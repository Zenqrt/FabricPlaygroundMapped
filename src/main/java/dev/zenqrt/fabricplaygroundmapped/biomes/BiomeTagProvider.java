package dev.zenqrt.fabricplaygroundmapped.biomes;

import dev.zenqrt.fabricplaygroundmapped.FabricPlaygroundMapped;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

import java.util.concurrent.CompletableFuture;

public final class BiomeTagProvider extends FabricTagProvider<Biome> {

    public BiomeTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.BIOME, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        getOrCreateTagBuilder(TagKey.create(Registries.BIOME, new ResourceLocation(FabricPlaygroundMapped.MOD_ID, "biome_tag")))
                .add(CustomBiomes.CUSTOM);
    }
}
