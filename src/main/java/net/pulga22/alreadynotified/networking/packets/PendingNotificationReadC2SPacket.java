package net.pulga22.alreadynotified.networking.packets;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.pulga22.alreadynotified.util.IPlayerNotifications;

public class PendingNotificationReadC2SPacket {

    public static void onServer(MinecraftServer server, ServerPlayerEntity player,
                                ServerPlayNetworkHandler networkHandler, PacketByteBuf buf, PacketSender packetSender) {

        server.execute(() -> {
            ((IPlayerNotifications) player).alreadynotified$notificationRead();
        });

    }

}
