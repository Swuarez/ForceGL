package com.swuarez.forcegl.mixin;

import com.mojang.renderpearl.backend.opengl.GlBackend;
import org.lwjgl.sdl.SDLVideo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GlBackend.class)
public class GlBackendMixin {
    @Redirect(method = "createWindow", at = @At(value = "INVOKE", target = "Lorg/lwjgl/sdl/SDLVideo;SDL_GL_SetAttribute(II)Z"))
    private boolean forcegl(int attribute, int value) {
        if (attribute == SDLVideo.SDL_GL_CONTEXT_MAJOR_VERSION) {
            value = 4;
        } else if (attribute == SDLVideo.SDL_GL_CONTEXT_MINOR_VERSION) {
            value = 6;
        } else if (attribute == SDLVideo.SDL_GL_CONTEXT_PROFILE_MASK) {
            value = SDLVideo.SDL_GL_CONTEXT_PROFILE_CORE;
        } else if (attribute == SDLVideo.SDL_GL_CONTEXT_FLAGS) {
            value = SDLVideo.SDL_GL_CONTEXT_FORWARD_COMPATIBLE_FLAG;
        }
        return SDLVideo.SDL_GL_SetAttribute(attribute, value);
    }
}
