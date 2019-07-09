package com.builtbroken.sheepmetal.data;


import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.Random;

/**
 * Created by Dark(DarkGuardsman, Robert) on 6/28/2019.
 */
public class SheepParent implements INBTSerializable<CompoundNBT>
{
    public static final String NBT_TYPE = "type";
    public static final String NBT_PARENT_A = "parent_a";
    public static final String NBT_PARENT_B = "parent_b";

    private SheepTypes type;
    private SheepParent[] parents;

    public SheepTypes get()
    {
        if (type == null)
        {
            type = SheepTypes.random();
        }
        return type;
    }

    public SheepParent getParentA()
    {
        return parents != null ? getParents()[0] : null;
    }

    public SheepParent getParentB()
    {
        return parents != null ? getParents()[1] : null;
    }

    public SheepParent[] getParents()
    {
        return parents;
    }

    public static SheepParent[] buildRandomParents(int depth)
    {
        return new SheepParent[]{buildRandomParent(depth - 1), buildRandomParent(depth - 1)};
    }

    public static SheepParent buildRandomParent(int depth)
    {
        final SheepParent parent = new SheepParent();
        parent.type = SheepTypes.random();
        if (depth > 0)
        {
            parent.parents = buildRandomParents(depth - 1);
        }
        return parent;
    }

    public static SheepParent merge(SheepParent parent_a, SheepParent parent_b)
    {
        final SheepParent parent = new SheepParent();
        parent.parents = new SheepParent[]{parent_a.copy(), parent_b.copy()};
        return parent;
    }

    public static SheepParent load(CompoundNBT nbt)
    {
        final SheepParent parent = new SheepParent();
        parent.deserializeNBT(nbt);
        return parent;
    }

    public SheepParent copy()
    {
        return load(serializeNBT()); //Ya I will fix this later
    }

    @Override
    public CompoundNBT serializeNBT()
    {
        final CompoundNBT tag = new CompoundNBT();
        tag.putByte(NBT_TYPE, (byte) get().ordinal());
        if (getParentA() != null)
        {
            tag.put(NBT_PARENT_A, getParentA().serializeNBT());
        }
        if (getParentB() != null)
        {
            tag.put(NBT_PARENT_B, getParentB().serializeNBT());
        }
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt)
    {
        if (nbt.contains(NBT_TYPE))
        {
            type = SheepTypes.get(nbt.getByte(NBT_TYPE));
        }
        if (nbt.contains(NBT_PARENT_A) || nbt.contains(NBT_PARENT_B))
        {
            parents = new SheepParent[]{
                    load(nbt.getCompound(NBT_PARENT_A)),
                    load(nbt.getCompound(NBT_PARENT_B))
            };
        }
    }

    public SheepTypes getRandom(Random random)
    {
        if (getParents() != null)
        {
            if (random.nextBoolean())
            {
                return getNotNull(getParentA(), random);
            }
            else
            {
                return getNotNull(getParentB(), random);
            }
        }
        return SheepTypes.random();
    }

    private SheepTypes getNotNull(SheepParent parent, Random random)
    {
        if (parent != null)
        {
            if (random.nextBoolean())
            {
                return parent.getRandom(random);
            }
            else if (parent.type != null)
            {
                return parent.type;
            }
        }
        return SheepTypes.random();
    }
}
