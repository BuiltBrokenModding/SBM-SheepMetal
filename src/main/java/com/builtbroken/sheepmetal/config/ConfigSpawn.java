package com.builtbroken.sheepmetal.config;

import java.util.ArrayList;
import java.util.List;

import com.builtbroken.sheepmetal.SheepMetal;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by bl4ckscor3 on 9/16/2018.
 */
@Config(modid = SheepMetal.DOMAIN, name = "sheepmetal/spawning")
@Config.LangKey("config.sheepmetal:spawning.title")
public class ConfigSpawn
{
    @Config.Name("should_spawn")
    @Config.Comment("Should the sheeps spawn in the world")
    @Config.RequiresMcRestart
    public static boolean SHOULD_SPAWN = true;

    @Config.Name("spawn_weight")
    @Config.Comment("How likely the entity is to spawn, 5 is the same as a witch. The higher the value, the higher the spawn chance")
    @Config.RequiresMcRestart
    public static int SPAWN_WEIGHT = 5;

    @Config.Name("spawn_group_min")
    @Config.Comment("Smallest number to spawn in a group.")
    @Config.RequiresMcRestart
    public static int SPAWN_MIN = 1;

    @Config.Name("spawn_group_max")
    @Config.Comment("Largest number to spawn in a group.")
    @Config.RequiresMcRestart
    public static int SPAWN_MAX = 3;

    @Config.Name("spawn_biomes")
    @Config.Comment("Biomes to spawn entities inside")
    @Config.RequiresMcRestart
    public static String[] BIOMES = getSheepBiomeSpawnList();

    /**
     * @return A String array containig the resource locations of all biomes that vanilla sheep can spawn in
     */
    private static String[] getSheepBiomeSpawnList()
    {
        List<Biome> biomes = new ArrayList<Biome>();

        for(Biome biome : ForgeRegistries.BIOMES.getValuesCollection())
        {
            for(SpawnListEntry entry : biome.getSpawnableList(EnumCreatureType.CREATURE))
            {
                if(entry.entityClass == EntitySheep.class)
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