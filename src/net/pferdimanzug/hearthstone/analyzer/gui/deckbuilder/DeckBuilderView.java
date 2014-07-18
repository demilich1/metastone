package net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder;

import java.io.IOException;

import net.pferdimanzug.hearthstone.analyzer.ApplicationFacade;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class DeckBuilderView extends BorderPane implements EventHandler<ActionEvent> {

	@FXML
	private ScrollPane scrollPane;

	@FXML
	private Pane lowerInfoArea;
	
	@FXML
	private Pane upperInfoArea;
	
	@FXML
	private TextField importField;
	
	@FXML
	private Button importButton;

	public DeckBuilderView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DeckBuilderView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		
		importButton.setOnAction(this);
	}

	public void showBottomBar(Node content) {
		BorderPane.setAlignment(content, Pos.CENTER);
		setBottom(content);
	}

	public void showLowerInfoArea(Node content) {
		lowerInfoArea.getChildren().clear();
		lowerInfoArea.getChildren().add(content);
	}
	
	public void showUpperInfoArea(Node content) {
		upperInfoArea.getChildren().clear();
		upperInfoArea.getChildren().add(content);
	}
	
	public void showMainArea(Node content) {
		setCenter(content);
	}
	
	public void showSidebar(Node content) {
		scrollPane.setContent(content);
	}

	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == importButton) {
			ApplicationFacade.getInstance().sendNotification(GameNotification.IMPORT_DECK_FROM_URL, importField.getText());
		}
	}

}
