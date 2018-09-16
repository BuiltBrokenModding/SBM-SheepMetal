package com.builtbroken.sheepmetal.content;

import com.builtbroken.sheepmetal.SheepMetal;
import com.builtbroken.sheepmetal.SheepTypes;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 7/21/2018.
 */
public class ItemMetalWool extends Item
{
    public final SheepTypes type;

    public ItemMetalWool(SheepTypes type)
    {
        this.type = type;
        setTranslationKey(SheepMetal.PREFIX + "wool." + type.name);
        setRegistryName(SheepMetal.PREFIX + "wool_item_" + type.name);
        setCreativeTab(CreativeTabs.MATERIALS);
    }
}
