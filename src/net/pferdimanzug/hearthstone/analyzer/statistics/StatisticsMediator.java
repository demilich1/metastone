package net.pferdimanzug.hearthstone.analyzer.statistics;

import java.util.ArrayList;
import java.util.List;

import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.playmode.PlayModeWindow;
import de.pferdimanzug.nittygrittymvc.Mediator;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

public class StatisticsMediator extends Mediator<GameNotification> {

	public static final String NAME = "StatisticsMediator";
	
	private final StatisticsView view;
	
	public StatisticsMediator() {
		super(NAME);
		view = new StatisticsView();
	}

	@Override
	public void handleNotification(INotification<GameNotification> notification) {
		switch (notification.getId()) {
		case GAME_OVER:
			view.onGameOver((GameContext) notification.getBody());
			break;
		case BATCH_START:
			view.onBatchStart();
			break;
		case BATCH_STOP:
			view.onBatchStop();
			break;
		default:
			break;
		}
	}
	
	@Override
	public List<GameNotification> listNotificationInterests() {
		List<GameNotification> notificationInterests = new ArrayList<GameNotification>();
		notificationInterests.add(GameNotification.BATCH_START);
		notificationInterests.add(GameNotification.BATCH_STOP);
		notificationInterests.add(GameNotification.GAME_OVER);
		return notificationInterests;
	}
}
