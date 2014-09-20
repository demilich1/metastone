package net.pferdimanzug.hearthstone.analyzer.gui.trainingmode;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import net.pferdimanzug.hearthstone.analyzer.gui.mainmenu.MainMenuMediator;
import de.pferdimanzug.nittygrittymvc.Mediator;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

public class TrainingModeMediator extends Mediator<GameNotification> {

	public static final String NAME = "TrainingModeMediator";

	private final TrainingModeView view;

	public TrainingModeMediator() {
		super(NAME);
		view = new TrainingModeView();
	}

	@Override
	public void handleNotification(final INotification<GameNotification> notification) {
		switch (notification.getId()) {
		case TRAINING_PROGRESS_UPDATE:
			TrainingProgressReport progress = (TrainingProgressReport) notification.getBody();
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					view.showProgress(progress);
				}
			});
			
			break;
		case COMMIT_TRAININGMODE_CONFIG:
			view.startTraining();
			getFacade().sendNotification(GameNotification.START_TRAINING, notification.getBody());
			break;
		default:
			break;
		}
		getFacade().removeMediator(MainMenuMediator.NAME);
	}

	@Override
	public List<GameNotification> listNotificationInterests() {
		List<GameNotification> notificationInterests = new ArrayList<GameNotification>();
		notificationInterests.add(GameNotification.TRAINING_PROGRESS_UPDATE);
		notificationInterests.add(GameNotification.COMMIT_TRAININGMODE_CONFIG);
		return notificationInterests;
	}

	@Override
	public void onRegister() {
		getFacade().sendNotification(GameNotification.SHOW_VIEW, view);
		getFacade().sendNotification(GameNotification.REQUEST_DECKS);
	}

}
