package com.builtbroken.sheepmetal;

import com.builtbroken.sheepmetal.config.ConfigMain;
import com.builtbroken.sheepmetal.data.SheepTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 7/24/2018.
 */
@Mod.EventBusSubscriber(modid = SheepMetal.DOMAIN)
public class SmeltingHandler
{
    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event)
    {
        for (SheepTypes type : SheepTypes.values())
        {
            if (type != SheepTypes.COAL)
            {
                String name = type.name.substring(0, 1).toUpperCase() + type.name.substring(1, type.name.length());
                ItemStack ingotStack = getOreItem("ingot" + name);
                if (ingotStack != null)
                {
                    FurnaceRecipes.instance().addSmelting(type.woolItemBlock, ingotStack, 0.5f);
                }

                ItemStack nuggetStack = getOreItem("nugget" + name);
                if (nuggetStack != null)
                {
                    FurnaceRecipes.instance().addSmelting(type.woolItem, nuggetStack, 0.05f);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onFurnaceBurnTime(FurnaceFuelBurnTimeEvent event)
    {
        final ItemStack input = event.getItemStack();
        if(!input.isEmpty())
        {
            if(input.getItem() == SheepTypes.COAL.woolItem)
            {
                event.setBurnTime(ConfigMain.COAL_ITEM_FUEL);
            }
            else if(input.getItem() == SheepTypes.COAL.woolItemBlock)
            {
                event.setBurnTime(ConfigMain.COAL_BLOCK_FUEL);
            }
        }
    }

    public static ItemStack getOreItem(String name)
    {
        NonNullList<ItemStack> list = OreDictionary.getOres(name);
        if (list != null)
        {
            for (ItemStack stack : list)
            {
                if (!stack.isEmpty())
                {
                    ItemStack stack1 = stack.copy();
                    stack1.setCount(1);
                    return stack1;
                }
            }
        }
        return null;
    }

}
