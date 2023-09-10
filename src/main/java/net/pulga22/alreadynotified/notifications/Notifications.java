package net.pulga22.alreadynotified.notifications;

import net.pulga22.alreadynotified.AlreadyNotified;

import java.util.ArrayList;
import java.util.List;

public class Notifications {

    public int version;
    private List<Notification> notifications = new ArrayList<>();

    public Notifications(){
        this.version = AlreadyNotified.NOTIFICATION_SYSTEM_VERSION;
    }

    public void addNotification(Notification notification){
        this.notifications.add(notification);
    }

    public List<Notification> getNotifications(){
        return this.notifications;
    }

    public void sync(List<Notification> newNotifications){
        this.notifications = newNotifications;
    }

}
