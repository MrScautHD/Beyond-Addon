package com.mrscauthd.beyond_addon.events;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mrscauthd.beyond_addon.BeyondAddonMod;
import com.mrscauthd.beyond_addon.network.PlanetSelectionGuiNetworkHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mrscauthd.beyond_earth.events.forge.PlanetSelectionGuiBackgroundRenderEvent;
import net.mrscauthd.beyond_earth.events.forge.PlanetSelectionGuiButtonVisibilityEvent;
import net.mrscauthd.beyond_earth.events.forge.PlanetSelectionGuiInitEvent;
import net.mrscauthd.beyond_earth.guis.helper.ImageButtonPlacer;
import net.mrscauthd.beyond_earth.guis.screens.planetselection.PlanetSelectionGuiWindow;
import net.mrscauthd.beyond_earth.guis.screens.planetselection.helper.CategoryHelper;
import net.mrscauthd.beyond_earth.guis.screens.planetselection.helper.PlanetSelectionGuiHelper;

import java.util.List;

@Mod.EventBusSubscriber(modid = BeyondAddonMod.MODID, value = Dist.CLIENT)
public class PlanetSelectionGuiEvents {

    /** TEXT */
    public static final Component SOLAR_SYSTEM_BUTTON_TEXT = tl("solar_system_button");
    public static final Component PLANET_BUTTON_TEXT = tl("planet_button");

    /** BUTTONS */
    private static ImageButtonPlacer backButton;

    private static ImageButtonPlacer solarSystemButton;
    private static ImageButtonPlacer planetCategoryButton;
    private static ImageButtonPlacer planetHandlerButton;
    private static ImageButtonPlacer planetOrbitHandlerButton;
    private static ImageButtonPlacer planetSpaceStationHandlerButton;

    /** CATEGORY */
    private static CategoryHelper category;

    /** TEXTURES */
    public static final ResourceLocation PLANET_TEXTURE = new ResourceLocation(BeyondAddonMod.MODID, "textures/sky/planet.png");

    @SubscribeEvent
    public static void buttonVisibilityPre(PlanetSelectionGuiButtonVisibilityEvent.Pre event) {
        PlanetSelectionGuiWindow screen = (PlanetSelectionGuiWindow) event.getScreen();

        /** SET THE MAIN (BEYOND EARTH) CATEGORY TO -1 */
        if (PlanetSelectionGuiHelper.categoryRange(category.get(), 1,2)) {
            screen.category.set(-1);
        }
    }

    @SubscribeEvent
    public static void buttonVisibilityPost(PlanetSelectionGuiButtonVisibilityEvent.Post event) {
        PlanetSelectionGuiWindow screen = (PlanetSelectionGuiWindow) event.getScreen();

        /** BUTTONS VISIBLE SYSTEM */
        screen.visibleButton(backButton, PlanetSelectionGuiHelper.categoryRange(category.get(), 1, 2));

        screen.visibleButton(solarSystemButton, screen.category.get() == 0);
        screen.visibleButton(planetCategoryButton, screen.category.get() == 1);
        screen.visibleButton(planetHandlerButton, category.get() == 2);
        screen.visibleButton(planetOrbitHandlerButton, category.get() == 2);
        screen.visibleButton(planetSpaceStationHandlerButton, category.get() == 2);
    }

    @SubscribeEvent
    public static void backgroundRenderPost(PlanetSelectionGuiBackgroundRenderEvent.Post event) {
        PlanetSelectionGuiWindow screen = (PlanetSelectionGuiWindow) event.getScreen();
        PoseStack poseStack = event.getPoseStack();

        /** ENABLE BLEND */
        PlanetSelectionGuiHelper.enableRenderSystem();

        /** SOLAR SYSTEM */
        if (PlanetSelectionGuiHelper.categoryRange(category.get(), 1, 2)) {
            PlanetSelectionGuiHelper.addCircle(screen.width / 2, screen.height / 2, 23.0D, 180);
        }

        /** SUN */
        if (PlanetSelectionGuiHelper.categoryRange(category.get(), 1, 2)) {
            PlanetSelectionGuiHelper.addTexture(poseStack, (screen.width - 15) / 2, (screen.height - 15) / 2, 15, 15, PlanetSelectionGuiWindow.SUN_TEXTURE);
        }

        /** PLANETS */
        if (PlanetSelectionGuiHelper.categoryRange(category.get(), 1, 2)) {
            PlanetSelectionGuiHelper.addRotatedObject(screen, poseStack, PLANET_TEXTURE, -20.5F, -20.5F, 10, 10, screen.rotationEarth);
        }

        /** SMALL MENU RENDERER */
        if (PlanetSelectionGuiHelper.categoryRange(category.get(), 1, 1)) {
            PlanetSelectionGuiHelper.addTexture(poseStack, 0, (screen.height / 2) - 177 / 2, 105, 177, PlanetSelectionGuiWindow.SMALL_MENU_LIST);
        }

        /** LARGE MENU RENDERER */
        if (PlanetSelectionGuiHelper.categoryRange(category.get(), 2, 2)) {
            PlanetSelectionGuiHelper.addTexture(poseStack, 0, (screen.height / 2) - 177 / 2, 215, 177, PlanetSelectionGuiWindow.LARGE_MENU_TEXTURE);
        }

        /** DISABLE BLEND */
        PlanetSelectionGuiHelper.disableRenderSystem();
    }

