package com.builtbroken.sheepmetal.config;

import com.builtbroken.sheepmetal.SheepMetal;

import net.minecraftforge.common.config.Config;

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
    @Config.Comment("Biomes in which to black-/whitelist spawning of the metal sheep. Which behavior (black-/whitelist) is defined in the config option \"list_type\".")
    @Config.RequiresMcRestart
    public static String[] BIOME_LIST = {};

    @Config.Name("list_type")
    @Config.Comment({"WHITELIST: Metal sheep can only spawn in the biomes listed in \"spawn_biomes\".",
    "BLACKLIST: Metal sheep will spawn in the biomes vanilla sheep spawn in, EXCEPT for the ones listed in \"spawn_biomes\""})
    @Config.RequiresMcRestart
    public static ListType LIST_TYPE = ListType.BLACKLIST;

    public static enum ListType
    {
        BLACKLIST, WHITELIST;
    }
}