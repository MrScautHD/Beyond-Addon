package com.mrscauthd.beyond_addon.events;

import com.mrscauthd.beyond_addon.BeyondAddonMod;
import com.mrscauthd.beyond_addon.world.PlanetsRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mrscauthd.beyond_earth.events.EntityGravity;
import net.mrscauthd.beyond_earth.events.Methods;
import net.mrscauthd.beyond_earth.events.forge.LivingEntityTickEndEvent;

@Mod.EventBusSubscriber(modid = BeyondAddonMod.MODID)
public class GravityEvents {

    @SubscribeEvent
    public static void livingEntityEndTick(LivingEntityTickEndEvent event) {

        /** PLANET GRAVITY */
        if (Methods.isWorld(event.getEntityLiving().level, PlanetsRegistry.PLANET)) {
            EntityGravity.gravitySystem(event.getEntityLiving(), 0.03F);
        }

        /** ORBIT GRAVITY */
        if (Methods.isWorld(event.getEntityLiving().level, PlanetsRegistry.ORBIT)) {
            EntityGravity.gravitySystem(event.getEntityLiving(), 0.02F);
        }
    }
}
