package net.demilich.metastone.gui.playmode.animation;

import net.demilich.nittygrittymvc.SimpleCommand;
import net.demilich.nittygrittymvc.interfaces.INotification;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.game.visuals.GameContextVisuals;

public class AnimationLockCommand extends SimpleCommand<GameNotification> {

	@Override
	public void execute(INotification<GameNotification> notification) {
		AnimationProxy animationProxy = (AnimationProxy) getFacade().retrieveProxy(AnimationProxy.NAME);

		GameContextVisuals contextVisualizable = (GameContextVisuals) notification.getBody();
		animationProxy.setContext(contextVisualizable);
	}

}
