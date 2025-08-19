package org.polyfrost.windowedfullscreen.config;

import org.polyfrost.windowedfullscreen.WindowedFullscreen;
import org.polyfrost.oneconfig.api.config.v1.Config;
import org.polyfrost.oneconfig.api.config.v1.annotations.Switch;

public class WindowedFullscreenConfig extends Config {
    public WindowedFullscreenConfig() {
        super(WindowedFullscreen.ID + ".json", WindowedFullscreen.NAME, Category.QOL);
    }

    @Switch(
            title = "Windowed Fullscreen",
            description = "Implement Windowed Fullscreen in Minecraft, allowing you to drag your mouse outside the window."
    )
    public boolean windowedFullscreen = false;
}
