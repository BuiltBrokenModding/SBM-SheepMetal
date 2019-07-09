package com.builtbroken.sheepmetal.content;

import com.builtbroken.sheepmetal.SheepMetal;
import com.builtbroken.sheepmetal.data.SheepTypes;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 7/21/2018.
 */
public class BlockMetalWool extends Block
{
    public final SheepTypes type;

    public BlockMetalWool(SheepTypes type, Material material)
    {
        super(Block.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(4.0F, 6.0F));
        this.type = type;
        setRegistryName(SheepMetal.PREFIX + "wool_block_" + type.name);
    }
}
