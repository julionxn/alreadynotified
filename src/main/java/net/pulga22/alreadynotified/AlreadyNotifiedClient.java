package net.pulga22.alreadynotified;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.pulga22.alreadynotified.networking.Packets;
import net.pulga22.alreadynotified.screen.hud.NewNotificationHud;

public class AlreadyNotifiedClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Packets.registerS2CPackets();
        Keybindings.register();
        HudRenderCallback.EVENT.register(new NewNotificationHud());
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client)->{
            if (client.player != null){
                ClientPlayNetworking.send(Packets.REQUEST_SYNC, PacketByteBufs.empty());
            }
        });
    }

}
