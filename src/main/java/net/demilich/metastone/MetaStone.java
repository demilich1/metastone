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
		primaryStage.setResizable(false);
		primaryStage.getIcons().add(new Image(IconFactory.getImageUrl("ui/app_icon.png")));

		ApplicationFacade facade = (ApplicationFacade) ApplicationFacade.getInstance();
		final MetaStone instance = new MetaStone();
		facade.startUp(instance);

		StackPane root = new StackPane();
		root.setAlignment(Pos.CENTER);
		Scene scene = new Scene(root);
		//scene.getStylesheets().add();
		scene.getStylesheets().add(getClass().getResource("/css/main.css").toExternalForm());
		primaryStage.setScene(scene);
		facade.sendNotification(GameNotification.CANVAS_CREATED, root);
		facade.sendNotification(GameNotification.MAIN_MENU);
		primaryStage.show();
	}

}
