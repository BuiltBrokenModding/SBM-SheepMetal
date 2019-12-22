package com.builtbroken.sheepmetal.data;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import com.builtbroken.sheepmetal.SheepMetal;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 7/21/2018.
 */
public enum SheepTypes //TODO rebuild as a registry once we stop supporting 1.12, til then leave alone to allow back porting
{
    /*  0 */ COPPER("copper", new Color(158, 90, 56), 12),
    /*  1 */ TIN("tin", new Color(172, 198, 197), 12),
    /*  2 */ LEAD("lead", new Color(67, 60, 85), 8),
    /*  3 */ SILVER("silver", new Color(138, 176, 201), 8),

    /*  4 */ URANIUM("uranium", new Color(90, 121, 74), 1),
    /*  5 */ BRASS("brass", new Color(214, 177, 60), 4),
    /*  6 */ BRONZE("bronze", new Color(181, 127, 66), 8),
    /*  7 */ STEEL("steel", new Color(104, 105, 107), 4),

    /*  8 */ ELECTRUM("electrum", new Color(224, 220, 96), 4),
    /*  9 */ NICKEL("nickel", new Color(174, 185, 130), 8),
    /* 10 */ ALUMINUM("aluminum", new Color(199, 205, 206), 8),
    /* 11 */ ZINC("zinc", new Color(215, 215, 145), 4),

    /* 12 */ PLATINUM("platinum", new Color(206, 222, 236), 8),
    /* 13 */ TITANIUM("titanium", new Color(119, 133, 153), 4),
    /* 14 */ GOLD("gold", new Color(255, 240, 90), 8),
    /* 15 */ IRON("iron", new Color(168, 168, 168), 12),

    /* 16 */ OSMIUM("osmium", new Color(107, 142, 168), 12),
    /* 17 */ COAL("coal", new Color(43, 43, 43, 253), 12,
            "minecraft:items/coals", null),
    /* 18 */ STONE("stone", new Color(87, 87, 87), 12,
            "forge:blocks/cobblestone", "forge:blocks/stone");


    public static final HashMap<String, SheepTypes> NAME_TO_TYPE = new HashMap<>();

    //Spawning random
    private static final Random random = new Random();
    private static final List<SheepTypes> sorted = new ArrayList<>();
    private static int weightCount;

    //Transform items
    public Tag<Item>[] tags;

    //Loot table
    public final ResourceLocation deathLootTable;
    public final ResourceLocation shearLootTable;

    //Registry name
    public final ResourceLocation woolItemName;
    public final ResourceLocation woolBlockName;

    //Properties
    public final String name;
    private final Color woolColor;

    private final int defaultSpawnWeight;

    //Configs
    public ForgeConfigSpec.IntValue spawnWeight;
    public ForgeConfigSpec.BooleanValue enabled;

    SheepTypes(String name, Color woolColor, int defaultSpawnWeight)
    {
        this(name, woolColor, defaultSpawnWeight, "forge:items/ingots/" + name, "forge:items/nuggets/" + name);
    }

    SheepTypes(String name, Color woolColor, int defaultSpawnWeight, String... tags)
    {
        this.name = name;
        this.woolItemName = new ResourceLocation(SheepMetal.PREFIX + "wool_item_" + name);
        this.woolBlockName = new ResourceLocation(SheepMetal.PREFIX + "wool_block_" + name);

        this.deathLootTable = new ResourceLocation(SheepMetal.DOMAIN, "entities/" + name);
        this.shearLootTable = new ResourceLocation(SheepMetal.DOMAIN, "shears/" + name);

        this.woolColor = woolColor;
        this.defaultSpawnWeight = defaultSpawnWeight;
        if (tags != null && tags.length > 0)
        {
            this.tags = Arrays.stream(tags)
                    .map(s -> new ItemTags.Wrapper(new ResourceLocation("forge", name)))
                    .toArray(Tag[]::new);
        }
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
            for (Tag<Item> tag : type.tags)
            {
                if (tag.contains(item))
                {
                    return type;
                }
            }
        }
        return null;
    }

    public static SheepTypes get(String value)
    {
        return value != null ? NAME_TO_TYPE.get(value.toLowerCase()) : null;
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

    public Block getBlock()
    {
        return ForgeRegistries.BLOCKS.getValue(woolBlockName);
    }

    public Item getItem()
    {
        return ForgeRegistries.ITEMS.getValue(woolItemName);
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
