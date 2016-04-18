package net.demilich.metastone.gui.trainingmode;

import net.demilich.metastone.trainingmode.ITrainingDataListener;
import net.demilich.metastone.trainingmode.RequestTrainingDataNotification;
import net.demilich.metastone.trainingmode.TrainingData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.demilich.nittygrittymvc.SimpleCommand;
import net.demilich.nittygrittymvc.interfaces.INotification;
import net.demilich.metastone.GameNotification;

public class RequestTrainingDataCommand extends SimpleCommand<GameNotification> {

	private static Logger logger = LoggerFactory.getLogger(RequestTrainingDataCommand.class);

	@Override
	public void execute(INotification<GameNotification> notification) {
		RequestTrainingDataNotification trainingDataNotification = (RequestTrainingDataNotification) notification;
		TrainingProxy trainingProxy = (TrainingProxy) getFacade().retrieveProxy(TrainingProxy.NAME);
		TrainingData trainingData = trainingProxy.getTrainingData(trainingDataNotification.getDeckName());
		if (trainingData == null) {
			logger.debug("No training data found for " + trainingDataNotification.getDeckName());
		} else {
			logger.debug("Training data found for " + trainingDataNotification.getDeckName());
		}

		ITrainingDataListener listener = trainingDataNotification.getListener();
		listener.answerTrainingData(trainingData);
	}

}
