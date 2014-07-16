package net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class DeckBuilderView extends BorderPane {

	@FXML
	private ScrollPane scrollPane;

	@FXML
	private Pane contentArea;
	
	@FXML
	private Pane infoArea;

	private DeckListView deckListView;

	public DeckBuilderView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DeckBuilderView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		deckListView = new DeckListView();
		showSidebar(deckListView);
	}

	public void showMainArea(Node content) {
		contentArea.getChildren().clear();
		contentArea.getChildren().add(content);
	}

	public void showSidebar(Node content) {
		scrollPane.setContent(content);
	}
	
	public void showInfoArea(Node content) {
		infoArea.getChildren().clear();
		infoArea.getChildren().add(content);
	}

}
