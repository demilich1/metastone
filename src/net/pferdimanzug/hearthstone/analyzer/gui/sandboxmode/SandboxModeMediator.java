package net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.Player;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.human.HumanActionOptions;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.human.HumanTargetOptions;
import de.pferdimanzug.nittygrittymvc.Mediator;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

public class SandboxModeMediator extends Mediator<GameNotification> {

	public static final String NAME = "SandboxModeMediator";

	private final SandboxModeView view;

	public SandboxModeMediator() {
		super(NAME);
		view = new SandboxModeView();
	}

	@Override
	public void handleNotification(final INotification<GameNotification> notification) {

		switch (notification.getId()) {
		case GAME_STATE_UPDATE:
		case UPDATE_SANDBOX_STATE:
			GameContext context = (GameContext) notification.getBody();
			Platform.runLater(() -> view.updateSandbox(context));
			break;
		case SELECT_TARGET:
			HumanTargetOptions targetOptions = (HumanTargetOptions) notification.getBody();
			Platform.runLater(() -> view.getBoardView().enableTargetSelection(targetOptions));
			break;
		case HUMAN_PROMPT_FOR_ACTION:
			HumanActionOptions actionOptions = (HumanActionOptions) notification.getBody();
			Platform.runLater(() -> view.getActionPromptView().setActions(actionOptions));
			break;
		case HUMAN_PROMPT_FOR_TARGET:
			HumanTargetOptions options = (HumanTargetOptions) notification.getBody();
			Platform.runLater(() -> view.getBoardView().enableTargetSelection(options));
			break;
		case SELECT_PLAYER:
			view.onPlayerSelectionChanged((Player) notification.getBody());
			break;
		default:
			break;
		}
	}

	@Override
	public List<GameNotification> listNotificationInterests() {
		List<GameNotification> notificationInterests = new ArrayList<GameNotification>();
		notificationInterests.add(GameNotification.UPDATE_SANDBOX_STATE);
		notificationInterests.add(GameNotification.SELECT_TARGET);
		notificationInterests.add(GameNotification.HUMAN_PROMPT_FOR_ACTION);
		notificationInterests.add(GameNotification.HUMAN_PROMPT_FOR_TARGET);
		notificationInterests.add(GameNotification.GAME_STATE_UPDATE);
		notificationInterests.add(GameNotification.SELECT_PLAYER);
		return notificationInterests;
	}

	@Override
	public void onRegister() {
		getFacade().sendNotification(GameNotification.SHOW_VIEW, view);
		getFacade().sendNotification(GameNotification.CREATE_NEW_SANDBOX);
	}

}
