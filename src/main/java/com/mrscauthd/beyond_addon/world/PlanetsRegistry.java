package com.mrscauthd.beyond_addon.world;

import com.mrscauthd.beyond_addon.BeyondAddonMod;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class PlanetsRegistry {

    public static final ResourceKey<Level> PLANET = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(BeyondAddonMod.MODID, "planet"));
    public static final ResourceKey<Level> ORBIT = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(BeyondAddonMod.MODID, "orbit"));
}
