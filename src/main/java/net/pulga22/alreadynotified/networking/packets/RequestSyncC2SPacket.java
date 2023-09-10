package net.pulga22.alreadynotified.networking.packets;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.pulga22.alreadynotified.networking.Packets;
import net.pulga22.alreadynotified.notifications.Notifications;
import net.pulga22.alreadynotified.notifications.NotificationsSystem;
import net.pulga22.alreadynotified.util.NotificationsHandler;

public class RequestSyncC2SPacket {

    public static void onServer(MinecraftServer server, ServerPlayerEntity player,
                                ServerPlayNetworkHandler networkHandler, PacketByteBuf buf, PacketSender packetSender) {

        server.execute(() -> {
            Notifications savedNotifications = NotificationsSystem.getInstance().getSavedNotifications();
            if (savedNotifications == null) return;
            PacketByteBuf newBuf = NotificationsHandler.collectionBufOf(savedNotifications);
            ServerPlayNetworking.send(player, Packets.SYNC, newBuf);
        });

    }

}
