package org.polyfrost.windowedfullscreen.mixins;

import net.minecraft.client.Minecraft;
import org.polyfrost.windowedfullscreen.hooks.FullscreenHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    @Inject(method = "toggleFullscreen", at = @At("HEAD"), cancellable = true)
    private void borderless$windowedFullscreen(CallbackInfo ci) {
        if (FullscreenHook.INSTANCE.fullscreen()) ci.cancel();
    }
}
