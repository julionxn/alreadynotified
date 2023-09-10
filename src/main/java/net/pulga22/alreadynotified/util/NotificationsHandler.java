package net.pulga22.alreadynotified.util;

import com.google.common.collect.Lists;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.pulga22.alreadynotified.notifications.Notification;
import net.pulga22.alreadynotified.notifications.Notifications;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NotificationsHandler {

    public static PacketByteBuf bufOf(String title, String message){
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(title);
        buf.writeString(message);
        return buf;
    }

    public static PacketByteBuf bufOf(Notification notification){
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeString(notification.title());
        buf.writeString(notification.message());
        return buf;
    }

    public static Notification fromBuf(PacketByteBuf buf){
        String title = buf.readString();
        String message = buf.readString();
        return Notification.of(title, message);
    }

    public static PacketByteBuf collectionBufOf(Notifications notifications){
        PacketByteBuf buf = PacketByteBufs.create();
        List<Notification> readNotifications = notifications.getNotifications();
        Collection<String> titles = new ArrayList<>();
        Collection<String> messages = new ArrayList<>();
        readNotifications.forEach(notification -> {
            titles.add(notification.title());
            messages.add(notification.message());
        });
        buf.writeCollection(titles, PacketByteBuf::writeString);
        buf.writeCollection(messages, PacketByteBuf::writeString);
        return buf;
    }

    public static List<Notification> collectionFromBuf(PacketByteBuf buf){
        List<String> titles = buf.readCollection(Lists::newArrayListWithCapacity, PacketByteBuf::readString);
        List<String> messages = buf.readCollection(Lists::newArrayListWithCapacity, PacketByteBuf::readString);
        List<Notification> notifications = new ArrayList<>();
        for (int i = 0; i < titles.size(); i++) {
            notifications.add(Notification.of(titles.get(i), messages.get(i)));
        }
        return notifications;
    }

}
