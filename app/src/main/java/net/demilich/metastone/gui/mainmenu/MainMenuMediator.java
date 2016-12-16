package net.demilich.metastone.gui.mainmenu;

import java.util.ArrayList;
import java.util.List;

import net.demilich.metastone.gui.draftmode.DraftModeMediator;
import net.demilich.metastone.gui.draftmode.StartDraftOptions;
import net.demilich.nittygrittymvc.Mediator;
import net.demilich.nittygrittymvc.interfaces.INotification;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.gui.battleofdecks.BattleOfDecksMediator;
import net.demilich.metastone.gui.deckbuilder.DeckBuilderMediator;
import net.demilich.metastone.gui.playmode.config.PlayModeConfigMediator;
import net.demilich.metastone.gui.sandboxmode.SandboxModeMediator;
import net.demilich.metastone.gui.simulationmode.SimulationMediator;
import net.demilich.metastone.gui.trainingmode.TrainingModeMediator;
import org.apache.commons.lang3.RandomStringUtils;

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
			getFacade().registerMediator(new PlayModeConfigMediator(false));
			break;
		case MULTIPLAYER_MODE_SELECTED:
			getFacade().registerMediator(new PlayModeConfigMediator(true));
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
		case DRAFT_MODE_SELECTED:
			getFacade().registerMediator(new DraftModeMediator());
			// TODO: Where would be the wiser place to put this?
			getFacade().sendNotification(GameNotification.START_DRAFT, new StartDraftOptions(RandomStringUtils.randomAlphanumeric(35)));
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
		notificationInterests.add(GameNotification.MULTIPLAYER_MODE_SELECTED);
		notificationInterests.add(GameNotification.SIMULATION_MODE_SELECTED);
		notificationInterests.add(GameNotification.SANDBOX_MODE_SELECTED);
		notificationInterests.add(GameNotification.TRAINING_MODE_SELECTED);
		notificationInterests.add(GameNotification.BATTLE_OF_DECKS_SELECTED);
		notificationInterests.add(GameNotification.DRAFT_MODE_SELECTED);
		return notificationInterests;
	}

	@Override
	public void onRegister() {
		getFacade().sendNotification(GameNotification.SHOW_VIEW, view);
	}

}
