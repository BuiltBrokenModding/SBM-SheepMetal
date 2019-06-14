package com.builtbroken.sheepmetal.client;

import com.builtbroken.sheepmetal.SheepMetal;
import com.builtbroken.sheepmetal.entity.EntityMetalSheep;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 7/21/2018.
 */
@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = SheepMetal.DOMAIN)
public class ClientReg
{
    @SubscribeEvent
    public static void onModelRegistry(ModelRegistryEvent event)
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityMetalSheep.class, manager -> new RenderMetalSheep(manager));
    }
}
