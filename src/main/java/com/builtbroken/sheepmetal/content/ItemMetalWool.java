package com.builtbroken.sheepmetal.content;

import com.builtbroken.sheepmetal.SheepMetal;
import com.builtbroken.sheepmetal.data.SheepTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 7/21/2018.
 */
public class ItemMetalWool extends Item
{
    public final SheepTypes type;

    public ItemMetalWool(SheepTypes type)
    {
        super(new Item.Properties().group(ItemGroup.MATERIALS));
        this.type = type;
        setRegistryName(SheepMetal.PREFIX + "wool_item_" + type.name);
    }
}