    @SubscribeEvent
    public static void screenInitPost(PlanetSelectionGuiInitEvent.Post event) {
        PlanetSelectionGuiWindow screen = (PlanetSelectionGuiWindow) event.getScreen();

        /** CREATE A CATEGORY */
        category = new CategoryHelper();

        /** SOLAR SYSTEM BUTTON */
        solarSystemButton = PlanetSelectionGuiHelper.addCategoryButton(screen, category, 10, 1, 70, 20, 1, true, ImageButtonPlacer.Types.MILKY_WAY_CATEGORY, List.of(SOLAR_SYSTEM_BUTTON_TEXT.getString()), screen.BLUE_BUTTON_TEXTURE, screen.BLUE_LIGHT_BUTTON_TEXTURE, SOLAR_SYSTEM_BUTTON_TEXT);
        screen.visibleButton(solarSystemButton, false);

        /** BACK BUTTON */
        backButton = PlanetSelectionGuiHelper.addBackButton(screen, 10, 1, 70, 20, PlanetSelectionGuiWindow.DARK_BLUE_BUTTON_TEXTURE, PlanetSelectionGuiWindow.DARK_BLUE_LIGHT_BUTTON_TEXTURE, PlanetSelectionGuiWindow.BACK_TEXT, (onPress) -> {
            if (category.get() == 1) {
                category.set(0);
                screen.category.set(0);
                screen.scrollIndex = 0;
                screen.updateButtonVisibility();
            } else if (PlanetSelectionGuiHelper.categoryRange(category.get(), 2, 3)) {
                category.set(1);
                screen.scrollIndex = 0;
                screen.updateButtonVisibility();
            }
        });
        screen.visibleButton(backButton, false);

        /** PLANET BUTTONS */
        planetCategoryButton = PlanetSelectionGuiHelper.addCategoryButton(screen, category, 10, 1, 70, 20, 2, screen.checkTier(4), ImageButtonPlacer.Types.SOLAR_SYSTEM_CATEGORY, List.of(PLANET_BUTTON_TEXT.getString(), screen.ROCKET_TIER_4_TEXT.getString()), screen.RED_BUTTON_TEXTURE, screen.RED_LIGHT_BUTTON_TEXTURE, PLANET_BUTTON_TEXT);
        screen.visibleButton(planetCategoryButton, false);

        /** PLANET TELEPORT BUTTONS */
        planetHandlerButton = PlanetSelectionGuiHelper.addHandlerButton(screen, 10, 1, 70, 20, true, BeyondAddonMod.PACKET_HANDLER, getNetworkHandler(0), ImageButtonPlacer.Types.PLANET_CATEGORY, List.of(screen.PLANET_TEXT.getString(), "3.721 m/s", "a" + screen.OXYGEN_TRUE_TEXT.getString(), "a" + "-20"), screen.BLUE_BUTTON_TEXTURE, screen.BLUE_LIGHT_BUTTON_TEXTURE, PLANET_BUTTON_TEXT);
        screen.visibleButton(planetHandlerButton, false);

        /** PLANET ORBIT TELEPORT BUTTONS */
        planetOrbitHandlerButton = PlanetSelectionGuiHelper.addHandlerButton(screen, 84, 2, 37, 20, true, BeyondAddonMod.PACKET_HANDLER, getNetworkHandler(1), ImageButtonPlacer.Types.PLANET_CATEGORY, List.of(screen.ORBIT_TEXT.getString(), screen.NO_GRAVITY_TEXT.getString(), "c" + screen.OXYGEN_FALSE_TEXT.getString(), "c" + "-270"), screen.SMALL_BLUE_BUTTON_TEXTURE, screen.SMALL_BLUE_LIGHT_BUTTON_TEXTURE, screen.ORBIT_TEXT);
        screen.visibleButton(planetOrbitHandlerButton, false);

        /** PLANET SPACE STATION TELEPORT BUTTONS */
        planetSpaceStationHandlerButton = PlanetSelectionGuiHelper.addHandlerButton(screen, 125, 3, 75, 20, screen.spaceStationItemList, BeyondAddonMod.PACKET_HANDLER, getNetworkHandler(2), ImageButtonPlacer.Types.PLANET_SPACE_STATION_CATEGORY, List.of(screen.ORBIT_TEXT.getString(), screen.NO_GRAVITY_TEXT.getString(), "c" + screen.OXYGEN_FALSE_TEXT.getString(), "c" + "-270"), screen.LARGE_RED_BUTTON_TEXTURE, screen.LARGE_RED_LIGHT_BUTTON_TEXTURE, screen.SPACE_STATION_TEXT);
        screen.visibleButton(planetSpaceStationHandlerButton, false);

        /** UPDATE BUTTON VISIBILITY */
        screen.updateButtonVisibility();
    }

    /** GET NETWORK HANDLER */
    public static PlanetSelectionGuiNetworkHandler getNetworkHandler(int handler) {
        return new PlanetSelectionGuiNetworkHandler(handler);
    }

    /** CREATE A TRANSLATABLE KEY */
    public static Component tl(String string) {
        return new TranslatableComponent("gui." + BeyondAddonMod.MODID + ".planet_selection." + string);
    }
}