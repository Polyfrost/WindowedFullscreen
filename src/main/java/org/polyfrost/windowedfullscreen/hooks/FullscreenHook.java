package org.polyfrost.windowedfullscreen.hooks;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Util;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.polyfrost.windowedfullscreen.WindowedFullscreen;
import org.polyfrost.windowedfullscreen.mixins.IMinecraft;
import org.polyfrost.oneconfig.api.event.v1.events.TickEvent;
import org.polyfrost.oneconfig.api.event.v1.invoke.impl.Subscribe;

import java.awt.*;

public class FullscreenHook {
    public static final FullscreenHook INSTANCE = new FullscreenHook();
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static final IMinecraft mca = (IMinecraft) mc;

    private boolean lastFullscreen = false;

    public boolean fullscreen() {
        if (!WindowedFullscreen.config.windowedFullscreen || Util.getOSType() != Util.EnumOS.WINDOWS) {
            return false;
        }

        mca.setFullScreen(!mc.isFullScreen());

        boolean grabbed = Mouse.isGrabbed();
        if (grabbed)
            Mouse.setGrabbed(false);
        try {
            DisplayMode displayMode = Display.getDesktopDisplayMode();
            if (mc.isFullScreen()) {
                System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
                Display.setDisplayMode(displayMode);
                Display.setLocation(0, 0);
            } else {
                System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
                displayMode = new DisplayMode(mca.getTempDisplayWidth(), mca.getTempDisplayHeight());
                Display.setDisplayMode(displayMode);
                displayCommon();
            }
            Display.setFullscreen(false);

            mc.displayWidth = displayMode.getWidth();
            mc.displayHeight = displayMode.getHeight();
            if (mc.currentScreen != null) {
                mca.invokeResize(mc.displayWidth, mc.displayHeight);
            } else {
                mca.invokeUpdateFramebufferSize();
            }
            INSTANCE.lastFullscreen = mc.isFullScreen(); // Forward so both behavior isn't ran
            mc.updateDisplay();
            Mouse.setCursorPosition((Display.getX() + Display.getWidth()) >> 1, (Display.getY() + Display.getHeight()) >> 1);
            if (grabbed)
                Mouse.setGrabbed(true);
            Display.setResizable(false);
            Display.setResizable(true);
            return true;
        } catch (LWJGLException e) {
            // TODO: Replace with a logger
            System.err.println("Failed to toggle fullscreen. " + e);
        }
        return false;
    }

    private void displayCommon() {
        Display.setResizable(false);
        Display.setResizable(true);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - Display.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - Display.getHeight()) / 2);
        Display.setLocation(x, y);
    }

    @Subscribe
    public void tick(TickEvent event) {
        if (!WindowedFullscreen.config.windowedFullscreen) return;

        boolean fullScreenNow = Minecraft.getMinecraft().isFullScreen();
        if (lastFullscreen != fullScreenNow) {
            fix(fullScreenNow);
            lastFullscreen = fullScreenNow;
        }
    }

    public void fix(boolean fullscreen) {
        try {
            if (fullscreen) {
                System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
                Display.setDisplayMode(Display.getDesktopDisplayMode());
                Display.setLocation(0, 0);
                Display.setFullscreen(false);
            } else {
                System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
                Display.setDisplayMode(new DisplayMode(Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight));
                displayCommon();
            }

            Display.setResizable(!fullscreen);
        } catch (LWJGLException e) {
            // TODO: Replace with a logger
            System.err.println("Failed to update screen type. " + e);
        }
    }
}
