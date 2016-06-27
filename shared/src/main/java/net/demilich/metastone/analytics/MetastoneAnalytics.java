package net.demilich.metastone.analytics;

import com.akoscz.googleanalytics.GoogleAnalytics;
import net.demilich.metastone.BuildConfig;
import net.demilich.metastone.utils.MetastoneProperties;
import net.demilich.metastone.utils.UserHomeMetastone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;

/**
 * This class encapsulates the analytics implementation and exposes application specific methods to be invoked
 * throughout the app to register analytics events.
 */
public class MetastoneAnalytics {

    private static final MetastoneAnalytics INSTANCE;
    private static final String ANALYTICS_CLIENT_ID_PROPERTY = "client.id";
    public  static final String ANALYTICS_OPT_OUT_PROPERTY = "analytics.optout";
    private static Logger logger = LoggerFactory.getLogger(MetastoneAnalytics.class);
    private static boolean DISABLED = false; // analytics tracking is ON by default

    private final GoogleAnalytics.Tracker analyticsTracker;

    static {
        INSTANCE = new MetastoneAnalytics();
    }

    private MetastoneAnalytics() {
        UUID clientId = null;
        try {
            // if we have a userId property
            if (MetastoneProperties.hasProperty(ANALYTICS_CLIENT_ID_PROPERTY)) {
                // read it from the metastone.properties file
                clientId = UUID.fromString(MetastoneProperties.getProperty(ANALYTICS_CLIENT_ID_PROPERTY));
            } else {
                // otherwise create a new random user id
                clientId = UUID.randomUUID();
                // and save it to metastone.properties
                MetastoneProperties.setProperty(ANALYTICS_CLIENT_ID_PROPERTY, clientId.toString());
            }

            // if user has opted out of analytics tracking, set the DISABLED flag
            DISABLED = MetastoneProperties.getBoolean(ANALYTICS_OPT_OUT_PROPERTY);

        } catch (IOException e) {
            logger.error("Could not read or write to " + UserHomeMetastone.getPath() + " metastone.properties.");
            e.printStackTrace();
        }

        // create a GoogleAnalytics tracker instance to be used throughout the app
        analyticsTracker = GoogleAnalytics.buildTracker(BuildConfig.ANALYTICS_TRACKING_ID, clientId, BuildConfig.NAME);

        // turn verbose logging on for dev builds
        GoogleAnalytics.setLogLevel(BuildConfig.DEV_BUILD ? Level.ALL : null);
    }

    /**
     * Disable analytics tracking.
     */
    public static void disable() {
        DISABLED = true;
        logger.info("Disabling Analytics");
    }

    /**
     * Enable analytics tracking.
     */
    public static void enable() {
        DISABLED = false;
        logger.info("Enabling Analytics");
    }

    /**
     * Register the application startup event.
     *  type     :   event
     *  category :   application
     *  action   :   startup
     */
    public static void registerAppStartupEvent() {
        if (DISABLED) return;
        if (BuildConfig.DEV_BUILD) logger.info("registerAppStartupEvent");

        // report the application startup event
        INSTANCE.analyticsTracker
                .type(GoogleAnalytics.HitType.event)
                .applicationVersion(BuildConfig.VERSION)
                .category("application")
                .action("startup")
                .build()
                .send();
    }

    /**
     * Register the application shutdown event.
     *  type     :   event
     *  category :   application
     *  action   :   shutdown
     *
     * NOTE:
     *  This method will perform the network operation on the same thread it was invoked from.
     *  This is to ensure that the shutdown event is fired off before the application process is terminated.
     *  It is highly recommended to invoke this method from a Shutdown Hook thread.
     *  See Runtime.getRuntime().addShutdownHook(Thread hook)
     */
    public static void registerAppShutdownEvent() {
        if (DISABLED) return;
        if (BuildConfig.DEV_BUILD) logger.info("registerAppShutdownEvent");

        // report the application shutdown event
        INSTANCE.analyticsTracker.type(GoogleAnalytics.HitType.event)
                .applicationVersion(BuildConfig.VERSION)
                .category("application")
                .action("shutdown")
                .build()
                .send(false);
    }

    /**
     * Register navigation to a given screen.
     *  type        :   screenview
     *  category    :   navigation
     *  action      :   show
     *  screenName  :   [parameter]
     *
     * @param screenName The screen name that was shown.
     */
    public static void registerShowScreen(String screenName) {
        if (DISABLED) return;
        if (BuildConfig.DEV_BUILD) logger.info("registerShowScreen: " + screenName);

        INSTANCE.analyticsTracker
                .type(GoogleAnalytics.HitType.screenview)
                .applicationVersion(BuildConfig.VERSION)
                .category("navigation")
                .action("show")
                .screenName(screenName)
                .build()
                .send(false);
    }

    /**
     * Register a dialog being shown to the user.
     *  type        :   screenview
     *  category    :   dialog
     *  action      :   show
     *  screenName  :   [parameter]
     *  label       :   [parameter]
     *
     * @param title The title of the dialog that was shown
     * @param type  The type of dialog that was shown. [CONFIRM, INFO, WARNING, ERROR]
     */
    public static void registerShowDialog(String title, String type) {
        if (DISABLED) return;
        if (BuildConfig.DEV_BUILD) logger.info("registerShowDialog: " + title + ", type: " + type);

        INSTANCE.analyticsTracker
                .type(GoogleAnalytics.HitType.screenview)
                .applicationVersion(BuildConfig.VERSION)
                .category("dialog")
                .action("show")
                .screenName(title)
                .label(type)
                .build()
                .send(false);
    }

    /**
     * Register a dialog being dismissed by the user.
     *  type        :   screenview
     *  category    :   dialog
     *  action      :   dismiss
     *  screenName  :   [parameter]
     *  label       :   [parameter]
     *  value       :   [parameter]
     *
     * @param title        The title of the dialog that was dismissed
     * @param type         The type of dialog that was dismissed. [CONFIRM, INFO, WARNING, ERROR]
     * @param dialogResult The result the dialog was dismissed with. [OK = 0, CANCEL = 1]
     */
    public static void registerDismissDialog(String title, String type, int dialogResult) {
        if (DISABLED) return;
        if (BuildConfig.DEV_BUILD)
            logger.info("registerShowDialog: " + title + ", type: " + type + ", result: " + dialogResult);

        INSTANCE.analyticsTracker
                .type(GoogleAnalytics.HitType.screenview)
                .applicationVersion(BuildConfig.VERSION)
                .category("dialog")
                .action("dismiss")
                .screenName(title)
                .label(type)
                .value(dialogResult)
                .build()
                .send(false);
    }
}