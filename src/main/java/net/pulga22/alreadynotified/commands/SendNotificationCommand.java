package net.pulga22.alreadynotified.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.pulga22.alreadynotified.networking.Packets;

public class SendNotificationCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(CommandManager.literal("notified").requires((source) -> source.hasPermissionLevel(2))
                .then(
                        CommandManager.literal("send").executes(SendNotificationCommand::run)
                )
        );
    }

    private static int run(CommandContext<ServerCommandSource> source) throws CommandSyntaxException {
        ServerPlayerEntity player = source.getSource().isExecutedByPlayer() ? source.getSource().getPlayer() : null;
        if (player != null) {
            ServerPlayNetworking.send(player, Packets.OPEN_SEND_NOTIFICATION_SCREEN, PacketByteBufs.empty());
        } else {
            throw new SimpleCommandExceptionType(Text.literal("Player is null. Maybe this command is being called by a non player.")).create();
        }
        return 1;
    }

}
