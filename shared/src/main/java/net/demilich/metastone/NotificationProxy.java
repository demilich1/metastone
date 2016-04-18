package net.demilich.metastone;

import net.demilich.nittygrittymvc.interfaces.INotification;
import net.demilich.nittygrittymvc.interfaces.INotifier;


public class NotificationProxy {

    public static INotifier SUBJECT;

    public static void init(INotifier<GameNotification> subject) {
        SUBJECT = subject;
    }

    public static void notifyObservers(INotification<GameNotification> var1) {
        SUBJECT.notifyObservers(var1);
    }

    public static void sendNotification(GameNotification var1) {
        SUBJECT.sendNotification(var1);
    }

    public static void sendNotification(GameNotification var1, Object var2) {
        SUBJECT.sendNotification(var1, var2);
    }
}