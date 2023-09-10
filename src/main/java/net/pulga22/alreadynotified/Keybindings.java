package net.pulga22.alreadynotified;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.pulga22.alreadynotified.networking.Packets;
import net.pulga22.alreadynotified.screen.gui.NotificationsScreen;
import net.pulga22.alreadynotified.util.IPlayerNotifications;
import org.lwjgl.glfw.GLFW;

public class Keybindings {

    public static final String KEY_CATEGORY_ALREADYNOTIFIED = "key.category.alreadynotified.alreadynotified";
    public static final String KEY_OPEN_NOTIFICATIONS = "key.alreadynotified.open_notifications";

    public static KeyBinding openNotifications;

    private static void registerKeyInputs(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            PlayerEntity player = client.player;
            if (openNotifications.wasPressed() && player != null){
                IPlayerNotifications notifications = (IPlayerNotifications) player;
                if (notifications.alreadynotified$isPendingReadNewNotification()){
                    notifications.alreadynotified$notificationRead();
                    ClientPlayNetworking.send(Packets.PENDING_NOTIFICATION_READ, PacketByteBufs.empty());
                }
                client.setScreen(new NotificationsScreen());
            }
        });
    }

    public static void register(){
        openNotifications = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_OPEN_NOTIFICATIONS,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_N,
                KEY_CATEGORY_ALREADYNOTIFIED
        ));

        registerKeyInputs();
    }

}
