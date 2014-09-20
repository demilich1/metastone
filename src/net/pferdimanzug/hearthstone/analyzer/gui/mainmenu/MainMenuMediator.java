package net.pferdimanzug.hearthstone.analyzer.gui.mainmenu;

import java.util.ArrayList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.gui.deckbuilder.DeckBuilderMediator;
import net.pferdimanzug.hearthstone.analyzer.gui.playmode.config.PlayModeConfigMediator;
import net.pferdimanzug.hearthstone.analyzer.gui.simulationmode.SimulationMediator;
import net.pferdimanzug.hearthstone.analyzer.gui.trainingmode.TrainingModeMediator;
import de.pferdimanzug.nittygrittymvc.Mediator;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

public class MainMenuMediator extends Mediator<GameNotification> {

	public static final String NAME = "MainMenuMediator";

	private final MainMenuView view;

	public MainMenuMediator() {
		super(NAME);
		view = new MainMenuView();
	}

	@Override
	public void handleNotification(final INotification<GameNotification> notification) {
		switch (notification.getId()) {
		case DECK_BUILDER_SELECTED:
			getFacade().registerMediator(new DeckBuilderMediator());
			break;
		case PLAY_MODE_SELECTED:
			getFacade().registerMediator(new PlayModeConfigMediator());
			break;
		case SIMULATION_MODE_SELECTED:
			getFacade().registerMediator(new SimulationMediator());
			break;
		case TRAINING_MODE_SELECTED:
			getFacade().registerMediator(new TrainingModeMediator());
			break;
		default:
			break;
		}
		getFacade().removeMediator(MainMenuMediator.NAME);
	}

	@Override
	public List<GameNotification> listNotificationInterests() {
		List<GameNotification> notificationInterests = new ArrayList<GameNotification>();
		notificationInterests.add(GameNotification.DECK_BUILDER_SELECTED);
		notificationInterests.add(GameNotification.PLAY_MODE_SELECTED);
		notificationInterests.add(GameNotification.SIMULATION_MODE_SELECTED);
		notificationInterests.add(GameNotification.TRAINING_MODE_SELECTED);
		return notificationInterests;
	}

	@Override
	public void onRegister() {
		getFacade().sendNotification(GameNotification.SHOW_VIEW, view);
	}

}
