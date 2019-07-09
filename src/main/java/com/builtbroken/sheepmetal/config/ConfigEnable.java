package com.builtbroken.sheepmetal.config;

import com.builtbroken.sheepmetal.SheepMetal;
import net.minecraftforge.common.config.Config;

/**
 * Created by darkguardsman on 6/26/2019.
 */
@Config(modid = SheepMetal.DOMAIN, name = "sheepmetal/enable")
@Config.LangKey("config.sheepmetal:enable.title")
public class ConfigEnable
{
    @Config.Name("should_spawn")
    @Config.Comment("Should the sheeps spawn in the world")
    @Config.RequiresMcRestart
    public static boolean SHOULD_SPAWN = true;


}