package net.pulga22.alreadynotified;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.pulga22.alreadynotified.commands.SendNotificationCommand;
import net.pulga22.alreadynotified.networking.Packets;
import net.pulga22.alreadynotified.notifications.NotificationsSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AlreadyNotified implements ModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("alreadynotified");
	public static final String MOD_ID = "alreadynotified";
	public static final int NOTIFICATION_SYSTEM_VERSION = 1;

	@Override
	public void onInitialize() {
		NotificationsSystem.getInstance().init();
		Packets.registerC2SPackets();
		CommandRegistrationCallback.EVENT.register(SendNotificationCommand::register);
		LOGGER.info("22 new messages!");
	}
}