package com.mrscauthd.beyond_addon.network;

import com.mrscauthd.beyond_addon.world.PlanetsRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.mrscauthd.beyond_earth.events.Methods;
import net.mrscauthd.beyond_earth.guis.screens.planetselection.helper.PlanetSelectionGuiNetworkHandlerHelper;

import java.util.function.Supplier;

public class PlanetSelectionGuiNetworkHandler extends PlanetSelectionGuiNetworkHandlerHelper {
    private int integer = 0;

    public PlanetSelectionGuiNetworkHandler(int integer) {
        this.setInteger(integer);
    }

    public int getInteger() {
        return this.integer;
    }

    public void setInteger(int integer) {
        this.integer = integer;
    }

    public PlanetSelectionGuiNetworkHandler(FriendlyByteBuf buffer) {
        this.setInteger(buffer.readInt());
    }

    public static PlanetSelectionGuiNetworkHandler decode(FriendlyByteBuf buffer) {
        return new PlanetSelectionGuiNetworkHandler(buffer);
    }

    public static void encode(PlanetSelectionGuiNetworkHandler message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.getInteger());
    }

    public static void handle(PlanetSelectionGuiNetworkHandler message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        ServerPlayer player = context.getSender();

        switch (message.getInteger()) {
            /** Teleport Planet Button */
            case 0:
                message.defaultOptions(player);
                Methods.teleportButton(player, PlanetsRegistry.PLANET, false);
                break;

            /** Teleport Orbit Button */
            case 1:
                message.defaultOptions(player);
                Methods.teleportButton(player, PlanetsRegistry.ORBIT, false);
                break;

            /** Teleport Space Station Button */
            case 2:
                message.defaultOptions(player);
                message.deleteItems(player);
                Methods.teleportButton(player, PlanetsRegistry.ORBIT, true);
                break;
        }

        context.setPacketHandled(true);
    }
}