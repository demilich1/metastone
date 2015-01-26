package net.demilich.metastone.gui.dialog;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class ModalDialog extends StackPane {

	public ModalDialog(Window parent, Node content) {

		Stage stage = new Stage();
		Scene scene = new Scene(this);
		scene.setFill(null);
		stage.setScene(scene);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.initOwner(parent);
		stage.setX(parent.getX());
		stage.setY(parent.getY());

		setPrefSize(parent.getWidth(), parent.getHeight());
		setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
		getChildren().add(content);

		stage.show();
	}

}
