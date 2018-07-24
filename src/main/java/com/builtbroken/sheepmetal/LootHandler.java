package com.builtbroken.sheepmetal;

import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 7/24/2018.
 */
@Mod.EventBusSubscriber(modid = SheepMetal.DOMAIN)
public class LootHandler
{
    @SubscribeEvent
    public static void registerLoot(LootTableLoadEvent event)
    {

    }
}
