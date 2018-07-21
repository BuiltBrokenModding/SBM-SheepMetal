package com.builtbroken.sheepmetal;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 7/21/2018.
 */
public enum SheepTypes
{
    COPPER("copper"),
    TIN("tin"),
    LEAD("lead"),
    SILVER("silver"),
    URANIUM("uranium"),
    BRASS("brass"),
    BRONZE("bronze"),
    STEEL("steel"),
    ELECTRUM("electrum"),
    NICKEL("nickel"),
    ALUMINUM("aluminum"),
    ZINC("zinc"),
    PLATINUM("platinum"),
    TITANIUM("titanium"),
    GOLD("gold"),
    IRON("iron");

    public Item woolItem;

    public Item woolItemBlock;
    public Block woolBlock;

    public ResourceLocation entityDropTable;

    public final String name;

    SheepTypes(String name)
    {
        this.name = name;
    }

    public static void initAll()
    {
        for(SheepTypes sheepTypes : values())
        {
            sheepTypes.init();
        }
    }

    public void init()
    {
        entityDropTable = LootTableList.register(new ResourceLocation(SheepMetal.DOMAIN, name));
    }

    public static SheepTypes get(int value)
    {
        if (value >= 0 && value < values().length)
        {
            return values()[value];
        }
        return COPPER;
    }

    public ItemStack getWoolItem()
    {
        return new ItemStack(woolItem);
    }
}
