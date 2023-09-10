package net.pulga22.alreadynotified;

import net.fabricmc.api.DedicatedServerModInitializer;

public class AlreadyNotifiedDedicatedServer implements DedicatedServerModInitializer {

    public static boolean isDedicated = false;

    @Override
    public void onInitializeServer() {
        isDedicated = true;
    }
}
