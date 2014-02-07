package net.pferdimanzug.hearthstone.analyzer.playmode;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class GameBoardView {
	
	private Parent root;
	
	public GameBoardView() {
		try {
			root = FXMLLoader.load(getClass().getResource("fxml_example.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
