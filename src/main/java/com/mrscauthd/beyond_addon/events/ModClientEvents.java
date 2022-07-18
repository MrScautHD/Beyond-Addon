package com.mrscauthd.beyond_addon.events;

import com.mrscauthd.beyond_addon.BeyondAddonMod;
import com.mrscauthd.beyond_addon.world.PlanetsRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mrscauthd.beyond_earth.client.events.forge.PlanetOverlayEvent;
import net.mrscauthd.beyond_earth.client.overlays.RocketHeightBarOverlay;

@Mod.EventBusSubscriber(modid = BeyondAddonMod.MODID, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void overlayChange(PlanetOverlayEvent event) {
        Level level = Minecraft.getInstance().level;

        if (level.dimension() == PlanetsRegistry.PLANET) {
            event.setResourceLocation(RocketHeightBarOverlay.MOON_PLANET_BAR_TEXTURE);
        }
    }
}
