package net.demilich.metastone.gui.mainmenu;

import java.util.ArrayList;
import java.util.List;

import net.demilich.nittygrittymvc.Mediator;
import net.demilich.nittygrittymvc.interfaces.INotification;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.gui.battleofdecks.BattleOfDecksMediator;
import net.demilich.metastone.gui.deckbuilder.DeckBuilderMediator;
import net.demilich.metastone.gui.playmode.config.PlayModeConfigMediator;
import net.demilich.metastone.gui.sandboxmode.SandboxModeMediator;
import net.demilich.metastone.gui.simulationmode.SimulationMediator;
import net.demilich.metastone.gui.trainingmode.TrainingModeMediator;

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
		case SANDBOX_MODE_SELECTED:
			getFacade().registerMediator(new SandboxModeMediator());
			break;
		case TRAINING_MODE_SELECTED:
			getFacade().registerMediator(new TrainingModeMediator());
			break;
		case BATTLE_OF_DECKS_SELECTED:
			getFacade().registerMediator(new BattleOfDecksMediator());
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
		notificationInterests.add(GameNotification.SANDBOX_MODE_SELECTED);
		notificationInterests.add(GameNotification.TRAINING_MODE_SELECTED);
		notificationInterests.add(GameNotification.BATTLE_OF_DECKS_SELECTED);
		return notificationInterests;
	}

	@Override
	public void onRegister() {
		getFacade().sendNotification(GameNotification.SHOW_VIEW, view);
	}

}
