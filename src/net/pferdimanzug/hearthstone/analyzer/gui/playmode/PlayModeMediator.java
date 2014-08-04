package net.pferdimanzug.hearthstone.analyzer.gui.playmode;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.human.HumanActionOptions;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.human.HumanMulliganOptions;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.human.HumanTargetOptions;
import de.pferdimanzug.nittygrittymvc.Mediator;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

public class PlayModeMediator extends Mediator<GameNotification> {

	public static final String NAME = "PlayModeMediator";

	private final GameBoardView boardView;

	public PlayModeMediator() {
		super(NAME);
		boardView = new GameBoardView();
	}

	@Override
	public void handleNotification(final INotification<GameNotification> notification) {
		switch (notification.getId()) {
		case GAME_STATE_UPDATE:
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					boardView.updateGameState((GameContextVisualizable) notification.getBody());
				}
			});

			break;
		case GAME_ACTION_PERFORMED:
			// view.updateTurnLog((GameContext) notification.getBody());
			break;
		case HUMAN_PROMPT_FOR_ACTION:
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					new HumanActionPromptView(boardView.getScene().getWindow(), (HumanActionOptions) notification.getBody());
				}
			});
			break;
		case HUMAN_PROMPT_FOR_TARGET:
			HumanTargetOptions targetOptions = (HumanTargetOptions) notification.getBody();
			//selectTarget(targetOptions);
			break;
		case HUMAN_PROMPT_FOR_MULLIGAN:
			HumanMulliganOptions mulliganOptions = (HumanMulliganOptions) notification.getBody();
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					new HumanMulliganView(mulliganOptions);
				}
			});
			break;
		default:
			break;
		}
	}

	@Override
	public List<GameNotification> listNotificationInterests() {
		List<GameNotification> notificationInterests = new ArrayList<GameNotification>();
		// notificationInterests.add(GameNotification.CANVAS_CREATED);
		notificationInterests.add(GameNotification.GAME_STATE_UPDATE);
		notificationInterests.add(GameNotification.GAME_ACTION_PERFORMED);
		notificationInterests.add(GameNotification.HUMAN_PROMPT_FOR_ACTION);
		notificationInterests.add(GameNotification.HUMAN_PROMPT_FOR_TARGET);
		notificationInterests.add(GameNotification.HUMAN_PROMPT_FOR_MULLIGAN);
		notificationInterests.add(GameNotification.ANSWER_DECKS);
		return notificationInterests;
	}

	@Override
	public void onRegister() {
		getFacade().sendNotification(GameNotification.SHOW_VIEW, boardView);
	}

}
