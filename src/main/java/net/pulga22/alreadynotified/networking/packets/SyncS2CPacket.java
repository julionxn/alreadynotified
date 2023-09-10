package net.pulga22.alreadynotified.networking.packets;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.pulga22.alreadynotified.notifications.Notification;
import net.pulga22.alreadynotified.notifications.Notifications;
import net.pulga22.alreadynotified.notifications.NotificationsSystem;
import net.pulga22.alreadynotified.util.NotificationsHandler;

import java.util.List;

public class SyncS2CPacket {
    public static void onClient(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {

        List<Notification> newNotifications = NotificationsHandler.collectionFromBuf(buf);
        NotificationsSystem system = NotificationsSystem.getInstance();
        Notifications notifications = system.getSavedNotifications();
        if (notifications == null) return;
        notifications.sync(newNotifications);
        system.save();

    }
}
