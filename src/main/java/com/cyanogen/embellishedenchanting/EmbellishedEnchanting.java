package com.cyanogen.embellishedenchanting;

import com.cyanogen.embellishedenchanting.config.Options;
import com.cyanogen.embellishedenchanting.enchantments._RegisterEnchants;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.cyanogen.embellishedenchanting.EmbellishedEnchanting.MOD_ID;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MOD_ID)
public class EmbellishedEnchanting
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "embellishedenchanting";

    public EmbellishedEnchanting() {
        // Register the setup method for modloading
        IEventBus eventBus =  FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);
        _RegisterEnchants.register(eventBus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Options.COMMON_SPEC);



        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new EnchantmentEventHandler());

    }

    private void clientSetup(final FMLClientSetupEvent event){

    }
}
