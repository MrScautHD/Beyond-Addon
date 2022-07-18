package com.mrscauthd.beyond_addon.events;

import com.mrscauthd.beyond_addon.BeyondAddon;
import com.mrscauthd.beyond_addon.world.PlanetsRegistry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mrscauthd.beyond_earth.common.events.forge.ItemEntityTickAtEndEvent;
import net.mrscauthd.beyond_earth.common.events.forge.LivingGravityEvent;
import net.mrscauthd.beyond_earth.common.util.EntityGravity;
import net.mrscauthd.beyond_earth.common.util.ItemGravity;
import net.mrscauthd.beyond_earth.common.util.Methods;

@Mod.EventBusSubscriber(modid = BeyondAddon.MODID)
public class ModEvents {

    @SubscribeEvent
    public static void entityGravity(LivingGravityEvent event) {
        LivingEntity entity = event.getEntity();
        Level level = entity.level;

        /** PLANET GRAVITY */
        if (Methods.isLevel(level, PlanetsRegistry.PLANET)) {
            EntityGravity.setGravity(entity, event.getAttributeInstance(), 0.02, true);
        }
    }

    @SubscribeEvent
    public static void itemGravity(ItemEntityTickAtEndEvent event) {
        ItemEntity entity = event.getEntity();
        Level level = entity.level;

        /** ITEM ENTITY GRAVITY SYSTEM */
        if (Methods.isLevel(level, PlanetsRegistry.PLANET)) {
            ItemGravity.gravitySystem(entity, 0.05F);
        }
    }

    @SubscribeEvent
    public static void entityGravityFallDamageHandler(LivingFallEvent event) {
        LivingEntity entity = event.getEntity();
        Level level = entity.level;

        if (Methods.isLevel(level, PlanetsRegistry.PLANET)) {
            event.setDistance(event.getDistance() - 5.5F);
        }
    }
}
