package com.builtbroken.sheepmetal;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

import java.awt.*;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 7/21/2018.
 */
public enum SheepTypes
{
    COPPER("copper", new Color(158,90,56)),
    TIN("tin", new Color(172,198,197)),
    LEAD("lead", new Color(67,60,85)),
    SILVER("silver", new Color(138,176,201)),
    URANIUM("uranium", new Color(90,121,74)),
    BRASS("brass", new Color(214,177,60)),
    BRONZE("bronze", new Color(181,127,66)),
    STEEL("steel", new Color(104,105,107)),
    ELECTRUM("electrum", new Color(224,220,96)),
    NICKEL("nickel", new Color(174,185,130)),
    ALUMINUM("aluminum", new Color(199,205,206)),
    ZINC("zinc", new Color(215,215,145)),
    PLATINUM("platinum", new Color(206,222,236)),
    TITANIUM("titanium", new Color(119,133,153)),
    GOLD("gold", new Color(255,240,90)),
    IRON("iron", new Color(168,168,168));

    public Item woolItem;

    public Item woolItemBlock;
    public Block woolBlock;

    public ResourceLocation entityDropTable;

    public final String name;
    private final Color woolColor;

    SheepTypes(String name, Color woolColor)
    {
        this.name = name;
        this.woolColor = woolColor;
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

    public Color getWoolColor()
    {
        return woolColor;
    }
}
