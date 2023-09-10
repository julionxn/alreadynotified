package net.pulga22.alreadynotified.screen.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.pulga22.alreadynotified.AlreadyNotified;
import net.pulga22.alreadynotified.util.IPlayerNotifications;

public class NewNotificationHud implements HudRenderCallback {
    private static final Identifier TEXTURE = new Identifier(AlreadyNotified.MOD_ID, "textures/gui/new_notification.png");

    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        PlayerEntity player = client.player;
        if (player == null) return;
        if (!((IPlayerNotifications) player).alreadynotified$isPendingReadNewNotification()) return;
        int size = 36;
        int x = client.getWindow().getScaledWidth() - (size / 2) - 18;
        int y = client.getWindow().getScaledHeight() - (size / 2) - 18;

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0f,1.0f,1.0f,1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        DrawableHelper.drawTexture(matrixStack, x, y, 0,0, size, size, size, size);
    }
}
