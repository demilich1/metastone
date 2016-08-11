package net.demilich.metastone.gui.main;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.gui.battleofdecks.BattleOfDecksMediator;
import net.demilich.metastone.gui.deckbuilder.DeckBuilderMediator;
import net.demilich.metastone.gui.mainmenu.MainMenuMediator;
import net.demilich.metastone.gui.playmode.PlayModeMediator;
import net.demilich.metastone.gui.playmode.config.PlayModeConfigMediator;
import net.demilich.metastone.gui.sandboxmode.SandboxModeMediator;
import net.demilich.metastone.gui.simulationmode.SimulationMediator;
import net.demilich.metastone.gui.trainingmode.TrainingModeMediator;
import net.demilich.nittygrittymvc.Mediator;
import net.demilich.nittygrittymvc.interfaces.INotification;

public class ApplicationMediator extends Mediator<GameNotification> {

	public static final String NAME = "ApplicationMediator";

	private Pane root;

	public ApplicationMediator() {
		super(NAME);
	}

	@Override
	public void handleNotification(final INotification<GameNotification> notification) {
		switch (notification.getId()) {
		case CANVAS_CREATED:
			root = (Pane) notification.getBody();
			break;
		case SHOW_VIEW:
			final Node view = (Node) notification.getBody();
			root.getChildren().clear();
			root.getChildren().add(view);
			break;
		case MAIN_MENU:
			removeOtherViews();
			getFacade().registerMediator(new MainMenuMediator());
			break;
		default:
			break;
		}
	}

	@Override
	public List<GameNotification> listNotificationInterests() {
		List<GameNotification> notificationInterests = new ArrayList<GameNotification>();
		notificationInterests.add(GameNotification.CANVAS_CREATED);
		notificationInterests.add(GameNotification.SHOW_VIEW);
		notificationInterests.add(GameNotification.MAIN_MENU);
		notificationInterests.add(GameNotification.CARD_PARSE_ERROR);
		return notificationInterests;
	}
	
	private void removeOtherViews() {
		getFacade().removeMediator(PlayModeMediator.NAME);
		getFacade().removeMediator(PlayModeConfigMediator.NAME);
		getFacade().removeMediator(DeckBuilderMediator.NAME);
		getFacade().removeMediator(SimulationMediator.NAME);
		getFacade().removeMediator(TrainingModeMediator.NAME);
		getFacade().removeMediator(SandboxModeMediator.NAME);
		getFacade().removeMediator(BattleOfDecksMediator.NAME);
	}

}
