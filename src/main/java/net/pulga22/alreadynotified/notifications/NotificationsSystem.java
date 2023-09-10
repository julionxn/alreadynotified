package net.pulga22.alreadynotified.notifications;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.pulga22.alreadynotified.AlreadyNotified;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class NotificationsSystem {

    private static NotificationsSystem INSTANCE;

    public static NotificationsSystem getInstance(){
        if (INSTANCE == null){
            INSTANCE = new NotificationsSystem();
        }
        return INSTANCE;
    }

    private final Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private Notifications notifications;
    private File notisFile;

    public void init(){
        this.notisFile = FabricLoader.getInstance().getConfigDir().resolve(AlreadyNotified.MOD_ID + ".json").toFile();
        try {
            boolean isFileNew = this.notisFile.createNewFile();
            if (isFileNew){
                this.createSystem();
            } else {
                try (FileReader reader = new FileReader(this.notisFile)) {
                    Notifications system = gson.fromJson(reader, Notifications.class);
                    if (system.version < AlreadyNotified.NOTIFICATION_SYSTEM_VERSION){
                        this.createSystem();
                    } else {
                        this.notifications = system;
                    }
                }
            }
        } catch (IOException e){
           throw new RuntimeException(e);
        }
    }

    private void createSystem(){
        this.notifications = new Notifications();
        this.save();
    }

    @Nullable
    public Notifications getSavedNotifications(){
        return this.notifications;
    }

    public void save(){
        try (FileWriter fileWriter = new FileWriter(this.notisFile)){
            gson.toJson(this.notifications, fileWriter);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

}
