package dev.zenqrt.fabricplaygroundmapped.biomes;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.random.Weight;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.AmbientParticleSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import org.joml.Vector3f;

import java.awt.*;

public final class CustomBiomes {

    public static final Biome CUSTOM = new Biome.BiomeBuilder()
            .hasPrecipitation(false)
            .mobSpawnSettings(new MobSpawnSettings.Builder()
                    .addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.WITHER, Weight.of(1), 3, 8))
                    .build())
            .specialEffects(new BiomeSpecialEffects.Builder()
                    .skyColor(Color.BLACK.getRGB())
                    .fogColor(Color.BLACK.getRGB())
                    .ambientParticle(new AmbientParticleSettings(new DustParticleOptions(new Vector3f(0.5f), 1), 1))
                    .grassColorOverride(Color.DARK_GRAY.getRGB())
                    .foliageColorOverride(Color.GRAY.getRGB())
                    .waterColor(Color.RED.getRGB())
                    .build())
            .build();

}
