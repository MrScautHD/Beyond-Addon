package com.mrscauthd.beyond_addon.events;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrscauthd.beyond_addon.BeyondAddon;
import com.mrscauthd.beyond_addon.network.NetworksAddonRegistry;
import com.mrscauthd.beyond_addon.network.PlanetAddonSelectionMenuNetworkHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mrscauthd.beyond_earth.client.events.forge.PlanetSelectionScreenBackgroundRenderEvent;
import net.mrscauthd.beyond_earth.client.events.forge.PlanetSelectionScreenButtonVisibilityEvent;
import net.mrscauthd.beyond_earth.client.events.forge.PlanetSelectionScreenInitEvent;
import net.mrscauthd.beyond_earth.client.screens.buttons.ModifiedButton;
import net.mrscauthd.beyond_earth.client.screens.helper.ScreenHelper;
import net.mrscauthd.beyond_earth.client.screens.planetselection.PlanetSelectionScreen;
import net.mrscauthd.beyond_earth.client.screens.planetselection.helper.CategoryHelper;
import net.mrscauthd.beyond_earth.client.screens.planetselection.helper.PlanetSelectionScreenHelper;

import java.util.List;

@Mod.EventBusSubscriber(modid = BeyondAddon.MODID, value = Dist.CLIENT)
public class PlanetSelectionScreenEvents {

    /** TEXT */
    public static final Component SOLAR_SYSTEM_BUTTON_TEXT = tl("solar_system_button");
    public static final Component PLANET_BUTTON_TEXT = tl("planet_button");

    /** BUTTONS */
    private static ModifiedButton backButton;

    private static ModifiedButton solarSystemButton;
    private static ModifiedButton planetCategoryButton;
    private static ModifiedButton planetHandlerButton;
    private static ModifiedButton planetOrbitHandlerButton;
    private static ModifiedButton planetSpaceStationHandlerButton;

    /** CATEGORY */
    private static CategoryHelper category;

    /** TEXTURES */
    public static final ResourceLocation PLANET_TEXTURE = new ResourceLocation(BeyondAddon.MODID, "textures/sky/planet.png");

    @SubscribeEvent
    public static void buttonVisibilityPre(PlanetSelectionScreenButtonVisibilityEvent.Pre event) {
        PlanetSelectionScreen screen = (PlanetSelectionScreen) event.getScreen();

        /** SET THE MAIN (BEYOND EARTH) CATEGORY TO -1 */
        if (PlanetSelectionScreenHelper.categoryRange(category.get(), 1,2)) {
            screen.category.set(-1);
        }
    }

    @SubscribeEvent
    public static void buttonVisibilityPost(PlanetSelectionScreenButtonVisibilityEvent.Post event) {
        PlanetSelectionScreen screen = (PlanetSelectionScreen) event.getScreen();

        /** BUTTONS VISIBLE SYSTEM */
        screen.visibleButton(backButton, PlanetSelectionScreenHelper.categoryRange(category.get(), 1, 2));

        screen.visibleButton(solarSystemButton, screen.category.get() == 0);
        screen.visibleButton(planetCategoryButton, category.get() == 1);
        screen.visibleButton(planetHandlerButton, category.get() == 2);
        screen.visibleButton(planetOrbitHandlerButton, category.get() == 2);
        screen.visibleButton(planetSpaceStationHandlerButton, category.get() == 2);
    }

    @SubscribeEvent
    public static void backgroundRenderPost(PlanetSelectionScreenBackgroundRenderEvent.Post event) {
        PlanetSelectionScreen screen = (PlanetSelectionScreen) event.getScreen();
        PoseStack poseStack = event.getPoseStack();

        /** ENABLE BLEND */
        PlanetSelectionScreenHelper.enableRenderSystem();

        /** SOLAR SYSTEM */
        if (PlanetSelectionScreenHelper.categoryRange(category.get(), 1, 2)) {
            PlanetSelectionScreenHelper.addCircle(screen.width / 2, screen.height / 2, 23.0D, 180);
        }

        /** SUN */
        if (PlanetSelectionScreenHelper.categoryRange(category.get(), 1, 2)) {
            ScreenHelper.drawTexture(poseStack, (screen.width - 15) / 2, (screen.height - 15) / 2, 15, 15, PlanetSelectionScreen.SUN_TEXTURE);
        }

        /** PLANETS */
        if (PlanetSelectionScreenHelper.categoryRange(category.get(), 1, 2)) {
            PlanetSelectionScreenHelper.addRotatedObject(screen, poseStack, PLANET_TEXTURE, -20.5F, -20.5F, 10, 10, screen.rotationEarth);
        }

        /** SMALL MENU RENDERER */
        if (PlanetSelectionScreenHelper.categoryRange(category.get(), 1, 1)) {
            ScreenHelper.drawTexture(poseStack, 0, (screen.height / 2) - 177 / 2, 105, 177, PlanetSelectionScreen.SMALL_MENU_LIST);
        }

        /** LARGE MENU RENDERER */
        if (PlanetSelectionScreenHelper.categoryRange(category.get(), 2, 2)) {
            ScreenHelper.drawTexture(poseStack, 0, (screen.height / 2) - 177 / 2, 215, 177, PlanetSelectionScreen.LARGE_MENU_TEXTURE);
        }

        /** DISABLE BLEND */
        PlanetSelectionScreenHelper.disableRenderSystem();
    }

