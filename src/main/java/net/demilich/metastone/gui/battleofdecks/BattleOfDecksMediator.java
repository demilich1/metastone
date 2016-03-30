package net.demilich.metastone.gui.battleofdecks;

import java.util.ArrayList;
import java.util.List;

import net.demilich.nittygrittymvc.Mediator;
import net.demilich.nittygrittymvc.interfaces.INotification;
import javafx.application.Platform;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.decks.DeckFormat;

public class BattleOfDecksMediator extends Mediator<GameNotification> {

	public final static String NAME = "BattleOfDecksMediator";

	private final BattleOfDecksConfigView configView;
	private final BattleOfDecksResultView resultView;

	public BattleOfDecksMediator() {
		super(NAME);
		configView = new BattleOfDecksConfigView();
		resultView = new BattleOfDecksResultView();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void handleNotification(final INotification<GameNotification> notification) {
		switch (notification.getId()) {
		case REPLY_DECKS:
			configView.injectDecks((List<Deck>) notification.getBody());
			break;
		case REPLY_DECK_FORMATS:
			configView.injectDeckFormats((List<DeckFormat>) notification.getBody());
			break;
		case BATTLE_OF_DECKS_PROGRESS_UPDATE:
			final BattleResult result = (BattleResult) notification.getBody();
			Platform.runLater(() -> resultView.updateResults(result));
			break;
		case COMMIT_BATTLE_OF_DECKS_CONFIG:
			sendNotification(GameNotification.SHOW_VIEW, resultView);
			break;
		default:
			break;
		}
	}

	@Override
	public List<GameNotification> listNotificationInterests() {
		List<GameNotification> notificationInterests = new ArrayList<GameNotification>();
		notificationInterests.add(GameNotification.REPLY_DECKS);
		notificationInterests.add(GameNotification.REPLY_DECK_FORMATS);
		notificationInterests.add(GameNotification.BATTLE_OF_DECKS_PROGRESS_UPDATE);
		notificationInterests.add(GameNotification.COMMIT_BATTLE_OF_DECKS_CONFIG);
		return notificationInterests;
	}

	@Override
	public void onRegister() {
		sendNotification(GameNotification.SHOW_VIEW, configView);
		sendNotification(GameNotification.REQUEST_DECKS);
		sendNotification(GameNotification.REQUEST_DECK_FORMATS);
	}

}
