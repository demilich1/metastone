package net.demilich.metastone.gui.sandboxmode.commands;

import net.demilich.nittygrittymvc.SimpleCommand;
import net.demilich.nittygrittymvc.interfaces.INotification;
import net.demilich.metastone.GameNotification;
import net.demilich.metastone.game.GameContext;
import net.demilich.metastone.gui.sandboxmode.SandboxProxy;

public class StopPlaySandboxCommand extends SimpleCommand<GameNotification> {

	@Override
	public void execute(INotification<GameNotification> notification) {
		SandboxProxy sandboxProxy = (SandboxProxy) getFacade().retrieveProxy(SandboxProxy.NAME);

		GameContext context = sandboxProxy.getSandbox();
		context.setIgnoreEvents(true);

		getFacade().sendNotification(GameNotification.UPDATE_SANDBOX_STATE, context);
	}

}
