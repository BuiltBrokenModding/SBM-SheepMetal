package com.builtbroken.sheepmetal.content;

import com.builtbroken.sheepmetal.SheepMetal;
import com.builtbroken.sheepmetal.SheepTypes;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 7/21/2018.
 */
public class BlockMetalWool extends Block
{
    public final SheepTypes type;

    public BlockMetalWool(SheepTypes type)
    {
        super(Material.IRON);
        this.type = type;
        setHardness(4.0F);
        setResistance(6.0F);
        setSoundType(SoundType.METAL);
        setTranslationKey(SheepMetal.PREFIX + "wool." + type.name);
        setRegistryName(SheepMetal.PREFIX + "wool_block_" + type.name);
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
    }
}
