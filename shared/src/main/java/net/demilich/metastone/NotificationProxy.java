package net.demilich.metastone;

import net.demilich.nittygrittymvc.interfaces.INotification;
import net.demilich.nittygrittymvc.interfaces.INotifier;


public class NotificationProxy {

    private static INotifier<GameNotification> SUBJECT;

    public static void init(INotifier<GameNotification> subject) {
        SUBJECT = subject;
    }

    public static void notifyObservers(INotification<GameNotification> notification) {
        if (SUBJECT == null) {
            throw new RuntimeException("NotificationProxy must first be initialized!");
        }
        SUBJECT.notifyObservers(notification);
    }

    public static void sendNotification(GameNotification notification) {
        if (SUBJECT == null) {
            throw new RuntimeException("NotificationProxy must first be initialized!");
        }
        SUBJECT.sendNotification(notification);
    }

    public static void sendNotification(GameNotification notification, Object data) {
        if (SUBJECT == null) {
            throw new RuntimeException("NotificationProxy must first be initialized!");
        }
        SUBJECT.sendNotification(notification, data);
    }
}