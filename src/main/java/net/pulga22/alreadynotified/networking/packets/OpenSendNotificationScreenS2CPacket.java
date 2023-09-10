package net.pulga22.alreadynotified.networking.packets;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.pulga22.alreadynotified.screen.gui.SendNewNotificationScreen;

public class OpenSendNotificationScreenS2CPacket {
    public static void onClient(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {

        PlayerEntity player = client.player;
        if (player == null) return;
        client.execute(() -> {
            client.setScreen(new SendNewNotificationScreen(player));
        });

    }
}
