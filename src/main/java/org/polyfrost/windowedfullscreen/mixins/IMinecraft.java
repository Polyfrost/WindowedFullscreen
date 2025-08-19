package org.polyfrost.windowedfullscreen.mixins;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Minecraft.class)
public interface IMinecraft {
    @Accessor("fullscreen")
    void setFullScreen(boolean b);

    @Accessor("tempDisplayWidth")
    int getTempDisplayWidth();

    @Accessor("tempDisplayHeight")
    int getTempDisplayHeight();

    @Invoker("updateFramebufferSize")
    void invokeUpdateFramebufferSize();

    @Invoker("resize")
    void invokeResize(int width, int height);
}
