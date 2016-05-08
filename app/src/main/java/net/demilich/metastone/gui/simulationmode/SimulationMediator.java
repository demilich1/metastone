package net.demilich.metastone.gui.simulationmode;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.demilich.nittygrittymvc.Mediator;
import net.demilich.nittygrittymvc.interfaces.INotification;
import javafx.application.Platform;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.utils.Tuple;

public class SimulationMediator extends Mediator<GameNotification> {

	public static final String NAME = "SimulationMediator";

	private static Logger logger = LoggerFactory.getLogger(SimulationMediator.class);

	private final SimulationModeConfigView view;
	private final WaitForSimulationView waitView;
	private final SimulationResultView resultView;

	public SimulationMediator() {
		super(NAME);
		view = new SimulationModeConfigView();
		waitView = new WaitForSimulationView();
		resultView = new SimulationResultView();
	}

	@Override
	@SuppressWarnings("unchecked")
	public void handleNotification(final INotification<GameNotification> notification) {
		switch (notification.getId()) {
		case REPLY_DECKS:
			List<Deck> decks = (List<Deck>) notification.getBody();
			view.injectDecks(decks);
			break;
		case REPLY_DECK_FORMATS:
			List<DeckFormat> deckFormats = (List<DeckFormat>) notification.getBody();
			view.injectDeckFormats(deckFormats);
			break;
		case COMMIT_SIMULATIONMODE_CONFIG:
			getFacade().sendNotification(GameNotification.SHOW_MODAL_DIALOG, waitView);
			getFacade().sendNotification(GameNotification.SIMULATE_GAMES, notification.getBody());
			break;
		case SIMULATION_PROGRESS_UPDATE:
			Tuple<Integer, Integer> progress = (Tuple<Integer, Integer>) notification.getBody();
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					waitView.update(progress.getFirst(), progress.getSecond());
				}
			});
			break;
		case SIMULATION_RESULT:
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					waitView.getScene().getWindow().hide();
					SimulationResult result = (SimulationResult) notification.getBody();
					resultView.showSimulationResult(result);
					getFacade().sendNotification(GameNotification.SHOW_VIEW, resultView);
				}
			});
			break;
		default:
			logger.warn("Unhandled notification {} in {}", notification, getClass().getSimpleName());
			break;

		}
	}

	@Override
	public List<GameNotification> listNotificationInterests() {
		List<GameNotification> notificationInterests = new ArrayList<GameNotification>();
		notificationInterests.add(GameNotification.REPLY_DECKS);
		notificationInterests.add(GameNotification.REPLY_DECK_FORMATS);
		notificationInterests.add(GameNotification.COMMIT_SIMULATIONMODE_CONFIG);
		notificationInterests.add(GameNotification.SIMULATION_PROGRESS_UPDATE);
		notificationInterests.add(GameNotification.SIMULATION_RESULT);
		return notificationInterests;
	}

	@Override
	public void onRegister() {
		getFacade().sendNotification(GameNotification.SHOW_VIEW, view);
		getFacade().sendNotification(GameNotification.REQUEST_DECKS);
		getFacade().sendNotification(GameNotification.REQUEST_DECK_FORMATS);
	}

}
