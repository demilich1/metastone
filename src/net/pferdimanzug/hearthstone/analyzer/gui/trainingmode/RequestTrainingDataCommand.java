package net.pferdimanzug.hearthstone.analyzer.gui.trainingmode;

import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import de.pferdimanzug.nittygrittymvc.SimpleCommand;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

public class RequestTrainingDataCommand extends SimpleCommand<GameNotification> {

	@Override
	public void execute(INotification<GameNotification> notification) {
		RequestTrainingDataNotification trainingDataNotification = (RequestTrainingDataNotification) notification;
		TrainingProxy trainingProxy = (TrainingProxy) getFacade().retrieveProxy(TrainingProxy.NAME);
		TrainingData trainingData = trainingProxy.getTrainingData(trainingDataNotification.getDeckName());

		ITrainingDataListener listener = trainingDataNotification.getListener();
		listener.answerTrainingData(trainingData);
	}

}
