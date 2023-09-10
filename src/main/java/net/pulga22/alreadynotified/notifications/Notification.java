package net.pulga22.alreadynotified.notifications;

public record Notification(String title, String message) {

    public static Notification of(String title, String message){
        return new Notification(title, message);
    }

}
