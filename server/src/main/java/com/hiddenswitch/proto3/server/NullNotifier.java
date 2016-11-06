package com.hiddenswitch.proto3.server;

import net.demilich.metastone.GameNotification;
import net.demilich.nittygrittymvc.interfaces.INotification;
import net.demilich.nittygrittymvc.interfaces.INotifier;

public class NullNotifier implements INotifier<GameNotification> {
	@Override
	public void notifyObservers(INotification<GameNotification> iNotification) {
	}

	@Override
	public void sendNotification(GameNotification gameNotification) {
	}

	@Override
	public void sendNotification(GameNotification gameNotification, Object o) {
	}
}
