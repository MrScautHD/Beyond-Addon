package com.mrscauthd.beyond_addon;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.mrscauthd.beyond_earth.common.registries.NetworksRegistry;
import org.slf4j.Logger;

@Mod(BeyondAddon.MODID)
public class BeyondAddon {

    public static final String MODID = "beyond_addon";
    public static final Logger LOGGER = LogUtils.getLogger();

    public BeyondAddon() {
        MinecraftForge.EVENT_BUS.register(this);

        /** NETWORK REGISTRIES */
        NetworksRegistry.register();
    }
}
