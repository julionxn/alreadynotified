package net.pulga22.alreadynotified.screen.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.pulga22.alreadynotified.AlreadyNotified;
import net.pulga22.alreadynotified.networking.Packets;
import net.pulga22.alreadynotified.util.NotificationsHandler;

public class SendNewNotificationScreen extends Screen {

    private static final Identifier BACKGROUND = new Identifier(AlreadyNotified.MOD_ID, "textures/gui/send_notification_window.png");
    private TextFieldWidget notificationTitleField;
    private TextFieldWidget notificationMessageField;
    private final PlayerEntity player;

    public SendNewNotificationScreen(PlayerEntity player) {
        super(Text.of("Send Notification"));
        this.player = player;
    }

    @Override
    protected void init() {
        super.init();

        CustomButtonWidget buttonClose = CustomButtonWidget.builder(Text.literal("x"), (onPress) -> {
            this.close();
        }).dimensions(this.width /2 - 37,this.height/2 + 12,20,20).build();
        this.addDrawableChild(buttonClose);

        CustomButtonWidget buttonSend = CustomButtonWidget.builder
                (Text.translatable("alreadynotified.send_notification_screen.send"), (onPress) -> {
                    String title = this.notificationTitleField.getText();
                    String message = this.notificationMessageField.getText();
                    if (title.replace(" ", "").isEmpty()) return;
                    if (message.replace(" ", "").isEmpty()) return;

                    ClientPlayNetworking.send(Packets.BROADCAST_NOTIFICATION, NotificationsHandler.bufOf(
                            this.notificationTitleField.getText(),
                            this.notificationMessageField.getText()
                    ));

                    this.player.sendMessage(Text.translatable
                            ("alreadynotified.send_notification_screen.notification_sent",
                                    this.notificationTitleField.getText()).fillStyle
                            (Style.EMPTY.withColor(Formatting.GRAY))
                    );
                    this.close();
                }).dimensions((int)this.width/2 - 12,this.height/2 + 12,50,20).build();
        this.addDrawableChild(buttonSend);

        this.notificationTitleField = new TextFieldWidget(this.textRenderer, this.width / 2 - 50, this.height/2 - 32, 100, 15, Text.literal("Title"));
        this.notificationTitleField.setMaxLength(16);
        this.addDrawableChild(this.notificationTitleField);

        this.notificationMessageField = new TextFieldWidget(this.textRenderer, this.width / 2 - 100, this.height/2 - 32 + 22, 200, 15, Text.literal("Title"));
        this.notificationMessageField.setMaxLength(64);
        this.addDrawableChild(this.notificationMessageField);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        Window window = MinecraftClient.getInstance().getWindow();
        int textureWidth = 256;
        int textureHeight = 100;
        int x = window.getScaledWidth() / 2 - 128;
        int y = window.getScaledHeight() / 2 - 50;

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0f,1.0f,1.0f,1.0f);
        RenderSystem.setShaderTexture(0, BACKGROUND);

        drawTexture(matrices, x, y, 0, 0, textureWidth, textureHeight, textureWidth, textureHeight);

        super.render(matrices, mouseX, mouseY, delta);
    }

}
