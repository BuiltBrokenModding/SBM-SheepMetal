package com.builtbroken.sheepmetal.config;

/**
 * Created by Dark(DarkGuardsman, Robert) on 6/28/2019.
 */

public class ConfigTypes
{
    public static class SheepConfig
    {
        //@Config.Name("")
        //@Config.Comment("")
        //@Config.RangeInt(min = 1)
        public int spawnWeight = 1;

        //@Config.Name("")
        //@Config.Comment("")
        public boolean enable = true;

        public SheepConfig(int defWeight)
        {
            this.spawnWeight = defWeight;
        }
    }


}
