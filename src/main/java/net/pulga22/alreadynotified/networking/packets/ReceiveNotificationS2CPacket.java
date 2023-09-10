package net.pulga22.alreadynotified.networking.packets;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.pulga22.alreadynotified.notifications.Notification;
import net.pulga22.alreadynotified.notifications.Notifications;
import net.pulga22.alreadynotified.notifications.NotificationsSystem;
import net.pulga22.alreadynotified.util.IPlayerNotifications;
import net.pulga22.alreadynotified.util.NotificationsHandler;

public class ReceiveNotificationS2CPacket {
    public static void onClient(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {

        PlayerEntity player = client.player;
        if (player == null) return;
        Notification notification = NotificationsHandler.fromBuf(buf);
        boolean isDedicated = buf.readBoolean();
        NotificationsSystem system = NotificationsSystem.getInstance();
        Notifications notifications = system.getSavedNotifications();
        if (notifications == null) return;
        if (isDedicated){
            notifications.addNotification(notification);
            system.save();
        }
        ((IPlayerNotifications) player).alreadynotified$markNewNotification();

    }
}
