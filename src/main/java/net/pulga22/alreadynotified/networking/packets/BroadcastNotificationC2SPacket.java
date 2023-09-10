package net.pulga22.alreadynotified.networking.packets;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.pulga22.alreadynotified.AlreadyNotifiedDedicatedServer;
import net.pulga22.alreadynotified.networking.Packets;
import net.pulga22.alreadynotified.notifications.Notification;
import net.pulga22.alreadynotified.notifications.Notifications;
import net.pulga22.alreadynotified.notifications.NotificationsSystem;
import net.pulga22.alreadynotified.util.IPlayerNotifications;
import net.pulga22.alreadynotified.util.NotificationsHandler;

import java.util.Collection;

public class BroadcastNotificationC2SPacket {

    public static void onServer(MinecraftServer server, ServerPlayerEntity serverPlayer,
                                ServerPlayNetworkHandler networkHandler, PacketByteBuf buf, PacketSender packetSender) {

        Notification notification = NotificationsHandler.fromBuf(buf);
        server.execute(() -> {
            PacketByteBuf newBuf = NotificationsHandler.bufOf(notification);
            boolean isDedicated = AlreadyNotifiedDedicatedServer.isDedicated;
            newBuf.writeBoolean(isDedicated);
            NotificationsSystem system = NotificationsSystem.getInstance();
            Notifications notifications = system.getSavedNotifications();
            if (notifications == null) return;
            notifications.addNotification(notification);
            system.save();
            Collection<ServerPlayerEntity> players = PlayerLookup.all(server);
            players.forEach(player -> {
                ((IPlayerNotifications) player).alreadynotified$markNewNotification();
                ServerPlayNetworking.send(player, Packets.RECEIVE_NOTIFICATION, newBuf);
            });
        });


    }

}
