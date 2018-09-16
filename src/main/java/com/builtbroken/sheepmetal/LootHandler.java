package com.builtbroken.sheepmetal;

import net.minecraft.world.storage.loot.LootEntryItem;
import net.minecraft.world.storage.loot.LootPool;
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
        final String VANILLA_LOOT_POOL_ID = "main";
        if (event.getName().getNamespace().equalsIgnoreCase(SheepMetal.DOMAIN))
        {
            SheepTypes type = SheepTypes.get(event.getName().getPath());
            if (type != null)
            {
                LootPool lootPool = event.getTable().getPool(VANILLA_LOOT_POOL_ID);
                if (lootPool != null)
                {
                    lootPool.addEntry(new LootEntryItem(type.woolItem, 1, 3, null, null, SheepMetal.PREFIX + "wool"));
                }
            }
        }
    }
}
