package net.pferdimanzug.hearthstone.analyzer.gui.playmode.config;

import java.util.ArrayList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.decks.Deck;
import de.pferdimanzug.nittygrittymvc.Mediator;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

public class PlayModeConfigMediator extends Mediator<GameNotification> {

	public static final String NAME = "PlayModeConfigMediator";

	private final PlayModeSetupView view;

	public PlayModeConfigMediator() {
		super(NAME);
		view = new PlayModeSetupView();
	}
	
	@Override
	public void onRegister() {
		getFacade().sendNotification(GameNotification.SHOW_VIEW, view);
		getFacade().sendNotification(GameNotification.REQUEST_DECKS);
	}

	@Override
	public void handleNotification(final INotification<GameNotification> notification) {
		switch (notification.getId()) {
		case ANSWER_DECKS:
			@SuppressWarnings("unchecked")
			List<Deck> decks = (List<Deck>) notification.getBody();
			view.injectDecks(decks);
			break;
		default:
			break;
		}
	}

	@Override
	public List<GameNotification> listNotificationInterests() {
		List<GameNotification> notificationInterests = new ArrayList<GameNotification>();
		notificationInterests.add(GameNotification.ANSWER_DECKS);
		return notificationInterests;
	}

}
