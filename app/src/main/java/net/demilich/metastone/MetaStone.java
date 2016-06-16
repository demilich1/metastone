package net.demilich.metastone;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.demilich.metastone.gui.IconFactory;
import net.demilich.metastone.utils.UserHomeMetastone;

import javax.swing.filechooser.FileSystemView;
import java.io.File;

public class MetaStone extends Application {

	public static void main(String[] args) {
		//DevCardTools.formatJsons();

		// initialize the user home metastone dir path with the platform specific path for the user starting up the app
		UserHomeMetastone.init((FileSystemView.getFileSystemView().getDefaultDirectory().getPath()
				+ File.separator + BuildConfig.NAME).replace("\\", "\\\\"));
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
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
