package com.builtbroken.sheepmetal.config;

import com.builtbroken.sheepmetal.SheepMetal;
import com.builtbroken.sheepmetal.data.SheepTypes;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by Dark(DarkGuardsman, Robert) on 6/28/2019.
 */
@Config(modid = SheepMetal.DOMAIN, name = "sheepmetal/types")
@Config.LangKey("config.sheepmetal:types.title")
@Mod.EventBusSubscriber(modid = SheepMetal.DOMAIN)
public class ConfigTypes
{
    @Config.Ignore
    public static final int TIER_1 = 12;
    @Config.Ignore
    public static final int TIER_2 = 8;
    @Config.Ignore
    public static final int TIER_3 = 4;
    @Config.Ignore
    public static final int TIER_4 = 1;

    public final static SheepConfig COPPER = new SheepConfig(TIER_1);
    public final static SheepConfig TIN = new SheepConfig(TIER_1);
    public final static SheepConfig LEAD = new SheepConfig(TIER_2);
    public final static SheepConfig SILVER = new SheepConfig(TIER_2);
    public final static SheepConfig URANIUM = new SheepConfig(TIER_4);
    public final static SheepConfig BRASS = new SheepConfig(TIER_3);
    public final static SheepConfig BRONZE = new SheepConfig(TIER_2);
    public final static SheepConfig STEEL = new SheepConfig(TIER_3);
    public final static SheepConfig ELECTRUM = new SheepConfig(TIER_3);
    public final static SheepConfig NICKEL = new SheepConfig(TIER_2);
    public final static SheepConfig ALUMINUM = new SheepConfig(TIER_2);
    public final static SheepConfig ZINC = new SheepConfig(TIER_3);
    public final static SheepConfig PLATINUM = new SheepConfig(TIER_2);
    public final static SheepConfig TITANIUM = new SheepConfig(TIER_3);
    public final static SheepConfig GOLD = new SheepConfig(TIER_2);
    public final static SheepConfig IRON = new SheepConfig(TIER_1);
    public final static SheepConfig OSMIUM = new SheepConfig(TIER_1);
    public final static SheepConfig COAL = new SheepConfig(TIER_1);

    public static class SheepConfig
    {
        @Config.Name("spawning_weight")
        @Config.Comment("Weight of this material being selected during random spawning. Higher the number the lower the chance.")
        @Config.RangeInt(min = 1)
        public int spawnWeight = 1;

        @Config.Name("spawning_enabled")
        @Config.Comment("Set to true to enable the spawning")
        public boolean enable = true;

        public SheepConfig(int defWeight)
        {
            this.spawnWeight = defWeight;
        }
    }

    @SubscribeEvent
    public static void configReload(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID() == SheepMetal.DOMAIN)
        {
            SheepTypes.configReload();
        }
    }
}
