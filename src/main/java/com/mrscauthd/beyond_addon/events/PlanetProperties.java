package com.mrscauthd.beyond_addon.events;

import com.mojang.datafixers.util.Pair;
import com.mrscauthd.beyond_addon.BeyondAddon;
import com.mrscauthd.beyond_addon.world.PlanetsRegistry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.mrscauthd.beyond_earth.common.registries.LevelRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(modid = BeyondAddon.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PlanetProperties {

    public static final List<ResourceKey<Level>> LEVELS_WITHOUT_RAIN = Arrays.asList(
            PlanetsRegistry.PLANET,
            PlanetsRegistry.ORBIT
    );

    public static List<ResourceKey<Level>> LEVELS_WITHOUT_OXYGEN = Arrays.asList(
            PlanetsRegistry.PLANET,
            PlanetsRegistry.ORBIT
    );

    public static List<ResourceKey<Level>> SPACE_LEVELS = Arrays.asList(
            PlanetsRegistry.PLANET,
            PlanetsRegistry.ORBIT
    );

    public static List<Pair<ResourceKey<Level>, ResourceKey<Level>>> LEVELS_WITH_ORBIT = Arrays.asList(
            new Pair<>(PlanetsRegistry.PLANET, PlanetsRegistry.ORBIT)
    );

    public static List<ResourceKey<Level>> ORBIT_LEVELS = Arrays.asList(
            PlanetsRegistry.ORBIT
    );

    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            LevelRegistry.LEVELS_WITHOUT_RAIN = new ArrayList<>(LevelRegistry.LEVELS_WITHOUT_RAIN);
            LevelRegistry.LEVELS_WITHOUT_RAIN.addAll(LEVELS_WITHOUT_RAIN);

            LevelRegistry.LEVELS_WITHOUT_OXYGEN = new ArrayList<>(LevelRegistry.LEVELS_WITHOUT_OXYGEN);
            LevelRegistry.LEVELS_WITHOUT_OXYGEN.addAll(LEVELS_WITHOUT_OXYGEN);

            LevelRegistry.SPACE_LEVELS = new ArrayList<>(LevelRegistry.SPACE_LEVELS);
            LevelRegistry.SPACE_LEVELS.addAll(SPACE_LEVELS);

            LevelRegistry.LEVELS_WITH_ORBIT = new ArrayList<>(LevelRegistry.LEVELS_WITH_ORBIT);
            LevelRegistry.LEVELS_WITH_ORBIT.addAll(LEVELS_WITH_ORBIT);

            LevelRegistry.ORBIT_LEVELS = new ArrayList<>(LevelRegistry.ORBIT_LEVELS);
            LevelRegistry.ORBIT_LEVELS.addAll(ORBIT_LEVELS);
        });
    }
}
