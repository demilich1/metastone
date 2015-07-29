package net.demilich.metastone.gui.playmode.config;

import java.util.ArrayList;
import java.util.List;

import de.pferdimanzug.nittygrittymvc.Mediator;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.game.decks.Deck;
import net.demilich.metastone.gui.gameconfig.GameConfig;
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
		notificationInterests.add(GameNotification.COMMIT_PLAYMODE_CONFIG);
		return notificationInterests;
	}

	@Override
	public void onRegister() {
		getFacade().sendNotification(GameNotification.SHOW_VIEW, view);
		getFacade().sendNotification(GameNotification.REQUEST_DECKS);
	}

}
