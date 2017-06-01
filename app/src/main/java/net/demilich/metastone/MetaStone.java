package net.demilich.metastone;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.demilich.metastone.analytics.MetastoneAnalytics;
import net.demilich.metastone.gui.IconFactory;
import net.demilich.metastone.utils.UserHomeMetastone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MetaStone extends Application {

	private static Logger logger = LoggerFactory.getLogger(MetaStone.class);

	public static void main(String[] args) {
		//DevCardTools.formatJsons();

		try {
			// ensure that the user home metastone dir exists
			Files.createDirectories(Paths.get(UserHomeMetastone.getPath()));
 		} catch (IOException e) {
			logger.error("Trouble creating " + UserHomeMetastone.getPath());
			e.printStackTrace();
		}

		// register a shutdown hook for reporting analytics on shutdown
		Runtime.getRuntime().addShutdownHook(new Thread () {

			@Override
			public void run() {
				logger.info("Shutting down!");
				MetastoneAnalytics.registerAppShutdownEvent();
			}
		});

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		MetastoneAnalytics.registerAppStartupEvent();

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
