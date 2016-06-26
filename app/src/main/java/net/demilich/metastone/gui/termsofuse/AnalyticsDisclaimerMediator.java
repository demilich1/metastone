package net.demilich.metastone.gui.termsofuse;

import net.demilich.metastone.GameNotification;
import net.demilich.metastone.analytics.MetastoneAnalytics;
import net.demilich.nittygrittymvc.Mediator;
import net.demilich.nittygrittymvc.interfaces.INotification;

import java.util.ArrayList;
import java.util.List;

public class AnalyticsDisclaimerMediator extends Mediator<GameNotification> {

    public static final String NAME = "AnalyticsDisclaimerMediator";

    private final AnalyticsDisclaimerView view;

    public AnalyticsDisclaimerMediator() {
        super(NAME);
        view = new AnalyticsDisclaimerView();
    }

    @Override
    public void handleNotification(final INotification<GameNotification> notification) {
        switch (notification.getId()) {
            case ANALYTICS_OPT_OUT_TOGGLED:
                if ((boolean)notification.getBody()) {
                    // user is opting out. Turn off Analytics
                    MetastoneAnalytics.disable();
                } else {
                    // user is opting in. Turn on Analytics
                    MetastoneAnalytics.enable();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public List<GameNotification> listNotificationInterests() {
        List<GameNotification> notificationInterests = new ArrayList<GameNotification>();
        notificationInterests.add(GameNotification.ANALYTICS_OPT_OUT_TOGGLED);
        return notificationInterests;
    }

    @Override
    public void onRegister() {
        getFacade().sendNotification(GameNotification.SHOW_VIEW, view);
    }

}
