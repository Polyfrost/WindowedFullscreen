package org.polyfrost.windowedfullscreen;

//#if FABRIC
//$$ import net.fabricmc.api.ModInitializer;
//#elseif FORGE
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
//#endif

import org.polyfrost.windowedfullscreen.config.WindowedFullscreenConfig;
import org.polyfrost.windowedfullscreen.hooks.FullscreenHook;
import org.polyfrost.oneconfig.api.event.v1.EventManager;

//#if FORGE-LIKE
@Mod(modid = WindowedFullscreen.ID, name = WindowedFullscreen.NAME, version = WindowedFullscreen.VERSION)
//#endif
public class WindowedFullscreen
        //#if FABRIC
        //$$ implements ModInitializer
        //#endif
{
    public static final String ID = "@MOD_ID@";
    public static final String NAME = "@MOD_NAME@";
    public static final String VERSION = "@MOD_VERSION@";

    public static WindowedFullscreenConfig config;

    //#if FABRIC
    //$$ @Override
    //#elseif FORGE
    @Mod.EventHandler
    //#endif
    public void onInitialize(
            //#if FORGE
            FMLInitializationEvent event
            //#endif
    ) {
        config = new WindowedFullscreenConfig();
        EventManager.INSTANCE.register(FullscreenHook.INSTANCE);
    }
}