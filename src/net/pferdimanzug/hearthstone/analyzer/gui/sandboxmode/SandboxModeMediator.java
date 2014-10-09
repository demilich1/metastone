package net.pferdimanzug.hearthstone.analyzer.gui.sandboxmode;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
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
		case UPDATE_SANDBOX_STATE:
			GameContext context = (GameContext) notification.getBody();
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					view.updateSandbox(context);
				}
			});
			break;
		case SELECT_TARGET:
			HumanTargetOptions targetOptions = (HumanTargetOptions) notification.getBody();
			view.getBoardView().enableTargetSelection(targetOptions);
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
		return notificationInterests;
	}

	@Override
	public void onRegister() {
		getFacade().sendNotification(GameNotification.SHOW_VIEW, view);
		getFacade().sendNotification(GameNotification.CREATE_NEW_SANDBOX);
	}

}
