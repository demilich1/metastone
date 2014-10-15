package net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;

public class ToolboxView extends ToolBar {

	private final PlayerPanel playerPanel;
	private final CardPanel cardPanel;
	private final MinionPanel minionPanel;

	public ToolboxView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ToolboxView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		playerPanel = new PlayerPanel();
		getItems().add(playerPanel);
		getItems().add(new Separator());
		cardPanel = new CardPanel();
		getItems().add(cardPanel);
		getItems().add(new Separator());
		minionPanel = new MinionPanel();
		getItems().add(minionPanel);
	}

	public void onPlayerSelectionChanged(Player selectedPlayer) {
		cardPanel.onPlayerSelectionChanged(selectedPlayer);
	}
	
	public void setContext(GameContext context) {
		playerPanel.setContext(context);
		minionPanel.setContext(context);
	}

}
