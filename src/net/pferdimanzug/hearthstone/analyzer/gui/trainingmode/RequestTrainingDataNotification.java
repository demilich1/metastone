package net.pferdimanzug.hearthstone.analyzer.gui.trainingmode;

import net.pferdimanzug.hearthstone.analyzer.GameNotification;
import de.pferdimanzug.nittygrittymvc.Notification;

public class RequestTrainingDataNotification extends Notification<GameNotification> {

	private final String deckName;
	private final ITrainingDataListener listener;

	public RequestTrainingDataNotification(String deckName, ITrainingDataListener listener) {
		super(GameNotification.REQUEST_TRAINING_DATA);
		this.deckName = deckName;
		this.listener = listener;
	}

	public String getDeckName() {
		return deckName;
	}

	public ITrainingDataListener getListener() {
		return listener;
	}

}
