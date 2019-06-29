package com.builtbroken.sheepmetal.config;

import com.builtbroken.sheepmetal.SheepMetal;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.fml.common.Mod;

/**
 * Created by Dark(DarkGuardsman, Robert) on 6/29/2019.
 */
@Config(modid = SheepMetal.DOMAIN, name = "sheepmetal/main")
@Config.LangKey("config.sheepmetal:main.title")
@Mod.EventBusSubscriber(modid = SheepMetal.DOMAIN)
public class ConfigMain
{
    @Config.Name("enable_spawn_weight_debug")
    @Config.Comment("Enabled a file to export to the main game directly with spawning weights and chances for sheep")
    @Config.RequiresMcRestart
    public static boolean ENABLE_SPAWN_WEIGHT_DEBUG = true;
}
