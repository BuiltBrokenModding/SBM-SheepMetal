package com.builtbroken.sheepmetal;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.builtbroken.sheepmetal.config.ConfigSpawn;
import com.builtbroken.sheepmetal.config.ConfigSpawn.ListType;
import com.builtbroken.sheepmetal.content.BlockMetalWool;
import com.builtbroken.sheepmetal.content.ItemMetalWool;
import com.builtbroken.sheepmetal.data.SheepTypes;
import com.builtbroken.sheepmetal.entity.EntityMetalSheep;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 7/21/2018.
 */
@Mod(modid = SheepMetal.DOMAIN, name = "[SBM] Sheep Metal", version = SheepMetal.VERSION)
@Mod.EventBusSubscriber(modid = SheepMetal.DOMAIN)
public class SheepMetal
{
    public static final String DOMAIN = "sbmsheepmetal";
    public static final String PREFIX = DOMAIN + ":";

    public static final String MAJOR_VERSION = "@MAJOR@";
    public static final String MINOR_VERSION = "@MINOR@";
    public static final String REVISION_VERSION = "@REVIS@";
    public static final String BUILD_VERSION = "@BUILD@";
    public static final String MC_VERSION = "@MC@";
    public static final String VERSION = MC_VERSION + "-" + MAJOR_VERSION + "." + MINOR_VERSION + "." + REVISION_VERSION + "." + BUILD_VERSION;

    public static final int ENTITY_ID_PREFIX = 50;
    private static int nextEntityID = ENTITY_ID_PREFIX;

    private static final Logger logger = LogManager.getLogger(DOMAIN);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        SheepTypes.setupTypes();
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        for (SheepTypes type : SheepTypes.values())
        {
            event.getRegistry().register(type.woolItem = new ItemMetalWool(type));
            event.getRegistry().register(type.woolItemBlock = new ItemBlock(type.woolBlock).setRegistryName(type.woolBlock.getRegistryName()));
        }
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        for (SheepTypes type : SheepTypes.values())
        {
            event.getRegistry().register(type.woolBlock = new BlockMetalWool(type,
                    type != SheepTypes.COAL ? Material.IRON : Material.ROCK));
        }
        Blocks.FIRE.setFireInfo(SheepTypes.COAL.woolBlock, 15, 100);
    }

    @SubscribeEvent
    public static void registerEntity(RegistryEvent.Register<EntityEntry> event)
    {
        EntityEntryBuilder<EntityMetalSheep> builder = EntityEntryBuilder.create();
        builder.name(PREFIX + "sheep.metal");
        builder.id(new ResourceLocation(DOMAIN, "sheep.metal"), nextEntityID++);
        builder.tracker(128, 1, true);
        builder.entity(EntityMetalSheep.class);
        builder.egg(Color.green.getRGB(), Color.RED.getRGB());

        if (ConfigSpawn.SHOULD_SPAWN)
        {
            List<Biome> biomes = getSpawnBiomes();

            builder.spawn(EnumCreatureType.CREATURE, ConfigSpawn.SPAWN_WEIGHT, ConfigSpawn.SPAWN_MIN, ConfigSpawn.SPAWN_MAX, biomes.toArray(new Biome[biomes.size()]));
        }

        event.getRegistry().register(builder.build());
    }

    @Mod.EventHandler
    public void preInit(FMLPostInitializationEvent event)
    {
        final File file = new File(".", "sheep-metal-spawn-output.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file)))
        {
            SheepTypes.outputRandomData((str) -> {
                try
                {
                    writer.write(str);
                }
                catch (IOException e)
                {
                    throw new RuntimeException(e);
                }
            }, 1000000);
        }
        catch (Exception e)
        {
            logger.error("Failed to write spawn data output", e);
        }
    }

    /**
     * @return A Biome list containing all biomes that the metal sheep should spawn in
     */
    private static List<Biome> getSpawnBiomes()
    {
        List<String> configBiomes = Arrays.asList(ConfigSpawn.BIOME_LIST); //easier to check for contained elements this way
        List<Biome> biomes = new ArrayList<Biome>();

        if(ConfigSpawn.LIST_TYPE == ListType.BLACKLIST)
        {
            for(Biome biome : ForgeRegistries.BIOMES.getValuesCollection())
            {
                for(SpawnListEntry entry : biome.getSpawnableList(EnumCreatureType.CREATURE))
                {
                    if(!configBiomes.contains(biome.getRegistryName().toString()) && entry.entityClass == EntitySheep.class)
                        biomes.add(biome);
                }
            }
        }
        else if(ConfigSpawn.LIST_TYPE == ListType.WHITELIST)
        {
            for(Biome biome : ForgeRegistries.BIOMES.getValuesCollection())
            {
                if(configBiomes.contains(biome.getRegistryName().toString()))
                    biomes.add(biome);
            }
        }

        return biomes;
    }
}
