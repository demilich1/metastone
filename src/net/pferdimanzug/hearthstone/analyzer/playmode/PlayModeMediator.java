package net.pferdimanzug.hearthstone.analyzer.playmode;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.game.behaviour.human.HumanTargetOptions;
import net.pferdimanzug.hearthstone.analyzer.game.cards.concrete.neutral.HarvestGolem;
import net.pferdimanzug.hearthstone.analyzer.game.entities.Entity;
import net.pferdimanzug.hearthstone.analyzer.game.entities.minions.Minion;
import de.pferdimanzug.nittygrittymvc.Mediator;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

public class PlayModeMediator extends Mediator<GameNotification> {

	public static final String NAME = "PlayModeMediator";

	private final PlayModePane view;

	public PlayModeMediator() {
		super(NAME);
		view = new PlayModePane();
	}
	
	@Override
	public List<GameNotification> listNotificationInterests() {
		List<GameNotification> notificationInterests = new ArrayList<GameNotification>();
		notificationInterests.add(GameNotification.CANVAS_CREATED);
		notificationInterests.add(GameNotification.GAME_STATE_UPDATE);
		notificationInterests.add(GameNotification.GAME_ACTION_PERFORMED);
		notificationInterests.add(GameNotification.HUMAN_PROMPT_FOR_ACTION);
		notificationInterests.add(GameNotification.HUMAN_PROMPT_FOR_TARGET);
		return notificationInterests;
	}

	@Override
	public void handleNotification(final INotification<GameNotification> notification) {
		switch (notification.getId()) {
		case CANVAS_CREATED:
			Pane parent = (Pane) notification.getBody();
			parent.getChildren().add(view);
			break;
		case GAME_STATE_UPDATE:
			 Platform.runLater(new Runnable() {
                 @Override public void run() {
                	 view.updateGameState((GameContextVisualizable) notification.getBody());
                 }
             });
			
			break;
		case GAME_ACTION_PERFORMED:
			//view.updateTurnLog((GameContext) notification.getBody());
			break;
		case HUMAN_PROMPT_FOR_ACTION:
			//new HumanActionPromptDialog(view, (HumanActionOptions) notification.getBody());
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
//		Entity selectedTarget = (Entity) JOptionPane.showInputDialog(view, "Select a target", "Select a target",
//				JOptionPane.PLAIN_MESSAGE, null, targetOptions.getAction().getValidTargets().toArray(), null);
//		targetOptions.getBehaviour().setSelectedTarget(selectedTarget);
	}

	

}
