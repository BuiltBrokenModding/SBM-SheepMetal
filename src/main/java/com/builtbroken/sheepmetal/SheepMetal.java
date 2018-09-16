package com.builtbroken.sheepmetal;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.builtbroken.sheepmetal.config.ConfigSpawn;
import com.builtbroken.sheepmetal.content.BlockMetalWool;
import com.builtbroken.sheepmetal.content.ItemMetalWool;
import com.builtbroken.sheepmetal.entity.EntityMetalSheep;

import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
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
        SheepTypes.initAll();
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
            event.getRegistry().register(type.woolBlock = new BlockMetalWool(type));
        }
    }

    @SubscribeEvent
    public static void registerEntity(RegistryEvent.Register<EntityEntry> event)
    {
        EntityEntryBuilder builder = EntityEntryBuilder.create();
        builder.name(PREFIX + "sheep.metal");
        builder.id(new ResourceLocation(DOMAIN, "sheep.metal"), nextEntityID++);
        builder.tracker(128, 1, true);
        builder.entity(EntityMetalSheep.class);
        builder.egg(Color.green.getRGB(), Color.RED.getRGB());

        if(ConfigSpawn.SHOULD_SPAWN)
        {
            List<Biome> biomes = new ArrayList<Biome>();

            for(String location : ConfigSpawn.BIOMES)
            {
                Biome biome = ForgeRegistries.BIOMES.getValue(new ResourceLocation(location));

                if(biome != null)
                    biomes.add(biome);
                else
                    logger.error("SheepMetal#registerEntity() -> Failed to find a biome with id [" + location + "] while loading config data for entity registry");
            }

            builder.spawn(EnumCreatureType.CREATURE, ConfigSpawn.SPAWN_WEIGHT, ConfigSpawn.SPAWN_MIN, ConfigSpawn.SPAWN_MAX, biomes.toArray(new Biome[biomes.size()]));
        }

        event.getRegistry().register(builder.build());
    }
}
