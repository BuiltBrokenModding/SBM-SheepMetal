package com.builtbroken.sheepmetal.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by bl4ckscor3 on 9/16/2018.
 */
public class ConfigSpawn
{
    public static final ForgeConfigSpec CONFIG_SPEC;
    public static final ConfigSpawn CONFIG;

    public final BooleanValue shouldSpawn;
    public final IntValue spawnWeight;
    public final IntValue spawnMin;
    public final IntValue spawnMax;
    public final ConfigValue<List<? extends String>> biomes;

    static
    {
        Pair<ConfigSpawn,ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ConfigSpawn::new);

        CONFIG_SPEC = specPair.getRight();
        CONFIG = specPair.getLeft();
    }

    ConfigSpawn(ForgeConfigSpec.Builder builder)
    {
        shouldSpawn = builder
                .comment("Should the sheeps spawn in the world")
                .define("should_spawn", true);
        spawnWeight = builder
                .comment("How likely the entity is to spawn, 5 is the same as a witch. The higher the value, the higher the spawn chance")
                .defineInRange("spawn_weight", 5, 0, Integer.MAX_VALUE);
        spawnMin = builder
                .comment("Smallest number to spawn in a group.")
                .defineInRange("spawn_group_min", 1, 0, Integer.MAX_VALUE);
        spawnMax = builder
                .comment("Largest number to spawn in a group.")
                .defineInRange("spawn_group_max", 3, 0, Integer.MAX_VALUE);
        biomes = builder
                .comment("Biomes to spawn entities inside")
                .defineList("spawn_biomes", Lists.newArrayList(getSheepBiomeSpawnList()), e -> e instanceof String);
    }

    /**
     * @return A String array containig the resource locations of all biomes that vanilla sheep can spawn in
     */
    private static String[] getSheepBiomeSpawnList()
    {
        List<Biome> biomes = new ArrayList<Biome>();

        for(Biome biome : ForgeRegistries.BIOMES.getValues())
        {
            for(SpawnListEntry entry : biome.getSpawns(EntityClassification.CREATURE))
            {
                if(entry.entityType == EntityType.SHEEP)
                    biomes.add(biome);
            }
        }

        String[] toReturn = new String[biomes.size()];

        for(int i = 0; i < toReturn.length; i++)
        {
            toReturn[i] = biomes.get(i).getRegistryName().toString();
        }

        return toReturn;
    }
}