    @SubscribeEvent
    public static void screenInitPost(PlanetSelectionScreenInitEvent.Post event) {
        PlanetSelectionScreen screen = (PlanetSelectionScreen) event.getScreen();

        /** CREATE A CATEGORY */
        category = new CategoryHelper();

        /** SOLAR SYSTEM BUTTON */
        solarSystemButton = PlanetSelectionScreenHelper.addCategoryButton(screen, category, 10, 1, 70, 20, 1, true, false, ModifiedButton.Types.MILKY_WAY_CATEGORY, List.of(SOLAR_SYSTEM_BUTTON_TEXT.getString()), screen.BLUE_BUTTON_TEXTURE, screen.BLUE_LIGHT_BUTTON_TEXTURE, SOLAR_SYSTEM_BUTTON_TEXT);

        /** BACK BUTTON */
        backButton = PlanetSelectionScreenHelper.addBackButton(screen, 10, 1, 70, 20, false, PlanetSelectionScreen.DARK_BLUE_BUTTON_TEXTURE, PlanetSelectionScreen.DARK_BLUE_LIGHT_BUTTON_TEXTURE, PlanetSelectionScreen.BACK_TEXT, (onPress) -> {
            if (category.get() == 1) {
                category.set(0);
                screen.category.set(0);
                screen.scrollIndex = 0;
                screen.updateButtonVisibility();
            } else if (PlanetSelectionScreenHelper.categoryRange(category.get(), 2, 3)) {
                category.set(1);
                screen.scrollIndex = 0;
                screen.updateButtonVisibility();
            }
        });

        /** PLANET BUTTONS */
        planetCategoryButton = PlanetSelectionScreenHelper.addCategoryButton(screen, category, 10, 1, 70, 20, 2, screen.checkTier(4), false, ModifiedButton.Types.SOLAR_SYSTEM_CATEGORY, List.of(PLANET_BUTTON_TEXT.getString(), screen.ROCKET_TIER_4_TEXT.getString()), screen.RED_BUTTON_TEXTURE, screen.RED_LIGHT_BUTTON_TEXTURE, PLANET_BUTTON_TEXT);

        /** PLANET TELEPORT BUTTONS */
        planetHandlerButton = PlanetSelectionScreenHelper.addHandlerButton(screen, 10, 1, 70, 20, true, false, true, NetworksAddonRegistry.PACKET_HANDLER, getNetworkHandler(0), ModifiedButton.Types.PLANET_CATEGORY, List.of(screen.PLANET_TEXT.getString(), "3.721 m/s", "a" + screen.OXYGEN_TRUE_TEXT.getString(), "a" + "-20"), screen.BLUE_BUTTON_TEXTURE, screen.BLUE_LIGHT_BUTTON_TEXTURE, PLANET_BUTTON_TEXT);

        /** PLANET ORBIT TELEPORT BUTTONS */
        planetOrbitHandlerButton = PlanetSelectionScreenHelper.addHandlerButton(screen, 84, 2, 37, 20, true, false, true, NetworksAddonRegistry.PACKET_HANDLER, getNetworkHandler(1), ModifiedButton.Types.PLANET_CATEGORY, List.of(screen.ORBIT_TEXT.getString(), screen.NO_GRAVITY_TEXT.getString(), "c" + screen.OXYGEN_FALSE_TEXT.getString(), "c" + "-270"), screen.SMALL_BLUE_BUTTON_TEXTURE, screen.SMALL_BLUE_LIGHT_BUTTON_TEXTURE, screen.ORBIT_TEXT);

        /** PLANET SPACE STATION TELEPORT BUTTONS */
        planetSpaceStationHandlerButton = PlanetSelectionScreenHelper.addHandlerButton(screen, 125, 3, 75, 20, screen.spaceStationItemList, false, true, NetworksAddonRegistry.PACKET_HANDLER, getNetworkHandler(2), ModifiedButton.Types.PLANET_SPACE_STATION_CATEGORY, List.of(screen.ORBIT_TEXT.getString(), screen.NO_GRAVITY_TEXT.getString(), "c" + screen.OXYGEN_FALSE_TEXT.getString(), "c" + "-270"), screen.LARGE_RED_BUTTON_TEXTURE, screen.LARGE_RED_LIGHT_BUTTON_TEXTURE, screen.SPACE_STATION_TEXT);
    }

    /** GET NETWORK HANDLER */
    public static PlanetAddonSelectionMenuNetworkHandler getNetworkHandler(int handler) {
        return new PlanetAddonSelectionMenuNetworkHandler(handler);
    }

    /** CREATE A TRANSLATABLE KEY */
    public static Component tl(String text) {
        return Component.translatable("gui." + BeyondAddon.MODID + ".planet_selection." + text);
    }
}