package com.builtbroken.sheepmetal.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.builtbroken.sheepmetal.SheepMetal;
import com.builtbroken.sheepmetal.data.SheepTypes;
import com.google.common.collect.Lists;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by bl4ckscor3 on 9/16/2018.
 */
@Mod.EventBusSubscriber(modid = SheepMetal.DOMAIN, bus=Bus.MOD)
public class ConfigSheep
{

    public static final ForgeConfigSpec CONFIG_SPEC;
    public static final ConfigSheep CONFIG;

    //Spawning configs
    public final BooleanValue shouldSpawn;
    public final IntValue spawnWeight;
    public final IntValue spawnMin;
    public final IntValue spawnMax;
    public final ConfigValue<List<? extends String>> biomes;

    //General configs
    public BooleanValue enableSpawnWeightDebug;

    //Item Settings
    public IntValue coalItemFuelValue;
    public IntValue coalBlockFuelValue;

    static
    {
        Pair<ConfigSheep, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ConfigSheep::new);

        CONFIG_SPEC = specPair.getRight();
        CONFIG = specPair.getLeft();
    }

    ConfigSheep(ForgeConfigSpec.Builder builder)
    {
        //General Settings
        builder.comment("General Settings")
        .push("general");

        enableSpawnWeightDebug = builder
                .comment("Enabled a file to export to the main game directly with spawning weights and chances for sheep")
                .define("enable_spawn_weight_debug", false);

        builder.pop();

        //Item Settings
        builder.comment("Item Settings").push("items");

        coalItemFuelValue = builder
                .comment("Fuel value of the coal wool item") //Coal item is 1600
                .defineInRange("coal_fuel_item", 160, 1, Integer.MAX_VALUE);

        coalBlockFuelValue = builder
                .comment("Fuel value of the coal wool block") //Coal item is 1600
                .defineInRange("coal_fuel_block", 1600, 1, Integer.MAX_VALUE);

        builder.pop();

        //Spawning Settings
        builder.comment("Global Spawn Settings")
        .push("spawning");
        shouldSpawn = builder
                .comment("Should the sheeps spawn in the world")
                .define("should_spawn", true);
        spawnWeight = builder
                .comment("How likely the entity is to spawn, 5 is the same as a witch. The higher the value, the higher the spawn chance")
                .defineInRange("spawn_weight", 5, 0, Integer.MAX_VALUE);
        spawnMin = builder
                .comment("Smallest number to spawn in a group.")
                .defineInRange("spawn_group_min", 1, 0, 100);
        spawnMax = builder
                .comment("Largest number to spawn in a group.")
                .defineInRange("spawn_group_max", 3, 0, 100);
        biomes = builder
                .comment("Biomes to spawn entities inside")
                .defineList("spawn_biomes", Lists.newArrayList(getSheepBiomeSpawnList()), e -> e instanceof String);
        builder.pop();

        //Per Type Settings
        builder.comment("Per Sheep Settings")
        .push("sheep");

        for (SheepTypes type : SheepTypes.values())
        {
            builder.comment(("" + type.name.charAt(0)).toUpperCase() + type.name.substring(1) + " Sheep")
            .push(type.name);

            type.enabled = builder
                    .comment("Set to true to enable the spawning")
                    .define("spawning_enabled", true);

            type.spawnWeight = builder
                    .comment("Weight of this material being selected during random spawning. Higher the number the higher the chance.")
                    .defineInRange("spawning_weight", type.spawnWeight(), 1, Integer.MAX_VALUE);


            builder.pop();
        }


        builder.pop();
    }

    /**
     * @return A String array containig the resource locations of all biomes that vanilla sheep can spawn in
     */
    private static String[] getSheepBiomeSpawnList()
    {
        List<Biome> biomes = new ArrayList<Biome>();

        for (Biome biome : ForgeRegistries.BIOMES.getValues())
        {
            for (SpawnListEntry entry : biome.getSpawns(EntityClassification.CREATURE))
            {
                if (entry.entityType == EntityType.SHEEP)
                {
                    biomes.add(biome);
                }
            }
        }

        String[] toReturn = new String[biomes.size()];

        for (int i = 0; i < toReturn.length; i++)
        {
            toReturn[i] = biomes.get(i).getRegistryName().toString();
        }

        return toReturn;
    }

    @SubscribeEvent
    public static void configReload(ModConfig.Reloading event)
    {
        if (event.getConfig().getModId().equalsIgnoreCase(SheepMetal.DOMAIN))
        {
            SheepTypes.configReload();
        }
    }
}