package com.mrscauthd.beyond_addon.network;

import com.mrscauthd.beyond_addon.world.PlanetsRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.mrscauthd.beyond_earth.common.menus.planetselection.helper.PlanetSelectionMenuNetworkHandlerHelper;
import net.mrscauthd.beyond_earth.common.util.Methods;

import java.util.function.Supplier;

public class PlanetAddonSelectionMenuNetworkHandler extends PlanetSelectionMenuNetworkHandlerHelper {
    private int integer = 0;

    public PlanetAddonSelectionMenuNetworkHandler(int integer) {
        this.setInteger(integer);
    }

    public int getInteger() {
        return this.integer;
    }

    public void setInteger(int integer) {
        this.integer = integer;
    }

    public PlanetAddonSelectionMenuNetworkHandler(FriendlyByteBuf buffer) {
        this.setInteger(buffer.readInt());
    }

    public static PlanetAddonSelectionMenuNetworkHandler decode(FriendlyByteBuf buffer) {
        return new PlanetAddonSelectionMenuNetworkHandler(buffer);
    }

    public static void encode(PlanetAddonSelectionMenuNetworkHandler message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.getInteger());
    }

    public static void handle(PlanetAddonSelectionMenuNetworkHandler message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        ServerPlayer player = context.getSender();

        switch (message.getInteger()) {
            /** Teleport Planet Button */
            case 0:
                message.defaultOptions(player);
                Methods.teleportTo(player, PlanetsRegistry.PLANET, 700);
                break;

            /** Teleport Orbit Button */
            case 1:
                message.defaultOptions(player);
                Methods.teleportTo(player, PlanetsRegistry.ORBIT, 700);
                break;

            /** Teleport Space Station Button */
            case 2:
                message.defaultOptions(player);
                message.deleteItems(player);
                Methods.teleportTo(player, PlanetsRegistry.ORBIT, 700);
                break;
        }

        context.setPacketHandled(true);
    }
}