package net.pferdimanzug.hearthstone.analyzer;

import de.pferdimanzug.nittygrittymvc.Facade;
import de.pferdimanzug.nittygrittymvc.interfaces.IFacade;

public class ApplicationFacade extends Facade<GameNotification> {
	
	@SuppressWarnings("unchecked")
	public static IFacade<GameNotification> getInstance() {
		if (instance == null) {
			instance = new ApplicationFacade();
		}

		return instance;
	}

	public ApplicationFacade() {
		registerCommand(GameNotification.APPLICATION_STARTUP, new ApplicationStartupCommand());
		registerCommand(GameNotification.START_GAME, new StartGameCommand());
		
	}

	public void startUp(HearthstoneAnalyzer main) {
		sendNotification(GameNotification.APPLICATION_STARTUP);
	}


}

