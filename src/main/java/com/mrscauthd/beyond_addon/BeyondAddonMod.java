package com.mrscauthd.beyond_addon;

import com.mojang.logging.LogUtils;
import com.mrscauthd.beyond_addon.network.PlanetAddonSelectionMenuNetworkHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.slf4j.Logger;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Mod(BeyondAddonMod.MODID)
public class BeyondAddonMod {

    public static final String MODID = "beyond_addon";
    public static final Logger LOGGER = LogUtils.getLogger();

    /** PACKET HANDLER */
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(MODID, MODID), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
    private static int messageID;

    public BeyondAddonMod() {
        MinecraftForge.EVENT_BUS.register(this);

        // NETWORK
        BeyondAddonMod.addNetworkMessage(PlanetAddonSelectionMenuNetworkHandler.class, PlanetAddonSelectionMenuNetworkHandler::encode, PlanetAddonSelectionMenuNetworkHandler::decode, PlanetAddonSelectionMenuNetworkHandler::handle);
    }

    public static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
        PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
        messageID++;
    }
}
