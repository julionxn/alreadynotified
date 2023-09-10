package net.pulga22.alreadynotified.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import net.pulga22.alreadynotified.AlreadyNotified;
import net.pulga22.alreadynotified.networking.packets.*;

public class Packets {

    public static final Identifier OPEN_SEND_NOTIFICATION_SCREEN = of("open_send_notification_screen");
    public static final Identifier RECEIVE_NOTIFICATION = of("receive_notification");
    public static final Identifier SYNC = of("sync");

    public static final Identifier BROADCAST_NOTIFICATION = of("broadcast_notification");
    public static final Identifier REQUEST_SYNC = of("request_sync");
    public static final Identifier PENDING_NOTIFICATION_READ = of("pending_notification_read");


    public static void registerS2CPackets(){
        ClientPlayNetworking.registerGlobalReceiver(OPEN_SEND_NOTIFICATION_SCREEN, OpenSendNotificationScreenS2CPacket::onClient);
        ClientPlayNetworking.registerGlobalReceiver(RECEIVE_NOTIFICATION, ReceiveNotificationS2CPacket::onClient);
        ClientPlayNetworking.registerGlobalReceiver(SYNC, SyncS2CPacket::onClient);
    }

    public static void registerC2SPackets(){
        ServerPlayNetworking.registerGlobalReceiver(BROADCAST_NOTIFICATION, BroadcastNotificationC2SPacket::onServer);
        ServerPlayNetworking.registerGlobalReceiver(REQUEST_SYNC, RequestSyncC2SPacket::onServer);
        ServerPlayNetworking.registerGlobalReceiver(PENDING_NOTIFICATION_READ, PendingNotificationReadC2SPacket::onServer);
    }

    private static Identifier of(String id){
        return new Identifier(AlreadyNotified.MOD_ID, id);
    }

}
