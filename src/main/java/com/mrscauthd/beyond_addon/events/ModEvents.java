package com.mrscauthd.beyond_addon.events;

import com.mrscauthd.beyond_addon.BeyondAddonMod;
import com.mrscauthd.beyond_addon.world.PlanetsRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mrscauthd.beyond_earth.entities.LanderEntity;
import net.mrscauthd.beyond_earth.events.EntityGravity;
import net.mrscauthd.beyond_earth.events.ItemGravity;
import net.mrscauthd.beyond_earth.events.Methods;
import net.mrscauthd.beyond_earth.events.forge.EntityTickEvent;
import net.mrscauthd.beyond_earth.events.forge.ItemEntityTickEndEvent;
import net.mrscauthd.beyond_earth.events.forge.LivingEntityTickEndEvent;

@Mod.EventBusSubscriber(modid = BeyondAddonMod.MODID)
public class ModEvents {

    @SubscribeEvent
    public static void entityGravity(LivingEntityTickEndEvent event) {

        /** PLANET GRAVITY */
        if (Methods.isWorld(event.getEntityLiving().level, PlanetsRegistry.PLANET)) {
            EntityGravity.gravitySystem(event.getEntityLiving(), 0.03F);
        }
    }

    @SubscribeEvent
    public static void itemGravity(ItemEntityTickEndEvent event) {
        ItemEntity entity = event.getEntityItem();
        Level level = entity.level;

        /** ITEM ENTITY GRAVITY SYSTEM */
        if (Methods.isWorld(level, PlanetsRegistry.PLANET)) {
            ItemGravity.gravitySystem(entity, 0.05F);
        }
    }

    @SubscribeEvent
    public static void entityGravityFallDamageHandler(LivingFallEvent event) {
        LivingEntity entity = event.getEntityLiving();
        Level level = entity.level;

        if (Methods.isWorld(level, PlanetsRegistry.PLANET)) {
            event.setDistance(event.getDistance() - 5.5F);
        }
    }

    @SubscribeEvent
    public static void landerTeleport(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Player player = event.player;
            Level level = player.level;

            /** LANDER ORBIT TELEPORT SYSTEM */
            if (player.getVehicle() instanceof LanderEntity) {

                if (Methods.isWorld(level, PlanetsRegistry.ORBIT)) {
                    Methods.landerTeleport(player, PlanetsRegistry.PLANET);
                }
            }
        }
    }

    @SubscribeEvent
    public static void entityFallToPlanet(EntityTickEvent event) {
        Entity entity = event.getEntity();
        Level level = entity.level;

        /** ORBIT TELEPORT SYSTEM */
        if (entity.getY() < 1 && !(entity.getVehicle() instanceof LanderEntity)) {

            if ((entity instanceof LanderEntity) && entity.isVehicle()) {
                return;
            }

            if (level.dimension() == PlanetsRegistry.ORBIT) {
                Methods.entityWorldTeleporter(entity, PlanetsRegistry.PLANET, 450.0D);
            }
        }
    }
}
