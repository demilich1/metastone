package net.pferdimanzug.hearthstone.analyzer;

import net.pferdimanzug.hearthstone.analyzer.playmode.PlayModeMediator;
import de.pferdimanzug.nittygrittymvc.SimpleCommand;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

public class ApplicationStartupCommand extends SimpleCommand<GameNotification> {

	@Override
	public void execute(INotification<GameNotification> notification) {

		getFacade().registerMediator(new PlayModeMediator());
	}

}
