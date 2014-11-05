package net.pferdimanzug.hearthstone.analyzer.gui.playmode.config;

import java.util.ArrayList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.decks.Deck;
import net.pferdimanzug.hearthstone.analyzer.gui.gameconfig.GameConfig;
import net.pferdimanzug.hearthstone.analyzer.gui.playmode.PlayModeMediator;
import de.pferdimanzug.nittygrittymvc.Mediator;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

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
			GameConfig gameConfig = (GameConfig) notification.getBody();
			getFacade().sendNotification(GameNotification.START_GAME, gameConfig);
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
