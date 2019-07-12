package com.builtbroken.sheepmetal.content;

import com.builtbroken.sheepmetal.data.SheepTypes;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 7/21/2018.
 */
public class BlockMetalWool extends Block
{
    public final SheepTypes type;
    public static final Material MATERIAL = new Material.Builder(MaterialColor.IRON).build();

    public BlockMetalWool(SheepTypes type)
    {
        super(Block.Properties.create(MATERIAL)
                .sound(type == SheepTypes.COAL ? SoundType.STONE : SoundType.METAL) //TODO customize step sound
                .hardnessAndResistance(4.0F, 6.0F));
        this.type = type;
    }
}
