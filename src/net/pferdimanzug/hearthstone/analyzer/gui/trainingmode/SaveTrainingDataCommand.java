package net.pferdimanzug.hearthstone.analyzer.gui.trainingmode;

import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import de.pferdimanzug.nittygrittymvc.SimpleCommand;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

public class SaveTrainingDataCommand extends SimpleCommand<GameNotification> {

	@Override
	public void execute(INotification<GameNotification> notification) {
		TrainingData trainingData = (TrainingData) notification.getBody();
		
		TrainingProxy trainingProxy = (TrainingProxy) getFacade().retrieveProxy(TrainingProxy.NAME);
		trainingProxy.saveTrainingData(trainingData);;
	}

}
