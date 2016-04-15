package net.demilich.metastone.gui.playmode.config;

import java.util.ArrayList;
import java.util.List;

import net.demilich.nittygrittymvc.Mediator;
import net.demilich.nittygrittymvc.interfaces.INotification;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.game.decks.DeckFormat;
import net.demilich.metastone.game.gameconfig.GameConfig;
import net.demilich.metastone.gui.playmode.PlayModeMediator;

public class PlayModeConfigMediator extends Mediator<GameNotification> {

	public static final String NAME = "PlayModeConfigMediator";

	private final PlayModeConfigView view;

	public PlayModeConfigMediator() {
		super(NAME);
		view = new PlayModeConfigView();
	}

	@Override
	public void handleNotification(final INotification<GameNotification> notification) {
		switch (notification.getId()) {
		case REPLY_DECKS:
			@SuppressWarnings("unchecked")
			List<Deck> decks = (List<Deck>) notification.getBody();
			view.injectDecks(decks);
			break;
		case REPLY_DECK_FORMATS:
			@SuppressWarnings("unchecked")
			List<DeckFormat> deckFormats = (List<DeckFormat>) notification.getBody();
			view.injectDeckFormats(deckFormats);
			break;
		case COMMIT_PLAYMODE_CONFIG:
			getFacade().registerMediator(new PlayModeMediator());
			new Thread(new Runnable() {

				@Override
				public void run() {
					GameConfig gameConfig = (GameConfig) notification.getBody();
					getFacade().sendNotification(GameNotification.START_GAME, gameConfig);
				}
			}).start();
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
		notificationInterests.add(GameNotification.COMMIT_PLAYMODE_CONFIG);
		return notificationInterests;
	}

	@Override
	public void onRegister() {
		getFacade().sendNotification(GameNotification.SHOW_VIEW, view);
		getFacade().sendNotification(GameNotification.REQUEST_DECKS);
		getFacade().sendNotification(GameNotification.REQUEST_DECK_FORMATS);
	}

}
