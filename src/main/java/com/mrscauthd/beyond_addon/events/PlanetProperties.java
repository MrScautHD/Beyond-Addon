package com.mrscauthd.beyond_addon.events;

import com.google.common.collect.ImmutableSet;
import com.mrscauthd.beyond_addon.BeyondAddonMod;
import com.mrscauthd.beyond_addon.world.PlanetsRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.mrscauthd.beyond_earth.events.Methods;

import java.util.Set;

@Mod.EventBusSubscriber(modid = BeyondAddonMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PlanetProperties {

    public static Set<ResourceKey<Level>> worldsWithoutRain = Set.of(
            PlanetsRegistry.PLANET,
            PlanetsRegistry.ORBIT
    );

    public static Set<ResourceKey<Level>> spaceWorldsWithoutOxygen = Set.of(
            PlanetsRegistry.PLANET,
            PlanetsRegistry.ORBIT
    );

    public static Set<ResourceKey<Level>> spaceWorlds = Set.of(
            PlanetsRegistry.PLANET,
            PlanetsRegistry.ORBIT
    );

    public static Set<ResourceKey<Level>> orbitWorlds = Set.of(
            PlanetsRegistry.ORBIT
    );

    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            Methods.worldsWithoutRain = new ImmutableSet.Builder<ResourceKey<Level>>().addAll(Methods.worldsWithoutRain).addAll(worldsWithoutRain).build();
            Methods.spaceWorldsWithoutOxygen = new ImmutableSet.Builder<ResourceKey<Level>>().addAll(Methods.spaceWorldsWithoutOxygen).addAll(spaceWorldsWithoutOxygen).build();
            Methods.spaceWorlds = new ImmutableSet.Builder<ResourceKey<Level>>().addAll(Methods.spaceWorlds).addAll(spaceWorlds).build();
            Methods.orbitWorlds = new ImmutableSet.Builder<ResourceKey<Level>>().addAll(Methods.worldsWithoutRain).addAll(orbitWorlds).build();
        });
    }
}
