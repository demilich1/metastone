package net.demilich.metastone;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.demilich.metastone.gui.IconFactory;

public class MetaStone extends Application {

	public static void main(String[] args) {
		//DevCardTools.formatJsons();
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("MetaStone");
		primaryStage.initStyle(StageStyle.UNIFIED);
		primaryStage.setResizable(true);
		primaryStage.setWidth(1280);
		primaryStage.setHeight(900);
		primaryStage.setMinWidth(1280);
		primaryStage.setMinHeight(768);
		primaryStage.getIcons().add(new Image(IconFactory.getImageUrl("ui/app_icon.png")));

		ApplicationFacade facade = (ApplicationFacade) ApplicationFacade.getInstance();
		facade.startUp();

		StackPane root = new StackPane();
		root.setAlignment(Pos.CENTER);
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/css/main.css").toExternalForm());
		primaryStage.setScene(scene);
		
		//Implementing potential visual fix for JavaFX
		//Setting the visual opacity to zero, and then
		//once the stage is shown, setting the opacity to one.
		primaryStage.setOpacity(0.0);
		
		facade.sendNotification(GameNotification.CANVAS_CREATED, root);
		facade.sendNotification(GameNotification.MAIN_MENU);
		primaryStage.show();
		
		//Setting opacity to one for JavaFX hotfix
		primaryStage.setOpacity(1.0);
		
		facade.sendNotification(GameNotification.CHECK_FOR_UPDATE);
	}

}
