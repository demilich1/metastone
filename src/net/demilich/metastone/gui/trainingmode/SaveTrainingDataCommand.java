package net.demilich.metastone.gui.trainingmode;

import de.pferdimanzug.nittygrittymvc.SimpleCommand;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;
import net.demilich.metastone.GameNotification;

public class SaveTrainingDataCommand extends SimpleCommand<GameNotification> {

	@Override
	public void execute(INotification<GameNotification> notification) {
		TrainingData trainingData = (TrainingData) notification.getBody();

		TrainingProxy trainingProxy = (TrainingProxy) getFacade().retrieveProxy(TrainingProxy.NAME);
		trainingProxy.saveTrainingData(trainingData);
		;
	}

}
