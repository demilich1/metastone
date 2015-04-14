package net.demilich.metastone.tools;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CardCreator extends Application {
	
	public static void main(String[] args) {
		launch(args);	
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Card Creator");
		primaryStage.initStyle(StageStyle.UNIFIED);

		Scene scene = new Scene(new EditorMainWindow());
		//scene.getStylesheets().add(IconFactory.class.getResource("main.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
