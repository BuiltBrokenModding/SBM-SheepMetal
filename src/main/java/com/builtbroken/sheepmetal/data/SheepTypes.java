package com.builtbroken.sheepmetal.data;

import com.builtbroken.sheepmetal.SheepMetal;
import com.builtbroken.sheepmetal.config.ConfigTypes;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.Tags;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.IntSupplier;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 7/21/2018.
 */
public enum SheepTypes
{
    COPPER("copper", new Color(158, 90, 56), 12),
    TIN("tin", new Color(172, 198, 197), 12),
    LEAD("lead", new Color(67, 60, 85), 8),
    SILVER("silver", new Color(138, 176, 201), 8),
    URANIUM("uranium", new Color(90, 121, 74), 1),
    BRASS("brass", new Color(214, 177, 60), 4),
    BRONZE("bronze", new Color(181, 127, 66), 8),
    STEEL("steel", new Color(104, 105, 107), 4),
    ELECTRUM("electrum", new Color(224, 220, 96), 4),
    NICKEL("nickel", new Color(174, 185, 130), 8),
    ALUMINUM("aluminum", new Color(199, 205, 206), 8),
    ZINC("zinc", new Color(215, 215, 145), 4),
    PLATINUM("platinum", new Color(206, 222, 236), 8),
    TITANIUM("titanium", new Color(119, 133, 153), 4),
    GOLD("gold", new Color(255, 240, 90), 8),
    IRON("iron", new Color(168, 168, 168), 12),
    OSMIUM("osmium", new Color(107, 142, 168), 12),
    COAL("coal", new Color(43, 43, 43, 253), 12, "minecraft:items/coals", null);


    public static final HashMap<String, SheepTypes> NAME_TO_TYPE = new HashMap();

    //Spawning random
    private static final Random random = new Random();
    private static final List<SheepTypes> sorted = new ArrayList();
    private static int weightCount;

    //Item/block cache
    public Item woolItem; //TODO switch to supplier

    public Item woolItemBlock; //TODO switch to supplier
    public Block woolBlock; //TODO switch to supplier

    //Transform items
    public Tag<Item> ingot;
    public Tag<Item> nugget;

    //Loot table
    public ResourceLocation entityDropTable;

    public final String name;
    private final Color woolColor;

    private final int defaultSpawnWeight;

    public ForgeConfigSpec.IntValue spawnWeight;
    public ForgeConfigSpec.BooleanValue enabled;

    SheepTypes(String name, Color woolColor, int defaultSpawnWeight)
    {
        this(name, woolColor, defaultSpawnWeight, "forge:items/ingots/" + name, "forge:items/nuggets/" + name);
    }

    SheepTypes(String name, Color woolColor, int defaultSpawnWeight, String ingot, String nugget)
    {
        this.name = name;
        this.woolColor = woolColor;
        this.defaultSpawnWeight = defaultSpawnWeight;
        this.ingot = ingot != null ? new ItemTags.Wrapper(new ResourceLocation(ingot)) : null;
        this.nugget = nugget != null ? new ItemTags.Wrapper(new ResourceLocation(nugget)) : null;
    }

    public static void setupTypes()
    {
        for (SheepTypes sheepTypes : values())
        {
            sheepTypes.setup();
        }
        configReload();
    }

    public void setup()
    {
        entityDropTable = new ResourceLocation(SheepMetal.DOMAIN, "entities/" + name);
        NAME_TO_TYPE.put(name.toLowerCase(), this);
    }

    public static SheepTypes random()
    {
        if (!sorted.isEmpty())
        {
            int num = random.nextInt(weightCount);
            int index = 0;
            SheepTypes type;
            do
            {
                type = sorted.get(index);
                num -= type.spawnWeight();

                //Increase and wrap index
                index++;
                if (index >= sorted.size())
                {
                    index = 0;
                }
            }
            while (num > 0);

            //Return type
            if (type != null)
            {
                return type;
            }
        }
        return SheepTypes.get(random.nextInt(SheepTypes.values().length));
    }

    public static void configReload()
    {
        sorted.clear();
        for (SheepTypes type : SheepTypes.values())
        {
            if (type.isEnabled())
            {
                sorted.add(type);
            }
        }
        sorted.sort(Comparator.comparingInt(s -> s.spawnWeight()));
        weightCount = sorted.stream().mapToInt(s -> s.spawnWeight()).sum();
    }

    public static SheepTypes get(int value)
    {
        if (value >= 0 && value < values().length)
        {
            return values()[value];
        }
        return COPPER;
    }

    public static SheepTypes get(Item item)
    {
        for (SheepTypes type : values())
        {
            if (type.ingot != null && type.ingot.contains(item))
            {
                return type;
            }
            else if (type.nugget != null && type.nugget.contains(item))
            {
                return type;
            }
        }
        return null;
    }

    public static SheepTypes get(String value)
    {
        return value != null ? NAME_TO_TYPE.get(value.toLowerCase()) : null;
    }


    public ItemStack getWoolItem()
    {
        return new ItemStack(woolItem);
    }

    public Color getWoolColor()
    {
        return woolColor;
    }

    public int spawnWeight()
    {
        if (spawnWeight == null)
        {
            return defaultSpawnWeight;
        }
        return spawnWeight.get();
    }

    public boolean isEnabled()
    {
        return enabled == null || enabled.get();
    }

    //public static void main(String... args)
    //{
    //    outputRandomData((string) -> System.out.println(string), 1000000);
    //}

    /**
     * Used to output information about random chances for sheep spawn weights
     *
     * @param output - system to output towards "(string) -> System.out.println(string)"
     * @param runs   - number of times to random, recommend 100k+
     */
    public static void outputRandomData(final Consumer<String> output, final int runs)
    {
        configReload();
        output.accept("Output for spawn weight data found near the end of the " +
                "loading phase of the mod. Anything changed after this point " +
                "is not recorded. As well percentages are based on randomly " +
                "rolling the spawn check [" + runs + "] to see the outcome. This" +
                "means its an estimation based on a sample set and my not reflect " +
                "the actual gameplay.\n");
        output.accept("Weights: \n");
        output.accept(String.format("%10s >> %6s\n", "METAL", "WEIGHT"));
        for (SheepTypes type : sorted)
        {
            output.accept(String.format("%10s >> %6d\n", type, type.spawnWeight()));
        }

        //Run randoms
        int[] counts = new int[SheepTypes.values().length];
        for (int r = 0; r < runs; r++)
        {
            SheepTypes type = random();
            counts[type.ordinal()] += 1;
        }

        //Output results
        output.accept("\nRandomization Output: \n");
        output.accept(String.format("%10s >> %6s >> %s\n", "METAL", "COUNT", "PERCENTAGE OF TOTAL"));
        Arrays.stream(values())
                .sorted(Comparator.comparingInt(s -> counts[s.ordinal()]))
                .forEach(s ->
                        {
                            int count = counts[s.ordinal()];
                            double percent = count / (double) runs;
                            output.accept(String.format("%10s >> %6d >> %.2f\n", s, count, percent));
                        }

                );
    }

}
