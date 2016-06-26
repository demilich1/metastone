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
    private static Logger logger = LoggerFactory.getLogger(MetastoneAnalytics.class);

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
     * Register the application startup event.
     *  type     :   event
     *  category :   application
     *  action   :   startup
     */
    public static void registerAppStartupEvent() {
        if (BuildConfig.DEV_BUILD) logger.info("registerAppStartupEvent");

        // report the application startup event
        INSTANCE.analyticsTracker.type(GoogleAnalytics.HitType.event)
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
     *  This is to ensure that the shutdown even is fired off before the application process is terminated.
     *  It is highly recommended to invoke this method from a Shutdown Hook.
     *  See Runtime.getRuntime().addShutdownHook(Thread hook)
     */
    public static void registerAppShutdownEvent() {
        if (BuildConfig.DEV_BUILD) logger.info("registerAppShutdownEvent");

        // report the application shutdown event
        INSTANCE.analyticsTracker.type(GoogleAnalytics.HitType.event)
                .applicationVersion(BuildConfig.VERSION)
                .category("application")
                .action("shutdown")
                .build()
                .send(false);
    }
}
