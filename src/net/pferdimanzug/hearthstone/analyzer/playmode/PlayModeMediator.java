package net.pferdimanzug.hearthstone.analyzer.playmode;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.GameContext;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.human.HumanActionOptions;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.human.HumanTargetOptions;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
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
		case GAME_ACTION_PERFORMED:
			view.updateTurnLog((GameContext) notification.getBody());
			break;
		case HUMAN_PROMPT_FOR_ACTION:
			new HumanActionPromptDialog(view, (HumanActionOptions) notification.getBody());
			break;
		case HUMAN_PROMPT_FOR_TARGET:
			HumanTargetOptions targetOptions =(HumanTargetOptions) notification.getBody();
			selectTarget(targetOptions);
			break;
		default:
			break;
		}
	}
	
	private void selectTarget(HumanTargetOptions targetOptions) {
		List<Entity> validTargets = targetOptions.getAction().getValidTargets();
		if (validTargets.size() == 1) {
			targetOptions.getBehaviour().setSelectedTarget(validTargets.get(0));
			return;
		}
		// open new window for target selection
		Entity selectedTarget = (Entity) JOptionPane.showInputDialog(view, "Select a target", "Select a target",
				JOptionPane.PLAIN_MESSAGE, null, targetOptions.getAction().getValidTargets().toArray(), null);
		targetOptions.getBehaviour().setSelectedTarget(selectedTarget);
	}

	@Override
	public List<GameNotification> listNotificationInterests() {
		List<GameNotification> notificationInterests = new ArrayList<GameNotification>();
		notificationInterests.add(GameNotification.GAME_STATE_UPDATE);
		notificationInterests.add(GameNotification.GAME_ACTION_PERFORMED);
		notificationInterests.add(GameNotification.HUMAN_PROMPT_FOR_ACTION);
		notificationInterests.add(GameNotification.HUMAN_PROMPT_FOR_TARGET);
		return notificationInterests;
	}

}
