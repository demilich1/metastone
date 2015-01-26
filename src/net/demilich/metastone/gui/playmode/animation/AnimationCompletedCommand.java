package net.demilich.metastone.gui.playmode.animation;

import net.demilich.metastone.GameNotification;
import de.pferdimanzug.nittygrittymvc.SimpleCommand;
import de.pferdimanzug.nittygrittymvc.interfaces.INotification;

public class AnimationCompletedCommand extends SimpleCommand<GameNotification> {

	@Override
	public void execute(INotification<GameNotification> notification) {
		AnimationProxy animationProxy = (AnimationProxy) getFacade().retrieveProxy(AnimationProxy.NAME);
		animationProxy.animationCompleted();
	}

}
