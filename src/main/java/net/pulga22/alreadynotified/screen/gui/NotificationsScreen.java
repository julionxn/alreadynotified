package net.pulga22.alreadynotified.screen.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.MultilineTextWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.pulga22.alreadynotified.AlreadyNotified;
import net.pulga22.alreadynotified.notifications.Notification;
import net.pulga22.alreadynotified.notifications.Notifications;
import net.pulga22.alreadynotified.notifications.NotificationsSystem;

import java.util.ArrayList;
import java.util.List;

public class NotificationsScreen extends Screen {

    private static final Identifier BACKGROUND = new Identifier(AlreadyNotified.MOD_ID, "textures/gui/notification_window.png");
    private List<Notification> notifications = new ArrayList<>();
    private TextWidget titleText;
    private MultilineTextWidget messageText;
    private int currentIndex = 0;
    private TextWidget currentIndexText;
    private CustomButtonWidget prevMsgButton;
    private CustomButtonWidget nextMsgButton;

    public NotificationsScreen() {
        super(Text.of("Notifications"));
        Notifications savedNotifications = NotificationsSystem.getInstance().getSavedNotifications();
        if (savedNotifications != null){
            this.notifications = savedNotifications.getNotifications();
        }
    }

    @Override
    protected void init() {
        super.init();
        CustomButtonWidget closeButton = CustomButtonWidget.builder
                (Text.translatable("alreadynotified.notifications_screen.close"), (onPress) -> {
                    this.close();
                }).dimensions((int)this.width/2 - 24,(int)this.height/2+55,50,20).build();
        this.addDrawableChild(closeButton);

        this.prevMsgButton = CustomButtonWidget.builder(Text.literal("◀"), (onPress) -> {
            changeCurrentNotification(To.PREVIOUS);
        }).dimensions((int) this.width / 2 - 45, (int) this.height / 2 + 31, 20, 20).build();
        this.addDrawableChild(this.prevMsgButton);
        this.prevMsgButton.active = false;

        this.nextMsgButton = CustomButtonWidget.builder(Text.literal("▶"), (onPress) -> {
            changeCurrentNotification(To.NEXT);
        }).dimensions((int) this.width / 2 + 27, (int) this.height / 2 + 31, 20, 20).build();
        this.addDrawableChild(this.nextMsgButton);

        this.titleText = new TextWidget(this.width/2 - 25, this.height/2 - 51 ,
                50, 20, Text.literal(""), this.textRenderer).alignCenter();
        this.addDrawableChild(this.titleText);

        this.messageText = new MultilineTextWidget(0, 0,
                Text.literal(""), this.textRenderer).setCentered(true).setMaxWidth(80).setMaxRows(5);
        this.addDrawableChild(this.messageText);

        this.currentIndexText = new TextWidget(this.width/2 - 24, this.height/2 + 32,
                50, 20, Text.literal("0/0"), this.textRenderer);
        this.currentIndexText.alignCenter();
        this.addDrawableChild(this.currentIndexText);

        if (this.notifications.isEmpty()){
            this.titleText.setMessage(Text.literal(" "));
            this.messageText.setMessage(Text.literal("No messages"));
            this.currentIndexText.setMessage(Text.literal("0/0"));
            this.centerMessageText();
        } else {
            this.changeCurrentTexts();
        }

        if (this.notifications.size() < 2){
            this.nextMsgButton.active = false;
        }
    }

    private void changeCurrentNotification(To to){
        switch (to){
            case PREVIOUS -> {
                if (this.currentIndex > 0){
                    this.currentIndex--;
                    this.nextMsgButton.active = true;
                    if (this.currentIndex == 0){
                        this.prevMsgButton.active = false;
                    }
                }
            }
            case NEXT -> {
                if (this.currentIndex < notifications.size() - 1){
                    this.currentIndex++;
                    this.prevMsgButton.active = true;
                    if (this.currentIndex == this.notifications.size() - 1){
                        this.nextMsgButton.active = false;
                    }
                }
            }
        }
        this.changeCurrentTexts();
    }

    private void changeCurrentTexts(){
        Notification notification = notifications.get(currentIndex);
        this.titleText.setMessage(Text.literal(notification.title()));
        this.messageText.setMessage(Text.literal(notification.message()));
        this.currentIndexText.setMessage(Text.literal(currentIndex + 1 + "/" + notifications.size()));
        this.centerMessageText();
    }

    private void centerMessageText(){
        this.messageText.setPosition(this.width/2 - this.messageText.getWidth() / 2, this.height/2 - 24);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        Window window = MinecraftClient.getInstance().getWindow();
        int textureWidth = 128;
        int textureHeight = 184;
        int x = window.getScaledWidth() / 2 - 64;
        int y = window.getScaledHeight() / 2 - 92;

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0f,1.0f,1.0f,1.0f);
        RenderSystem.setShaderTexture(0, BACKGROUND);

        drawTexture(matrices, x, y, 0, 0, textureWidth, textureHeight, textureWidth, textureHeight);
        super.render(matrices, mouseX, mouseY, delta);
    }

    private enum To {
        PREVIOUS,
        NEXT
    }
}
