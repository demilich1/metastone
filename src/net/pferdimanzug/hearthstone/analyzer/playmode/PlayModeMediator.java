package net.pferdimanzug.hearthstone.analyzer.playmode;

import java.util.ArrayList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.human.HumanActionOptions;
import de.pferdimanzug.nittygrittymvc.Mediator;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

public class PlayModeMediator extends Mediator<GameNotification> {

	public static final String NAME = "PlayModeMediator";
	
	private final PlayModeWindow view;
	
	public PlayModeMediator() {
		super(NAME);
		view = new PlayModeWindow();
	}

	@Override
	public void handleNotification(INotification<GameNotification> notification) {
		switch (notification.getId()) {
		case GAME_STATE_UPDATE:
			view.update((GameContext) notification.getBody());
			break;
		case HUMAN_PROMPT_FOR_ACTION:
			new HumanActionPromptDialog(view, (HumanActionOptions) notification.getBody());
			break;
		default:
			break;
		}
	}
	
	@Override
	public List<GameNotification> listNotificationInterests() {
		List<GameNotification> notificationInterests = new ArrayList<GameNotification>();
		notificationInterests.add(GameNotification.GAME_STATE_UPDATE);
		notificationInterests.add(GameNotification.HUMAN_PROMPT_FOR_ACTION);
		return notificationInterests;
	}

	
	
}
