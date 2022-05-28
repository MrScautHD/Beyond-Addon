package com.mrscauthd.beyond_addon.events;

import com.mrscauthd.beyond_addon.BeyondAddonMod;
import com.mrscauthd.beyond_addon.world.PlanetsRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.events.forge.PlanetOverlayEvent;

@Mod.EventBusSubscriber(modid = BeyondAddonMod.MODID, value = Dist.CLIENT)
public class ModClientEvents {

    private static final ResourceLocation ORBIT_PLANET_BAR_TEXTURE = new ResourceLocation(BeyondEarthMod.MODID, "textures/planet_bar/orbit_planet_bar.png");
    private static final ResourceLocation PLANET_BAR_TEXTURE = new ResourceLocation(BeyondEarthMod.MODID, "textures/planet_bar/venus_planet_bar.png");

    @SubscribeEvent
    public static void overlayChange(PlanetOverlayEvent event) {
        Level level = Minecraft.getInstance().level;

        if (level.dimension() == PlanetsRegistry.PLANET) {
            event.setResourceLocation(PLANET_BAR_TEXTURE);
        }

        if (level.dimension() == PlanetsRegistry.ORBIT) {
            event.setResourceLocation(ORBIT_PLANET_BAR_TEXTURE);
        }
    }
}
