package com.builtbroken.sheepmetal;

import com.builtbroken.sheepmetal.config.ConfigSheep;
import com.builtbroken.sheepmetal.content.BlockMetalWool;
import com.builtbroken.sheepmetal.content.ItemMetalWool;
import com.builtbroken.sheepmetal.data.SheepTypes;
import com.builtbroken.sheepmetal.entity.EntityMetalSheep;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 7/21/2018.
 */
@Mod(SheepMetal.DOMAIN)
@Mod.EventBusSubscriber(modid = SheepMetal.DOMAIN, bus = Bus.MOD)
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

    public static EntityType<EntityMetalSheep> ENTITY_TYPE_METAL_SHEEP = (EntityType<EntityMetalSheep>) EntityType.Builder.<EntityMetalSheep>create(EntityMetalSheep::new, EntityClassification.CREATURE)
            .size(0.9F, 1.3F)
            .setTrackingRange(128)
            .setUpdateInterval(1)
            .setCustomClientFactory((spawnEntity, world) -> new EntityMetalSheep(world))
            .build(PREFIX + "sheep.metal")
            .setRegistryName(new ResourceLocation(DOMAIN, "sheep.metal"));

    public SheepMetal()
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigSheep.CONFIG_SPEC);
    }

    @SubscribeEvent
    public static void onFMLCommonSetup(FMLCommonSetupEvent event)
    {
        SheepTypes.setupTypes();
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        for (SheepTypes type : SheepTypes.values())
        {
            event.getRegistry().register(type.woolItem = new ItemMetalWool(type));
            event.getRegistry().register(type.woolItemBlock = new BlockItem(type.woolBlock, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)).setRegistryName(type.woolBlock.getRegistryName()));
        }

        event.getRegistry().register(new SpawnEggItem(ENTITY_TYPE_METAL_SHEEP, Color.green.getRGB(), Color.RED.getRGB(), new Item.Properties()
                .group(ItemGroup.MISC)).setRegistryName(PREFIX + "metal_sheep_spawn_egg"));
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        for (SheepTypes type : SheepTypes.values())
        {
            event.getRegistry().register(type.woolBlock = new BlockMetalWool(type,
                    type != SheepTypes.COAL ? Material.IRON : Material.ROCK));
        }
        ((FireBlock) Blocks.FIRE).setFireInfo(SheepTypes.COAL.woolBlock, 15, 100);
    }

    @SubscribeEvent
    public static void registerEntity(RegistryEvent.Register<EntityType<?>> event)
    {
        event.getRegistry().register(ENTITY_TYPE_METAL_SHEEP);
    }

    @SubscribeEvent
    public static void onLoadComplete(FMLLoadCompleteEvent event)
    {
        List<? extends String> biomes = ConfigSheep.CONFIG.biomes.get();

        if (ConfigSheep.CONFIG.shouldSpawn.get() && biomes.size() > 0)
        {
            for (Biome biome : ForgeRegistries.BIOMES)
            {
                if (biomes.contains(biome.getRegistryName().toString()))
                {
                    biome.getSpawns(EntityClassification.CREATURE).add(
                            new Biome.SpawnListEntry(ENTITY_TYPE_METAL_SHEEP,
                                    ConfigSheep.CONFIG.spawnWeight.get(),
                                    ConfigSheep.CONFIG.spawnMin.get(),
                                    ConfigSheep.CONFIG.spawnMax.get()));
                }
            }
        }

        if (ConfigSheep.CONFIG.enableSpawnWeightDebug.get())
        {
            writeChanceData();
        }
    }

    public static void writeChanceData()
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
            e.printStackTrace();
            //logger.err("Failed to write spawn data output", e); TODO
        }
    }
}
