package com.builtbroken.sheepmetal.client;

import com.builtbroken.sheepmetal.SheepMetal;
import com.builtbroken.sheepmetal.SheepTypes;
import com.builtbroken.sheepmetal.entity.EntityMetalSheep;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 7/21/2018.
 */
@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = SheepMetal.DOMAIN)
public class ClientReg
{
    @SubscribeEvent
    public static void registerAllModels(ModelRegistryEvent event)
    {
        for (SheepTypes type : SheepTypes.values())
        {
            ModelLoader.setCustomModelResourceLocation(type.woolItemBlock, 0, new ModelResourceLocation(type.woolItemBlock.getRegistryName(), "inventory"));
            ModelLoader.setCustomModelResourceLocation(type.woolItem, 0, new ModelResourceLocation(type.woolItem.getRegistryName(), "inventory"));
        }

        RenderingRegistry.registerEntityRenderingHandler(EntityMetalSheep.class, manager -> new RenderMetalSheep(manager));
    }
}
