package com.builtbroken.sheepmetal.content;

import com.builtbroken.sheepmetal.SheepMetal;
import com.builtbroken.sheepmetal.SheepTypes;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

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
        setUnlocalizedName(SheepMetal.PREFIX + "wool." + type.name);
        setRegistryName(SheepMetal.PREFIX + "wool_block_" + type.name);
    }
}
