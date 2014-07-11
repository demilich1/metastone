package net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import net.pferdimanzug.hearthstone.analyzer.ApplicationFacade;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.entities.heroes.HeroClass;

public class ChooseClassView extends BorderPane {
	@FXML
	private Button warriorButton;

	@FXML
	private Button paladinButton;

	@FXML
	private Button druidButton;

	@FXML
	private Button rogueButton;

	@FXML
	private Button warlockButton;

	@FXML
	private Button hunterButton;

	@FXML
	private Button shamanButton;

	@FXML
	private Button mageButton;

	@FXML
	private Button priestButton;

	public ChooseClassView() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ChooseClassView.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		warriorButton.setOnAction(actionEvent -> ApplicationFacade.getInstance().sendNotification(GameNotification.CLASS_CHOSEN,
				HeroClass.WARRIOR));
		paladinButton.setOnAction(actionEvent -> ApplicationFacade.getInstance().sendNotification(GameNotification.CLASS_CHOSEN,
				HeroClass.PALADIN));
		druidButton.setOnAction(actionEvent -> ApplicationFacade.getInstance().sendNotification(GameNotification.CLASS_CHOSEN,
				HeroClass.DRUID));
		
		rogueButton.setOnAction(actionEvent -> ApplicationFacade.getInstance().sendNotification(GameNotification.CLASS_CHOSEN,
				HeroClass.ROGUE));
		warlockButton.setOnAction(actionEvent -> ApplicationFacade.getInstance().sendNotification(GameNotification.CLASS_CHOSEN,
				HeroClass.WARLOCK));
		hunterButton.setOnAction(actionEvent -> ApplicationFacade.getInstance().sendNotification(GameNotification.CLASS_CHOSEN,
				HeroClass.HUNTER));
		
		shamanButton.setOnAction(actionEvent -> ApplicationFacade.getInstance().sendNotification(GameNotification.CLASS_CHOSEN,
				HeroClass.SHAMAN));
		mageButton.setOnAction(actionEvent -> ApplicationFacade.getInstance().sendNotification(GameNotification.CLASS_CHOSEN,
				HeroClass.MAGE));
		priestButton.setOnAction(actionEvent -> ApplicationFacade.getInstance().sendNotification(GameNotification.CLASS_CHOSEN,
				HeroClass.PRIEST));
	}
}
