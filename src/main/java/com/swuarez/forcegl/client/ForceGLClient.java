package com.swuarez.forcegl.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import org.lwjgl.opengl.GL11;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ForceGLClient implements ClientModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger("ForceGL");

    @Override
    public void onInitializeClient() {
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            String version = GL11.glGetString(GL11.GL_VERSION);
            String vendor = GL11.glGetString(GL11.GL_VENDOR);
            String renderer = GL11.glGetString(GL11.GL_RENDERER);

            LOGGER.info("ForceGL: OpenGL version={}", version);
            LOGGER.info("ForceGL: vendor={}", vendor);
            LOGGER.info("ForceGL: renderer={}", renderer);

            if (!supports46(version)) {
                throw new IllegalStateException("ForceGL strict mode: OpenGL 4.6 is required but detected " + version
                        + " on " + renderer + " (" + vendor + "). Update GPU drivers or hardware.");
            }
            LOGGER.info("ForceGL: OpenGL 4.6 enforcement succeeded.");
        });
    }

    private static boolean supports46(String version) {
        if (version == null) return false;
        String[] parts = version.trim().split("[ .]");
        int major = 0, minor = 0;
        try { major = Integer.parseInt(parts[0]); } catch (Exception ignored) {}
        if (parts.length > 1) try { minor = Integer.parseInt(parts[1]); } catch (Exception ignored) {}
        return major > 4 || (major == 4 && minor >= 6);
    }
}
