package net.demilich.metastone;

import com.akoscz.googleanalytics.GoogleAnalytics;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.demilich.metastone.gui.IconFactory;
import net.demilich.metastone.utils.MetastoneProperties;
import net.demilich.metastone.utils.UserHomeMetastone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class MetaStone extends Application {

	private static Logger logger = LoggerFactory.getLogger(MetaStone.class);
	private static final String ANALYTICS_CLIENT_ID_PROPERTY = "client.id";
	public static GoogleAnalytics.Tracker analytics;

	public static void main(String[] args) {
		//DevCardTools.formatJsons();

		try {
			// ensure that the user home metastone dir exists
			Files.createDirectories(Paths.get(UserHomeMetastone.getPath()));
 		} catch (IOException e) {
			logger.error("Trouble creating " + UserHomeMetastone.getPath());
			e.printStackTrace();
		}

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
		analytics = GoogleAnalytics.buildTracker(BuildConfig.ANALYTICS_TRACKING_ID, clientId, BuildConfig.NAME);

		// register a shutdown hook for analytics
		Runtime.getRuntime().addShutdownHook(new Thread() {

			@Override
			public void run() {
				System.out.println("Shutting down!");
				// report the application shutdown event
				analytics.type(GoogleAnalytics.HitType.event)
						.applicationVersion(BuildConfig.VERSION)
						.category("application")
						.action("shutdown")
						.build()
						.send(false);
			}

		});

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// report the application startup event
		analytics.type(GoogleAnalytics.HitType.event)
				.applicationVersion(BuildConfig.VERSION)
				.category("application")
				.action("startup")
				.build()
				.send();

		primaryStage.setTitle("MetaStone");
		primaryStage.initStyle(StageStyle.UNIFIED);
		primaryStage.setResizable(false);
		primaryStage.getIcons().add(new Image(IconFactory.getImageUrl("ui/app_icon.png")));

		ApplicationFacade facade = (ApplicationFacade) ApplicationFacade.getInstance();
		facade.startUp();

		StackPane root = new StackPane();
		root.setAlignment(Pos.CENTER);
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/css/main.css").toExternalForm());
		primaryStage.setScene(scene);
		
		// implementing potential visual fix for JavaFX
		// setting the visual opacity to zero, and then
		// once the stage is shown, setting the opacity to one.
		// this fixes an issue where some users would only see a blank
		// screen on application startup
		primaryStage.setOpacity(0.0);
		
		facade.sendNotification(GameNotification.CANVAS_CREATED, root);
		facade.sendNotification(GameNotification.MAIN_MENU);
		primaryStage.show();
		
		// setting opacity to one for JavaFX hotfix
		primaryStage.setOpacity(1.0);
		
		facade.sendNotification(GameNotification.CHECK_FOR_UPDATE);
	}

}
